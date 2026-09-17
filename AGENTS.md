# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Diseño de Sistema de Conciliación Bancaria en Tiempo Real**.

| | |
|---|---|
| Tema | TEST-CT |
| Nivel | senior-l2 |
| Chapter | Generico |
| Especialidad | Inferido del contexto |
| Stack | Java / Spring Boot 3.4 |
| Patron arquitectonico | hexagonal/clean con CQRS y Event Sourcing |
| Tiempo estimado | 10 horas |

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `el comando de build o arranque canonico del stack elegido` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `el comando de build o arranque canonico del stack elegido` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Exploración y Definición de Requisitos**: Documento de requisitos funcionales y no funcionales del sistema de conciliación.
- **Fase 2 — Diseño de la Máquina de Estados**: Diagrama de la máquina de estados y documentación de las decisiones tomadas.
- **Fase 3 — Estrategia de Reprocesamiento**: Documentación de la estrategia de reprocesamiento elegida y justificación de la decisión.
- **Fase 4 — Alerta de Lag de Conciliación**: Documentación del mecanismo de alerta y definición del SLA.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Lo que falta y tenes que completar

### 1. Boilerplate del stack (1)

Sin esto el proyecto no compila ni arranca. **Es tu trabajo crearlo**, y no toca nada de lo pedagogico: es andamiaje del stack.

- [ ] **Punto de entrada del stack elegido** — Sin un punto de entrada reconocible, el runtime no tiene por donde arrancar la aplicacion.

### 2. Archivos que la arquitectura declara (2 de 19)

La propuesta arquitectonica del reto los lista y no llegaron al repo. Crealos con implementacion real, respetando la capa en la que viven:

- [ ] `docs/maquina-estados.md`
- [ ] `docs/estrategia-reprocesamiento.md`

### 3. Referencias colgando (55)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/example/reconciliation/infrastructure/messaging/KafkaMovementConsumer.java` — `Reconciliation`
      El import com.example.reconciliation.domain.model.Reconciliation no se usa en ningun lado del cuerpo del archivo. Se puede eliminar.
- [ ] `src/test/java/com/example/reconciliation/infrastructure/messaging/KafkaMovementConsumerTest.java` — `ReconcileMovementUseCase`
      El import com.example.reconciliation.application.usecases.ReconcileMovementUseCase no se usa en ningun lado del cuerpo del archivo. Se puede eliminar.
- [ ] `src/main/java/com/example/reconciliation/domain/repository/ReconciliationRepository.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/example/reconciliation/application/usecases/ReconcileMovementUseCase.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/example/reconciliation/application/services/ReconciliationService.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/example/reconciliation/infrastructure/messaging/KafkaMovementConsumer.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/example/reconciliation/infrastructure/config/KafkaConfig.java` — `reactor.kafka.receiver`
      El import reactor.kafka.receiver.ReceiverOptions pertenece a reactor.kafka.receiver, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/example/reconciliation/infrastructure/config/KafkaConfig.java` — `reactor.kafka.sender`
      El import reactor.kafka.sender.ReactorKafkaProducer pertenece a reactor.kafka.sender, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/example/reconciliation/infrastructure/rest/ReconciliationController.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/example/reconciliation/infrastructure/monitoring/LagMonitor.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/example/reconciliation/application/usecases/ReconcileMovementUseCaseTest.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/example/reconciliation/infrastructure/messaging/KafkaMovementConsumerTest.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/example/reconciliation/domain/model/Movement.java` — `SourceType.name`
      Se invoca `name` sobre `SourceType`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/domain/event/ReconciliationEvent.java` — `EventType.name`
      Se invoca `name` sobre `EventType`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setVersion`
      Se invoca `setVersion` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setUpdatedAt`
      Se invoca `setUpdatedAt` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setCreatedAt`
      Se invoca `setCreatedAt` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setId`
      Se invoca `setId` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setEventId`
      Se invoca `setEventId` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setSourceType`
      Se invoca `setSourceType` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setTargetType`
      Se invoca `setTargetType` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setStatus`
      Se invoca `setStatus` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setAmount`
      Se invoca `setAmount` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setCurrency`
      Se invoca `setCurrency` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setReference`
      Se invoca `setReference` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setDescription`
      Se invoca `setDescription` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setMatchedAt`
      Se invoca `setMatchedAt` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setMismatchReason`
      Se invoca `setMismatchReason` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setManualResolution`
      Se invoca `setManualResolution` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getId`
      Se invoca `getId` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getEventId`
      Se invoca `getEventId` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getVersion`
      Se invoca `getVersion` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getSourceType`
      Se invoca `getSourceType` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getTargetType`
      Se invoca `getTargetType` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getStatus`
      Se invoca `getStatus` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getAmount`
      Se invoca `getAmount` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getCurrency`
      Se invoca `getCurrency` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getReference`
      Se invoca `getReference` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getDescription`
      Se invoca `getDescription` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getMatchedAt`
      Se invoca `getMatchedAt` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getCreatedAt`
      Se invoca `getCreatedAt` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getUpdatedAt`
      Se invoca `getUpdatedAt` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getMismatchReason`
      Se invoca `getMismatchReason` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getManualResolution`
      Se invoca `getManualResolution` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/application/usecases/ReconcileMovementUseCase.java` — `MovementProvider.findMovements`
      Se invoca `findMovements` sobre `MovementProvider`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/application/usecases/ReconcileMovementUseCase.java` — `SourceType.name`
      Se invoca `name` sobre `SourceType`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/application/usecases/ReconcileMovementUseCase.java` — `ReconciliationEventPublisher.publish`
      Se invoca `publish` sobre `ReconciliationEventPublisher`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/messaging/KafkaMovementConsumer.java` — `LagMonitor.recordMovementReceived`
      Se invoca `recordMovementReceived` sobre `LagMonitor`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/messaging/KafkaMovementConsumer.java` — `LagMonitor.recordDltMessage`
      Se invoca `recordDltMessage` sobre `LagMonitor`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/rest/ReconciliationController.java` — `ReprocessRequest.resolution`
      Se invoca `resolution` sobre `ReprocessRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/rest/ReconciliationController.java` — `ReprocessRequest.reason`
      Se invoca `reason` sobre `ReprocessRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/rest/ReconciliationController.java` — `ReprocessRequest.eventId`
      Se invoca `eventId` sobre `ReprocessRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/rest/ReconciliationController.java` — `ReprocessRequest.version`
      Se invoca `version` sobre `ReprocessRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/example/reconciliation/infrastructure/monitoring/LagMonitor.java` — `LagMetrics.lagSeconds`
      Se invoca `lagSeconds` sobre `LagMetrics`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (20)

- `pom.xml`
- `src/main/java/com/example/reconciliation/ReconciliationApplication.java`
- `src/main/resources/application.yml`
- `src/main/java/com/example/reconciliation/domain/model/Reconciliation.java`
- `src/main/java/com/example/reconciliation/domain/model/Movement.java`
- `src/main/java/com/example/reconciliation/domain/repository/ReconciliationRepository.java`
- `src/main/java/com/example/reconciliation/domain/event/ReconciliationEvent.java`
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java`
- `src/main/java/com/example/reconciliation/application/usecases/ReconcileMovementUseCase.java`
- `src/main/java/com/example/reconciliation/application/services/ReconciliationService.java`
- `src/main/java/com/example/reconciliation/infrastructure/messaging/KafkaMovementConsumer.java`
- `src/main/java/com/example/reconciliation/infrastructure/config/KafkaConfig.java`
- `src/main/java/com/example/reconciliation/infrastructure/config/PostgreSQLConfig.java`
- `src/main/java/com/example/reconciliation/infrastructure/rest/ReconciliationController.java`
- `src/main/java/com/example/reconciliation/infrastructure/monitoring/LagMonitor.java`
- `src/test/java/com/example/reconciliation/domain/model/ReconciliationTest.java`
- `src/test/java/com/example/reconciliation/application/usecases/ReconcileMovementUseCaseTest.java`
- `src/test/java/com/example/reconciliation/infrastructure/messaging/KafkaMovementConsumerTest.java`
- `docs/requisitos.md`
- `docs/alertas-lag.md`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/example/reconciliation`
- `src/main/java/com/example/reconciliation/application`
- `src/main/java/com/example/reconciliation/application/usecases`
- `src/main/java/com/example/reconciliation/application/services`
- `src/main/java/com/example/reconciliation/domain`
- `src/main/java/com/example/reconciliation/domain/model`
- `src/main/java/com/example/reconciliation/domain/repository`
- `src/main/java/com/example/reconciliation/domain/event`
- `src/main/java/com/example/reconciliation/infrastructure`
- `src/main/java/com/example/reconciliation/infrastructure/config`
- `src/main/java/com/example/reconciliation/infrastructure/messaging`
- `src/main/java/com/example/reconciliation/infrastructure/persistence`
- `src/main/java/com/example/reconciliation/infrastructure/rest`
- `src/main/java/com/example/reconciliation/infrastructure/monitoring`
- `src/test/java/com/example/reconciliation`

## Verificacion

```bash
el comando de build o arranque canonico del stack elegido
```

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean con CQRS y Event Sourcing**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Brecha que el reto ataca: Diseño de motor de conciliación que consume streams de movimientos desde 3 fuentes (core bancario, gateway de pagos, sistema de liquidación) y detecta discrepancias en ventanas móviles. Cada movimiento debe reconciliarse contra las 3 fuentes con tolerancia a mensajes fuera de orden y llegadas duplicadas. El desarrollador senior debe diseñar la máquina de estados de cada Reconciliation (Pending, Matched, Mismatched, Manual), justificar la ventana de matching (5 min vs 1 hora), definir cómo maneja idempotencia con eventId + version, y elegir entre reprocesamiento por replay de Kafka vs snapshot desde PostgreSQL. Adicionalmente debe explicar cómo alertar al equipo de operaciones cuando el lag de conciliación supera un SLA.

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
