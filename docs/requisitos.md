# Requisitos del Sistema de Conciliación Bancaria en Tiempo Real

## Visión General del Sistema

El sistema de conciliación bancaria procesa streams de movimientos financieros provenientes de tres fuentes externas: el core bancario interno, el gateway de pagos y el sistema de liquidación. Cada movimiento representa una transacción financiera que debe ser validada y reconciliada contra las otras fuentes para detectar discrepancias.

## Fuentes de Datos

### Core Bancario
Sistema principal de gestión de cuentas y transacciones bancarias. Emite eventos de movimientos con alta frecuencia y constituye la fuente de verdad para saldos y posiciones.

### Gateway de Payments
Plataforma de procesamiento de pagos que maneja transacciones con tarjetas, transferencias y otros métodos de pago. Proveedor externo con SLA propio.

### Sistema de Liquidación
Sistema de compensación y liquidación que registra el resultado final de las transacciones. Utilizado como fuente de verificación para confirmar que los pagos se completaron exitosamente.

## Requisitos Funcionales

### RF-001: Consumo de Streams en Tiempo Real
El sistema debe consumir eventos de movimiento desde tres topics de Kafka simultáneamente. Cada topic representa una fuente de datos distinta y los eventos contienen la información completa del movimiento financiero.

### RF-002: Identificación de Movimientos Duplicados
El sistema debe detectar y descartar eventos duplicados utilizando la combinación de eventId y versión como clave de idempotencia. Un evento con el mismo eventId y versión no debe procesarse dos veces.

### RF-003: Tolerancia a Mensajes Fuera de Orden
Los eventos pueden llegar con timestamps ligeramente desordenados debido a latencias de red o diferencias entre sistemas. El sistema debe mantener una ventana de tiempo que permita asociar movimientos relacionados aunque lleguen en orden no cronológico.

### RF-004: Reconciliación Multi-Fuente
Cada movimiento debe validarse contra las tres fuentes de datos. La conciliación se considera exitosa cuando existe correspondencia en al menos dos de las tres fuentes con amounts y referencias compatibles.

### RF-005: Detección de Discrepancias
Cuando un movimiento no encuentra correspondencia en las fuentes esperadas, el sistema debe marcar la conciliación como fallida y registrar la razón específica de la discrepancia.

### RF-006: Máquina de Estados
El proceso de conciliación sigue una máquina de estados con los siguientes estados: Pending (conciliación en progreso), Matched (conciliación exitosa), Mismatched (discrepancia detectada), Manual (requiere intervención humana).

### RF-007: Resolución Manual
Los casos que lleguen al estado Manual deben almacenar la resolución tomada por el equipo de operaciones para auditoría y trazabilidad.

### RF-008: Expiración de Conciliaciones
Las conciliaciones pendientes que no se resuelven dentro de un tiempo configurable deben marcarse como expiradas para evitar acumulación de estados intermedios.

## Requisitos No Funcionales

### RNF-001: Latencia de Procesamiento
El tiempo entre la recepción de un evento y la determinación del resultado de conciliación debe ser menor a 500 milisegundos en el percentil 99.

### RNF-002: Throughput
El sistema debe procesar al menos 1000 movimientos por segundo con latencia sostenida dentro de los límites especificados.

### RNF-003: Consistencia Eventual
El sistema utiliza reprocesamiento mediante replay de Kafka para garantizar consistencia eventual en caso de fallos. No se garantiza consistencia fuerte.

### RNF-004: Tolerancia a Fallos
El sistema debe continuar operando ante fallos parciales de alguna de las fuentes de datos. Los movimientos que no puedan validarse contra una fuente específica deben procesarse con la información disponible.

### RNF-005: Monitoreo de Lag
El equipo de operaciones debe recibir alertas cuando el lag de procesamiento supere umbrales definidos. El lag se mide como la diferencia entre el timestamp del evento más antiguo sin procesar y el tiempo actual.

### RNF-006: Persistencia de Estados
Todos los estados de conciliación deben persistirse en PostgreSQL para permitir recuperación ante fallos y auditoría.

### RNF-007: Alta Disponibilidad
El sistema debe desplegarse en configuración de alta disponibilidad con múltiples réplicas consumidoras de Kafka.

## Modelo de Datos

### Movimiento
Entidad que representa un evento financiero recibido desde una de las tres fuentes. Contiene: identificador único, tipo de movimiento, monto, moneda, referencia, número de cuenta, timestamp del evento y metadatos adicionales.

### Conciliación
Entidad que representa el resultado de procesar un movimiento. Contiene: referencia al movimiento original, estado actual, resultado de la comparación con cada fuente, timestamp de creación y actualización, razón de discrepancia si aplica, y resolución manual siapply.

## Configuración del Sistema

### Ventana de Matching
Tiempo configurable durante el cual el sistema busca correspondencias para un movimiento. Valor recomendado: 5 minutos para balancear detección temprana con tolerancia a latencias.

### Ventana de Expiración
Tiempo máximo que una conciliación puede permanecer en estado Pending antes de marcarse como expirada. Valor recomendado: 30 minutos.

### Políticas de Reintento
Número de reintentos y backoff exponencial para operaciones que fallen temporalmente al consultar fuentes de datos externas.