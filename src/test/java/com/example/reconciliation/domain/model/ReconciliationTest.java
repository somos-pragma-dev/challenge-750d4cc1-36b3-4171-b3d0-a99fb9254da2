package com.example.reconciliation.domain.model;

import com.example.reconciliation.domain.model.Reconciliation.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Reconciliation - Tests de Entidad de Dominio")
class ReconciliationTest {

    @Nested
    @DisplayName("Creación de Instancias")
    class Creacion {

        @Test
        @DisplayName("create: instancia nueva con estado PENDING")
        void create_nuevaInstancia_estadoPending() {
            Reconciliation reconciliation = Reconciliation.create(
                "evt-001", 1, "BANK_CORE", "PAYMENT_GATEWAY",
                new BigDecimal("1500.00"), "USD", "REF-001", "Pago de cliente"
            );

            assertNotNull(reconciliation.getId());
            assertEquals("evt-001", reconciliation.getEventId());
            assertEquals(1, reconciliation.getVersion());
            assertEquals(Status.PENDING, reconciliation.getStatus());
            assertEquals(new BigDecimal("1500.00"), reconciliation.getAmount());
            assertEquals("USD", reconciliation.getCurrency());
            assertNotNull(reconciliation.getCreatedAt());
        }

        @Test
        @DisplayName("restore: restaura instancia existente desde base de datos")
        void restore_instanciaExistente_datosPreservados() {
            UUID id = UUID.randomUUID();
            Instant createdAt = Instant.now().minusSeconds(3600);

            Reconciliation reconciliation = Reconciliation.restore(
                id, "evt-002", 2, "LIQUIDATION", "BANK_CORE",
                new BigDecimal("2500.50"), "EUR", "REF-002", "Liquidación",
                Status.MATCHED, createdAt
            );

            assertEquals(id, reconciliation.getId());
            assertEquals("evt-002", reconciliation.getEventId());
            assertEquals(2, reconciliation.getVersion());
            assertEquals(Status.MATCHED, reconciliation.getStatus());
            assertEquals(createdAt, reconciliation.getCreatedAt());
        }

        @Test
        @DisplayName("create: genera key de idempotencia única")
        void create_keyIdempotencia_únicaPorEventIdYVersion() {
            Reconciliation r1 = Reconciliation.create(
                "evt-001", 1, "A", "B", BigDecimal.TEN, "USD", "ref", "desc"
            );
            Reconciliation r2 = Reconciliation.create(
                "evt-001", 1, "A", "B", BigDecimal.TEN, "USD", "ref", "desc"
            );
            Reconciliation r3 = Reconciliation.create(
                "evt-001", 2, "A", "B", BigDecimal.TEN, "USD", "ref", "desc"
            );

            assertEquals(r1.getIdempotencyKey(), r2.getIdempotencyKey());
            assertNotEquals(r1.getIdempotencyKey(), r3.getIdempotencyKey());
        }
    }

    @Nested
    @DisplayName("Transiciones de Estado")
    class TransicionesEstado {

        @Test
        @DisplayName("match: transita de PENDING a MATCHED")
        void match_transicionValida_estadoMatched() {
            Reconciliation reconciliation = Reconciliation.create(
                "evt-001", 1, "A", "B", BigDecimal.TEN, "USD", "ref", "desc"
            );

            reconciliation.match();

            assertEquals(Status.MATCHED, reconciliation.getStatus());
            assertNotNull(reconciliation.getMatchedAt());
            assertNotNull(reconciliation.getUpdatedAt());
        }

        @Test
        @DisplayName("mismatch: transita de PENDING a MISMATCHED")
        void mismatch_transicionValida_estadoMismatched() {
            Reconciliation reconciliation = Reconciliation.create(
                "evt-001", 1, "A", "B", BigDecimal.TEN, "USD", "ref", "desc"
            );

            reconciliation.mismatch("Monto no coincide: esperado 100.00, recibido 99.50");

            assertEquals(Status.MISMATCHED, reconciliation.getStatus());
            assertEquals("Monto no coincide: esperado 100.00, recibido 99.50", reconciliation.getMismatchReason());
        }

        @Test
        @DisplayName("escalateToManual: transita de MISMATCHED a MANUAL")
        void escalateToManual_transicionValida_estadoManual() {
            Reconciliation reconciliation = Reconciliation.create(
                "evt-001", 1, "A", "B", BigDecimal.TEN, "USD", "ref", "desc"
            );
            reconciliation.mismatch("Diferencia detectada");

            reconciliation.escalateToManual("Requiere revisión del equipo de operaciones");

            assertEquals(Status.MANUAL, reconciliation.getStatus());
        }

        @Test
        @DisplayName("resolveManually: transita de MANUAL a MATCHED")
        void resolveManually_transicionValida_resolucionRegistrada() {
            Reconciliation reconciliation = Reconciliation.create(
                "evt-001", 1, "A", "B", BigDecimal.TEN, "USD", "ref", "desc"
            );
            reconciliation.mismatch("Diferencia");
            reconciliation.escalateToManual("Escalado");

            reconciliation.resolveManually("Confirmado: movimiento válido");

            assertEquals(Status.MATCHED, reconciliation.getStatus());
            assertEquals("Confirmado: movimiento válido", reconciliation.getManualResolution());
        }

        @Test
        @DisplayName("match: lanza excepción si estado no permite transición")
        void match_transicionInvalida_lanzaExcepcion() {
            Reconciliation reconciliation = Reconciliation.create(
                "evt-001", 1, "A", "B", BigDecimal.TEN, "USD", "ref", "desc"
            );
            reconciliation.match();

            assertThrows(IllegalStateException.class, () -> reconciliation.match());
        }

        @Test
        @DisplayName("canTransitionTo: valida transiciones permitidas")
        void canTransitionTo_transicionesValidas_retornaTrue() {
            Reconciliation reconciliation = Reconciliation.create(
                "evt-001", 1, "A", "B", BigDecimal.TEN, "USD", "ref", "desc"
            );

            assertTrue(reconciliation.canTransitionTo(Status.MATCHED));
            assertTrue(reconciliation.canTransitionTo(Status.MISMATCHED));
            assertFalse(reconciliation.canTransitionTo(Status.MANUAL));
            assertFalse(reconciliation.canTransitionTo(Status.PENDING));
        }
    }

    @Nested
    @DisplayName("Validación de Ventana de Tiempo")
    class ValidacionVentana {

        @Test
        @DisplayName("isExpired: dentro de ventana de 5 minutos retorna false")
        void isExpired_dentroVentana_retornaFalse() {
            Reconciliation reconciliation = Reconciliation.create(
                "evt-001", 1, "A", "B", BigDecimal.TEN, "USD", "ref", "desc"
            );
            Instant cuatroMinutosDespues = reconciliation.getCreatedAt().plus(4, ChronoUnit.MINUTES);

            assertFalse(reconciliation.isExpired(5));
        }

        @Test
        @DisplayName("isExpired: fuera de ventana de 5 minutos retorna true")
        void isExpired_fueraVentana_retornaTrue() {
            Reconciliation reconciliation = Reconciliation.create(
                "evt-001", 1, "A", "B", BigDecimal.TEN, "USD", "ref", "desc"
            );
            Instant seisMinutosDespues = reconciliation.getCreatedAt().plus(6, ChronoUnit.MINUTES);

            assertTrue(reconciliation.isExpired(5));
        }

        @Test
        @DisplayName("isExpired: exactamente en el límite retorna false")
        void isExpired_enLimite_retornaFalse() {
            Reconciliation reconciliation = Reconciliation.create(
                "evt-001", 1, "A", "B", BigDecimal.TEN, "USD", "ref", "desc"
            );

            assertFalse(reconciliation.isExpired(5));
        }
    }

    @Nested
    @DisplayName("Idempotencia")
    class Idempotencia {

        @Test
        @DisplayName("isIdempotentKeyUnique: mismo eventId+version es duplicado")
        void isIdempotentKeyUnique_mismoEventIdYVersion_retornaFalse() {
            Reconciliation r1 = Reconciliation.create(
                "evt-001", 1, "A", "B", BigDecimal.TEN, "USD", "ref", "desc"
            );
            Reconciliation r2 = Reconciliation.create(
                "evt-001", 1, "A", "B", BigDecimal.TEN, "USD", "ref", "desc"
            );

            assertFalse(r1.isIdempotentKeyUnique(r2));
        }

        @Test
        @DisplayName("isIdempotentKeyUnique: diferente version es único")
        void isIdempotentKeyUnique_diferenteVersion_retornaTrue() {
            Reconciliation r1 = Reconciliation.create(
                "evt-001", 1, "A", "B", BigDecimal.TEN, "USD", "ref", "desc"
            );
            Reconciliation r2 = Reconciliation.create(
                "evt-001", 2, "A", "B", BigDecimal.TEN, "USD", "ref", "desc"
            );

            assertTrue(r1.isIdempotentKeyUnique(r2));
        }

        @Test
        @DisplayName("isIdempotentKeyUnique: diferente eventId es único")
        void isIdempotentKeyUnique_diferenteEventId_retornaTrue() {
            Reconciliation r1 = Reconciliation.create(
                "evt-001", 1, "A", "B", BigDecimal.TEN, "USD", "ref", "desc"
            );
            Reconciliation r2 = Reconciliation.create(
                "evt-002", 1, "A", "B", BigDecimal.TEN, "USD", "ref", "desc"
            );

            assertTrue(r1.isIdempotentKeyUnique(r2));
        }
    }
}