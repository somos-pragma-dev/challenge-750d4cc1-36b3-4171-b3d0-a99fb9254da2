package com.example.reconciliation.application.services;

import com.example.reconciliation.application.usecases.ReconcileMovementUseCase;
import com.example.reconciliation.domain.model.Movement;
import com.example.reconciliation.domain.model.Movement.SourceType;
import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.domain.model.Reconciliation.Status;
import com.example.reconciliation.domain.repository.ReconciliationRepository;
import com.example.reconciliation.infrastructure.monitoring.LagMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
public class ReconciliationService {

    private static final Logger log = LoggerFactory.getLogger(ReconciliationService.class);
    private static final int MATCHING_WINDOW_MINUTES = 5;
    private static final int MANUAL_ESCALATION_THRESHOLD_HOURS = 24;

    private final ReconciliationRepository reconciliationRepository;
    private final ReconcileMovementUseCase reconcileMovementUseCase;
    private final LagMonitor lagMonitor;

    public ReconciliationService(
            ReconciliationRepository reconciliationRepository,
            ReconcileMovementUseCase reconcileMovementUseCase,
            LagMonitor lagMonitor) {
        this.reconciliationRepository = reconciliationRepository;
        this.reconcileMovementUseCase = reconcileMovementUseCase;
        this.lagMonitor = lagMonitor;
    }

    public Mono<Reconciliation> initiateReconciliation(final Movement movement) {
        log.info("Initiating reconciliation for movement: {}", movement.getEventId());
        return createNewReconciliation(movement);
    }

    private Mono<Reconciliation> createNewReconciliation(final Movement movement) {
        final Reconciliation reconciliation = Reconciliation.create(
            movement.getEventId(),
            movement.getVersion(),
            movement.getSourceType().name(),
            "TARGET",
            movement.getAmount(),
            movement.getCurrency(),
            movement.getReference(),
            "Auto-reconciliation initiated"
        );
        return reconciliationRepository.save(reconciliation)
            .doOnSuccess(r -> log.info("Reconciliation created with id: {}", r.getId()));
    }

    public Mono<Reconciliation> processMatching(final UUID reconciliationId,
                                                 final Movement movement) {
        return reconciliationRepository.findById(reconciliationId)
            .flatMap(opt -> {
                if (opt.isEmpty()) {
                    return Mono.empty();
                }
                final Reconciliation reconciliation = opt.get();
                return performMatching(reconciliation, movement);
            });
    }

    private Mono<Reconciliation> performMatching(final Reconciliation reconciliation,
                                                  final Movement movement) {
        final boolean amountMatch = reconciliation.getAmount().compareTo(movement.getAmount()) == 0;
        final boolean referenceMatch = reconciliation.getReference().equals(movement.getReference());

        if (amountMatch && referenceMatch) {
            reconciliation.match();
            return reconciliationRepository.save(reconciliation)
                .doOnSuccess(r -> log.info("Reconciliation {} matched", r.getId()));
        } else {
            final String reason = buildMismatchReason(amountMatch, referenceMatch);
            reconciliation.mismatch(reason);
            return reconciliationRepository.save(reconciliation)
                .doOnSuccess(r -> log.info("Reconciliation {} mismatched: {}", r.getId(), reason));
        }
    }

    private boolean verifyAllMovementsMatch(final Reconciliation reconciliation,
                                            final java.util.List<Movement> movements) {
        return movements.stream()
            .allMatch(m -> m.getAmount().compareTo(reconciliation.getAmount()) == 0);
    }

    public Mono<Reconciliation> handleMismatch(final Reconciliation reconciliation,
                                               final String reason) {
        reconciliation.mismatch(reason);
        return reconciliationRepository.save(reconciliation);
    }

    public Mono<Reconciliation> escalateToManual(final UUID reconciliationId,
                                                 final String reason) {
        return reconciliationRepository.findById(reconciliationId)
            .flatMap(opt -> {
                if (opt.isEmpty()) {
                    return Mono.empty();
                }
                final Reconciliation reconciliation = opt.get();
                reconciliation.escalateToManual(reason);
                return reconciliationRepository.save(reconciliation);
            });
    }

    public Mono<Reconciliation> resolveManually(final UUID reconciliationId,
                                                final String resolution) {
        return reconciliationRepository.findById(reconciliationId)
            .flatMap(opt -> {
                if (opt.isEmpty()) {
                    return Mono.empty();
                }
                final Reconciliation reconciliation = opt.get();
                reconciliation.resolveManually(resolution);
                return reconciliationRepository.save(reconciliation);
            });
    }

    private Mono<Reconciliation> checkForExpiredReconciliation(final Reconciliation reconciliation) {
        if (reconciliation.isExpired(MANUAL_ESCALATION_THRESHOLD_HOURS * 60)) {
            reconciliation.escalateToManual("Expired - no match found within threshold");
            return reconciliationRepository.save(reconciliation);
        }
        return Mono.just(reconciliation);
    }

    public Flux<Reconciliation> findReconciliationsByStatus(final Status status) {
        return reconciliationRepository.findByStatus(status);
    }

    public Flux<Reconciliation> findPendingReconciliationsOlderThan(final Duration age) {
        final Instant cutoff = Instant.now().minus(age);
        return reconciliationRepository.findPendingOlderThan(cutoff);
    }

    public Mono<Long> countByStatus(final Status status) {
        return reconciliationRepository.countByStatus(status);
    }

    public Mono<Long> countReconciliationsInWindow(final Instant start, final Instant end) {
        return reconciliationRepository.findByCreatedAtBetween(start, end)
            .count();
    }

    public Mono<Reconciliation> retryReconciliation(final UUID reconciliationId) {
        return reconciliationRepository.findById(reconciliationId)
            .flatMap(opt -> {
                if (opt.isEmpty()) {
                    return Mono.empty();
                }
                final Reconciliation reconciliation = opt.get();
                reconciliation.mismatch("Retrying reconciliation");
                return reconciliationRepository.save(reconciliation);
            });
    }

    public Mono<Void> reprocessAllPending() {
        return reconciliationRepository.findByStatus(Status.PENDING)
            .flatMap(reconciliation -> {
                reconciliation.mismatch("Reprocessed");
                return reconciliationRepository.save(reconciliation);
            })
            .then();
    }

    public Mono<Boolean> validateReconciliationIntegrity(final UUID reconciliationId) {
        return reconciliationRepository.findById(reconciliationId)
            .map(opt -> opt.isPresent());
    }

    private String buildMismatchReason(boolean amountMatch, boolean referenceMatch) {
        if (!amountMatch && !referenceMatch) {
            return "Amount and reference mismatch";
        } else if (!amountMatch) {
            return "Amount mismatch";
        } else {
            return "Reference mismatch";
        }
    }

    public Mono<Map<String, Long>> getStatistics() {
        return reconciliationRepository.countByStatus(Status.PENDING)
            .zipWith(reconciliationRepository.countByStatus(Status.MATCHED))
            .zipWith(reconciliationRepository.countByStatus(Status.MISMATCHED))
            .zipWith(reconciliationRepository.countByStatus(Status.ESCALATED))
            .map(tuple -> {
                long pending = tuple.getT1().getT1();
                long matched = tuple.getT1().getT2();
                long mismatched = tuple.getT2().getT1();
                long escalated = tuple.getT2().getT2();
                return Map.of(
                    "PENDING", pending,
                    "MATCHED", matched,
                    "MISMATCHED", mismatched,
                    "ESCALATED", escalated
                );
            });
    }

    public Mono<Reconciliation> manualMatch(UUID id) {
        return reconciliationRepository.findById(id)
            .flatMap(opt -> {
                if (opt.isEmpty()) {
                    return Mono.empty();
                }
                final Reconciliation reconciliation = opt.get();
                reconciliation.match();
                return reconciliationRepository.save(reconciliation);
            });
    }

    public Flux<Reconciliation> searchByReference(String reference, String sourceType, String targetType) {
        Flux<Reconciliation> results = reconciliationRepository.findBySourceTypeAndTargetType(
            sourceType != null ? sourceType : "*",
            targetType != null ? targetType : "*"
        );
        
        return results.filter(r -> r.getReference().equals(reference));
    }

    public Mono<Reconciliation> reprocess(String eventId, int version) {
        return reconciliationRepository.findByEventIdAndVersion(eventId, version)
            .flatMap(opt -> {
                if (opt.isEmpty()) {
                    return Mono.empty();
                }
                final Reconciliation reconciliation = opt.get();
                reconciliation.mismatch("Reprocessed");
                return reconciliationRepository.save(reconciliation);
            });
    }
}