package com.example.reconciliation.application.usecases;

import com.example.reconciliation.domain.model.Movement;
import com.example.reconciliation.domain.model.Movement.SourceType;
import com.example.reconciliation.domain.model.Movement.MovementType;
import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.domain.model.Reconciliation.Status;
import com.example.reconciliation.domain.repository.ReconciliationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReconcileMovementUseCase - Tests de Caso de Uso")
class ReconcileMovementUseCaseTest {

    @Mock
    private ReconciliationRepository reconciliationRepository;

    private ReconcileMovementUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ReconcileMovementUseCase(reconciliationRepository);
    }

    @Nested
    @DisplayName("Escenario: Movimiento Nuevo sin Duplicado")
    class MovimientoNuevo {

        @Test
        @DisplayName("execute: procesa movimiento nuevo y crea reconciliación PENDING")
        void execute_movimientoNuevo_creaReconciliacionPending() {
            Movement movement = Movement.create(
                "evt-001", 1, SourceType.BANK_CORE, MovementType.CREDIT,
                new BigDecimal("1000.00"), "USD", "REF-001", "ACC-123",
                Instant.now(), null
            );

            when(reconciliationRepository.existsByEventIdAndVersion("evt-001", 1))
                .thenReturn(Mono.just(false));
            when(reconciliationRepository.save(any(Reconciliation.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

            StepVerifier.create(useCase.execute(movement))
                .assertNext(reconciliation -> {
                    assertThat(reconciliation.getEventId()).isEqualTo("evt-001");
                    assertThat(reconciliation.getStatus()).isEqualTo(Status.PENDING);
                    assertThat(reconciliation.getAmount()).isEqualByComparingTo("1000.00");
                })
                .verifyComplete();

            verify(reconciliationRepository).save(any(Reconciliation.class));
        }

        @Test
        @DisplayName("execute: busca movimientos equivalentes en otras fuentes")
        void execute_movimientoNuevo_buscaEnOtrasFuentes() {
            Movement movement = Movement.create(
                "evt-001", 1, SourceType.BANK_CORE, MovementType.CREDIT,
                new BigDecimal("1000.00"), "USD", "REF-001", "ACC-123",
                Instant.now(), null
            );

            when(reconciliationRepository.existsByEventIdAndVersion("evt-001", 1))
                .thenReturn(Mono.just(false));
            when(reconciliationRepository.findByStatusAndCreatedAtAfter(
                any(), any()))
                .thenReturn(Flux.empty());
            when(reconciliationRepository.save(any(Reconciliation.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

            useCase.execute(movement).block();

            verify(reconciliationRepository).findByStatusAndCreatedAtAfter(
                Status.PENDING, any());
        }
    }

    @Nested
    @DisplayName("Escenario: Movimiento Duplicado")
    class MovimientoDuplicado {

        @Test
        @DisplayName("execute: detecta duplicado por eventId+version y retorna existente")
        void execute_movimientoDuplicado_retornaExistente() {
            Movement movement = Movement.create(
                "evt-001", 1, SourceType.BANK_CORE, MovementType.CREDIT,
                new BigDecimal("1000.00"), "USD", "REF-001", "ACC-123",
                Instant.now(), null
            );

            Reconciliation existingReconciliation = Reconciliation.restore(
                UUID.randomUUID(), "evt-001", 1, "BANK_CORE", "PAYMENT_GATEWAY",
                new BigDecimal("1000.00"), "USD", "REF-001", "Duplicado",
                Status.PENDING, Instant.now()
            );

            when(reconciliationRepository.existsByEventIdAndVersion("evt-001", 1))
                .thenReturn(Mono.just(true));
            when(reconciliationRepository.findByEventIdAndVersion("evt-001", 1))
                .thenReturn(Mono.just(Optional.of(existingReconciliation)));

            StepVerifier.create(useCase.execute(movement))
                .assertNext(reconciliation -> {
                    assertThat(reconciliation.getId()).isEqualTo(existingReconciliation.getId());
                })
                .verifyComplete();

            verify(reconciliationRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Escenario: Matching Exitoso")
    class MatchingExitoso {

        @Test
        @DisplayName("execute: encuentra coincidencia y transita a MATCHED")
        void execute_encuentraCoincidencia_transitaMatched() {
            Movement movement = Movement.create(
                "evt-001", 1, SourceType.BANK_CORE, MovementType.CREDIT,
                new BigDecimal("1000.00"), "USD", "REF-001", "ACC-123",
                Instant.now(), null
            );

            Movement matchingMovement = Movement.create(
                "evt-002", 1, SourceType.PAYMENT_GATEWAY, MovementType.CREDIT,
                new BigDecimal("1000.00"), "USD", "REF-001", "ACC-123",
                Instant.now(), null
            );

            when(reconciliationRepository.existsByEventIdAndVersion("evt-001", 1))
                .thenReturn(Mono.just(false));
            when(reconciliationRepository.findByStatusAndCreatedAtAfter(
                any(), any()))
                .thenReturn(Flux.just(
                    createReconciliationWithMovement(movement),
                    createReconciliationWithMovement(matchingMovement)
                ));
            when(reconciliationRepository.save(any(Reconciliation.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

            StepVerifier.create(useCase.execute(movement))
                .assertNext(reconciliation -> {
                    assertThat(reconciliation.getStatus()).isEqualTo(Status.MATCHED);
                })
                .verifyComplete();
        }

        private Reconciliation createReconciliationWithMovement(Movement movement) {
            return Reconciliation.create(
                movement.getEventId(), movement.getVersion(),
                movement.getSourceType().name(), "TARGET",
                movement.getAmount(), movement.getCurrency(),
                movement.getReference(), movement.getReference()
            );
        }
    }

    @Nested
    @DisplayName("Escenario: Mismatch de Montos")
    class MismatchMontos {

        @Test
        @DisplayName("execute: montos diferentes genera MISMATCHED")
        void execute_montosDiferentes_generaMismatched() {
            Movement movement = Movement.create(
                "evt-001", 1, SourceType.BANK_CORE, MovementType.CREDIT,
                new BigDecimal("1000.00"), "USD", "REF-001", "ACC-123",
                Instant.now(), null
            );

            Movement differentAmount = Movement.create(
                "evt-002", 1, SourceType.PAYMENT_GATEWAY, MovementType.CREDIT,
                new BigDecimal("999.50"), "USD", "REF-001", "ACC-123",
                Instant.now(), null
            );

            when(reconciliationRepository.existsByEventIdAndVersion("evt-001", 1))
                .thenReturn(Mono.just(false));
            when(reconciliationRepository.findByStatusAndCreatedAtAfter(any(), any()))
                .thenReturn(Flux.just(
                    createReconciliationWithMovement(movement),
                    createReconciliationWithMovement(differentAmount)
                ));
            when(reconciliationRepository.save(any(Reconciliation.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

            StepVerifier.create(useCase.execute(movement))
                .assertNext(reconciliation -> {
                    assertThat(reconciliation.getStatus()).isEqualTo(Status.MISMATCHED);
                    assertThat(reconciliation.getMismatchReason()).contains("Monto");
                })
                .verifyComplete();
        }

        private Reconciliation createReconciliationWithMovement(Movement movement) {
            return Reconciliation.create(
                movement.getEventId(), movement.getVersion(),
                movement.getSourceType().name(), "TARGET",
                movement.getAmount(), movement.getCurrency(),
                movement.getReference(), movement.getReference()
            );
        }
    }

    @Nested
    @DisplayName("Escenario: Mensajes Fuera de Orden")
    class MensajesFueraDeOrden {

        @Test
        @DisplayName("execute: mensaje con timestamp anterior se procesa dentro de ventana")
        void execute_mensajeAnterior_dentroDeVentana() {
            Instant now = Instant.now();
            Instant tresMinutosAntes = now.minusSeconds(180);

            Movement oldMovement = Movement.create(
                "evt-001", 1, SourceType.BANK_CORE, MovementType.CREDIT,
                new BigDecimal("1000.00"), "USD", "REF-001", "ACC-123",
                tresMinutosAntes, null
            );

            when(reconciliationRepository.existsByEventIdAndVersion("evt-001", 1))
                .thenReturn(Mono.just(false));
            when(reconciliationRepository.findByStatusAndCreatedAtAfter(any(), any()))
                .thenReturn(Flux.empty());
            when(reconciliationRepository.save(any(Reconciliation.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

            StepVerifier.create(useCase.execute(oldMovement))
                .assertNext(reconciliation -> {
                    assertThat(reconciliation.getStatus()).isEqualTo(Status.PENDING);
                })
                .verifyComplete();
        }

        @Test
        @DisplayName("execute: mensaje muy antiguo se rechaza por ventana de tiempo")
        void execute_mensajeMuyAntiguo_fueraDeVentana() {
            Instant now = Instant.now();
            Instant unaHoraAntes = now.minusSeconds(3600);

            Movement oldMovement = Movement.create(
                "evt-001", 1, SourceType.BANK_CORE, MovementType.CREDIT,
                new BigDecimal("1000.00"), "USD", "REF-001", "ACC-123",
                unaHoraAntes, null
            );

            when(reconciliationRepository.existsByEventIdAndVersion("evt-001", 1))
                .thenReturn(Mono.just(false));
            when(reconciliationRepository.findByStatusAndCreatedAtAfter(any(), any()))
                .thenReturn(Flux.empty());
            when(reconciliationRepository.save(any(Reconciliation.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

            StepVerifier.create(useCase.execute(oldMovement))
                .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Validación de Parámetros")
    class ValidacionParametros {

        @Test
        @DisplayName("execute: movimiento null lanza excepción")
        void execute_movimientoNull_lanzaExcepcion() {
            StepVerifier.create(useCase.execute(null))
                .expectError(IllegalArgumentException.class)
                .verify();
        }

        @Test
        @DisplayName("execute: movimiento sin eventId lanza excepción")
        void execute_sinEventId_lanzaExcepcion() {
            Movement invalidMovement = Movement.restore(
                UUID.randomUUID(), null, 1, SourceType.BANK_CORE,
                MovementType.CREDIT, BigDecimal.TEN, "USD", "ref", "acc",
                Instant.now(), false
            );

            StepVerifier.create(useCase.execute(invalidMovement))
                .expectError(IllegalArgumentException.class)
                .verify();
        }
    }
}