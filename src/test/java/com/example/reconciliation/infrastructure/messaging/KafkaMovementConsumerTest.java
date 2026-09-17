package com.example.reconciliation.infrastructure.messaging;

import com.example.reconciliation.application.services.ReconciliationService;
import com.example.reconciliation.application.usecases.ReconcileMovementUseCase;
import com.example.reconciliation.domain.model.Movement;
import com.example.reconciliation.domain.model.Movement.SourceType;
import com.example.reconciliation.domain.model.Movement.MovementType;
import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.domain.model.Reconciliation.Status;
import com.example.reconciliation.infrastructure.config.KafkaConfig;
import com.example.reconciliation.infrastructure.monitoring.LagMonitor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("KafkaMovementConsumer - Tests de Integración")
class KafkaMovementConsumerTest {

    @Mock
    private ReconciliationService reconciliationService;
    
    @Mock
    private LagMonitor lagMonitor;
    
    @Mock
    private ObjectMapper objectMapper;

    private KafkaMovementConsumer consumer;

    @BeforeEach
    void setUp() {
        consumer = new KafkaMovementConsumer(reconciliationService, lagMonitor, objectMapper);
    }

    @Nested
    @DisplayName("Consumo de Mensajes")
    class ConsumoMensajes {

        @Test
        @DisplayName("consume: procesa mensaje válido y retorna reconciliación")
        void consume_mensajeValido_procesaYRetorna() throws InterruptedException {
            String jsonMessage = buildValidMovementJson();
            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<Reconciliation> capturedReconciliation = new AtomicReference<>();
            
            Movement mockMovement = Movement.create(
                "evt-001", 1, SourceType.BANK_CORE, MovementType.CREDIT,
                BigDecimal.TEN, "USD", "ref-001", "ACC-123",
                Instant.now(), java.util.Collections.emptyMap()
            );
            
            Reconciliation expectedReconciliation = Reconciliation.create(
                "evt-001", 1, "BANK_CORE", "TARGET",
                BigDecimal.TEN, "USD", "ref-001", "test"
            );

            when(objectMapper.readTree(anyString())).thenReturn(mock(com.fasterxml.jackson.databind.JsonNode.class));
            when(objectMapper.readTree(anyString()).get(anyString())).thenReturn(mock(com.fasterxml.jackson.databind.JsonNode.class));
            when(objectMapper.readTree(anyString()).get(anyString()).asText()).thenReturn("evt-001");
            when(objectMapper.readTree(anyString()).get(anyString()).asInt()).thenReturn(1);
            when(objectMapper.readTree(anyString()).get(anyString()).get(anyString())).thenReturn(null);
            
            doAnswer(invocation -> {
                com.fasterxml.jackson.databind.JsonNode mockNode = mock(com.fasterxml.jackson.databind.JsonNode.class);
                when(mockNode.asText()).thenReturn("evt-001", "1", "BANK_CORE", "CREDIT", "10.00", "USD", "ref-001", "ACC-123", Instant.now().toString());
                when(mockNode.asInt()).thenReturn(1);
                when(mockNode.has(anyString())).thenReturn(false);
                return mockNode;
            }).when(objectMapper).readTree(anyString());

            when(reconciliationService.initiateReconciliation(any(Movement.class)))
                .thenAnswer(invocation -> {
                    latch.countDown();
                    return Mono.just(expectedReconciliation);
                });

            consumer.consumeMovement(jsonMessage);

            boolean completed = latch.await(5, TimeUnit.SECONDS);
            assertThat(completed).isTrue();
            verify(reconciliationService).initiateReconciliation(any(Movement.class));
        }
    }

    @Nested
    @DisplayName("Manejo de Errores")
    class ManejoErrores {

        @Test
        @DisplayName("consume: excepción en servicio loguea error")
        void consume_excepcionEnServicio_loggeaError() throws InterruptedException {
            String jsonMessage = buildValidMovementJson();
            CountDownLatch latch = new CountDownLatch(1);

            doAnswer(invocation -> {
                com.fasterxml.jackson.databind.JsonNode mockNode = mock(com.fasterxml.jackson.databind.JsonNode.class);
                when(mockNode.asText()).thenReturn("evt-001", "1", "BANK_CORE", "CREDIT", "10.00", "USD", "ref-001", "ACC-123", Instant.now().toString());
                when(mockNode.asInt()).thenReturn(1);
                when(mockNode.asText()).thenReturn("10.00");
                when(mockNode.get(anyString())).thenReturn(mockNode);
                return mockNode;
            }).when(objectMapper).readTree(anyString());

            when(reconciliationService.initiateReconciliation(any(Movement.class)))
                .thenReturn(Mono.error(new RuntimeException("Error de base de datos")));

            consumer.consumeMovement(jsonMessage);

            latch.await(3, TimeUnit.SECONDS);
            verify(reconciliationService).initiateReconciliation(any(Movement.class));
        }
    }

    @Nested
    @DisplayName("Configuración del Consumidor")
    class ConfiguracionConsumidor {

        @Test
        @DisplayName("consume: usa el topic correcto configurado en KafkaConfig")
        void consume_topicConfigurado_correcto() {
            KafkaConfig config = new KafkaConfig();
            assertThat(config.getMovementTopic()).isEqualTo("bank.movements");
        }

        @Test
        @DisplayName("consume: grupo de consumidores configurado correctamente")
        void consume_grupoConfigurado_correcto() {
            KafkaConfig config = new KafkaConfig();
            assertThat(config.getConsumerGroupId()).isEqualTo("reconciliation-consumer-group");
        }
    }

    @Nested
    @DisplayName("Procesamiento de Múltiples Fuentes")
    class MultiplesFuentes {

        @Test
        @DisplayName("consume: procesa mensaje de BANK_CORE")
        void consume_mensajeBankCore_procesa() throws InterruptedException {
            String bankCoreMessage = buildMovementJson("evt-bank", 1,
                "BANK_CORE", new BigDecimal("5000.00"));
            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<Movement> capturedMovement = new AtomicReference<>();

            Reconciliation expectedReconciliation = Reconciliation.create(
                "evt-bank", 1, "BANK_CORE", "TARGET",
                BigDecimal.TEN, "USD", "ref", "desc"
            );

            doAnswer(invocation -> {
                com.fasterxml.jackson.databind.JsonNode mockNode = mock(com.fasterxml.jackson.databind.JsonNode.class);
                when(mockNode.asText()).thenReturn("evt-bank", "1", "BANK_CORE", "CREDIT", "5000.00", "USD", "REF-evt-bank", "ACC-123", Instant.now().toString());
                when(mockNode.asInt()).thenReturn(1);
                when(mockNode.get(anyString())).thenReturn(mockNode);
                return mockNode;
            }).when(objectMapper).readTree(anyString());

            when(reconciliationService.initiateReconciliation(any(Movement.class)))
                .thenAnswer(invocation -> {
                    capturedMovement.set(invocation.getArgument(0));
                    latch.countDown();
                    return Mono.just(expectedReconciliation);
                });

            consumer.consumeMovement(bankCoreMessage);

            latch.await(5, TimeUnit.SECONDS);
            assertThat(capturedMovement.get().getSourceType()).isEqualTo(SourceType.BANK_CORE);
        }
    }

    private String buildValidMovementJson() {
        return buildMovementJson("evt-001", 1, "BANK_CORE", new BigDecimal("1000.00"));
    }

    private String buildMovementJson(String eventId, int version, String sourceType, BigDecimal amount) {
        return String.format("""
            {
                "id": "%s",
                "eventId": "%s",
                "version": %d,
                "sourceType": "%s",
                "movementType": "CREDIT",
                "amount": %s,
                "currency": "USD",
                "reference": "REF-%s",
                "accountNumber": "ACC-123",
                "timestamp": "%s",
                "receivedAt": "%s",
                "processed": false
            }
            """,
            UUID.randomUUID(),
            eventId,
            version,
            sourceType,
            amount.toPlainString(),
            eventId,
            Instant.now(),
            Instant.now()
        );
    }
}