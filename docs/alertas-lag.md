# Sistema de Alertas para Lag de Conciliación

## Visión General

Este documento describe el mecanismo de monitoreo y alertas para detectar cuando el lag de procesamiento de conciliación supera los umbrales definidos en el SLA del sistema.

## Arquitectura de Monitoreo

El sistema utiliza las siguientes métricas de Prometheus para monitorear el lag de conciliación:

### Métricas Disponibles

```
reconciliation_lag_seconds{status="pending"}     - Tiempo promedio en cola
reconciliation_lag_seconds{status="processing"} - Tiempo procesando
reconciliation_lag_seconds{status="completed"}  - Tiempo total de procesamiento
reconciliation_queue_size                          - Movimientos en cola
reconciliation_processing_rate                     - Movimientos por segundo
```

### Fuentes de Datos

Las métricas se收集an desde:
- **Spring Boot Actuator**: `/actuator/prometheus`
- **Micrometer**: Registro automático de métricas JPA y Kafka
- **Kafka Consumer Lag**: Métricas nativas de Kafka consumer

## Definición del SLA

### Umbrales de Alerta

| Nivel | Umbral | Descripción | Acción |
|-------|--------|-------------|--------|
| GREEN | < 30 segundos | Operación normal | Ninguna |
| YELLOW | 30s - 2min | Degradación leve | Notificación al equipo |
| ORANGE | 2min - 5min | Degradación severa | Escalar a on-call |
| RED | > 5min | критический | Alertar inmediatamente |

### SLA Operativo

- **Tiempo máximo de procesamiento**: 30 segundos al percentil 95
- **Disponibilidad de procesamiento**: 99.9%
- **Tolerancia a mensajes fuera de orden**: 5 minutos

## Mecanismo de Alerta

### Configuración de Alertmanager

```yaml
groups:
  - name: reconciliation-lag
    interval: 30s
    rules:
      - alert: ReconciliationLagYellow
        expr: reconciliation_lag_seconds{status="pending"} > 30
        for: 2m
        labels:
          severity: warning
        annotations:
          summary: "Lag de conciliación elevado"
          description: "El lag lleva 2 minutos por encima de 30 segundos"

      - alert: ReconciliationLagRed
        expr: reconciliation_lag_seconds{status="pending"} > 300
        for: 1m
        labels:
          severity: critical
        annotations:
          summary: "Lag de conciliación crítico"
          description: "Movimientos sin procesar por más de 5 minutos"
```

### Integración con Slack

Las alertas se notifican en el canal `#reconciliation-alerts` según la severidad:
- WARNING: Mensaje en hilo
- CRITICAL: Notificación directa a on-call

## Dashboard de Observabilidad

### Paneles Principales

1. **Lag Overview**: Vista agregada del lag actual
2. **Queue Depth**: Profundidad de cola por fuente
3. **Processing Rate**: Tasa de procesamiento vs tasa de llegada
4. **Error Rate**: Tasa de errores de conciliación

### Queries Prometheus Relevantes

```promql
# Lag actual promedio
avg(reconciliation_lag_seconds{status="pending"})

# Percentil 95
histogram_quantile(0.95, rate(reconciliation_lag_seconds_bucket[5m]))

# Tasa de procesamiento
rate(reconciliation_processed_total[5m])

# Tasa de llegada
rate(reconciliation_received_total[5m])
```

## Configuración del Sistema

### application.yml

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,prometheus,metrics
  metrics:
    tags:
      application: ${spring.application.name}
    export:
      prometheus:
        enabled: true
```

### Configuración de Thresholds

Los umbrales se configuran vía variables de entorno:
- `LAG_WARNING_THRESHOLD_SECONDS`: 30
- `LAG_CRITICAL_THRESHOLD_SECONDS`: 300
- `LAG_CHECK_INTERVAL_SECONDS`: 10

## Runbook de Respuesta

### Cuando se activa una alerta YELLOW

1. Verificar el dashboard de métricas
2. Revisar logs de la aplicación en el rango de tiempo
3. Verificar estado de Kafka consumers
4. Documentar en incidente si persiste > 10 minutos

### Cuando se activa una alerta RED

1. Notificar inmediatamente al equipo de operaciones
2. Verificar capacidad de procesamiento
3. Considerar escalado horizontal de consumidores
4. Revisar dead letter queue para mensajes fallidos
5. Iniciar proceso de recuperación si es necesario

## Métricas de Salud del Sistema

### KPIs Monitorizados

- **Throughput**: Movimientos procesados por segundo
- **Latencia P95**: Percentil 95 del tiempo de procesamiento
- **Error Rate**: Porcentaje de conciliaciones fallidas
- **Queue Depth**: Profundidad actual de la cola

### Objetivos de Calidad

| Métrica | Objetivo | Crítico |
|---------|----------|---------|
| Latencia P95 | < 30s | > 60s |
| Error Rate | < 0.1% | > 1% |
| Disponibilidad | 99.9% | < 99.5% |

## Referencias

- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/actuator/reference/html/)
- [Micrometer Metrics](https://micrometer.io/docs)
- [Prometheus Alerting](https://prometheus.io/docs/alerting/overview/)
- [Kafka Monitoring](https://kafka.apache.org/documentation/#monitoring)