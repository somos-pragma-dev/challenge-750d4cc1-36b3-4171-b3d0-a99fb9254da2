package com.example.reconciliation.infrastructure.messaging;

import com.example.reconciliation.application.services.ReconciliationService;
import com.example.reconciliation.domain.model.Movement;
import com.example.reconciliation.domain.model.Movement.SourceType;
import com.example.reconciliation.domain.model.Movement.MovementType;
import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.domain.model.Reconciliation.Status;
import com.example.reconciliation.infrastructure.monitoring.LagMonitor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class KafkaMovementConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaMovementConsumer.class);
    private static final String MOVEMENTS_TOPIC = "banking.movements";
    private static final String MOVEMENTS_GROUP = "reconciliation-processor";

    private final ReconciliationService reconciliationService;
    private final LagMonitor lagMonitor;
    private final ObjectMapper objectMapper;

    public KafkaMovementConsumer(
            final ReconciliationService reconciliationService,
            final LagMonitor lagMonitor,
            final ObjectMapper objectMapper) {
        this.reconciliationService = reconciliationService;
        this.lagMonitor = lagMonitor;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = MOVEMENTS_TOPIC,
            groupId = MOVEMENTS_GROUP,
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeMovement(final String message) {
        log.debug("Mensaje recibido del topic {}: {}", MOVEMENTS_TOPIC, message);

        Mono.fromCallable(() -> parseMovement(message))
            .flatMap(this::processMovement)
            .subscribe(
                result -> log.info("Movimiento procesado exitosamente: {}", result.getId()),
                error -> log.error("Error al procesar movimiento: {}", error.getMessage(), error)
            );
    }

    @RetryableTopic(
            attempts = "4",
            backoff = @Backoff(delay = 1000, multiplier = 2.0, maxDelay = 10000),
            dltStrategy = DltStrategy.FAIL_ON_ERROR,
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
            include = {Exception.class}
    )
    @KafkaListener(
            topics = MOVEMENTS_TOPIC,
            groupId = MOVEMENTS_GROUP + "-retry",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeMovementWithRetry(final String message) {
        consumeMovement(message);
    }

    private Movement parseMovement(final String json) {
        try {
            final JsonNode node = objectMapper.readTree(json);
            final String eventId = node.get("eventId").asText();
            final int version = node.get("version").asInt();
            final String sourceTypeStr = node.get("sourceType").asText();
            final String movementTypeStr = node.has("movementType") 
                    ? node.get("movementType").asText() 
                    : "CREDIT";
            final BigDecimal amount = new BigDecimal(node.get("amount").asText());
            final String currency = node.get("currency").asText();
            final String reference = node.get("reference").asText();
            final String accountNumber = node.has("accountNumber") 
                    ? node.get("accountNumber").asText() 
                    : "";
            final Instant timestamp = Instant.parse(node.get("timestamp").asText());

            final Map<String, String> metadata = new HashMap<>();
            if (node.has("metadata")) {
                final JsonNode metadataNode = node.get("metadata");
                metadataNode.fields().forEachRemaining(entry -> 
                        metadata.put(entry.getKey(), entry.getValue().asText()));
            }

            return Movement.create(
                    eventId,
                    version,
                    SourceType.valueOf(sourceTypeStr),
                    MovementType.valueOf(movementTypeStr),
                    amount,
                    currency,
                    reference,
                    accountNumber,
                    timestamp,
                    metadata
            );
        } catch (Exception e) {
            log.error("Error al parsear movimiento: {}", e.getMessage());
            throw new IllegalArgumentException("Formato de mensaje inválido", e);
        }
    }

    private Mono<Movement> processMovement(final Movement movement) {
        log.info("Procesando movimiento: eventId={}, sourceType={}, amount={}",
                movement.getEventId(), movement.getSourceType(), movement.getAmount());

        lagMonitor.recordMovementReceived(movement.getReceivedAt());

        return reconciliationService.initiateReconciliation(movement)
            .flatMap(reconciliation -> {
                if (reconciliation.getStatus() == Status.PENDING) {
                    return reconciliationService.processMatching(reconciliation.getId(), java.util.Collections.emptyList());
                }
                return Mono.just(reconciliation);
            });
    }

    @org.springframework.kafka.annotation.KafkaListener(
            topics = "banking.movements.dlt",
            groupId = MOVEMENTS_GROUP + "-dlt",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleDeadLetter(final String message) {
        log.error("Mensaje enviado a DLT después de agotar reintentos: {}", message);
        try {
            final Movement movement = parseMovement(message);
            lagMonitor.recordDltMessage(movement.getEventId());
        } catch (final Exception e) {
            log.error("No se pudo parsear mensaje de DLT: {}", e.getMessage());
        }
    }

    public void triggerReplay(final String eventId, final int version) {
        log.info("Iniciando replay para eventId={}, version={}", eventId, version);
        reconciliationService.retryReconciliation(
                UUID.nameUUIDFromBytes((eventId + "-" + version).getBytes()))
            .subscribe(
                result -> log.info("Replay completado: {}", result.getId()),
                error -> log.error("Error en replay: {}", error.getMessage())
            );
    }

    public Flux<Movement> consumeMovementStream() {
        log.warn("Método consumeMovementStream no implementado - usar consumeMovement");
        return Flux.empty();
    }
}