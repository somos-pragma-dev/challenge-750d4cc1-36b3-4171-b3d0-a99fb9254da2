package com.example.reconciliation;

import com.example.reconciliation.application.services.ReconciliationService;
import com.example.reconciliation.infrastructure.config.KafkaConfig;
import com.example.reconciliation.infrastructure.config.PostgreSQLConfig;
import com.example.reconciliation.infrastructure.messaging.KafkaMovementConsumer;
import com.example.reconciliation.infrastructure.monitoring.LagMonitor;
import com.example.reconciliation.infrastructure.rest.ReconciliationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Punto de entrada principal del sistema de conciliación bancaria en tiempo real.
 * 
 * Esta aplicación consume streams de movimientos desde tres fuentes (core bancario,
 * gateway de pagos, sistema de liquidación), detecta discrepancias en ventanas móviles
 * de 5 minutos y reconcile cada movimiento con tolerancia a mensajes fuera de orden.
 * 
 * El sistema implementa:
 * - Máquina de estados: Pending → Matched/Mismatched/Manual
 * - Idempotencia mediante eventId + version
 * - Reprocesamiento por replay de Kafka
 * - Monitoreo de lag con métricas Prometheus
 */
@SpringBootApplication
@EnableKafka
@EnableScheduling
@ConfigurationPropertiesScan
@Import({
    KafkaConfig.class,
    PostgreSQLConfig.class,
    KafkaMovementConsumer.class,
    ReconciliationService.class,
    ReconciliationController.class,
    LagMonitor.class
})
public class ReconciliationApplication {
    
    private static final Logger log = LoggerFactory.getLogger(ReconciliationApplication.class);
    
    public static void main(final String[] args) {
        log.info("Iniciando sistema de conciliación bancaria en tiempo real");
        log.info("Versión de Java: {}", System.getProperty("java.version"));
        log.info("Ventana de matching configurada: 5 minutos");
        log.info("Tolerancia a mensajes fuera de orden: habilitada");
        
        SpringApplication.run(ReconciliationApplication.class, args);
        
        log.info("Sistema de conciliación iniciado correctamente");
        log.info("Endpoints disponibles:");
        log.info("  - GET /actuator/health: Estado de salud");
        log.info("  - GET /actuator/prometheus: Métricas Prometheus");
        log.info("  - GET /api/v1/reconciliations: Lista de conciliaciones");
        log.info("  - GET /api/v1/reconciliations/{id}: Detalle de conciliación");
    }
    
    @Bean
    public ReconciliationService reconciliationService(
            final KafkaMovementConsumer kafkaConsumer,
            final LagMonitor lagMonitor) {
        log.info("Configurando servicio de conciliación");
        return new ReconciliationService(kafkaConsumer, lagMonitor);
    }
}