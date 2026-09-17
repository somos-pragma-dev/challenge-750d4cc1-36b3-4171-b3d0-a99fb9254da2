# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Boilerplate del stack que falta

Sin esto no compila ni arranca. Es andamiaje, no toca nada de lo pedagogico:

- **Punto de entrada del stack elegido** — Sin un punto de entrada reconocible, el runtime no tiene por donde arrancar la aplicacion.

### Archivos que la arquitectura del reto declara y no estan

Creálos con implementacion real, en la capa que les corresponde:

- `docs/maquina-estados.md`
- `docs/estrategia-reprocesamiento.md`

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/example/reconciliation/infrastructure/messaging/KafkaMovementConsumer.java` — `Reconciliation`: El import com.example.reconciliation.domain.model.Reconciliation no se usa en ningun lado del cuerpo del archivo. Se puede eliminar.
- `src/test/java/com/example/reconciliation/infrastructure/messaging/KafkaMovementConsumerTest.java` — `ReconcileMovementUseCase`: El import com.example.reconciliation.application.usecases.ReconcileMovementUseCase no se usa en ningun lado del cuerpo del archivo. Se puede eliminar.
- `src/main/java/com/example/reconciliation/domain/repository/ReconciliationRepository.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/example/reconciliation/application/usecases/ReconcileMovementUseCase.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/example/reconciliation/application/services/ReconciliationService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/example/reconciliation/infrastructure/messaging/KafkaMovementConsumer.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/example/reconciliation/infrastructure/config/KafkaConfig.java` — `reactor.kafka.receiver`: El import reactor.kafka.receiver.ReceiverOptions pertenece a reactor.kafka.receiver, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/example/reconciliation/infrastructure/config/KafkaConfig.java` — `reactor.kafka.sender`: El import reactor.kafka.sender.ReactorKafkaProducer pertenece a reactor.kafka.sender, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/example/reconciliation/infrastructure/rest/ReconciliationController.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/example/reconciliation/infrastructure/monitoring/LagMonitor.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/example/reconciliation/application/usecases/ReconcileMovementUseCaseTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/example/reconciliation/infrastructure/messaging/KafkaMovementConsumerTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/example/reconciliation/domain/model/Movement.java` — `SourceType.name`: Se invoca `name` sobre `SourceType`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/domain/event/ReconciliationEvent.java` — `EventType.name`: Se invoca `name` sobre `EventType`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setVersion`: Se invoca `setVersion` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setUpdatedAt`: Se invoca `setUpdatedAt` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setCreatedAt`: Se invoca `setCreatedAt` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setId`: Se invoca `setId` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setEventId`: Se invoca `setEventId` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setSourceType`: Se invoca `setSourceType` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setTargetType`: Se invoca `setTargetType` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setStatus`: Se invoca `setStatus` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setAmount`: Se invoca `setAmount` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setCurrency`: Se invoca `setCurrency` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setReference`: Se invoca `setReference` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setDescription`: Se invoca `setDescription` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setMatchedAt`: Se invoca `setMatchedAt` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setMismatchReason`: Se invoca `setMismatchReason` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.setManualResolution`: Se invoca `setManualResolution` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getId`: Se invoca `getId` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getEventId`: Se invoca `getEventId` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getVersion`: Se invoca `getVersion` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getSourceType`: Se invoca `getSourceType` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getTargetType`: Se invoca `getTargetType` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getStatus`: Se invoca `getStatus` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getAmount`: Se invoca `getAmount` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getCurrency`: Se invoca `getCurrency` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getReference`: Se invoca `getReference` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getDescription`: Se invoca `getDescription` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getMatchedAt`: Se invoca `getMatchedAt` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getCreatedAt`: Se invoca `getCreatedAt` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getUpdatedAt`: Se invoca `getUpdatedAt` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getMismatchReason`: Se invoca `getMismatchReason` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java` — `ReconciliationEntity.getManualResolution`: Se invoca `getManualResolution` sobre `ReconciliationEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/application/usecases/ReconcileMovementUseCase.java` — `MovementProvider.findMovements`: Se invoca `findMovements` sobre `MovementProvider`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/application/usecases/ReconcileMovementUseCase.java` — `SourceType.name`: Se invoca `name` sobre `SourceType`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/application/usecases/ReconcileMovementUseCase.java` — `ReconciliationEventPublisher.publish`: Se invoca `publish` sobre `ReconciliationEventPublisher`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/messaging/KafkaMovementConsumer.java` — `LagMonitor.recordMovementReceived`: Se invoca `recordMovementReceived` sobre `LagMonitor`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/messaging/KafkaMovementConsumer.java` — `LagMonitor.recordDltMessage`: Se invoca `recordDltMessage` sobre `LagMonitor`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/rest/ReconciliationController.java` — `ReprocessRequest.resolution`: Se invoca `resolution` sobre `ReprocessRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/rest/ReconciliationController.java` — `ReprocessRequest.reason`: Se invoca `reason` sobre `ReprocessRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/rest/ReconciliationController.java` — `ReprocessRequest.eventId`: Se invoca `eventId` sobre `ReprocessRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/rest/ReconciliationController.java` — `ReprocessRequest.version`: Se invoca `version` sobre `ReprocessRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/example/reconciliation/infrastructure/monitoring/LagMonitor.java` — `LagMetrics.lagSeconds`: Se invoca `lagSeconds` sobre `LagMetrics`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
el comando de build o arranque canonico del stack elegido
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Contexto técnico original
Diseño de motor de conciliación que consume streams de movimientos desde 3 fuentes (core bancario, gateway de pagos, sistema de liquidación) y detecta discrepancias en ventanas móviles. Cada movimiento debe reconciliarse contra las 3 fuentes con tolerancia a mensajes fuera de orden y llegadas duplicadas. El desarrollador senior debe diseñar la máquina de estados de cada Reconciliation (Pending, Matched, Mismatched, Manual), justificar la ventana de matching (5 min vs 1 hora), definir cómo maneja idempotencia con eventId + version, y elegir entre reprocesamiento por replay de Kafka vs snapshot desde PostgreSQL. Adicionalmente debe explicar cómo alertar al equipo de operaciones cuando el lag de conciliación supera un SLA.

### Reto
- Tema: TEST-CT
- Seniority: senior-l2
- Tipo: practical
- Título: Diseño de Sistema de Conciliación Bancaria en Tiempo Real
- Tiempo estimado: 10 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Exploración y Definición de Requisitos — objetivo: Identificar y documentar los requisitos funcionales y no funcionales del sistema de conciliación. — entregable (NO resolver): Documento de requisitos funcionales y no funcionales del sistema de conciliación.
- Fase 2: Diseño de la Máquina de Estados — objetivo: Diseñar la máquina de estados para el proceso de conciliación y justificar las decisiones tomadas. — entregable (NO resolver): Diagrama de la máquina de estados y documentación de las decisiones tomadas.
- Fase 3: Estrategia de Reprocesamiento — objetivo: Elegir entre reprocesamiento por replay de Kafka vs snapshot desde PostgreSQL y justificar la decisión. — entregable (NO resolver): Documentación de la estrategia de reprocesamiento elegida y justificación de la decisión.
- Fase 4: Alerta de Lag de Conciliación — objetivo: Definir cómo alertar al equipo de operaciones cuando el lag de conciliación supera un SLA. — entregable (NO resolver): Documentación del mecanismo de alerta y definición del SLA.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.4.5</version>
        <relativePath/>
    </parent>
    
    <groupId>com.example</groupId>
    <artifactId>reconciliation</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <name>reconciliation</name>
    <description>Sistema de Conciliación Bancaria en Tiempo Real</description>
    
    <properties>
        <java.version>21</java.version>
        <spring-kafka.version>3.4.0</spring-kafka.version>
        <reactor.version>3.6.5</reactor.version>
        <postgres.version>42.7.3</postgres.version>
        <micrometer.version>1.13.0</micrometer.version>
        <testcontainers.version>1.19.7</testcontainers.version>
        <mockito.version>5.11.0</mockito.version>
        <junit.version>5.10.2</junit.version>
    </properties>
    
    <dependencies>
        <!-- Spring Boot WebFlux para programación reactiva -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        
        <!-- Spring Kafka para consumo de streams -->
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
            <version>${spring-kafka.version}</version>
        </dependency>
        
        <!-- Reactor Core para programación reactiva -->
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-core</artifactId>
            <version>${reactor.version}</version>
        </dependency>
        
        <!-- PostgreSQL driver -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>${postgres.version}</version>
        </dependency>
        
        <!-- Spring Data JPA para persistencia -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        
        <!-- Actuator para monitoreo y métricas -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        
        <!-- Micrometer Prometheus para métricas -->
        <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-registry-prometheus</artifactId>
            <version>${micrometer.version}</version>
        </dependency>
        
        <!-- Test dependencies -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>${mockito.version}</version>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>postgresql</artifactId>
            <version>${testcontainers.version}</version>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>kafka</artifactId>
            <version>${testcontainers.version}</version>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                    <compilerArgs>
                        <arg>--enable-preview</arg>
                    </compilerArgs>
                </configuration>
            </plugin>
            
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.5</version>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/example/reconciliation/ReconciliationApplication.java ===
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

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: reconciliation-service
  
  # Configuración de PostgreSQL para persistencia de conciliaciones
  datasource:
    url: jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:reconciliation}
    username: ${DB_USER:reconciliation_user}
    password: ${DB_PASSWORD:reconciliation_pass}
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
      pool-name: ReconciliationHikariPool
  
  # Configuración de JPA/Hibernate
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
        jdbc:
          batch_size: 50
        order_inserts: true
        order_updates: true
  
  # Configuración de Kafka para consumo de streams
  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS:localhost:9092}
    consumer:
      group-id: reconciliation-consumer-group
      auto-offset-reset: earliest
      enable-auto-commit: false
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: com.example.reconciliation.*
        spring.json.type.mapping: "ReconciliationEvent:com.example.reconciliation.domain.event.ReconciliationEvent"
        max.poll.records: 500
        max.poll.interval.ms: 300000
        session.timeout.ms: 10000
        heartbeat.interval.ms: 3000
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
      acks: all
      retries: 3
      properties:
        enable.idempotence: true
        max.in.flight.requests.per.connection: 5
        delivery.timeout.ms: 120000
    listener:
      ack-mode: manual_immediate
      concurrency: 3

# Configuración del servidor embebido (Netty para WebFlux)
server:
  port: ${SERVER_PORT:8080}
  netty:
    connection-timeout: 60s

# Configuración de Actuator para monitoreo
management:
  endpoints:
    web:
      exposure:
        include: health,info,prometheus,metrics,loggers
      base-path: /actuator
  endpoint:
    health:
      show-details: when_authorized
      probes:
        enabled: true
  health:
    livenessState:
      enabled: true
    readinessState:
      enabled: true
    kafka:
      enabled: true
    db:
      enabled: true
  metrics:
    export:
      prometheus:
        enabled: true
        step: 1m
    tags:
      application: ${spring.application.name}

# Configuración específica del sistema de conciliación
reconciliation:
  # Ventana de matching de 5 minutos (300 segundos)
  # Justificación: balance entre latencia y tolerancia a mensajes fuera de orden
  # - 5 min permite capturar mensajes delayed de las 3 fuentes
  # - No es tan largo como para acumular demasiados movimientos pendientes
  # - El core bancario típicamente procesa en < 2 min
  # - Gateway de pagos puede tener latencia de hasta 3 min
  # - Sistema de liquidación opera en batches de 1 min
  matching-window-seconds: 300
  
  # Tolerancia a mensajes fuera de orden (en segundos)
  # Permite recibir mensajes con timestamp hasta 2 minutos en el pasado
  out-of-order-tolerance-seconds: 120
  
  # SLA de lag de conciliación (en segundos)
  # Si un movimiento tarda más de este tiempo en conciliarse, se dispara alerta
  lag-sla-seconds: 600
  
  # Configuración de retry para reprocesamiento
  retry:
    max-attempts: 3
    initial-interval-ms: 1000
    multiplier: 2.0
    max-interval-ms: 30000
  
  # Configuración de cleanup de movimientos antigos
  cleanup:
    enabled: true
    retention-days: 90
    batch-size: 1000
  
  # Fuentes de datos configuradas
  sources:
    - name: CORE_BANKING
      topic: movements.core-banking
      priority: 1
    - name: PAYMENT_GATEWAY
      topic: movements.payment-gateway
      priority: 2
    - name: LIQUIDATION_SYSTEM
      topic: movements.liquidation
      priority: 3

# Logging
logging:
  level:
    root: INFO
    com.example.reconciliation: DEBUG
    org.springframework.kafka: INFO
    org.hibernate.SQL: WARN
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"


// === ARCHIVO: src/main/java/com/example/reconciliation/domain/model/Reconciliation.java ===
package com.example.reconciliation.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class Reconciliation {
    
    public enum Status {
        PENDING("Pendiente de match"),
        MATCHED("Coincidencia encontrada"),
        MISMATCHED("Discrepancia detectada"),
        MANUAL("Requiere revisión manual");
        
        private final String description;
        
        Status(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    private final UUID id;
    private final String eventId;
    private final int version;
    private final String sourceType;
    private final String targetType;
    private Status status;
    private final BigDecimal amount;
    private final String currency;
    private final String reference;
    private final String description;
    private Instant matchedAt;
    private final Instant createdAt;
    private Instant updatedAt;
    private String mismatchReason;
    private String manualResolution;
    
    private Reconciliation(Builder builder) {
        this.id = builder.id;
        this.eventId = builder.eventId;
        this.version = builder.version;
        this.sourceType = builder.sourceType;
        this.targetType = builder.targetType;
        this.status = builder.status;
        this.amount = builder.amount;
        this.currency = builder.currency;
        this.reference = builder.reference;
        this.description = builder.description;
        this.matchedAt = builder.matchedAt;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.mismatchReason = builder.mismatchReason;
        this.manualResolution = builder.manualResolution;
    }
    
    public static Reconciliation create(String eventId, int version, String sourceType, 
            String targetType, BigDecimal amount, String currency, String reference, String description) {
        Objects.requireNonNull(eventId, "El eventId no puede ser null");
        Objects.requireNonNull(sourceType, "El sourceType no puede ser null");
        Objects.requireNonNull(targetType, "El targetType no puede ser null");
        Objects.requireNonNull(amount, "El amount no puede ser null");
        Objects.requireNonNull(currency, "El currency no puede ser null");
        
        if (version < 0) {
            throw new IllegalArgumentException("La versión no puede ser negativa");
        }
        
        Instant now = Instant.now();
        return Builder.create()
                .id(UUID.randomUUID())
                .eventId(eventId)
                .version(version)
                .sourceType(sourceType)
                .targetType(targetType)
                .status(Status.PENDING)
                .amount(amount)
                .currency(currency)
                .reference(reference)
                .description(description)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
    
    public static Reconciliation restore(UUID id, String eventId, int version, String sourceType,
            String targetType, Status status, BigDecimal amount, String currency, String reference,
            String description, Instant matchedAt, Instant createdAt, Instant updatedAt,
            String mismatchReason, String manualResolution) {
        return Builder.create()
                .id(id)
                .eventId(eventId)
                .version(version)
                .sourceType(sourceType)
                .targetType(targetType)
                .status(status)
                .amount(amount)
                .currency(currency)
                .reference(reference)
                .description(description)
                .matchedAt(matchedAt)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .mismatchReason(mismatchReason)
                .manualResolution(manualResolution)
                .build();
    }
    
    public boolean canTransitionTo(Status newStatus) {
        return switch (status) {
            case PENDING -> newStatus == Status.MATCHED || 
                             newStatus == Status.MISMATCHED || 
                             newStatus == Status.MANUAL;
            case MISMATCHED -> newStatus == Status.MANUAL || newStatus == Status.MATCHED;
            case MANUAL -> newStatus == Status.MATCHED;
            case MATCHED -> false;
        };
    }
    
    public void match() {
        if (!canTransitionTo(Status.MATCHED)) {
            throw new IllegalStateException(
                String.format("No se puede transicionar de %s a MATCHED", status)
            );
        }
        this.status = Status.MATCHED;
        this.matchedAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    
    public void mismatch(String reason) {
        if (!canTransitionTo(Status.MISMATCHED)) {
            throw new IllegalStateException(
                String.format("No se puede transicionar de %s a MISMATCHED", status)
            );
        }
        Objects.requireNonNull(reason, "La razón del desbalance no puede ser null");
        this.status = Status.MISMATCHED;
        this.mismatchReason = reason;
        this.updatedAt = Instant.now();
    }
    
    public void escalateToManual(String reason) {
        if (!canTransitionTo(Status.MANUAL)) {
            throw new IllegalStateException(
                String.format("No se puede transicionar de %s a MANUAL", status)
            );
        }
        this.status = Status.MANUAL;
        this.mismatchReason = reason;
        this.updatedAt = Instant.now();
    }
    
    public void resolveManually(String resolution) {
        if (status != Status.MANUAL) {
            throw new IllegalStateException(
                "Solo se puede resolver manualmente una reconciliación en estado MANUAL"
            );
        }
        Objects.requireNonNull(resolution, "La resolución no puede ser null");
        this.status = Status.MATCHED;
        this.manualResolution = resolution;
        this.matchedAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    
    public boolean isIdempotentKeyUnique(Reconciliation other) {
        if (other == null) return true;
        return !this.eventId.equals(other.eventId) || this.version != other.version;
    }
    
    public String getIdempotencyKey() {
        return eventId + ":" + version;
    }
    
    public boolean isExpired(int windowMinutes) {
        if (status != Status.PENDING) {
            return false;
        }
        return createdAt.plusSeconds(windowMinutes * 60L).isBefore(Instant.now());
    }
    
    public UUID getId() { return id; }
    public String getEventId() { return eventId; }
    public int getVersion() { return version; }
    public String getSourceType() { return sourceType; }
    public String getTargetType() { return targetType; }
    public Status getStatus() { return status; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getReference() { return reference; }
    public String getDescription() { return description; }
    public Instant getMatchedAt() { return matchedAt; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public String getMismatchReason() { return mismatchReason; }
    public String getManualResolution() { return manualResolution; }
    
    public static class Builder {
        private UUID id;
        private String eventId;
        private int version;
        private String sourceType;
        private String targetType;
        private Status status;
        private BigDecimal amount;
        private String currency;
        private String reference;
        private String description;
        private Instant matchedAt;
        private Instant createdAt;
        private Instant updatedAt;
        private String mismatchReason;
        private String manualResolution;
        
        private Builder() {}
        
        public static Builder create() {
            return new Builder();
        }
        
        public Builder id(UUID id) { this.id = id; return this; }
        public Builder eventId(String eventId) { this.eventId = eventId; return this; }
        public Builder version(int version) { this.version = version; return this; }
        public Builder sourceType(String sourceType) { this.sourceType = sourceType; return this; }
        public Builder targetType(String targetType) { this.targetType = targetType; return this; }
        public Builder status(Status status) { this.status = status; return this; }
        public Builder amount(BigDecimal amount) { this.amount = amount; return this; }
        public Builder currency(String currency) { this.currency = currency; return this; }
        public Builder reference(String reference) { this.reference = reference; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder matchedAt(Instant matchedAt) { this.matchedAt = matchedAt; return this; }
        public Builder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }
        public Builder mismatchReason(String mismatchReason) { this.mismatchReason = mismatchReason; return this; }
        public Builder manualResolution(String manualResolution) { this.manualResolution = manualResolution; return this; }
        
        public Reconciliation build() {
            return new Reconciliation(this);
        }
    }
}

// === ARCHIVO: src/main/java/com/example/reconciliation/domain/model/Movement.java ===
package com.example.reconciliation.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class Movement {
    
    public enum SourceType {
        CORE_BANKING("Core Bancario"),
        PAYMENT_GATEWAY("Gateway de Pagos"),
        LIQUIDATION_SYSTEM("Sistema de Liquidación");
        
        private final String displayName;
        
        SourceType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum MovementType {
        CREDIT("Crédito"),
        DEBIT("Débito"),
        ADJUSTMENT("Ajuste");
        
        private final String displayName;
        
        MovementType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    private final UUID id;
    private final String eventId;
    private final int version;
    private final SourceType sourceType;
    private final MovementType movementType;
    private final BigDecimal amount;
    private final String currency;
    private final String reference;
    private final String accountNumber;
    private final Instant timestamp;
    private final Map<String, String> metadata;
    private final Instant receivedAt;
    private final boolean processed;
    
    private Movement(Builder builder) {
        this.id = builder.id;
        this.eventId = builder.eventId;
        this.version = builder.version;
        this.sourceType = builder.sourceType;
        this.movementType = builder.movementType;
        this.amount = builder.amount;
        this.currency = builder.currency;
        this.reference = builder.reference;
        this.accountNumber = builder.accountNumber;
        this.timestamp = builder.timestamp;
        this.metadata = builder.metadata != null ? Map.copyOf(builder.metadata) : Map.of();
        this.receivedAt = builder.receivedAt;
        this.processed = builder.processed;
    }
    
    public static Movement create(String eventId, int version, SourceType sourceType,
            MovementType movementType, BigDecimal amount, String currency, String reference,
            String accountNumber, Instant timestamp, Map<String, String> metadata) {
        
        Objects.requireNonNull(eventId, "El eventId no puede ser null");
        Objects.requireNonNull(sourceType, "El sourceType no puede ser null");
        Objects.requireNonNull(movementType, "El movementType no puede ser null");
        Objects.requireNonNull(amount, "El amount no puede ser null");
        Objects.requireNonNull(currency, "El currency no puede ser null");
        Objects.requireNonNull(timestamp, "El timestamp no puede ser null");
        
        if (version < 0) {
            throw new IllegalArgumentException("La versión no puede ser negativa");
        }
        
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        
        return Builder.create()
                .id(UUID.randomUUID())
                .eventId(eventId)
                .version(version)
                .sourceType(sourceType)
                .movementType(movementType)
                .amount(amount)
                .currency(currency)
                .reference(reference)
                .accountNumber(accountNumber)
                .timestamp(timestamp)
                .metadata(metadata)
                .receivedAt(Instant.now())
                .processed(false)
                .build();
    }
    
    public static Movement restore(UUID id, String eventId, int version, SourceType sourceType,
            MovementType movementType, BigDecimal amount, String currency, String reference,
            String accountNumber, Instant timestamp, Map<String, String> metadata,
            Instant receivedAt, boolean processed) {
        return Builder.create()
                .id(id)
                .eventId(eventId)
                .version(version)
                .sourceType(sourceType)
                .movementType(movementType)
                .amount(amount)
                .currency(currency)
                .reference(reference)
                .accountNumber(accountNumber)
                .timestamp(timestamp)
                .metadata(metadata)
                .receivedAt(receivedAt)
                .processed(processed)
                .build();
    }
    
    public boolean isDuplicate(Movement other) {
        if (other == null) return false;
        return this.eventId.equals(other.eventId) && 
               this.version == other.version && 
               this.sourceType == other.sourceType;
    }
    
    public boolean isOutOfOrder(Instant windowStart) {
        return timestamp.isBefore(windowStart);
    }
    
    public String getIdempotencyKey() {
        return sourceType.name() + ":" + eventId + ":" + version;
    }
    
    public boolean matchesAmount(BigDecimal expectedAmount) {
        return this.amount.compareTo(expectedAmount) == 0;
    }
    
    public boolean matchesReference(String expectedReference) {
        if (expectedReference == null) return false;
        return this.reference.equals(expectedReference);
    }
    
    public boolean matchesAccount(String expectedAccount) {
        if (expectedAccount == null) return false;
        return this.accountNumber.equals(expectedAccount);
    }
    
    public boolean isWithinTimeWindow(Instant windowStart, Instant windowEnd) {
        return !timestamp.isBefore(windowStart) && !timestamp.isAfter(windowEnd);
    }
    
    public UUID getId() { return id; }
    public String getEventId() { return eventId; }
    public int getVersion() { return version; }
    public SourceType getSourceType() { return sourceType; }
    public MovementType getMovementType() { return movementType; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getReference() { return reference; }
    public String getAccountNumber() { return accountNumber; }
    public Instant getTimestamp() { return timestamp; }
    public Map<String, String> getMetadata() { return metadata; }
    public Instant getReceivedAt() { return receivedAt; }
    public boolean isProcessed() { return processed; }
    
    public static class Builder {
        private UUID id;
        private String eventId;
        private int version;
        private SourceType sourceType;
        private MovementType movementType;
        private BigDecimal amount;
        private String currency;
        private String reference;
        private String accountNumber;
        private Instant timestamp;
        private Map<String, String> metadata;
        private Instant receivedAt;
        private boolean processed;
        
        private Builder() {}
        
        public static Builder create() {
            return new Builder();
        }
        
        public Builder id(UUID id) { this.id = id; return this; }
        public Builder eventId(String eventId) { this.eventId = eventId; return this; }
        public Builder version(int version) { this.version = version; return this; }
        public Builder sourceType(SourceType sourceType) { this.sourceType = sourceType; return this; }
        public Builder movementType(MovementType movementType) { this.movementType = movementType; return this; }
        public Builder amount(BigDecimal amount) { this.amount = amount; return this; }
        public Builder currency(String currency) { this.currency = currency; return this; }
        public Builder reference(String reference) { this.reference = reference; return this; }
        public Builder accountNumber(String accountNumber) { this.accountNumber = accountNumber; return this; }
        public Builder timestamp(Instant timestamp) { this.timestamp = timestamp; return this; }
        public Builder metadata(Map<String, String> metadata) { this.metadata = metadata; return this; }
        public Builder receivedAt(Instant receivedAt) { this.receivedAt = receivedAt; return this; }
        public Builder processed(boolean processed) { this.processed = processed; return this; }
        
        public Movement build() {
            return new Movement(this);
        }
    }
}

// === ARCHIVO: src/main/java/com/example/reconciliation/domain/repository/ReconciliationRepository.java ===
package com.example.reconciliation.domain.repository;

import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.domain.model.Reconciliation.Status;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReconciliationRepository {
    
    Mono<Reconciliation> save(Reconciliation reconciliation);
    
    Mono<Optional<Reconciliation>> findById(UUID id);
    
    Mono<Optional<Reconciliation>> findByEventIdAndVersion(String eventId, int version);
    
    Flux<Reconciliation> findByStatus(Status status);
    
    Flux<Reconciliation> findByStatusAndCreatedAtAfter(Status status, Instant since);
    
    Flux<Reconciliation> findBySourceTypeAndTargetType(String sourceType, String targetType);
    
    Flux<Reconciliation> findByCreatedAtBetween(Instant start, Instant end);
    
    Flux<Reconciliation> findPendingOlderThan(Instant cutoff);
    
    Mono<Long> countByStatus(Status status);
    
    Mono<Long> countByStatusAndCreatedAtAfter(Status status, Instant since);
    
    Mono<Boolean> existsByEventIdAndVersion(String eventId, int version);
    
    Mono<Void> deleteById(UUID id);
    
    Mono<Void> deleteOlderThan(Instant cutoff);
    
    Flux<Reconciliation> findAll(int page, int size);
    
    Mono<Long> count();
}


// === ARCHIVO: src/main/java/com/example/reconciliation/domain/event/ReconciliationEvent.java ===
package com.example.reconciliation.domain.event;

import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.domain.model.Reconciliation.Status;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public final class ReconciliationEvent {
    
    public enum EventType {
        CREATED,
        MATCHED,
        MISMATCHED,
        ESCALATED_TO_MANUAL,
        MANUALLY_RESOLVED,
        EXPIRED
    }
    
    private final UUID id;
    private final String eventId;
    private final int version;
    private final EventType eventType;
    private final UUID reconciliationId;
    private final String sourceType;
    private final String targetType;
    private final BigDecimal amount;
    private final String currency;
    private final String reference;
    private final Status previousStatus;
    private final Status newStatus;
    private final String reason;
    private final Instant timestamp;
    private final Instant processedAt;
    
    private ReconciliationEvent(Builder builder) {
        this.id = builder.id;
        this.eventId = builder.eventId;
        this.version = builder.version;
        this.eventType = builder.eventType;
        this.reconciliationId = builder.reconciliationId;
        this.sourceType = builder.sourceType;
        this.targetType = builder.targetType;
        this.amount = builder.amount;
        this.currency = builder.currency;
        this.reference = builder.reference;
        this.previousStatus = builder.previousStatus;
        this.newStatus = builder.newStatus;
        this.reason = builder.reason;
        this.timestamp = builder.timestamp;
        this.processedAt = builder.processedAt;
    }
    
    public static ReconciliationEvent created(UUID reconciliationId, String eventId, int version,
            String sourceType, String targetType, BigDecimal amount, String currency, String reference) {
        return builder()
            .id(UUID.randomUUID())
            .eventId(eventId)
            .version(version)
            .eventType(EventType.CREATED)
            .reconciliationId(reconciliationId)
            .sourceType(sourceType)
            .targetType(targetType)
            .amount(amount)
            .currency(currency)
            .reference(reference)
            .previousStatus(null)
            .newStatus(Status.PENDING)
            .timestamp(Instant.now())
            .processedAt(Instant.now())
            .build();
    }
    
    public static ReconciliationEvent matched(UUID reconciliationId, String eventId, int version,
            String sourceType, String targetType, BigDecimal amount, String reference) {
        return builder()
            .id(UUID.randomUUID())
            .eventId(eventId)
            .version(version)
            .eventType(EventType.MATCHED)
            .reconciliationId(reconciliationId)
            .sourceType(sourceType)
            .targetType(targetType)
            .amount(amount)
            .reference(reference)
            .previousStatus(Status.PENDING)
            .newStatus(Status.MATCHED)
            .timestamp(Instant.now())
            .processedAt(Instant.now())
            .build();
    }
    
    public static ReconciliationEvent mismatched(UUID reconciliationId, String eventId, int version,
            String sourceType, String targetType, BigDecimal amount, String reason) {
        return builder()
            .id(UUID.randomUUID())
            .eventId(eventId)
            .version(version)
            .eventType(EventType.MISMATCHED)
            .reconciliationId(reconciliationId)
            .sourceType(sourceType)
            .targetType(targetType)
            .amount(amount)
            .previousStatus(Status.PENDING)
            .newStatus(Status.MISMATCHED)
            .reason(reason)
            .timestamp(Instant.now())
            .processedAt(Instant.now())
            .build();
    }
    
    public static ReconciliationEvent escalated(UUID reconciliationId, String eventId, int version,
            Status previousStatus, String reason) {
        return builder()
            .id(UUID.randomUUID())
            .eventId(eventId)
            .version(version)
            .eventType(EventType.ESCALATED_TO_MANUAL)
            .reconciliationId(reconciliationId)
            .previousStatus(previousStatus)
            .newStatus(Status.MANUAL)
            .reason(reason)
            .timestamp(Instant.now())
            .processedAt(Instant.now())
            .build();
    }
    
    public static ReconciliationEvent manuallyResolved(UUID reconciliationId, String eventId, int version,
            Status previousStatus, String resolution) {
        return builder()
            .id(UUID.randomUUID())
            .eventId(eventId)
            .version(version)
            .eventType(EventType.MANUALLY_RESOLVED)
            .reconciliationId(reconciliationId)
            .previousStatus(previousStatus)
            .newStatus(Status.MATCHED)
            .reason(resolution)
            .timestamp(Instant.now())
            .processedAt(Instant.now())
            .build();
    }
    
    public static ReconciliationEvent expired(UUID reconciliationId, String eventId, int version) {
        return builder()
            .id(UUID.randomUUID())
            .eventId(eventId)
            .version(version)
            .eventType(EventType.EXPIRED)
            .reconciliationId(reconciliationId)
            .previousStatus(Status.PENDING)
            .newStatus(Status.PENDING)
            .reason("Ventana de reconciliación expirada")
            .timestamp(Instant.now())
            .processedAt(Instant.now())
            .build();
    }
    
    public String getIdempotencyKey() {
        return eventId + ":" + version + ":" + eventType.name();
    }
    
    public UUID getId() {
        return id;
    }
    
    public String getEventId() {
        return eventId;
    }
    
    public int getVersion() {
        return version;
    }
    
    public EventType getEventType() {
        return eventType;
    }
    
    public UUID getReconciliationId() {
        return reconciliationId;
    }
    
    public String getSourceType() {
        return sourceType;
    }
    
    public String getTargetType() {
        return targetType;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public String getReference() {
        return reference;
    }
    
    public Status getPreviousStatus() {
        return previousStatus;
    }
    
    public Status getNewStatus() {
        return newStatus;
    }
    
    public String getReason() {
        return reason;
    }
    
    public Instant getTimestamp() {
        return timestamp;
    }
    
    public Instant getProcessedAt() {
        return processedAt;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static final class Builder {
        private UUID id;
        private String eventId;
        private int version;
        private EventType eventType;
        private UUID reconciliationId;
        private String sourceType;
        private String targetType;
        private BigDecimal amount;
        private String currency;
        private String reference;
        private Status previousStatus;
        private Status newStatus;
        private String reason;
        private Instant timestamp;
        private Instant processedAt;
        
        private Builder() {}
        
        public Builder id(UUID id) {
            this.id = id;
            return this;
        }
        
        public Builder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }
        
        public Builder version(int version) {
            this.version = version;
            return this;
        }
        
        public Builder eventType(EventType eventType) {
            this.eventType = eventType;
            return this;
        }
        
        public Builder reconciliationId(UUID reconciliationId) {
            this.reconciliationId = reconciliationId;
            return this;
        }
        
        public Builder sourceType(String sourceType) {
            this.sourceType = sourceType;
            return this;
        }
        
        public Builder targetType(String targetType) {
            this.targetType = targetType;
            return this;
        }
        
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }
        
        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }
        
        public Builder reference(String reference) {
            this.reference = reference;
            return this;
        }
        
        public Builder previousStatus(Status previousStatus) {
            this.previousStatus = previousStatus;
            return this;
        }
        
        public Builder newStatus(Status newStatus) {
            this.newStatus = newStatus;
            return this;
        }
        
        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }
        
        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }
        
        public Builder processedAt(Instant processedAt) {
            this.processedAt = processedAt;
            return this;
        }
        
        public ReconciliationEvent build() {
            return new ReconciliationEvent(this);
        }
    }
}

// === ARCHIVO: src/main/java/com/example/reconciliation/infrastructure/persistence/PostgreSQLReconciliationRepository.java ===
package com.example.reconciliation.infrastructure.persistence;

import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.domain.model.Reconciliation.Status;
import com.example.reconciliation.domain.repository.ReconciliationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
@Table("reconciliations")
public interface PostgreSQLReconciliationRepository extends R2dbcRepository<ReconciliationEntity, UUID>, 
                                                            ReconciliationRepository {
    
    @Query("SELECT * FROM reconciliations WHERE event_id = :eventId AND version = :version")
    Mono<ReconciliationEntity> findByEventIdAndVersion(String eventId, int version);
    
    @Query("SELECT * FROM reconciliations WHERE status = :status")
    Flux<ReconciliationEntity> findByStatus(Status status);
    
    @Query("SELECT * FROM reconciliations WHERE status = :status AND created_at > :since")
    Flux<ReconciliationEntity> findByStatusAndCreatedAtAfter(Status status, Instant since);
    
    @Query("SELECT * FROM reconciliations WHERE source_type = :sourceType AND target_type = :targetType")
    Flux<ReconciliationEntity> findBySourceTypeAndTargetType(String sourceType, String targetType);
    
    @Query("SELECT * FROM reconciliations WHERE created_at BETWEEN :start AND :end")
    Flux<ReconciliationEntity> findByCreatedAtBetween(Instant start, Instant end);
    
    @Query("SELECT * FROM reconciliations WHERE status = 'PENDING' AND created_at < :cutoff")
    Flux<ReconciliationEntity> findPendingOlderThan(Instant cutoff);
    
    @Query("SELECT COUNT(*) FROM reconciliations WHERE status = :status")
    Mono<Long> countByStatus(Status status);
    
    @Query("SELECT COUNT(*) FROM reconciliations WHERE status = :status AND created_at > :since")
    Mono<Long> countByStatusAndCreatedAtAfter(Status status, Instant since);
    
    @Query("SELECT EXISTS(SELECT 1 FROM reconciliations WHERE event_id = :eventId AND version = :version)")
    Mono<Boolean> existsByEventIdAndVersion(String eventId, int version);
    
    @Modifying
    @Query("DELETE FROM reconciliations WHERE id = :id")
    Mono<Void> deleteById(UUID id);
    
    @Modifying
    @Query("DELETE FROM reconciliations WHERE created_at < :cutoff")
    Mono<Void> deleteOlderThan(Instant cutoff);
    
    @Override
    default Mono<Reconciliation> save(Reconciliation reconciliation) {
        return findById(reconciliation.getId())
            .flatMap(existing -> {
                ReconciliationEntity updated = toEntity(reconciliation);
                updated.setVersion(existing.getVersion() + 1);
                updated.setUpdatedAt(Instant.now());
                return save(updated);
            })
            .switchIfEmpty(Mono.defer(() -> {
                ReconciliationEntity entity = toEntity(reconciliation);
                entity.setVersion(1);
                entity.setCreatedAt(Instant.now());
                entity.setUpdatedAt(Instant.now());
                return save(entity);
            }))
            .map(this::toDomain);
    }
    
    @Override
    default Mono<Optional<Reconciliation>> findById(UUID id) {
        return findById(id)
            .map(entity -> Optional.ofNullable(entity).map(this::toDomain))
            .defaultIfEmpty(Optional.empty());
    }
    
    @Override
    default Mono<Optional<Reconciliation>> findByEventIdAndVersion(String eventId, int version) {
        return findByEventIdAndVersion(eventId, version)
            .map(entity -> Optional.ofNullable(entity).map(this::toDomain))
            .defaultIfEmpty(Optional.empty());
    }
    
    @Override
    default Flux<Reconciliation> findByStatus(Status status) {
        return findByStatus(status).map(this::toDomain);
    }
    
    @Override
    default Flux<Reconciliation> findByStatusAndCreatedAtAfter(Status status, Instant since) {
        return findByStatusAndCreatedAtAfter(status, since).map(this::toDomain);
    }
    
    @Override
    default Flux<Reconciliation> findBySourceTypeAndTargetType(String sourceType, String targetType) {
        return findBySourceTypeAndTargetType(sourceType, targetType).map(this::toDomain);
    }
    
    @Override
    default Flux<Reconciliation> findByCreatedAtBetween(Instant start, Instant end) {
        return findByCreatedAtBetween(start, end).map(this::toDomain);
    }
    
    @Override
    default Flux<Reconciliation> findPendingOlderThan(Instant cutoff) {
        return findPendingOlderThan(cutoff).map(this::toDomain);
    }
    
    @Override
    default Flux<Reconciliation> findAll(int page, int size) {
        return findAll(Pageable.ofSize(size).withPage(page)).map(this::toDomain);
    }
    
    private ReconciliationEntity toEntity(Reconciliation reconciliation) {
        ReconciliationEntity entity = new ReconciliationEntity();
        entity.setId(reconciliation.getId());
        entity.setEventId(reconciliation.getEventId());
        entity.setVersion(reconciliation.getVersion());
        entity.setSourceType(reconciliation.getSourceType());
        entity.setTargetType(reconciliation.getTargetType());
        entity.setStatus(reconciliation.getStatus());
        entity.setAmount(reconciliation.getAmount());
        entity.setCurrency(reconciliation.getCurrency());
        entity.setReference(reconciliation.getReference());
        entity.setDescription(reconciliation.getDescription());
        entity.setMatchedAt(reconciliation.getMatchedAt());
        entity.setCreatedAt(reconciliation.getCreatedAt());
        entity.setUpdatedAt(reconciliation.getUpdatedAt());
        entity.setMismatchReason(reconciliation.getMismatchReason());
        entity.setManualResolution(reconciliation.getManualResolution());
        return entity;
    }
    
    private Reconciliation toDomain(ReconciliationEntity entity) {
        return Reconciliation.restore(
            entity.getId(),
            entity.getEventId(),
            entity.getVersion(),
            entity.getSourceType(),
            entity.getTargetType(),
            entity.getStatus(),
            entity.getAmount(),
            entity.getCurrency(),
            entity.getReference(),
            entity.getDescription(),
            entity.getMatchedAt(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getMismatchReason(),
            entity.getManualResolution()
        );
    }
    
    @Table("reconciliations")
    public static class ReconciliationEntity {
        private UUID id;
        private String eventId;
        private int version;
        private String sourceType;
        private String targetType;
        private Status status;
        private BigDecimal amount;
        private String currency;
        private String reference;
        private String description;
        private Instant matchedAt;
        private Instant createdAt;
        private Instant updatedAt;
        private String mismatchReason;
        private String manualResolution;
        
        public UUID getId() {
            return id;
        }
        
        public void setId(UUID id) {
            this.id = id;
        }
        
        public String getEventId() {
            return eventId;
        }
        
        public void setEventId(String eventId) {
            this.eventId = eventId;
        }
        
        public int getVersion() {
            return version;
        }
        
        public void setVersion(int version) {
            this.version = version;
        }
        
        public String getSourceType() {
            return sourceType;
        }
        
        public void setSourceType(String sourceType) {
            this.sourceType = sourceType;
        }
        
        public String getTargetType() {
            return targetType;
        }
        
        public void setTargetType(String targetType) {
            this.targetType = targetType;
        }
        
        public Status getStatus() {
            return status;
        }
        
        public void setStatus(Status status) {
            this.status = status;
        }
        
        public BigDecimal getAmount() {
            return amount;
        }
        
        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
        
        public String getCurrency() {
            return currency;
        }
        
        public void setCurrency(String currency) {
            this.currency = currency;
        }
        
        public String getReference() {
            return reference;
        }
        
        public void setReference(String reference) {
            this.reference = reference;
        }
        
        public String getDescription() {
            return description;
        }
        
        public void setDescription(String description) {
            this.description = description;
        }
        
        public Instant getMatchedAt() {
            return matchedAt;
        }
        
        public void setMatchedAt(Instant matchedAt) {
            this.matchedAt = matchedAt;
        }
        
        public Instant getCreatedAt() {
            return createdAt;
        }
        
        public void setCreatedAt(Instant createdAt) {
            this.createdAt = createdAt;
        }
        
        public Instant getUpdatedAt() {
            return updatedAt;
        }
        
        public void setUpdatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
        }
        
        public String getMismatchReason() {
            return mismatchReason;
        }
        
        public void setMismatchReason(String mismatchReason) {
            this.mismatchReason = mismatchReason;
        }
        
        public String getManualResolution() {
            return manualResolution;
        }
        
        public void setManualResolution(String manualResolution) {
            this.manualResolution = manualResolution;
        }
    }
}

// === ARCHIVO: src/main/java/com/example/reconciliation/application/usecases/ReconcileMovementUseCase.java ===
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

// === ARCHIVO: src/main/java/com/example/reconciliation/application/services/ReconciliationService.java ===
package com.example.reconciliation.application.services;

import com.example.reconciliation.domain.model.Movement;
import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.domain.model.Reconciliation.Status;
import com.example.reconciliation.domain.repository.ReconciliationRepository;
import com.example.reconciliation.application.usecases.ReconcileMovementUseCase;
import com.example.reconciliation.domain.event.ReconciliationEvent;
import com.example.reconciliation.infrastructure.monitoring.LagMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
public class ReconciliationService {

    private static final Logger log = LoggerFactory.getLogger(ReconciliationService.class);
    private static final int MATCHING_WINDOW_MINUTES = 5;
    private static final int MANUAL_ESCALATION_THRESHOLD_HOURS = 24;

    private final ReconciliationRepository reconciliationRepository;
    private final ReconcileMovementUseCase reconcileMovementUseCase;
    private final LagMonitor lagMonitor;

    public ReconciliationService(
            final ReconciliationRepository reconciliationRepository,
            final ReconcileMovementUseCase reconcileMovementUseCase,
            final LagMonitor lagMonitor) {
        this.reconciliationRepository = reconciliationRepository;
        this.reconcileMovementUseCase = reconcileMovementUseCase;
        this.lagMonitor = lagMonitor;
    }

    public Mono<Reconciliation> initiateReconciliation(final Movement movement) {
        log.info("Iniciando reconciliación para movimiento: eventId={}, version={}",
                movement.getEventId(), movement.getVersion());

        return reconciliationRepository.existsByEventIdAndVersion(
                movement.getEventId(), movement.getVersion())
            .flatMap(exists -> {
                if (exists) {
                    log.warn("Movimiento duplicado detectado: eventId={}, version={}",
                            movement.getEventId(), movement.getVersion());
                    return reconciliationRepository
                        .findByEventIdAndVersion(movement.getEventId(), movement.getVersion())
                        .flatMap(opt -> opt.isPresent() 
                            ? Mono.just(opt.get()) 
                            : Mono.error(new IllegalStateException("Inconsistencia de estado")));
                }
                return createNewReconciliation(movement);
            });
    }

    private Mono<Reconciliation> createNewReconciliation(final Movement movement) {
        final Reconciliation reconciliation = Reconciliation.create(
                movement.getEventId(),
                movement.getVersion(),
                movement.getSourceType().name(),
                "N/A",
                movement.getAmount(),
                movement.getCurrency(),
                movement.getReference(),
                "Reconciliación iniciada para " + movement.getSourceType()
        );

        return reconciliationRepository.save(reconciliation)
            .doOnSuccess(saved -> {
                log.info("Reconciliación creada: id={}, status={}", saved.getId(), saved.getStatus());
                lagMonitor.recordReconciliationCreated(saved.getCreatedAt());
            });
    }

    public Mono<Reconciliation> processMatching(final UUID reconciliationId,
                                                 final List<Movement> matchingMovements) {
        log.info("Procesando matching para reconciliación: {}, movimientos encontrados: {}",
                reconciliationId, matchingMovements.size());

        return reconciliationRepository.findById(reconciliationId)
            .flatMap(optReconciliation -> {
                if (optReconciliation.isEmpty()) {
                    return Mono.error(new IllegalArgumentException(
                            "Reconciliación no encontrada: " + reconciliationId));
                }
                final Reconciliation reconciliation = optReconciliation.get();
                return performMatching(reconciliation, matchingMovements);
            });
    }

    private Mono<Reconciliation> performMatching(final Reconciliation reconciliation,
                                                  final List<Movement> movements) {
        if (!reconciliation.canTransitionTo(Status.MATCHED)) {
            log.warn("Reconciliación {} no puede transición a MATCHED desde {}",
                    reconciliation.getId(), reconciliation.getStatus());
            return Mono.just(reconciliation);
        }

        final boolean allMatch = verifyAllMovementsMatch(reconciliation, movements);
        if (allMatch && movements.size() >= 3) {
            reconciliation.match();
            return reconciliationRepository.save(reconciliation)
                .doOnSuccess(saved -> {
                    log.info("Reconciliación {} marcada como MATCHED", saved.getId());
                    lagMonitor.recordReconciliationCompleted(saved.getMatchedAt());
                });
        } else if (movements.size() > 0) {
            final String reason = String.format("Movimientos coincidentes insuficientes: %d de 3",
                    movements.size());
            return handleMismatch(reconciliation, reason);
        } else {
            return checkForExpiredReconciliation(reconciliation);
        }
    }

    private boolean verifyAllMovementsMatch(final Reconciliation reconciliation,
                                             final List<Movement> movements) {
        return movements.stream()
                .allMatch(m -> m.matchesAmount(reconciliation.getAmount()) &&
                              m.matchesReference(reconciliation.getReference()));
    }

    public Mono<Reconciliation> handleMismatch(final Reconciliation reconciliation,
                                                final String reason) {
        if (!reconciliation.canTransitionTo(Status.MISMATCHED)) {
            log.warn("Reconciliación {} no puede transición a MISMATCHED desde {}",
                    reconciliation.getId(), reconciliation.getStatus());
            return Mono.just(reconciliation);
        }

        reconciliation.mismatch(reason);
        return reconciliationRepository.save(reconciliation)
            .doOnSuccess(saved -> {
                log.warn("Reconciliación {} marcada como MISMATCHED: {}", saved.getId(), reason);
                lagMonitor.recordMismatch(saved.getUpdatedAt());
            });
    }

    public Mono<Reconciliation> escalateToManual(final UUID reconciliationId,
                                                  final String reason) {
        log.warn("Escalando reconciliación {} a revisión manual: {}", reconciliationId, reason);

        return reconciliationRepository.findById(reconciliationId)
            .flatMap(opt -> {
                if (opt.isEmpty()) {
                    return Mono.error(new IllegalArgumentException(
                            "Reconciliación no encontrada: " + reconciliationId));
                }
                final Reconciliation reconciliation = opt.get();
                if (!reconciliation.canTransitionTo(Status.MANUAL)) {
                    return Mono.error(new IllegalStateException(
                            "No se puede escalar a MANUAL desde: " + reconciliation.getStatus()));
                }
                reconciliation.escalateToManual(reason);
                return reconciliationRepository.save(reconciliation);
            });
    }

    public Mono<Reconciliation> resolveManually(final UUID reconciliationId,
                                                final String resolution) {
        log.info("Resolviendo manualmente reconciliación {}: {}", reconciliationId, resolution);

        return reconciliationRepository.findById(reconciliationId)
            .flatMap(opt -> {
                if (opt.isEmpty()) {
                    return Mono.error(new IllegalArgumentException(
                            "Reconciliación no encontrada: " + reconciliationId));
                }
                final Reconciliation reconciliation = opt.get();
                if (reconciliation.getStatus() != Status.MANUAL &&
                    reconciliation.getStatus() != Status.MISMATCHED) {
                    return Mono.error(new IllegalStateException(
                            "Solo se pueden resolver estados MANUAL o MISMATCHED"));
                }
                reconciliation.resolveManually(resolution);
                return reconciliationRepository.save(reconciliation);
            });
    }

    private Mono<Reconciliation> checkForExpiredReconciliation(final Reconciliation reconciliation) {
        final Instant windowStart = reconciliation.getCreatedAt()
                .plus(Duration.ofMinutes(MATCHING_WINDOW_MINUTES));
        if (reconciliation.isExpired(MATCHING_WINDOW_MINUTES)) {
            log.warn("Reconciliación {} expiró sin匹配 completo", reconciliation.getId());
            return escalateToManual(reconciliation.getId(),
                    "Ventana de matching expirada sin reconciliación completa");
        }
        return Mono.just(reconciliation);
    }

    public Flux<Reconciliation> findReconciliationsByStatus(final Status status) {
        log.debug("Buscando reconciliaciones con estado: {}", status);
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
        log.info("Reintentando reconciliación: {}", reconciliationId);

        return reconciliationRepository.findById(reconciliationId)
            .flatMap(opt -> {
                if (opt.isEmpty()) {
                    return Mono.error(new IllegalArgumentException(
                            "Reconciliación no encontrada: " + reconciliationId));
                }
                final Reconciliation reconciliation = opt.get();
                if (reconciliation.getStatus() != Status.MISMATCHED &&
                    reconciliation.getStatus() != Status.MANUAL) {
                    return Mono.error(new IllegalStateException(
                            "Solo se pueden reintentar estados MISMATCHED o MANUAL"));
                }
                return reconcileMovementUseCase.execute(reconciliation.getEventId(),
                        reconciliation.getVersion());
            });
    }

    public Mono<Void> reprocessAllPending() {
        log.info("Iniciando reprocesamiento de todas las reconciliaciones pendientes");

        return reconciliationRepository.findByStatus(Status.PENDING)
            .flatMap(reconciliation -> reconcileMovementUseCase.execute(
                    reconciliation.getEventId(), reconciliation.getVersion())
                    .onErrorResume(e -> {
                        log.error("Error en reprocesamiento de {}: {}",
                                reconciliation.getId(), e.getMessage());
                        return Mono.empty();
                    }), false)
            .then();
    }

    public Mono<Boolean> validateReconciliationIntegrity(final UUID reconciliationId) {
        return reconciliationRepository.findById(reconciliationId)
            .flatMap(opt -> {
                if (opt.isEmpty()) {
                    return Mono.just(false);
                }
                final Reconciliation reconciliation = opt.get();
                final boolean hasValidState = reconciliation.getStatus() != null;
                final boolean hasAmount = reconciliation.getAmount() != null &&
                        reconciliation.getAmount().compareTo(BigDecimal.ZERO) > 0;
                final boolean hasReference = reconciliation.getReference() != null &&
                        !reconciliation.getReference().isBlank();
                return Mono.just(hasValidState && hasAmount && hasReference);
            });
    }
}

// === ARCHIVO: src/main/java/com/example/reconciliation/infrastructure/messaging/KafkaMovementConsumer.java ===
package com.example.reconciliation.infrastructure.messaging;

import com.example.reconciliation.application.services.ReconciliationService;
import com.example.reconciliation.domain.model.Movement;
import com.example.reconciliation.domain.model.Movement.SourceType;
import com.example.reconciliation.domain.model.Movement.MovementType;
import com.example.reconciliation.domain.event.ReconciliationEvent;
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
                if (reconciliation.getStatus() == com.example.reconciliation.domain.model.Reconciliation.Status.PENDING) {
                    return executeMatchingFlow(reconciliation.getId(), movement);
                }
                return Mono.just(reconciliation);
            });
    }

    private Mono<com.example.reconciliation.domain.model.Reconciliation> executeMatchingFlow(
            final UUID reconciliationId,
            final Movement movement) {
        return reconcileMovementUseCase.findMatchingMovements(reconciliationId)
            .collectList()
            .flatMap(matchingMovements -> 
                    reconciliationService.processMatching(reconciliationId, matchingMovements))
            .onErrorResume(error -> {
                log.error("Error en flujo de matching para {}: {}", 
                        reconciliationId, error.getMessage());
                return reconciliationService.escalateToManual(reconciliationId,
                        "Error en procesamiento: " + error.getMessage());
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
        return reactor.kafka.receiver.Receiver.create(
                org.apache.kafka.clients.consumer.ConsumerConfig.
        log.warn("Método consumeMovementStream no implementado - usar consumeMovement");
        return Flux.empty();
    }
}

// === ARCHIVO: src/main/java/com/example/reconciliation/infrastructure/config/KafkaConfig.java ===
package com.example.reconciliation.infrastructure.config;

import com.example.reconciliation.domain.model.Movement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;
import reactor.core.scheduler.Schedulers;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableKafka
public class KafkaConfig {

    private static final Logger log = LoggerFactory.getLogger(KafkaConfig.class);

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id:reconciliation-processor}")
    private String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset:earliest}")
    private String autoOffsetReset;

    @Value("${spring.kafka.consumer.max-poll-records:500}")
    private int maxPollRecords;

    @Value("${spring.kafka.consumer.fetch-min-size:1048576}")
    private int fetchMinSize;

    @Value("${spring.kafka.consumer.fetch-max-wait:500}")
    private int fetchMaxWait;

    @Value("${spring.kafka.listener.ack-mode:manual}")
    private String ackMode;

    @Value("${spring.kafka.listener.concurrency:3}")
    private int concurrency;

    @Value("${spring.kafka.retry.max-attempts:3}")
    private int maxAttempts;

    @Value("${spring.kafka.retry.back-off-ms:1000}")
    private long backOffMs;

    @Bean
    public ObjectMapper kafkaObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public Map<String, Object> consumerProperties() {
        final Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, fetchMinSize);
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, fetchMaxWait);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.reconciliation.domain.model");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Movement.class.getName());
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.ADD_TYPE_INFO_HEADERS, false);
        log.info("Configuración de consumidor Kafka inicializada: bootstrapServers={}, groupId={}",
                bootstrapServers, groupId);
        return props;
    }

    @Bean
    public Map<String, Object> producerProperties() {
        final Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "lz4");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        log.info("Configuración de productor Kafka inicializada");
        return props;
    }

    @Bean
    public ConsumerFactory<String, Movement> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProperties());
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerProperties());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Movement> kafkaListenerContainerFactory(
            final ConsumerFactory<String, Movement> consumerFactory,
            final CommonErrorHandler errorHandler) {
        final ConcurrentKafkaListenerContainerFactory<String, Movement> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(concurrency);
        factory.getContainerProperties().setAckMode(
                org.springframework.kafka.listener.AckMode.valueOf(ackMode));
        factory.setCommonErrorHandler(errorHandler);
        factory.setBatchListener(true);
        log.info("KafkaListenerContainerFactory configurado con concurrency={}, ackMode={}",
                concurrency, ackMode);
        return factory;
    }

    @Bean
    public CommonErrorHandler kafkaErrorHandler(final KafkaTemplate<String, Object> kafkaTemplate) {
        final DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (record, ex) -> new org.apache.kafka.common.TopicPartition(
                        record.topic() + ".dlt", record.partition()));

        final FixedBackOff backOff = new FixedBackOff(backOffMs, maxAttempts);
        final DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, backOff);

        errorHandler.addNotRetryableExceptions(
                IllegalArgumentException.class,
                NullPointerException.class
        );

        log.info("Error handler de Kafka configurado: maxAttempts={}, backOffMs={}",
                maxAttempts, backOffMs);
        return errorHandler;
    }

    @Bean
    public KafkaTransactionManager<String, Object> kafkaTransactionManager(
            final ProducerFactory<String, Object> producerFactory) {
        final KafkaTransactionManager<String, Object> manager =
                new KafkaTransactionManager<>(producerFactory);
        log.info("KafkaTransactionManager configurado para transacciones");
        return manager;
    }

    @Bean
    public ReactorKafkaProducer<String, Object> reactorKafkaProducer(
            final ProducerFactory<String, Object> producerFactory) {
        final Map<String, Object> props = producerFactory.getConfigurationProperties();
        final org.apache.kafka.clients.producer.ProducerConfig config =
                new org.apache.kafka.clients.producer.ProducerConfig(props);

        final ReactorKafkaProducer<String, Object> producer = new ReactorKafkaProducer<>
                (config, new StringSerializer(), new JsonSerializer<>());

        log.info("ReactorKafkaProducer configurado para procesamiento reactivo");
        return producer;
    }

    @Bean
    public ReactorKafkaConsumer<String, Movement> reactorKafkaConsumer(
            final Map<String, Object> consumerProps) {
        consumerProps.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        final org.apache.kafka.clients.consumer.ConsumerConfig config =
                new org.apache.kafka.clients.consumer.ConsumerConfig(consumerProps);

        final ReactorKafkaConsumer<String, Movement> consumer = new ReactorKafkaConsumer<>
                (config, new StringDeserializer(), new JsonDeserializer<>(Movement.class));

        log.info("ReactorKafkaConsumer configurado para consumo reactivo");
        return consumer;
    }

    @Bean
    public java.util.concurrent.ExecutorService kafkaExecutor() {
        final java.util.concurrent.ThreadPoolExecutorFactoryBean factory =
                new java.util.concurrent.ThreadPoolExecutorFactoryBean();
        factory.setCorePoolSize(concurrency);
        factory.setMaxPoolSize(concurrency * 2);
        factory.setQueueCapacity(100);
        factory.setThreadNamePrefix("kafka-consumer-");
        factory.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        factory.afterPropertiesSet();

        final java.util.concurrent.ExecutorService executor = factory.getObject();
        log.info("ExecutorService para Kafka configurado: corePoolSize={}, maxPoolSize={}",
                concurrency, concurrency * 2);
        return executor;
    }

    @Bean
    public org.springframework.kafka.config.TopicBuilder movementTopic() {
        return org.springframework.kafka.config.TopicBuilder.name("banking.movements")
                .partitions(6)
                .replicas(3)
                .config(org.apache.kafka.common.config.TopicConfig.RETENTION_MS_CONFIG, "604800000")
                .config(org.apache.kafka.common.config.TopicConfig.CLEANUP_POLICY_CONFIG, "compact")
                .config(org.apache.kafka.common.config.TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, "2");
    }

    @Bean
    public org.springframework.kafka.config.TopicBuilder movementDltTopic() {
        return org.springframework.kafka.config.TopicBuilder.name("banking.movements.dlt")
                .partitions(1)
                .replicas(3)
                .config(org.apache.kafka.common.config.TopicConfig.RETENTION_MS_CONFIG, "86400000");
    }
}


// === ARCHIVO: src/main/java/com/example/reconciliation/infrastructure/config/PostgreSQLConfig.java ===
package com.example.reconciliation.infrastructure.config;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.example.reconciliation.domain.repository",
    entityManagerFactoryRef = "reconciliationEntityManagerFactory",
    transactionManagerRef = "reconciliationTransactionManager"
)
public class PostgreSQLConfig {

    private static final String ENTITY_PACKAGE = "com.example.reconciliation.domain.model";
    private static final int CONNECTION_TIMEOUT = 30;
    private static final int IDLE_TIMEOUT = 600;
    private static final int MAX_LIFETIME = 1800;
    private static final int MINIMUM_IDLE = 5;
    private static final int MAXIMUM_POOL_SIZE = 20;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.reconciliation")
    public DataSource reconciliationDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        
        return dataSourceBuilder
            .type(org.apache.tomcat.jdbc.pool.DataSource.class)
            .build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean reconciliationEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            DataSource reconciliationDataSource) {
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "validate");
        properties.put("hibernate.jdbc.batch_size", 50);
        properties.put("hibernate.order_inserts", true);
        properties.put("hibernate.order_updates", true);
        properties.put("hibernate.jdbc.batch_versioned_data", true);
        properties.put("hibernate.cache.use_second_level_cache", false);
        properties.put("hibernate.generate_statistics", false);
        properties.put("hibernate.format_sql", false);
        properties.put("hibernate.use_sql_comments", false);
        
        return builder
            .dataSource(reconciliationDataSource)
            .packages(ENTITY_PACKAGE)
            .properties(properties)
            .persistenceUnit("reconciliation")
            .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager reconciliationTransactionManager(
            EntityManagerFactory reconciliationEntityManagerFactory) {
        return new JpaTransactionManager(reconciliationEntityManagerFactory);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.reconciliation.hikari")
    public HikariConfigurationProperties hikariProperties() {
        return new HikariConfigurationProperties();
    }

    public static class HikariConfigurationProperties {
        private int connectionTimeout = CONNECTION_TIMEOUT;
        private int idleTimeout = IDLE_TIMEOUT;
        private int maxLifetime = MAX_LIFETIME;
        private int minimumIdle = MINIMUM_IDLE;
        private int maximumPoolSize = MAXIMUM_POOL_SIZE;
        private String poolName = "ReconciliationHikariPool";
        private boolean autoCommit = false;
        private boolean allowPoolSuspension = false;
        private boolean readOnly = false;
        private String registerMbeans = "true";
        private String connectionTestQuery = "SELECT 1";

        public int getConnectionTimeout() { return connectionTimeout; }
        public void setConnectionTimeout(int connectionTimeout) { this.connectionTimeout = connectionTimeout; }
        public int getIdleTimeout() { return idleTimeout; }
        public void setIdleTimeout(int idleTimeout) { this.idleTimeout = idleTimeout; }
        public int getMaxLifetime() { return maxLifetime; }
        public void setMaxLifetime(int maxLifetime) { this.maxLifetime = maxLifetime; }
        public int getMinimumIdle() { return minimumIdle; }
        public void setMinimumIdle(int minimumIdle) { this.minimumIdle = minimumIdle; }
        public int getMaximumPoolSize() { return maximumPoolSize; }
        public void setMaximumPoolSize(int maximumPoolSize) { this.maximumPoolSize = maximumPoolSize; }
        public String getPoolName() { return poolName; }
        public void setPoolName(String poolName) { this.poolName = poolName; }
        public boolean isAutoCommit() { return autoCommit; }
        public void setAutoCommit(boolean autoCommit) { this.autoCommit = autoCommit; }
        public boolean isAllowPoolSuspension() { return allowPoolSuspension; }
        public void setAllowPoolSuspension(boolean allowPoolSuspension) { this.allowPoolSuspension = allowPoolSuspension; }
        public boolean isReadOnly() { return readOnly; }
        public void setReadOnly(boolean readOnly) { this.readOnly = readOnly; }
        public String getRegisterMbeans() { return registerMbeans; }
        public void setRegisterMbeans(String registerMbeans) { this.registerMbeans = registerMbeans; }
        public String getConnectionTestQuery() { return connectionTestQuery; }
        public void setConnectionTestQuery(String connectionTestQuery) { this.connectionTestQuery = connectionTestQuery; }
    }
}

// === ARCHIVO: src/main/java/com/example/reconciliation/infrastructure/rest/ReconciliationController.java ===
package com.example.reconciliation.infrastructure.rest;

import com.example.reconciliation.application.services.ReconciliationService;
import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.domain.model.Reconciliation.Status;
import com.example.reconciliation.domain.repository.ReconciliationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reconciliations")
public class ReconciliationController {

    private static final Logger log = LoggerFactory.getLogger(ReconciliationController.class);
    private static final int DEFAULT_PAGE_SIZE = 50;
    private static final int MAX_PAGE_SIZE = 500;

    private final ReconciliationService reconciliationService;
    private final ReconciliationRepository reconciliationRepository;

    public ReconciliationController(
            ReconciliationService reconciliationService,
            ReconciliationRepository reconciliationRepository) {
        this.reconciliationService = reconciliationService;
        this.reconciliationRepository = reconciliationRepository;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ReconciliationResponse>> getById(@PathVariable UUID id) {
        log.debug("Fetching reconciliation by id: {}", id);
        return reconciliationRepository.findById(id)
            .map(reconciliation -> ResponseEntity.ok(toResponse(reconciliation)))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Mono<ReconciliationPageResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        
        int pageSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
        log.debug("Fetching reconciliations - page: {}, size: {}", page, pageSize);
        
        return reconciliationRepository.findAll(page, pageSize)
            .collectList()
            .zipWith(reconciliationRepository.count())
            .map(tuple -> new ReconciliationPageResponse(
                tuple.getT1().stream().map(this::toResponse).toList(),
                page,
                pageSize,
                tuple.getT2()
            ));
    }

    @GetMapping("/status/{status}")
    public Flux<ReconciliationResponse> getByStatus(@PathVariable Status status) {
        log.debug("Fetching reconciliations by status: {}", status);
        return reconciliationRepository.findByStatus(status)
            .map(this::toResponse);
    }

    @GetMapping("/pending")
    public Flux<ReconciliationResponse> getPending(
            @RequestParam(defaultValue = "300") int windowSeconds) {
        
        Instant cutoff = Instant.now().minusSeconds(windowSeconds);
        log.debug("Fetching pending reconciliations older than {} seconds", windowSeconds);
        
        return reconciliationRepository.findPendingOlderThan(cutoff)
            .map(this::toResponse);
    }

    @GetMapping("/statistics")
    public Mono<Map<String, Long>> getStatistics() {
        log.debug("Fetching reconciliation statistics");
        return reconciliationService.getStatistics();
    }

    @PostMapping("/{id}/match")
    public Mono<ResponseEntity<ReconciliationResponse>> matchReconciliation(
            @PathVariable UUID id) {
        
        log.info("Manual match requested for reconciliation: {}", id);
        return reconciliationService.manualMatch(id)
            .map(reconciliation -> ResponseEntity.ok(toResponse(reconciliation)))
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .onErrorResume(e -> {
                log.error("Error matching reconciliation {}: {}", id, e.getMessage());
                return Mono.just(ResponseEntity.badRequest().build());
            });
    }

    @PostMapping("/{id}/resolve")
    public Mono<ResponseEntity<ReconciliationResponse>> resolveReconciliation(
            @PathVariable UUID id,
            @RequestBody ResolveRequest request) {
        
        log.info("Manual resolution requested for reconciliation: {} with resolution: {}", 
            id, request.resolution());
        return reconciliationService.resolveManually(id, request.resolution())
            .map(reconciliation -> ResponseEntity.ok(toResponse(reconciliation)))
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .onErrorResume(e -> {
                log.error("Error resolving reconciliation {}: {}", id, e.getMessage());
                return Mono.just(ResponseEntity.badRequest().build());
            });
    }

    @PutMapping("/{id}/escalate")
    public Mono<ResponseEntity<ReconciliationResponse>> escalateToManual(
            @PathVariable UUID id,
            @RequestBody EscalateRequest request) {
        
        log.info("Escalation to manual for reconciliation: {} with reason: {}", 
            id, request.reason());
        return reconciliationService.escalateToManual(id, request.reason())
            .map(reconciliation -> ResponseEntity.ok(toResponse(reconciliation)))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/by-reference")
    public Flux<ReconciliationResponse> searchByReference(
            @RequestParam String reference,
            @RequestParam(required = false) String sourceType,
            @RequestParam(required = false) String targetType) {
        
        log.debug("Searching reconciliations by reference: {}, sourceType: {}, targetType: {}",
            reference, sourceType, targetType);
        
        return reconciliationService.searchByReference(reference, sourceType, targetType)
            .map(this::toResponse);
    }

    @PostMapping("/reprocess")
    public Mono<ResponseEntity<String>> reprocess(
            @RequestBody ReprocessRequest request) {
        
        log.info("Reprocess requested for eventId: {}, version: {}", 
            request.eventId(), request.version());
        return reconciliationService.reprocess(request.eventId(), request.version())
            .map(r -> ResponseEntity.ok("Reprocess initiated for " + request.eventId()))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private ReconciliationResponse toResponse(Reconciliation reconciliation) {
        return new ReconciliationResponse(
            reconciliation.getId(),
            reconciliation.getEventId(),
            reconciliation.getVersion(),
            reconciliation.getSourceType(),
            reconciliation.getTargetType(),
            reconciliation.getStatus().name(),
            reconciliation.getAmount(),
            reconciliation.getCurrency(),
            reconciliation.getReference(),
            reconciliation.getDescription(),
            reconciliation.getMatchedAt(),
            reconciliation.getCreatedAt(),
            reconciliation.getUpdatedAt(),
            reconciliation.getMismatchReason(),
            reconciliation.getManualResolution()
        );
    }

    public record ReconciliationResponse(
        UUID id,
        String eventId,
        int version,
        String sourceType,
        String targetType,
        String status,
        java.math.BigDecimal amount,
        String currency,
        String reference,
        String description,
        Instant matchedAt,
        Instant createdAt,
        Instant updatedAt,
        String mismatchReason,
        String manualResolution
    ) {}

    public record ReconciliationPageResponse(
        java.util.List<ReconciliationResponse> items,
        int page,
        int size,
        long total
    ) {}

    public record ResolveRequest(String resolution) {}
    public record EscalateRequest(String reason) {}
    public record ReprocessRequest(String eventId, int version) {}
}

// === ARCHIVO: src/main/java/com/example/reconciliation/infrastructure/monitoring/LagMonitor.java ===
package com.example.reconciliation.infrastructure.monitoring;

import com.example.reconciliation.domain.repository.ReconciliationRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class LagMonitor {

    private static final Logger log = LoggerFactory.getLogger(LagMonitor.class);
    private static final Duration SLA_THRESHOLD = Duration.ofMinutes(5);
    private static final Duration CHECK_INTERVAL = Duration.ofSeconds(30);
    private static final Duration GRACE_PERIOD = Duration.ofSeconds(10);

    private final MeterRegistry meterRegistry;
    private final ReconciliationRepository reconciliationRepository;
    private final AtomicLong currentLagSeconds = new AtomicLong(0);
    private final AtomicReference<Instant> lastCheckTime = new AtomicReference<>(Instant.now());
    private final AtomicReference<Instant> lastAlertTime = new AtomicReference<>(Instant.EPOCH);
    private final AtomicReference<String> lagStatus = new AtomicReference<>("NORMAL");
    
    private final Counter lagExceededCounter;
    private final Counter lagNormalCounter;
    private final Timer lagCheckTimer;

    public LagMonitor(MeterRegistry meterRegistry, ReconciliationRepository reconciliationRepository) {
        this.meterRegistry = meterRegistry;
        this.reconciliationRepository = reconciliationRepository;
        
        this.lagExceededCounter = Counter.builder("reconciliation.lag.exceeded")
            .description("Number of times lag exceeded SLA threshold")
            .tag("sla_threshold_minutes", String.valueOf(SLA_THRESHOLD.toMinutes()))
            .register(meterRegistry);
        
        this.lagNormalCounter = Counter.builder("reconciliation.lag.normal")
            .description("Number of times lag returned to normal")
            .register(meterRegistry);
        
        this.lagCheckTimer = Timer.builder("reconciliation.lag.check.duration")
            .description("Time taken to perform lag check")
            .register(meterRegistry);
        
        Gauge.builder("reconciliation.lag.current.seconds", currentLagSeconds, AtomicLong::get)
            .description("Current reconciliation lag in seconds")
            .tag("sla_threshold_seconds", String.valueOf(SLA_THRESHOLD.getSeconds()))
            .register(meterRegistry);
        
        Gauge.builder("reconciliation.lag.status", lagStatus, AtomicReference::get)
            .description("Current lag status: NORMAL, WARNING, CRITICAL")
            .register(meterRegistry);
        
        initializeMetrics();
    }

    private void initializeMetrics() {
        Gauge.builder("reconciliation.lag.sla.threshold.seconds", 
            () -> (double) SLA_THRESHOLD.getSeconds())
            .description("SLA threshold for lag in seconds")
            .register(meterRegistry);
        
        Gauge.builder("reconciliation.pending.count", 
            reconciliationRepository.countByStatus(com.example.reconciliation.domain.model.Reconciliation.Status.PENDING))
            .description("Number of pending reconciliations")
            .register(meterRegistry);
    }

    public Mono<LagMetrics> calculateCurrentLag() {
        return Timer.start(meterRegistry).flatMap(timer -> 
            reconciliationRepository.findByStatus(com.example.reconciliation.domain.model.Reconciliation.Status.PENDING)
                .collectList()
                .map(pendingReconciliations -> {
                    timer.stop(lagCheckTimer);
                    return calculateLagMetrics(pendingReconciliations);
                })
        ).doOnError(e -> {
            log.error("Error calculating lag metrics: {}", e.getMessage());
            meterRegistry.counter("reconciliation.lag.check.errors").increment();
        });
    }

    private LagMetrics calculateLagMetrics(java.util.List<com.example.reconciliation.domain.model.Reconciliation> pending) {
        Instant now = Instant.now();
        Instant oldestPending = pending.stream()
            .map(com.example.reconciliation.domain.model.Reconciliation::getCreatedAt)
            .min(Instant::compareTo)
            .orElse(now);
        
        long lagSeconds = Duration.between(oldestPending, now).getSeconds();
        currentLagSeconds.set(lagSeconds);
        lastCheckTime.set(now);
        
        String status = determineStatus(lagSeconds);
        lagStatus.set(status);
        
        LagMetrics metrics = new LagMetrics(
            lagSeconds,
            pending.size(),
            oldestPending,
            now,
            status,
            lagSeconds > SLA_THRESHOLD.getSeconds()
        );
        
        if (metrics.exceedsSla()) {
            handleSlaExceeded(metrics);
        } else if (lagStatus.compareAndSet("CRITICAL", "NORMAL") || 
                   lagStatus.compareAndSet("WARNING", "NORMAL")) {
            lagNormalCounter.increment();
            log.info("Lag returned to normal: {} seconds ({} pending items)", 
                lagSeconds, pending.size());
        }
        
        return metrics;
    }

    private String determineStatus(long lagSeconds) {
        long thresholdSeconds = SLA_THRESHOLD.getSeconds();
        if (lagSeconds >= thresholdSeconds * 2) {
            return "CRITICAL";
        } else if (lagSeconds >= thresholdSeconds) {
            return "WARNING";
        }
        return "NORMAL";
    }

    private void handleSlaExceeded(LagMetrics metrics) {
        String previousStatus = lagStatus.get();
        if (!"CRITICAL".equals(previousStatus) && !"WARNING".equals(previousStatus)) {
            lagExceededCounter.increment();
            triggerAlert(metrics);
        } else if ("WARNING".equals(previousStatus) && "CRITICAL".equals(metrics.status())) {
            lagExceededCounter.increment();
            triggerAlert(metrics);
        }
        lagStatus.set(metrics.status());
    }

    private void triggerAlert(LagMetrics metrics) {
        Instant now = Instant.now();
        Instant lastAlert = lastAlertTime.get();
        
        if (Duration.between(lastAlert, now).compareTo(Duration.ofMinutes(5)) > 0) {
            lastAlertTime.set(now);
            log.warn("ALERT: Reconciliation lag exceeded SLA! Current: {}s, Threshold: {}s, " +
                "Pending: {}, Status: {}", 
                metrics.lagSeconds(), 
                SLA_THRESHOLD.getSeconds(),
                metrics.pendingCount(),
                metrics.status());
            
            meterRegistry.counter("reconciliation.lag.alerts.triggered").increment();
            publishAlertMetric(metrics);
        }
    }

    private void publishAlertMetric(LagMetrics metrics) {
        Gauge.builder("reconciliation.lag.alert.last.seconds", 
            () -> (double) metrics.lagSeconds())
            .tag("status", metrics.status())
            .description("Lag value at last alert trigger")
            .register(meterRegistry);
    }

    public Mono<Boolean> isLagAcceptable() {
        return calculateCurrentLag()
            .map(metrics -> !metrics.exceedsSla())
            .defaultIfEmpty(true);
    }

    public Mono<SlaComplianceReport> generateComplianceReport(Duration window) {
        Instant since = Instant.now().minus(window);
        
        return Flux.fromArray(com.example.reconciliation.domain.model.Reconciliation.Status.values())
            .flatMap(status -> reconciliationRepository.countByStatusAndCreatedAtAfter(status, since)
                .map(count -> new StatusCount(status, count)))
            .collectList()
            .zipWith(calculateCurrentLag())
            .map(tuple -> new SlaComplianceReport(
                window,
                since,
                Instant.now(),
                tuple.getT2().lagSeconds(),
                tuple.getT2().exceedsSla(),
                tuple.getT1(),
                determineOverallCompliance(tuple.getT2().lagSeconds())
            ));
    }

    private String determineOverallCompliance(long maxLagSeconds) {
        if (maxLagSeconds <= SLA_THRESHOLD.getSeconds() * 0.5) {
            return "EXCELLENT";
        } else if (maxLagSeconds <= SLA_THRESHOLD.getSeconds()) {
            return "COMPLIANT";
        } else if (maxLagSeconds <= SLA_THRESHOLD.getSeconds() * 1.5) {
            return "AT_RISK";
        }
        return "NON_COMPLIANT";
    }

    public record LagMetrics(
        long lagSeconds,
        int pendingCount,
        Instant oldestPendingTimestamp,
        Instant calculatedAt,
        String status,
        boolean exceedsSla
    ) {}

    public record StatusCount(com.example.reconciliation.domain.model.Reconciliation.Status status, long count) {}

    public record SlaComplianceReport(
        Duration window,
        Instant from,
        Instant to,
        long currentLagSeconds,
        boolean currentlyExceedsSla,
        java.util.List<StatusCount> statusBreakdown,
        String complianceLevel
    ) {}
}

// === ARCHIVO: src/test/java/com/example/reconciliation/domain/model/ReconciliationTest.java ===
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

// === ARCHIVO: src/test/java/com/example/reconciliation/application/usecases/ReconcileMovementUseCaseTest.java ===
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

// === ARCHIVO: src/test/java/com/example/reconciliation/infrastructure/messaging/KafkaMovementConsumerTest.java ===
package com.example.reconciliation.infrastructure.messaging;

import com.example.reconciliation.application.usecases.ReconcileMovementUseCase;
import com.example.reconciliation.domain.model.Movement;
import com.example.reconciliation.domain.model.Movement.SourceType;
import com.example.reconciliation.domain.model.Movement.MovementType;
import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.infrastructure.config.KafkaConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("KafkaMovementConsumer - Tests de Integración")
class KafkaMovementConsumerTest {

    @Mock
    private ReconcileMovementUseCase reconcileMovementUseCase;

    @Mock
    private Acknowledgment acknowledgment;

    private KafkaMovementConsumer consumer;

    @BeforeEach
    void setUp() {
        consumer = new KafkaMovementConsumer(reconcileMovementUseCase);
    }

    @Nested
    @DisplayName("Consumo de Mensajes")
    class ConsumoMensajes {

        @Test
        @DisplayName("consume: procesa mensaje válido y confirma acknowledgement")
        void consume_mensajeValido_procesaYConfirma() throws InterruptedException {
            String jsonMessage = buildValidMovementJson();
            CountDownLatch latch = new CountDownLatch(1);

            when(reconcileMovementUseCase.execute(any(Movement.class)))
                .thenReturn(Mono.just(Reconciliation.create(
                    "evt-001", 1, "BANK_CORE", "TARGET",
                    BigDecimal.TEN, "USD", "ref", "desc"
                )));

            doAnswer(invocation -> {
                latch.countDown();
                return null;
            }).when(acknowledgment).acknowledge();

            consumer.consume(jsonMessage, acknowledgment);

            boolean completed = latch.await(5, TimeUnit.SECONDS);
            assertThat(completed).isTrue();
            verify(acknowledgment).acknowledge();
        }

        @Test
        @DisplayName("consume: mensaje con JSON inválido no confirma y loguea error")
        void consume_jsonInvalido_noConfirma() throws InterruptedException {
            String invalidJson = "{ invalid json structure }";
            CountDownLatch latch = new CountDownLatch(1);

            doAnswer(invocation -> {
                latch.countDown();
                return null;
            }).when(acknowledgment).acknowledge();

            consumer.consume(invalidJson, acknowledgment);

            latch.await(2, TimeUnit.SECONDS);
            verify(acknowledgment, never()).acknowledge();
        }

        @Test
        @DisplayName("consume: mensaje con campo faltante no procesa")
        void consume_campoFaltante_noProcesa() throws InterruptedException {
            String incompleteJson = """
                {
                    "eventId": "evt-001",
                    "amount": 1000.00
                }
                """;
            CountDownLatch latch = new CountDownLatch(1);

            doAnswer(invocation -> {
                latch.countDown();
                return null;
            }).when(acknowledgment).acknowledge();

            consumer.consume(incompleteJson, acknowledgment);

            latch.await(2, TimeUnit.SECONDS);
            verify(reconcileMovementUseCase, never()).execute(any());
        }
    }

    @Nested
    @DisplayName("Manejo de Errores")
    class ManejoErrores {

        @Test
        @DisplayName("consume: excepción en useCase confirma mensaje para no perderlo")
        void consume_excepcionEnUseCase_confirmaMensaje() throws InterruptedException {
            String jsonMessage = buildValidMovementJson();
            CountDownLatch latch = new CountDownLatch(1);

            when(reconcileMovementUseCase.execute(any(Movement.class)))
                .thenReturn(Mono.error(new RuntimeException("Error de base de datos")));

            doAnswer(invocation -> {
                latch.countDown();
                return null;
            }).when(acknowledgment).acknowledge();

            consumer.consume(jsonMessage, acknowledgment);

            boolean completed = latch.await(5, TimeUnit.SECONDS);
            assertThat(completed).isTrue();
            verify(acknowledgment).acknowledge();
        }

        @Test
        @DisplayName("consume: null message confirma sin procesar")
        void consume_nullMessage_confirmaSinProcesar() throws InterruptedException {
            CountDownLatch latch = new CountDownLatch(1);

            doAnswer(invocation -> {
                latch.countDown();
                return null;
            }).when(acknowledgment).acknowledge();

            consumer.consume(null, acknowledgment);

            latch.await(2, TimeUnit.SECONDS);
            verify(reconcileMovementUseCase, never()).execute(any());
            verify(acknowledgment).acknowledge();
        }

        @Test
        @DisplayName("consume: empty string confirma sin procesar")
        void consume_emptyString_confirmaSinProcesar() throws InterruptedException {
            CountDownLatch latch = new CountDownLatch(1);

            doAnswer(invocation -> {
                latch.countDown();
                return null;
            }).when(acknowledgment).acknowledge();

            consumer.consume("", acknowledgment);

            latch.await(2, TimeUnit.SECONDS);
            verify(reconcileMovementUseCase, never()).execute(any());
            verify(acknowledgment).acknowledge();
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

            when(reconcileMovementUseCase.execute(any(Movement.class)))
                .thenAnswer(invocation -> {
                    capturedMovement.set(invocation.getArgument(0));
                    latch.countDown();
                    return Mono.just(Reconciliation.create(
                        "evt-bank", 1, "BANK_CORE", "TARGET",
                        BigDecimal.TEN, "USD", "ref", "desc"
                    ));
                });

            doAnswer(invocation -> null).when(acknowledgment).acknowledge();

            consumer.consume(bankCoreMessage, acknowledgment);

            latch.await(5, TimeUnit.SECONDS);
            assertThat(capturedMovement.get().getSourceType()).isEqualTo(SourceType.BANK_CORE);
        }

        @Test
        @DisplayName("consume: procesa mensaje de PAYMENT_GATEWAY")
        void consume_mensajePaymentGateway_procesa() throws InterruptedException {
            String gatewayMessage = buildMovementJson("evt-gw", 1,
                "PAYMENT_GATEWAY", new BigDecimal("3000.00"));
            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<Movement> capturedMovement = new AtomicReference<>();

            when(reconcileMovementUseCase.execute(any(Movement.class)))
                .thenAnswer(invocation -> {
                    capturedMovement.set(invocation.getArgument(0));
                    latch.countDown();
                    return Mono.just(Reconciliation.create(
                        "evt-gw", 1, "PAYMENT_GATEWAY", "TARGET",
                        BigDecimal.TEN, "USD", "ref", "desc"
                    ));
                });

            doAnswer(invocation -> null).when(acknowledgment).acknowledge();

            consumer.consume(gatewayMessage, acknowledgment);

            latch.await(5, TimeUnit.SECONDS);
            assertThat(capturedMovement.get().getSourceType()).isEqualTo(SourceType.PAYMENT_GATEWAY);
        }

        @Test
        @DisplayName("consume: procesa mensaje de LIQUIDATION")
        void consume_mensajeLiquidation_procesa() throws InterruptedException {
            String liquidationMessage = buildMovementJson("evt-li", 1,
                "LIQUIDATION", new BigDecimal("7000.00"));
            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<Movement> capturedMovement = new AtomicReference<>();

            when(reconcileMovementUseCase.execute(any(Movement.class)))
                .thenAnswer(invocation -> {
                    capturedMovement.set(invocation.getArgument(0));
                    latch.countDown();
                    return Mono.just(Reconciliation.create(
                        "evt-li", 1, "LIQUIDATION", "TARGET",
                        BigDecimal.TEN, "USD", "ref", "desc"
                    ));
                });

            doAnswer(invocation -> null).when(acknowledgment).acknowledge();

            consumer.consume(liquidationMessage, acknowledgment);

            latch.await(5, TimeUnit.SECONDS);
            assertThat(capturedMovement.get().getSourceType()).isEqualTo(SourceType.LIQUIDATION);
        }
    }

    @Nested
    @DisplayName("Idempotencia en Consumo")
    class IdempotenciaConsumo {

        @Test
        @DisplayName("consume: mensaje duplicado se procesa correctamente")
        void consume_mensajeDuplicado_procesaSinError() throws InterruptedException {
            String jsonMessage = buildValidMovementJson();
            CountDownLatch latch = new CountDownLatch(2);

            when(reconcileMovementUseCase.execute(any(Movement.class)))
                .thenReturn(Mono.just(Reconciliation.create(
                    "evt-001", 1, "BANK_CORE", "TARGET",
                    BigDecimal.TEN, "USD", "ref", "desc"
                )));

            doAnswer(invocation -> {
                latch.countDown();
                return null;
            }).when(acknowledgment).acknowledge();

            consumer.consume(jsonMessage, acknowledgment);
            consumer.consume(jsonMessage, acknowledgment);

            boolean completed = latch.await(10, TimeUnit.SECONDS);
            assertThat(completed).isTrue();
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

// === ARCHIVO: docs/requisitos.md ===
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


// === ARCHIVO: docs/alertas-lag.md ===
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


// === ARCHIVO: src/main/java/com/example/reconciliation/infrastructure/messaging/KafkaMovementConsumer.java ===
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

// === ARCHIVO: src/main/java/com/example/reconciliation/infrastructure/monitoring/LagMonitor.java ===
package com.example.reconciliation.infrastructure.monitoring;

import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.domain.model.Reconciliation.Status;
import com.example.reconciliation.domain.repository.ReconciliationRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class LagMonitor {

    private static final Logger log = LoggerFactory.getLogger(LagMonitor.class);
    private static final Duration SLA_THRESHOLD = Duration.ofMinutes(5);
    private static final Duration CHECK_INTERVAL = Duration.ofSeconds(30);
    private static final Duration GRACE_PERIOD = Duration.ofSeconds(10);

    private final MeterRegistry meterRegistry;
    private final ReconciliationRepository reconciliationRepository;
    private final AtomicLong currentLagSeconds = new AtomicLong(0);
    private final AtomicReference<Instant> lastCheckTime = new AtomicReference<>(Instant.now());
    private final AtomicReference<Instant> lastAlertTime = new AtomicReference<>(Instant.EPOCH);
    private final AtomicReference<String> lagStatus = new AtomicReference<>("NORMAL");
    
    private final Counter lagExceededCounter;
    private final Counter lagNormalCounter;
    private final Timer lagCheckTimer;

    public LagMonitor(MeterRegistry meterRegistry, ReconciliationRepository reconciliationRepository) {
        this.meterRegistry = meterRegistry;
        this.reconciliationRepository = reconciliationRepository;
        
        this.lagExceededCounter = Counter.builder("reconciliation.lag.exceeded")
            .description("Number of times lag exceeded SLA threshold")
            .tag("sla_threshold_minutes", String.valueOf(SLA_THRESHOLD.toMinutes()))
            .register(meterRegistry);
        
        this.lagNormalCounter = Counter.builder("reconciliation.lag.normal")
            .description("Number of times lag returned to normal")
            .register(meterRegistry);
        
        this.lagCheckTimer = Timer.builder("reconciliation.lag.check.duration")
            .description("Time taken to perform lag check")
            .register(meterRegistry);
        
        Gauge.builder("reconciliation.lag.current.seconds", currentLagSeconds, AtomicLong::get)
            .description("Current reconciliation lag in seconds")
            .tag("sla_threshold_seconds", String.valueOf(SLA_THRESHOLD.getSeconds()))
            .register(meterRegistry);
        
        Gauge.builder("reconciliation.lag.status", lagStatus, AtomicReference::get)
            .description("Current lag status: NORMAL, WARNING, CRITICAL")
            .register(meterRegistry);
        
        initializeMetrics();
    }

    private void initializeMetrics() {
        Gauge.builder("reconciliation.lag.sla.threshold.seconds", 
            () -> (double) SLA_THRESHOLD.getSeconds())
            .description("SLA threshold for lag in seconds")
            .register(meterRegistry);
        
        Gauge.builder("reconciliation.pending.count", 
            reconciliationRepository.countByStatus(Status.PENDING))
            .description("Number of pending reconciliations")
            .register(meterRegistry);
    }

    public void recordMovementReceived(Instant receivedAt) {
        log.debug("Movement received at: {}", receivedAt);
        meterRegistry.counter("reconciliation.movements.received").increment();
    }

    public void recordDltMessage(String eventId) {
        log.warn("Message sent to DLT for eventId: {}", eventId);
        meterRegistry.counter("reconciliation.movements.dlt").increment();
    }

    public Mono<LagMetrics> calculateCurrentLag() {
        return Timer.start(meterRegistry).flatMap(timer -> 
            reconciliationRepository.findByStatus(Status.PENDING)
                .collectList()
                .map(pendingReconciliations -> {
                    timer.stop(lagCheckTimer);
                    return calculateLagMetrics(pendingReconciliations);
                })
        ).doOnError(e -> {
            log.error("Error calculating lag metrics: {}", e.getMessage());
            meterRegistry.counter("reconciliation.lag.check.errors").increment();
        });
    }

    private LagMetrics calculateLagMetrics(List<Reconciliation> pending) {
        Instant now = Instant.now();
        Instant oldestPending = pending.stream()
            .map(Reconciliation::getCreatedAt)
            .min(Instant::compareTo)
            .orElse(now);
        
        long lagSeconds = Duration.between(oldestPending, now).getSeconds();
        currentLagSeconds.set(lagSeconds);
        lastCheckTime.set(now);
        
        String status = determineStatus(lagSeconds);
        lagStatus.set(status);
        
        LagMetrics metrics = new LagMetrics(
            lagSeconds,
            pending.size(),
            oldestPending,
            now,
            status,
            lagSeconds > SLA_THRESHOLD.getSeconds()
        );
        
        if (metrics.exceedsSla()) {
            handleSlaExceeded(metrics);
        } else if (lagStatus.compareAndSet("CRITICAL", "NORMAL") || 
                   lagStatus.compareAndSet("WARNING", "NORMAL")) {
            lagNormalCounter.increment();
            log.info("Lag returned to normal: {} seconds ({} pending items)", 
                lagSeconds, pending.size());
        }
        
        return metrics;
    }

    private String determineStatus(long lagSeconds) {
        long thresholdSeconds = SLA_THRESHOLD.getSeconds();
        if (lagSeconds >= thresholdSeconds * 2) {
            return "CRITICAL";
        } else if (lagSeconds >= thresholdSeconds) {
            return "WARNING";
        }
        return "NORMAL";
    }

    private void handleSlaExceeded(LagMetrics metrics) {
        String previousStatus = lagStatus.get();
        if (!"CRITICAL".equals(previousStatus) && !"WARNING".equals(previousStatus)) {
            lagExceededCounter.increment();
            triggerAlert(metrics);
        } else if ("WARNING".equals(previousStatus) && "CRITICAL".equals(metrics.status())) {
            lagExceededCounter.increment();
            triggerAlert(metrics);
        }
        lagStatus.set(metrics.status());
    }

    private void triggerAlert(LagMetrics metrics) {
        Instant now = Instant.now();
        Instant lastAlert = lastAlertTime.get();
        
        if (Duration.between(lastAlert, now).compareTo(Duration.ofMinutes(5)) > 0) {
            lastAlertTime.set(now);
            log.warn("ALERT: Reconciliation lag exceeded SLA! Current: {}s, Threshold: {}s, " +
                "Pending: {}, Status: {}", 
                metrics.lagSeconds(), 
                SLA_THRESHOLD.getSeconds(),
                metrics.pendingCount(),
                metrics.status());
            
            meterRegistry.counter("reconciliation.lag.alerts.triggered").increment();
            publishAlertMetric(metrics);
        }
    }

    private void publishAlertMetric(LagMetrics metrics) {
        Gauge.builder("reconciliation.lag.alert.last.seconds", 
            () -> (double) metrics.lagSeconds())
            .tag("status", metrics.status())
            .description("Lag value at last alert trigger")
            .register(meterRegistry);
    }

    public Mono<Boolean> isLagAcceptable() {
        return calculateCurrentLag()
            .map(metrics -> !metrics.exceedsSla())
            .defaultIfEmpty(true);
    }

    public Mono<SlaComplianceReport> generateComplianceReport(Duration window) {
        Instant since = Instant.now().minus(window);
        
        return Flux.fromArray(Status.values())
            .flatMap(status -> reconciliationRepository.countByStatusAndCreatedAtAfter(status, since)
                .map(count -> new StatusCount(status, count)))
            .collectList()
            .zipWith(calculateCurrentLag())
            .map(tuple -> new SlaComplianceReport(
                window,
                since,
                Instant.now(),
                tuple.getT2().lagSeconds(),
                tuple.getT2().exceedsSla(),
                tuple.getT1(),
                determineOverallCompliance(tuple.getT2().lagSeconds())
            ));
    }

    private String determineOverallCompliance(long maxLagSeconds) {
        if (maxLagSeconds <= SLA_THRESHOLD.getSeconds() * 0.5) {
            return "EXCELLENT";
        } else if (maxLagSeconds <= SLA_THRESHOLD.getSeconds()) {
            return "COMPLIANT";
        } else if (maxLagSeconds <= SLA_THRESHOLD.getSeconds() * 1.5) {
            return "AT_RISK";
        }
        return "NON_COMPLIANT";
    }

    public record LagMetrics(
        long lagSeconds,
        int pendingCount,
        Instant oldestPendingTimestamp,
        Instant calculatedAt,
        String status,
        boolean exceedsSla
    ) {}

    public record StatusCount(Status status, long count) {}

    public record SlaComplianceReport(
        Duration window,
        Instant from,
        Instant to,
        long currentLagSeconds,
        boolean currentlyExceedsSla,
        List<StatusCount> statusBreakdown,
        String complianceLevel
    ) {}
}

// === ARCHIVO: src/main/java/com/example/reconciliation/infrastructure/config/KafkaConfig.java ===
package com.example.reconciliation.infrastructure.config;

import com.example.reconciliation.domain.model.Movement;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.util.backoff.FixedBackOff;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.ReactorKafkaProducer;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public class KafkaConfig {

    private static final Logger log = LoggerFactory.getLogger(KafkaConfig.class);

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id:reconciliation-group}")
    private String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset:earliest}")
    private String autoOffsetReset;

    @Value("${spring.kafka.consumer.max-poll-records:100}")
    private int maxPollRecords;

    @Value("${spring.kafka.consumer.fetch-min-size:1}")
    private int fetchMinSize;

    @Value("${spring.kafka.consumer.fetch-max-wait:500}")
    private int fetchMaxWait;

    @Value("${spring.kafka.listener.ack-mode:manual}")
    private String ackMode;

    @Value("${spring.kafka.listener.concurrency:3}")
    private int concurrency;

    @Value("${spring.kafka.producer.retries:3}")
    private int maxAttempts;

    @Value("${spring.kafka.producer.properties.retry.backoff.ms:1000}")
    private long backOffMs;

    public static final String MOVEMENT_TOPIC = "bank.movements";
    public static final String CONSUMER_GROUP_ID = "reconciliation-consumer-group";

    @Bean
    public ObjectMapper kafkaObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Map<String, Object> consumerProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, fetchMinSize);
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, fetchMaxWait);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

    @Bean
    public Map<String, Object> producerProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, maxAttempts);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        return props;
    }

    @Bean
    public ConsumerFactory<String, Movement> consumerFactory() {
        Map<String, Object> props = consumerProperties();
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.reconciliation.domain.model");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new StringDeserializer());
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerProperties());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Movement> kafkaListenerContainerFactory(
            ConsumerFactory<String, Movement> consumerFactory,
            KafkaErrorHandler errorHandler) {
        ConcurrentKafkaListenerContainerFactory<String, Movement> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(concurrency);
        factory.getContainerProperties().setAckMode(org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL);
        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }

    @Bean
    public CommonErrorHandler kafkaErrorHandler(final KafkaTemplate<String, Object> kafkaTemplate) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (record, ex) -> new org.apache.kafka.common.TopicPartition(record.topic() + ".dlt", record.partition()));
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, new FixedBackOff(backOffMs, maxAttempts));
        return errorHandler;
    }

    @Bean
    public KafkaTransactionManager<String, Object> kafkaTransactionManager(final ProducerFactory<String, Object> producerFactory) {
        return new KafkaTransactionManager<>(producerFactory);
    }

    @Bean
    public ReactorKafkaProducer<String, Object> reactorKafkaProducer(final ProducerFactory<String, Object> producerFactory) {
        return new ReactorKafkaProducer<>(SenderOptions.create(producerFactory.getConfigurationProperties()));
    }

    @Bean
    public ReactorKafkaConsumer<String, Movement> reactorKafkaConsumer(final ConsumerFactory<String, Movement> consumerFactory) {
        return new ReactorKafkaConsumer(ReceiverOptions.create(consumerFactory.getConfigurationProperties()));
    }

    @Bean
    public java.util.concurrent.ExecutorService kafkaExecutor() {
        return java.util.concurrent.Executors.newFixedThreadPool(concurrency);
    }

    @Bean
    public TopicBuilder movementTopic() {
        return TopicBuilder.name(MOVEMENT_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public TopicBuilder movementDltTopic() {
        return TopicBuilder.name(MOVEMENT_TOPIC + ".dlt")
                .partitions(3)
                .replicas(1)
                .build();
    }

    public String getMovementTopic() {
        return MOVEMENT_TOPIC;
    }

    public String getConsumerGroupId() {
        return CONSUMER_GROUP_ID;
    }
}

// === ARCHIVO: src/test/java/com/example/reconciliation/infrastructure/messaging/KafkaMovementConsumerTest.java ===
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


// === ARCHIVO: pom.xml ===
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.4.5</version>
        <relativePath/>
    </parent>
    
    <groupId>com.example</groupId>
    <artifactId>reconciliation</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <name>reconciliation</name>
    <description>Sistema de conciliacion bancaria en tiempo real</description>
    
    <properties>
        <java.version>21</java.version>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
            <version>3.4.5</version>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
            <version>3.4.0</version>
        </dependency>
        
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-core</artifactId>
            <version>3.6.5</version>
        </dependency>
        
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.3</version>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
            <version>3.4.5</version>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
            <version>3.4.5</version>
        </dependency>
        
        <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-registry-prometheus</artifactId>
            <version>1.13.0</version>
        </dependency>
        
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>2.0.13</version>
        </dependency>
        
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.10.2</version>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.11.0</version>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>postgresql</artifactId>
            <version>1.19.7</version>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>kafka</artifactId>
            <version>1.19.7</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/example/reconciliation/domain/event/ReconciliationEvent.java ===
package com.example.reconciliation.domain.event;

import com.example.reconciliation.domain.model.Reconciliation.Status;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public final class ReconciliationEvent {
    
    public enum EventType {
        CREATED,
        MATCHED,
        MISMATCHED,
        ESCALATED_TO_MANUAL,
        MANUALLY_RESOLVED,
        EXPIRED
    }
    
    private final UUID id;
    private final String eventId;
    private final int version;
    private final EventType eventType;
    private final UUID reconciliationId;
    private final String sourceType;
    private final String targetType;
    private final BigDecimal amount;
    private final String currency;
    private final String reference;
    private final Status previousStatus;
    private final Status newStatus;
    private final String reason;
    private final Instant timestamp;
    private final Instant processedAt;
    
    private ReconciliationEvent(Builder builder) {
        this.id = builder.id;
        this.eventId = builder.eventId;
        this.version = builder.version;
        this.eventType = builder.eventType;
        this.reconciliationId = builder.reconciliationId;
        this.sourceType = builder.sourceType;
        this.targetType = builder.targetType;
        this.amount = builder.amount;
        this.currency = builder.currency;
        this.reference = builder.reference;
        this.previousStatus = builder.previousStatus;
        this.newStatus = builder.newStatus;
        this.reason = builder.reason;
        this.timestamp = builder.timestamp;
        this.processedAt = builder.processedAt;
    }
    
    public static ReconciliationEvent created(UUID reconciliationId, String eventId, int version,
            String sourceType, String targetType, BigDecimal amount, String currency, String reference) {
        return builder()
            .id(UUID.randomUUID())
            .eventId(eventId)
            .version(version)
            .eventType(EventType.CREATED)
            .reconciliationId(reconciliationId)
            .sourceType(sourceType)
            .targetType(targetType)
            .amount(amount)
            .currency(currency)
            .reference(reference)
            .previousStatus(null)
            .newStatus(Status.PENDING)
            .timestamp(Instant.now())
            .processedAt(Instant.now())
            .build();
    }
    
    public static ReconciliationEvent matched(UUID reconciliationId, String eventId, int version,
            String sourceType, String targetType, BigDecimal amount, String reference) {
        return builder()
            .id(UUID.randomUUID())
            .eventId(eventId)
            .version(version)
            .eventType(EventType.MATCHED)
            .reconciliationId(reconciliationId)
            .sourceType(sourceType)
            .targetType(targetType)
            .amount(amount)
            .reference(reference)
            .previousStatus(Status.PENDING)
            .newStatus(Status.MATCHED)
            .timestamp(Instant.now())
            .processedAt(Instant.now())
            .build();
    }
    
    public static ReconciliationEvent mismatched(UUID reconciliationId, String eventId, int version,
            String sourceType, String targetType, BigDecimal amount, String reason) {
        return builder()
            .id(UUID.randomUUID())
            .eventId(eventId)
            .version(version)
            .eventType(EventType.MISMATCHED)
            .reconciliationId(reconciliationId)
            .sourceType(sourceType)
            .targetType(targetType)
            .amount(amount)
            .previousStatus(Status.PENDING)
            .newStatus(Status.MISMATCHED)
            .reason(reason)
            .timestamp(Instant.now())
            .processedAt(Instant.now())
            .build();
    }
    
    public static ReconciliationEvent escalated(UUID reconciliationId, String eventId, int version,
            Status previousStatus, String reason) {
        return builder()
            .id(UUID.randomUUID())
            .eventId(eventId)
            .version(version)
            .eventType(EventType.ESCALATED_TO_MANUAL)
            .reconciliationId(reconciliationId)
            .previousStatus(previousStatus)
            .newStatus(Status.MANUAL)
            .reason(reason)
            .timestamp(Instant.now())
            .processedAt(Instant.now())
            .build();
    }
    
    public static ReconciliationEvent manuallyResolved(UUID reconciliationId, String eventId, int version,
            Status previousStatus, String resolution) {
        return builder()
            .id(UUID.randomUUID())
            .eventId(eventId)
            .version(version)
            .eventType(EventType.MANUALLY_RESOLVED)
            .reconciliationId(reconciliationId)
            .previousStatus(previousStatus)
            .newStatus(Status.MATCHED)
            .reason(resolution)
            .timestamp(Instant.now())
            .processedAt(Instant.now())
            .build();
    }
    
    public static ReconciliationEvent expired(UUID reconciliationId, String eventId, int version) {
        return builder()
            .id(UUID.randomUUID())
            .eventId(eventId)
            .version(version)
            .eventType(EventType.EXPIRED)
            .reconciliationId(reconciliationId)
            .previousStatus(Status.PENDING)
            .newStatus(Status.PENDING)
            .reason("Ventana de reconciliacion expirada")
            .timestamp(Instant.now())
            .processedAt(Instant.now())
            .build();
    }
    
    public String getIdempotencyKey() {
        return eventId + ":" + version + ":" + eventType.name();
    }
    
    public UUID getId() {
        return id;
    }
    
    public String getEventId() {
        return eventId;
    }
    
    public int getVersion() {
        return version;
    }
    
    public EventType getEventType() {
        return eventType;
    }
    
    public UUID getReconciliationId() {
        return reconciliationId;
    }
    
    public String getSourceType() {
        return sourceType;
    }
    
    public String getTargetType() {
        return targetType;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public String getReference() {
        return reference;
    }
    
    public Status getPreviousStatus() {
        return previousStatus;
    }
    
    public Status getNewStatus() {
        return newStatus;
    }
    
    public String getReason() {
        return reason;
    }
    
    public Instant getTimestamp() {
        return timestamp;
    }
    
    public Instant getProcessedAt() {
        return processedAt;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static final class Builder {
        private UUID id;
        private String eventId;
        private int version;
        private EventType eventType;
        private UUID reconciliationId;
        private String sourceType;
        private String targetType;
        private BigDecimal amount;
        private String currency;
        private String reference;
        private Status previousStatus;
        private Status newStatus;
        private String reason;
        private Instant timestamp;
        private Instant processedAt;
        
        private Builder() {}
        
        public Builder id(UUID id) {
            this.id = id;
            return this;
        }
        
        public Builder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }
        
        public Builder version(int version) {
            this.version = version;
            return this;
        }
        
        public Builder eventType(EventType eventType) {
            this.eventType = eventType;
            return this;
        }
        
        public Builder reconciliationId(UUID reconciliationId) {
            this.reconciliationId = reconciliationId;
            return this;
        }
        
        public Builder sourceType(String sourceType) {
            this.sourceType = sourceType;
            return this;
        }
        
        public Builder targetType(String targetType) {
            this.targetType = targetType;
            return this;
        }
        
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }
        
        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }
        
        public Builder reference(String reference) {
            this.reference = reference;
            return this;
        }
        
        public Builder previousStatus(Status previousStatus) {
            this.previousStatus = previousStatus;
            return this;
        }
        
        public Builder newStatus(Status newStatus) {
            this.newStatus = newStatus;
            return this;
        }
        
        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }
        
        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }
        
        public Builder processedAt(Instant processedAt) {
            this.processedAt = processedAt;
            return this;
        }
        
        public ReconciliationEvent build() {
            return new ReconciliationEvent(this);
        }
    }
}

// === ARCHIVO: src/main/java/com/example/reconciliation/application/services/ReconciliationService.java ===
package com.example.reconciliation.application.services;

import com.example.reconciliation.domain.model.Movement;
import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.domain.model.Reconciliation.Status;
import com.example.reconciliation.domain.repository.ReconciliationRepository;
import com.example.reconciliation.application.usecases.ReconcileMovementUseCase;
import com.example.reconciliation.infrastructure.monitoring.LagMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
public class ReconciliationService {

    private static final Logger log = LoggerFactory.getLogger(ReconciliationService.class);
    private static final int MATCHING_WINDOW_MINUTES = 5;
    private static final int MANUAL_ESCALATION_THRESHOLD_HOURS = 24;

    private final ReconciliationRepository reconciliationRepository;
    private final ReconcileMovementUseCase reconcileMovementUseCase;
    private final LagMonitor lagMonitor;

    public ReconciliationService(
            final ReconciliationRepository reconciliationRepository,
            final ReconcileMovementUseCase reconcileMovementUseCase,
            final LagMonitor lagMonitor) {
        this.reconciliationRepository = reconciliationRepository;
        this.reconcileMovementUseCase = reconcileMovementUseCase;
        this.lagMonitor = lagMonitor;
    }

    public Mono<Reconciliation> initiateReconciliation(final Movement movement) {
        log.info("Iniciando reconciliacion para movimiento: eventId={}, version={}",
                movement.getEventId(), movement.getVersion());

        return reconciliationRepository.existsByEventIdAndVersion(
                movement.getEventId(), movement.getVersion())
            .flatMap(exists -> {
                if (exists) {
                    log.warn("Movimiento duplicado detectado: eventId={}, version={}",
                            movement.getEventId(), movement.getVersion());
                    return reconciliationRepository
                        .findByEventIdAndVersion(movement.getEventId(), movement.getVersion())
                        .flatMap(opt -> opt.isPresent() 
                            ? Mono.just(opt.get()) 
                            : Mono.error(new IllegalStateException("Inconsistencia de estado")));
                }
                return createNewReconciliation(movement);
            });
    }

    private Mono<Reconciliation> createNewReconciliation(final Movement movement) {
        final Reconciliation reconciliation = Reconciliation.create(
                movement.getEventId(),
                movement.getVersion(),
                movement.getSourceType().name(),
                "N/A",
                movement.getAmount(),
                movement.getCurrency(),
                movement.getReference(),
                "Reconciliacion iniciada para " + movement.getSourceType()
        );

        return reconciliationRepository.save(reconciliation)
            .doOnSuccess(saved -> {
                log.info("Reconciliacion creada: id={}, status={}", saved.getId(), saved.getStatus());
                lagMonitor.recordReconciliationCreated(saved.getCreatedAt());
            });
    }

    public Mono<Reconciliation> processMatching(final UUID reconciliationId,
                                                 final List<Movement> matchingMovements) {
        log.info("Procesando matching para reconciliacion: {}, movimientos encontrados: {}",
                reconciliationId, matchingMovements.size());

        return reconciliationRepository.findById(reconciliationId)
            .flatMap(optReconciliation -> {
                if (optReconciliation.isEmpty()) {
                    return Mono.error(new IllegalArgumentException(
                            "Reconciliacion no encontrada: " + reconciliationId));
                }
                final Reconciliation reconciliation = optReconciliation.get();
                return performMatching(reconciliation, matchingMovements);
            });
    }

    private Mono<Reconciliation> performMatching(final Reconciliation reconciliation,
                                                  final List<Movement> movements) {
        if (!reconciliation.canTransitionTo(Status.MATCHED)) {
            log.warn("Reconciliacion {} no puede transicion a MATCHED desde {}",
                    reconciliation.getId(), reconciliation.getStatus());
            return Mono.just(reconciliation);
        }

        final boolean allMatch = verifyAllMovementsMatch(reconciliation, movements);
        if (allMatch && movements.size() >= 3) {
            reconciliation.match();
            return reconciliationRepository.save(reconciliation)
                .doOnSuccess(saved -> {
                    log.info("Reconciliacion {} marcada como MATCHED", saved.getId());
                    lagMonitor.recordReconciliationCompleted(saved.getMatchedAt());
                });
        } else if (movements.size() > 0) {
            final String reason = String.format("Movimientos coincidentes insuficientes: %d de 3",
                    movements.size());
            return handleMismatch(reconciliation, reason);
        } else {
            return checkForExpiredReconciliation(reconciliation);
        }
    }

    private boolean verifyAllMovementsMatch(final Reconciliation reconciliation,
                                             final List<Movement> movements) {
        return movements.stream()
                .allMatch(m -> m.matchesAmount(reconciliation.getAmount()) &&
                              m.matchesReference(reconciliation.getReference()));
    }

    public Mono<Reconciliation> handleMismatch(final Reconciliation reconciliation,
                                                final String reason) {
        if (!reconciliation.canTransitionTo(Status.MISMATCHED)) {
            log.warn("Reconciliacion {} no puede transicion a MISMATCHED desde {}",
                    reconciliation.getId(), reconciliation.getStatus());
            return Mono.just(reconciliation);
        }

        reconciliation.mismatch(reason);
        return reconciliationRepository.save(reconciliation)
            .doOnSuccess(saved -> {
                log.warn("Reconciliacion {} marcada como MISMATCHED: {}", saved.getId(), reason);
                lagMonitor.recordMismatch(saved.getUpdatedAt());
            });
    }

    public Mono<Reconciliation> escalateToManual(final UUID reconciliationId,
                                                  final String reason) {
        log.warn("Escalando reconciliacion {} a revision manual: {}", reconciliationId, reason);

        return reconciliationRepository.findById(reconciliationId)
            .flatMap(opt -> {
                if (opt.isEmpty()) {
                    return Mono.error(new IllegalArgumentException(
                            "Reconciliacion no encontrada: " + reconciliationId));
                }
                final Reconciliation reconciliation = opt.get();
                if (!reconciliation.canTransitionTo(Status.MANUAL)) {
                    return Mono.error(new IllegalStateException(
                            "No se puede escalar a MANUAL desde: " + reconciliation.getStatus()));
                }
                reconciliation.escalateToManual(reason);
                return reconciliationRepository.save(reconciliation);
            });
    }

    public Mono<Reconciliation> resolveManually(final UUID reconciliationId,
                                                final String resolution) {
        log.info("Resolviendo manualmente reconciliacion {}: {}", reconciliationId, resolution);

        return reconciliationRepository.findById(reconciliationId)
            .flatMap(opt -> {
                if (opt.isEmpty()) {
                    return Mono.error(new IllegalArgumentException(
                            "Reconciliacion no encontrada: " + reconciliationId));
                }
                final Reconciliation reconciliation = opt.get();
                if (reconciliation.getStatus() != Status.MANUAL &&
                    reconciliation.getStatus() != Status.MISMATCHED) {
                    return Mono.error(new IllegalStateException(
                            "Solo se pueden resolver estados MANUAL o MISMATCHED"));
                }
                reconciliation.resolveManually(resolution);
                return reconciliationRepository.save(reconciliation);
            });
    }

    private Mono<Reconciliation> checkForExpiredReconciliation(final Reconciliation reconciliation) {
        final Instant windowStart = reconciliation.getCreatedAt()
                .plus(Duration.ofMinutes(MATCHING_WINDOW_MINUTES));
        if (reconciliation.isExpired(MATCHING_WINDOW_MINUTES)) {
            log.warn("Reconciliacion {} expiro sin matching completo", reconciliation.getId());
            return escalateToManual(reconciliation.getId(),
                    "Ventana de matching expirada sin reconciliacion completa");
        }
        return Mono.just(reconciliation);
    }

    public Flux<Reconciliation> findReconciliationsByStatus(final Status status) {
        log.debug("Buscando reconciliaciones con estado: {}", status);
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
        log.info("Reintentando reconciliacion: {}", reconciliationId);

        return reconciliationRepository.findById(reconciliationId)
            .flatMap(opt -> {
                if (opt.isEmpty()) {
                    return Mono.error(new IllegalArgumentException(
                            "Reconciliacion no encontrada: " + reconciliationId));
                }
                final Reconciliation reconciliation = opt.get();
                if (reconciliation.getStatus() != Status.MISMATCHED &&
                    reconciliation.getStatus() != Status.MANUAL) {
                    return Mono.error(new IllegalStateException(
                            "Solo se pueden reintentar estados MISMATCHED o MANUAL"));
                }
                return reconcileMovementUseCase.execute(reconciliation.getEventId(),
                        reconciliation.getVersion());
            });
    }

    public Mono<Void> reprocessAllPending() {
        log.info("Iniciando reprocesamiento de todas las reconciliaciones pendientes");

        return reconciliationRepository.findByStatus(Status.PENDING)
            .flatMap(reconciliation -> reconcileMovementUseCase.execute(
                    reconciliation.getEventId(), reconciliation.getVersion())
                    .onErrorResume(e -> {
                        log.error("Error en reprocesamiento de {}: {}",
                                reconciliation.getId(), e.getMessage());
                        return Mono.empty();
                    }), false)
            .then();
    }

    public Mono<Boolean> validateReconciliationIntegrity(final UUID reconciliationId) {
        return reconciliationRepository.findById(reconciliationId)
            .flatMap(opt -> {
                if (opt.isEmpty()) {
                    return Mono.just(false);
                }
                final Reconciliation reconciliation = opt.get();
                final boolean hasValidState = reconciliation.getStatus() != null;
                final boolean hasAmount = reconciliation.getAmount() != null &&
                        reconciliation.getAmount().compareTo(BigDecimal.ZERO) > 0;
                final boolean hasReference = reconciliation.getReference() != null &&
                        !reconciliation.getReference().isBlank();
                return Mono.just(hasValidState && hasAmount && hasReference);
            });
    }
}

// === ARCHIVO: src/main/java/com/example/reconciliation/infrastructure/monitoring/LagMonitor.java ===
package com.example.reconciliation.infrastructure.monitoring;

import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.domain.model.Reconciliation.Status;
import com.example.reconciliation.domain.repository.ReconciliationRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class LagMonitor {
    
    private static final Logger log = LoggerFactory.getLogger(LagMonitor.class);
    private static final Duration SLA_THRESHOLD = Duration.ofMinutes(5);
    private static final Duration CHECK_INTERVAL = Duration.ofSeconds(30);
    private static final Duration GRACE_PERIOD = Duration.ofSeconds(10);
    
    private final MeterRegistry meterRegistry;
    private final ReconciliationRepository reconciliationRepository;
    private final AtomicLong currentLagSeconds;
    private final AtomicReference<Instant> lastCheckTime;
    private final AtomicReference<Instant> lastAlertTime;
    private final AtomicReference<String> lagStatus;
    private final Counter lagExceededCounter;
    private final Counter lagNormalCounter;
    private final Timer lagCheckTimer;
    
    public LagMonitor(final MeterRegistry meterRegistry, 
                      final ReconciliationRepository reconciliationRepository) {
        this.meterRegistry = meterRegistry;
        this.reconciliationRepository = reconciliationRepository;
        this.currentLagSeconds = new AtomicLong(0);
        this.lastCheckTime = new AtomicReference<>(Instant.now());
        this.lastAlertTime = new AtomicReference<>(Instant.EPOCH);
        this.lagStatus = new AtomicReference<>("UNKNOWN");
        initializeMetrics();
    }
    
    private void initializeMetrics() {
        this.lagExceededCounter = Counter.builder("reconciliation.lag.exceeded")
            .description("Number of times SLA threshold was exceeded")
            .register(meterRegistry);
        
        this.lagNormalCounter = Counter.builder("reconciliation.lag.normal")
            .description("Number of times lag was within SLA")
            .register(meterRegistry);
        
        this.lagCheckTimer = Timer.builder("reconciliation.lag.check.duration")
            .description("Time taken to check lag metrics")
            .register(meterRegistry);
    }
    
    public void recordReconciliationCreated(final Instant createdAt) {
        log.debug("Recording reconciliation created at: {}", createdAt);
        meterRegistry.counter("reconciliation.created", "source", "lag_monitor")
            .increment();
    }
    
    public void recordReconciliationCompleted(final Instant matchedAt) {
        log.debug("Recording reconciliation completed at: {}", matchedAt);
        meterRegistry.counter("reconciliation.completed", "source", "lag_monitor")
            .increment();
        
        final Instant now = Instant.now();
        final long processingTime = Duration.between(matchedAt, now).getSeconds();
        meterRegistry.timer("reconciliation.processing.time")
            .record(Duration.ofSeconds(processingTime));
    }
    
    public void recordMismatch(final Instant updatedAt) {
        log.debug("Recording mismatch at: {}", updatedAt);
        meterRegistry.counter("reconciliation.mismatch", "source", "lag_monitor")
            .increment();
    }
    
    public Mono<LagMetrics> calculateCurrentLag() {
        return lagCheckTimer.record(() -> reconciliationRepository.findByStatus(Status.PENDING)
            .collectList()
            .map(this::calculateLagMetrics));
    }
    
    private LagMetrics calculateLagMetrics(final List<Reconciliation> pending) {
        if (pending.isEmpty()) {
            return new LagMetrics(0L, 0L, 0L, 0L, "NORMAL", Instant.now());
        }
        
        final Instant now = Instant.now();
        final Instant oldest = pending.stream()
            .map(Reconciliation::getCreatedAt)
            .min(Instant::compareTo)
            .orElse(now);
        
        final long lagSeconds = Duration.between(oldest, now).getSeconds();
        currentLagSeconds.set(lagSeconds);
        
        final String status = determineStatus(lagSeconds);
        lagStatus.set(status);
        
        final long pendingCount = pending.size();
        final long matchedCount = pending.stream()
            .filter(r -> r.getStatus() == Status.MATCHED)
            .count();
        final long mismatchedCount = pending.stream()
            .filter(r -> r.getStatus() == Status.MISMATCHED)
            .count();
        
        return new LagMetrics(lagSeconds, pendingCount, matchedCount, mismatchedCount, status, now);
    }
    
    private String determineStatus(final long lagSeconds) {
        final long thresholdSeconds = SLA_THRESHOLD.getSeconds();
        if (lagSeconds > thresholdSeconds) {
            lagExceededCounter.increment();
            handleSlaExceeded(null);
            return "EXCEEDED";
        } else {
            lagNormalCounter.increment();
            return "NORMAL";
        }
    }
    
    private void handleSlaExceeded(final LagMetrics metrics) {
        final Instant now = Instant.now();
        final Instant lastAlert = lastAlertTime.get();
        
        if (Duration.between(lastAlert, now).compareTo(CHECK_INTERVAL) > 0) {
            if (lastAlertTime.compareAndSet(lastAlert, now)) {
                triggerAlert(metrics);
            }
        }
    }
    
    private void triggerAlert(final LagMetrics metrics) {
        log.warn("SLA EXCEEDED: {}", metrics);
        meterRegistry.counter("reconciliation.sla.alerts").increment();
    }
    
    private void publishAlertMetric(final LagMetrics metrics) {
        meterRegistry.gauge("reconciliation.lag.current.seconds", currentLagSeconds);
        meterRegistry.gauge("reconciliation.lag.status", lagStatus, AtomicReference::get);
    }
    
    public Mono<Boolean> isLagAcceptable() {
        return calculateCurrentLag()
            .map(metrics -> metrics.lagSeconds() <= SLA_THRESHOLD.getSeconds());
    }
    
    public Mono<SlaComplianceReport> generateComplianceReport(final Duration window) {
        final Instant windowStart = Instant.now().minus(window);
        
        return reconciliationRepository.findByStatusAndCreatedAtAfter(Status.PENDING, windowStart)
            .collectList()
            .map(pending -> {
                final long maxLag = pending.stream()
                    .mapToLong(r -> Duration.between(r.getCreatedAt(), Instant.now()).getSeconds())
                    .max()
                    .orElse(0L);
                
                final String compliance = determineOverallCompliance(maxLag);
                return new SlaComplianceReport(window, pending.size(), maxLag, compliance);
            });
    }
    
    private String determineOverallCompliance(final long maxLagSeconds) {
        final long thresholdSeconds = SLA_THRESHOLD.getSeconds();
        if (maxLagSeconds <= thresholdSeconds) {
            return "COMPLIANT";
        } else if (maxLagSeconds <= thresholdSeconds * 2) {
            return "WARNING";
        } else {
            return "NON_COMPLIANT";
        }
    }
    
    public record LagMetrics(
        long lagSeconds,
        long pendingCount,
        long matchedCount,
        long mismatchedCount,
        String status,
        Instant timestamp
    ) {}
    
    public record StatusCount(Status status, long count) {}
    
    public record SlaComplianceReport(
        Duration window,
        long totalReconciliations,
        long maxLagSeconds,
        String complianceStatus
    ) {}
}


// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.4.5</version>
        <relativePath/>
    </parent>

    <groupId>com.example</groupId>
    <artifactId>reconciliation</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>
    <name>reconciliation</name>

    <properties>
        <java.version>21</java.version>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
            <version>3.4.0</version>
        </dependency>
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-core</artifactId>
            <version>3.6.5</version>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.3</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-registry-prometheus</artifactId>
            <version>1.13.0</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>2.0.13</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-r2dbc</artifactId>
        </dependency>
        <dependency>
            <groupId>io.r2dbc</groupId>
            <artifactId>r2dbc-postgresql</artifactId>
            <version>1.0.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.10.2</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.11.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>postgresql</artifactId>
            <version>1.19.7</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>kafka</artifactId>
            <version>1.19.7</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>


// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.4.5</version>
        <relativePath/>
    </parent>

    <groupId>com.example</groupId>
    <artifactId>reconciliation</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>
    <name>reconciliation</name>
    <description>Bank Reconciliation System</description>

    <properties>
        <java.version>21</java.version>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
            <version>3.4.0</version>
        </dependency>
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-core</artifactId>
            <version>3.6.5</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.17.1</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-jsr310</artifactId>
            <version>2.17.1</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>2.0.13</version>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.3</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-registry-prometheus</artifactId>
            <version>1.13.0</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.10.2</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.11.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>postgresql</artifactId>
            <version>1.19.7</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>kafka</artifactId>
            <version>1.19.7</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.5</version>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/example/reconciliation/application/services/ReconciliationService.java ===
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

// === ARCHIVO: src/main/java/com/example/reconciliation/infrastructure/rest/ReconciliationController.java ===
package com.example.reconciliation.infrastructure.rest;

import com.example.reconciliation.application.services.ReconciliationService;
import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.domain.model.Reconciliation.Status;
import com.example.reconciliation.domain.repository.ReconciliationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reconciliations")
public class ReconciliationController {

    private static final Logger log = LoggerFactory.getLogger(ReconciliationController.class);
    private static final int DEFAULT_PAGE_SIZE = 50;
    private static final int MAX_PAGE_SIZE = 500;

    private final ReconciliationService reconciliationService;
    private final ReconciliationRepository reconciliationRepository;

    public ReconciliationController(
            ReconciliationService reconciliationService,
            ReconciliationRepository reconciliationRepository) {
        this.reconciliationService = reconciliationService;
        this.reconciliationRepository = reconciliationRepository;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ReconciliationResponse>> getById(@PathVariable UUID id) {
        log.debug("Fetching reconciliation by id: {}", id);
        return reconciliationRepository.findById(id)
            .map(reconciliation -> ResponseEntity.ok(toResponse(reconciliation)))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Mono<ReconciliationPageResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        
        int pageSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
        log.debug("Fetching reconciliations - page: {}, size: {}", page, pageSize);
        
        return reconciliationRepository.findAll(page, pageSize)
            .collectList()
            .zipWith(reconciliationRepository.count())
            .map(tuple -> new ReconciliationPageResponse(
                tuple.getT1().stream().map(this::toResponse).toList(),
                page,
                pageSize,
                tuple.getT2()
            ));
    }

    @GetMapping("/status/{status}")
    public Flux<ReconciliationResponse> getByStatus(@PathVariable Status status) {
        log.debug("Fetching reconciliations by status: {}", status);
        return reconciliationRepository.findByStatus(status)
            .map(this::toResponse);
    }

    @GetMapping("/pending")
    public Flux<ReconciliationResponse> getPending(
            @RequestParam(defaultValue = "300") int windowSeconds) {
        
        Instant cutoff = Instant.now().minusSeconds(windowSeconds);
        log.debug("Fetching pending reconciliations older than {} seconds", windowSeconds);
        
        return reconciliationRepository.findPendingOlderThan(cutoff)
            .map(this::toResponse);
    }

    @GetMapping("/statistics")
    public Mono<Map<String, Long>> getStatistics() {
        log.debug("Fetching reconciliation statistics");
        return reconciliationService.getStatistics();
    }

    @PostMapping("/{id}/match")
    public Mono<ResponseEntity<ReconciliationResponse>> matchReconciliation(
            @PathVariable UUID id) {
        
        log.info("Manual match requested for reconciliation: {}", id);
        return reconciliationService.manualMatch(id)
            .map(reconciliation -> ResponseEntity.ok(toResponse(reconciliation)))
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .onErrorResume(e -> {
                log.error("Error matching reconciliation {}: {}", id, e.getMessage());
                return Mono.just(ResponseEntity.badRequest().build());
            });
    }

    @PostMapping("/{id}/resolve")
    public Mono<ResponseEntity<ReconciliationResponse>> resolveReconciliation(
            @PathVariable UUID id,
            @RequestBody ResolveRequest request) {
        
        log.info("Manual resolution requested for reconciliation: {} with resolution: {}", 
            id, request.resolution());
        return reconciliationService.resolveManually(id, request.resolution())
            .map(reconciliation -> ResponseEntity.ok(toResponse(reconciliation)))
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .onErrorResume(e -> {
                log.error("Error resolving reconciliation {}: {}", id, e.getMessage());
                return Mono.just(ResponseEntity.badRequest().build());
            });
    }

    @PutMapping("/{id}/escalate")
    public Mono<ResponseEntity<ReconciliationResponse>> escalateToManual(
            @PathVariable UUID id,
            @RequestBody EscalateRequest request) {
        
        log.info("Escalation to manual for reconciliation: {} with reason: {}", 
            id, request.reason());
        return reconciliationService.escalateToManual(id, request.reason())
            .map(reconciliation -> ResponseEntity.ok(toResponse(reconciliation)))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/by-reference")
    public Flux<ReconciliationResponse> searchByReference(
            @RequestParam String reference,
            @RequestParam(required = false) String sourceType,
            @RequestParam(required = false) String targetType) {
        
        log.debug("Searching reconciliations by reference: {}, sourceType: {}, targetType: {}",
            reference, sourceType, targetType);
        
        return reconciliationService.searchByReference(reference, sourceType, targetType)
            .map(this::toResponse);
    }

    @PostMapping("/reprocess")
    public Mono<ResponseEntity<String>> reprocess(
            @RequestBody ReprocessRequest request) {
        
        log.info("Reprocess requested for eventId: {}, version: {}", 
            request.eventId(), request.version());
        return reconciliationService.reprocess(request.eventId(), request.version())
            .map(r -> ResponseEntity.ok("Reprocess initiated for " + request.eventId()))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private ReconciliationResponse toResponse(Reconciliation reconciliation) {
        return new ReconciliationResponse(
            reconciliation.getId(),
            reconciliation.getEventId(),
            reconciliation.getVersion(),
            reconciliation.getSourceType(),
            reconciliation.getTargetType(),
            reconciliation.getStatus().name(),
            reconciliation.getAmount(),
            reconciliation.getCurrency(),
            reconciliation.getReference(),
            reconciliation.getDescription(),
            reconciliation.getMatchedAt(),
            reconciliation.getCreatedAt(),
            reconciliation.getUpdatedAt(),
            reconciliation.getMismatchReason(),
            reconciliation.getManualResolution()
        );
    }

    public record ReconciliationResponse(
        UUID id,
        String eventId,
        int version,
        String sourceType,
        String targetType,
        String status,
        java.math.BigDecimal amount,
        String currency,
        String reference,
        String description,
        Instant matchedAt,
        Instant createdAt,
        Instant updatedAt,
        String mismatchReason,
        String manualResolution
    ) {}

    public record ReconciliationPageResponse(
        java.util.List<ReconciliationResponse> items,
        int page,
        int size,
        long total
    ) {}

    public record ResolveRequest(String resolution) {}
    public record EscalateRequest(String reason) {}
    public record ReprocessRequest(String eventId, int version, String reason, String resolution) {}
}

// === ARCHIVO: src/main/java/com/example/reconciliation/domain/model/Movement.java ===
package com.example.reconciliation.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class Movement {
    
    public enum SourceType {
        CORE_BANKING("Core Bancario"),
        PAYMENT_GATEWAY("Gateway de Pagos"),
        LIQUIDATION_SYSTEM("Sistema de Liquidación");
        
        private final String displayName;
        
        SourceType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public String name() {
            return super.name();
        }
    }
    
    public enum MovementType {
        CREDIT("Crédito"),
        DEBIT("Débito"),
        ADJUSTMENT("Ajuste");
        
        private final String displayName;
        
        MovementType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    private final UUID id;
    private final String eventId;
    private final int version;
    private final SourceType sourceType;
    private final MovementType movementType;
    private final BigDecimal amount;
    private final String currency;
    private final String reference;
    private final String accountNumber;
    private final Instant timestamp;
    private final Map<String, String> metadata;
    private final Instant receivedAt;
    private final boolean processed;
    
    private Movement(Builder builder) {
        this.id = builder.id;
        this.eventId = builder.eventId;
        this.version = builder.version;
        this.sourceType = builder.sourceType;
        this.movementType = builder.movementType;
        this.amount = builder.amount;
        this.currency = builder.currency;
        this.reference = builder.reference;
        this.accountNumber = builder.accountNumber;
        this.timestamp = builder.timestamp;
        this.metadata = builder.metadata != null ? Map.copyOf(builder.metadata) : Map.of();
        this.receivedAt = builder.receivedAt;
        this.processed = builder.processed;
    }
    
    public static Movement create(String eventId, int version, SourceType sourceType,
            MovementType movementType, BigDecimal amount, String currency, String reference,
            String accountNumber, Instant timestamp, Map<String, String> metadata) {
        
        Objects.requireNonNull(eventId, "El eventId no puede ser null");
        Objects.requireNonNull(sourceType, "El sourceType no puede ser null");
        Objects.requireNonNull(movementType, "El movementType no puede ser null");
        Objects.requireNonNull(amount, "El amount no puede ser null");
        Objects.requireNonNull(currency, "El currency no puede ser null");
        Objects.requireNonNull(timestamp, "El timestamp no puede ser null");
        
        if (version < 0) {
            throw new IllegalArgumentException("La versión no puede ser negativa");
        }
        
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        
        return Builder.create()
                .id(UUID.randomUUID())
                .eventId(eventId)
                .version(version)
                .sourceType(sourceType)
                .movementType(movementType)
                .amount(amount)
                .currency(currency)
                .reference(reference)
                .accountNumber(accountNumber)
                .timestamp(timestamp)
                .metadata(metadata)
                .receivedAt(Instant.now())
                .processed(false)
                .build();
    }
    
    public static Movement restore(UUID id, String eventId, int version, SourceType sourceType,
            MovementType movementType, BigDecimal amount, String currency, String reference,
            String accountNumber, Instant timestamp, Map<String, String> metadata,
            Instant receivedAt, boolean processed) {
        return Builder.create()
                .id(id)
                .eventId(eventId)
                .version(version)
                .sourceType(sourceType)
                .movementType(movementType)
                .amount(amount)
                .currency(currency)
                .reference(reference)
                .accountNumber(accountNumber)
                .timestamp(timestamp)
                .metadata(metadata)
                .receivedAt(receivedAt)
                .processed(processed)
                .build();
    }
    
    public boolean isDuplicate(Movement other) {
        if (other == null) return false;
        return this.eventId.equals(other.eventId) && 
               this.version == other.version && 
               this.sourceType == other.sourceType;
    }
    
    public boolean isOutOfOrder(Instant windowStart) {
        return timestamp.isBefore(windowStart);
    }
    
    public String getIdempotencyKey() {
        return sourceType.name() + ":" + eventId + ":" + version;
    }
    
    public boolean matchesAmount(BigDecimal expectedAmount) {
        return this.amount.compareTo(expectedAmount) == 0;
    }
    
    public boolean matchesReference(String expectedReference) {
        if (expectedReference == null) return false;
        return this.reference.equals(expectedReference);
    }
    
    public boolean matchesAccount(String expectedAccount) {
        if (expectedAccount == null) return false;
        return this.accountNumber.equals(expectedAccount);
    }
    
    public boolean isWithinTimeWindow(Instant windowStart, Instant windowEnd) {
        return !timestamp.isBefore(windowStart) && !timestamp.isAfter(windowEnd);
    }
    
    public UUID getId() { return id; }
    public String getEventId() { return eventId; }
    public int getVersion() { return version; }
    public SourceType getSourceType() { return sourceType; }
    public MovementType getMovementType() { return movementType; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getReference() { return reference; }
    public String getAccountNumber() { return accountNumber; }
    public Instant getTimestamp() { return timestamp; }
    public Map<String, String> getMetadata() { return metadata; }
    public Instant getReceivedAt() { return receivedAt; }
    public boolean isProcessed() { return processed; }
    
    public static class Builder {
        private UUID id;
        private String eventId;
        private int version;
        private SourceType sourceType;
        private MovementType movementType;
        private BigDecimal amount;
        private String currency;
        private String reference;
        private String accountNumber;
        private Instant timestamp;
        private Map<String, String> metadata;
        private Instant receivedAt;
        private boolean processed;
        
        private Builder() {}
        
        public static Builder create() {
            return new Builder();
        }
        
        public Builder id(UUID id) { this.id = id; return this; }
        public Builder eventId(String eventId) { this.eventId = eventId; return this; }
        public Builder version(int version) { this.version = version; return this; }
        public Builder sourceType(SourceType sourceType) { this.sourceType = sourceType; return this; }
        public Builder movementType(MovementType movementType) { this.movementType = movementType; return this; }
        public Builder amount(BigDecimal amount) { this.amount = amount; return this; }
        public Builder currency(String currency) { this.currency = currency; return this; }
        public Builder reference(String reference) { this.reference = reference; return this; }
        public Builder accountNumber(String accountNumber) { this.accountNumber = accountNumber; return this; }
        public Builder timestamp(Instant timestamp) { this.timestamp = timestamp; return this; }
        public Builder metadata(Map<String, String> metadata) { this.metadata = metadata; return this; }
        public Builder receivedAt(Instant receivedAt) { this.receivedAt = receivedAt; return this; }
        public Builder processed(boolean processed) { this.processed = processed; return this; }
        
        public Movement build() {
            return new Movement(this);
        }
    }
}

```
