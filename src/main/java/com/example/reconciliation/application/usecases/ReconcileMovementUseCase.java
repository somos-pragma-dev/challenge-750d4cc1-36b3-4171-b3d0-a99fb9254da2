package com.example.reconciliation.application.usecases;

import com.example.reconciliation.domain.event.ReconciliationEvent;
import com.example.reconciliation.domain.model.Movement;
import com.example.reconciliation.domain.model.Movement.SourceType;
import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.domain.model.Reconciliation.Status;
import com.example.reconciliation.domain.repository.ReconciliationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReconcileMovementUseCase {
    
    private static final Logger log = LoggerFactory.getLogger(ReconcileMovementUseCase.class);
    private static final int MATCHING_WINDOW_MINUTES = 5;
    private static final int MAX_MISMATCHES_BEFORE_ESCALATION = 3;
    
    private final ReconciliationRepository reconciliationRepository;
    private final MovementProvider movementProvider;
    private final ReconciliationEventPublisher eventPublisher;
    private final Map<SourceType, String> sourceToTargetMapping;
    
    public ReconcileMovementUseCase(
            ReconciliationRepository reconciliationRepository,
            MovementProvider movementProvider,
            ReconciliationEventPublisher eventPublisher) {
        this.reconciliationRepository = reconciliationRepository;
        this.movementProvider = movementProvider;
        this.eventPublisher = eventPublisher;
        this.sourceToTargetMapping = Map.of(
            SourceType.CORE_BANKING, "LIQUIDATION",
            SourceType.PAYMENT_GATEWAY, "CORE_BANKING",
            SourceType.LIQUIDATION, "PAYMENT_GATEWAY"
        );
    }
    
    public Mono<Reconciliation> execute(Movement movement) {
        log.info("Iniciando reconciliación para movimiento: eventId={}, version={}, sourceType={}",
            movement.getEventId(), movement.getVersion(), movement.getSourceType());
        
        return checkIdempotency(movement)
            .flatMap(this::fetchMovementsFromAllSources)
            .flatMap(this::performMatching)
            .flatMap(this::persistAndPublish);
    }
    
    private Mono<Movement> checkIdempotency(Movement movement) {
        String idempotencyKey = movement.getIdempotencyKey();
        log.debug("Verificando idempotencia: key={}", idempotencyKey);
        
        return reconciliationRepository.findByEventIdAndVersion(movement.getEventId(), movement.getVersion())
            .flatMap(existing -> {
                if (existing.isPresent()) {
                    log.info("Movimiento duplicado detectado, retornando existente: eventId={}, version={}",
                        movement.getEventId(), movement.getVersion());
                    return Mono.empty();
                }
                return Mono.just(movement);
            })
            .switchIfEmpty(Mono.just(movement));
    }
    
    private Mono<Map<SourceType, List<Movement>>> fetchMovementsFromAllSources(Movement movement) {
        String reference = movement.getReference();
        BigDecimal amount = movement.getAmount();
        Instant windowStart = movement.getTimestamp().minus(Duration.ofMinutes(MATCHING_WINDOW_MINUTES));
        Instant windowEnd = movement.getTimestamp().plus(Duration.ofMinutes(MATCHING_WINDOW_MINUTES));
        
        log.debug("Consultando movimientos desde todas las fuentes: reference={}, amount={}, window=[{} - {}]",
            reference, amount, windowStart, windowEnd);
        
        return Flux.fromArray(SourceType.values())
            .flatMap(source -> movementProvider.findMovements(reference, amount, windowStart, windowEnd, source)
                .collectList()
                .map(movements -> Map.entry(source, movements))
            )
            .collectMap(Map.Entry::getKey, Map.Entry::getValue)
            .doOnSuccess(allMovements -> {
                long total = allMovements.values().stream().mapToLong(List::size).sum();
                log.info("Movimientos recuperados desde todas las fuentes: total={}, por fuente={}",
                    total, allMovements.size());
            });
    }
    
    private Mono<Reconciliation> performMatching(Map<SourceType, List<Movement>> movementsBySource) {
        SourceType primarySource = movementsBySource.keySet().stream()
            .min(Comparator.comparingInt(SourceType::ordinal))
            .orElseThrow(() -> new IllegalStateException("No hay fuentes disponibles"));
        
        List<Movement> primaryMovements = movementsBySource.get(primarySource);
        
        if (primaryMovements == null || primaryMovements.isEmpty()) {
            return createMismatchReconciliation(primarySource, movementsBySource, 
                "No se encontraron movimientos en la fuente primaria");
        }
        
        Movement primaryMovement = primaryMovements.get(0);
        String targetType = sourceToTargetMapping.get(primarySource);
        
        List<Movement> targetMovements = movementsBySource.entrySet().stream()
            .filter(entry -> entry.getKey().name().equals(targetType))
            .flatMap(entry -> entry.getValue().stream())
            .collect(Collectors.toList());
        
        return matchAgainstTarget(primaryMovement, targetMovements, movementsBySource);
    }
    
    private Mono<Reconciliation> matchAgainstTarget(Movement primary, List<Movement> targets,
            Map<SourceType, List<Movement>> allMovements) {
        
        if (targets.isEmpty()) {
            return createMismatchReconciliation(primary.getSourceType(), allMovements,
                "No se encontraron movimientos en la fuente objetivo para hacer match");
        }
        
        boolean amountMatch = targets.stream()
            .anyMatch(t -> t.getAmount().compareTo(primary.getAmount()) == 0);
        
        boolean referenceMatch = targets.stream()
            .anyMatch(t -> t.getReference().equals(primary.getReference()));
        
        boolean accountMatch = targets.stream()
            .anyMatch(t -> t.getAccountNumber() != null && 
                         t.getAccountNumber().equals(primary.getAccountNumber()));
        
        if (amountMatch && referenceMatch) {
            log.info("Match encontrado para movimiento: eventId={}, reference={}",
                primary.getEventId(), primary.getReference());
            return createMatchedReconciliation(primary, targets);
        }
        
        String mismatchReason = buildMismatchReason(amountMatch, referenceMatch, accountMatch);
        return createMismatchReconciliation(primary.getSourceType(), allMovements, mismatchReason);
    }
    
    private String buildMismatchReason(boolean amountMatch, boolean referenceMatch, boolean accountMatch) {
        StringBuilder reason = new StringBuilder("Discrepancia en matching: ");
        if (!amountMatch) reason.append("monto no coincide; ");
        if (!referenceMatch) reason.append("referencia no coincide; ");
        if (!accountMatch) reason.append("cuenta no coincide; ");
        return reason.toString();
    }
    
    private Mono<Reconciliation> createMatchedReconciliation(Movement movement, List<Movement> targets) {
        Reconciliation reconciliation = Reconciliation.create(
            movement.getEventId(),
            movement.getVersion(),
            movement.getSourceType().name(),
            targets.get(0).getSourceType().name(),
            movement.getAmount(),
            movement.getCurrency(),
            movement.getReference()
        );
        reconciliation.match();
        return Mono.just(reconciliation);
    }
    
    private Mono<Reconciliation> createMismatchReconciliation(SourceType sourceType,
            Map<SourceType, List<Movement>> allMovements, String reason) {
        
        long mismatchCount = allMovements.values().stream()
            .mapToLong(List::size)
            .sum();
        
        String targetType = sourceToTargetMapping.get(sourceType);
        List<Movement> sourceMovements = allMovements.getOrDefault(sourceType, List.of());
        List<Movement> targetMovements = allMovements.entrySet().stream()
            .filter(e -> e.getKey().name().equals(targetType))
            .flatMap(e -> e.getValue().stream())
            .collect(Collectors.toList());
        
        Reconciliation reconciliation = Reconciliation.create(
            sourceMovements.isEmpty() ? "UNKNOWN" : sourceMovements.get(0).getEventId(),
            sourceMovements.isEmpty() ? 0 : sourceMovements.get(0).getVersion(),
            sourceType.name(),
            targetType,
            sourceMovements.isEmpty() ? BigDecimal.ZERO : sourceMovements.get(0).getAmount(),
            sourceMovements.isEmpty() ? "USD" : sourceMovements.get(0).getCurrency(),
            sourceMovements.isEmpty() ? "UNKNOWN" : sourceMovements.get(0).getReference()
        );
        reconciliation.mismatch(reason);
        
        if (mismatchCount >= MAX_MISMATCHES_BEFORE_ESCALATION) {
            reconciliation.escalateToManual("Máximo de intentos de reconciliación alcanzado");
            log.warn("Movimiento escalado a resolución manual: {} intentos fallidos", mismatchCount);
        }
        
        return Mono.just(reconciliation);
    }
    
    private Mono<Reconciliation> persistAndPublish(Reconciliation reconciliation) {
        return reconciliationRepository.save(reconciliation)
            .flatMap(saved -> {
                ReconciliationEvent event = buildEvent(saved);
                return eventPublisher.publish(event)
                    .thenReturn(saved);
            });
    }
    
    private ReconciliationEvent buildEvent(Reconciliation reconciliation) {
        Status status = reconciliation.getStatus();
        return switch (status) {
            case PENDING -> ReconciliationEvent.created(
                reconciliation.getId(),
                reconciliation.getEventId(),
                reconciliation.getVersion(),
                reconciliation.getSourceType(),
                reconciliation.getTargetType(),
                reconciliation.getAmount(),
                reconciliation.getCurrency(),
                reconciliation.getReference()
            );
            case MATCHED -> ReconciliationEvent.matched(
                reconciliation.getId(),
                reconciliation.getEventId(),
                reconciliation.getVersion(),
                reconciliation.getSourceType(),
                reconciliation.getTargetType(),
                reconciliation.getAmount(),
                reconciliation.getReference()
            );
            case MISMATCHED -> ReconciliationEvent.mismatched(
                reconciliation.getId(),
                reconciliation.getEventId(),
                reconciliation.getVersion(),
                reconciliation.getSourceType(),
                reconciliation.getTargetType(),
                reconciliation.getAmount(),
                reconciliation.getMismatchReason()
            );
            case MANUAL -> ReconciliationEvent.manuallyResolved(
                reconciliation.getId(),
                reconciliation.getEventId(),
                reconciliation.getVersion(),
                Status.MISMATCHED,
                reconciliation.getManualResolution()
            );
            default -> throw new IllegalStateException("Estado de reconciliación no soportado: " + status);
        };
    }
    
    public interface MovementProvider {
        Flux<Movement> findMovements(String reference, BigDecimal amount, 
            Instant windowStart, Instant windowEnd, SourceType sourceType);
    }
    
    public interface ReconciliationEventPublisher {
        Mono<Void> publish(ReconciliationEvent event);
    }
}