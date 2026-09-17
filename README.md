# Diseño de Sistema de Conciliación Bancaria en Tiempo Real

El sistema de conciliación bancaria debe consumir streams de movimientos desde tres fuentes: core bancario, gateway de pagos y sistema de liquidación. El objetivo es detectar discrepancias en ventanas móviles y reconciliar cada movimiento contra las tres fuentes con tolerancia a mensajes fuera de orden y llegadas duplicadas. Debes diseñar la máquina de estados de cada Reconciliation (Pending, Matched, Mismatched, Manual), justificar la ventana de matching (5 min vs 1 hora), definir cómo maneja idempotencia con eventId + version, y elegir entre reprocesamiento por replay de Kafka vs snapshot desde PostgreSQL. Adicionalmente, debes explicar cómo alertar al equipo de operaciones cuando el lag de conciliación supera un SLA.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | TEST-CT |
| **Nivel** | senior-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 10 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Exploración y Definición de Requisitos

**Objetivo:** Identificar y documentar los requisitos funcionales y no funcionales del sistema de conciliación.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Enumera las tres fuentes de movimientos y describe sus características.
- Identifica las discrepancias que el sistema debe detectar.
- Define la máquina de estados para el proceso de conciliación.
- Justifica la elección de la ventana de matching.
- Describe cómo manejará la idempotencia y las llegadas duplicadas.

**Entregable:** Documento de requisitos funcionales y no funcionales del sistema de conciliación.

<details>
<summary>Pistas de conocimiento</summary>

- Considera las propiedades operativas del dominio, como latencia y disponibilidad.
- Piensa en los posibles modos de falla y cómo el sistema debe responder.

</details>

### Fase 2: Diseño de la Máquina de Estados

**Objetivo:** Diseñar la máquina de estados para el proceso de conciliación y justificar las decisiones tomadas.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Diseña la máquina de estados con los estados Pending, Matched, Mismatched y Manual.
- Justifica la elección de cada estado y las transiciones entre ellos.
- Describe cómo el sistema manejará la idempotencia con eventId + version.

**Entregable:** Diagrama de la máquina de estados y documentación de las decisiones tomadas.

<details>
<summary>Pistas de conocimiento</summary>

- Considera los posibles modos de falla y cómo el sistema debe responder.
- Piensa en las transiciones entre estados y las condiciones que las desencadenan.

</details>

### Fase 3: Estrategia de Reprocesamiento

**Objetivo:** Elegir entre reprocesamiento por replay de Kafka vs snapshot desde PostgreSQL y justificar la decisión.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Evalúa las ventajas y desventajas de reprocesamiento por replay de Kafka vs snapshot desde PostgreSQL.
- Elige una estrategia y justifica tu decisión.
- Describe cómo implementará la estrategia elegida.

**Entregable:** Documentación de la estrategia de reprocesamiento elegida y justificación de la decisión.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la latencia, la disponibilidad y la consistencia del sistema.
- Piensa en los posibles modos de falla y cómo el sistema debe responder.

</details>

### Fase 4: Alerta de Lag de Conciliación

**Objetivo:** Definir cómo alertar al equipo de operaciones cuando el lag de conciliación supera un SLA.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Define el SLA para el lag de conciliación.
- Describe cómo el sistema detectará cuando el lag supera el SLA.
- Define el mecanismo de alerta al equipo de operaciones.

**Entregable:** Documentación del mecanismo de alerta y definición del SLA.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la latencia y la disponibilidad del sistema.
- Piensa en los posibles modos de falla y cómo el sistema debe responder.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es la máquina de estados del proceso de conciliación y cuáles son sus estados?
- **paraQueSirve**: ¿Para qué sirve la idempotencia en el proceso de conciliación?
- **comoSeUsa**: ¿Cómo se usa la idempotencia con eventId + version en el proceso de conciliación?
- **erroresComunes**: ¿Cuáles son los errores comunes en el proceso de conciliación y cómo los manejaría el sistema?
- **queDecisionesImplica**: ¿Qué decisiones implica la elección entre reprocesamiento por replay de Kafka vs snapshot desde PostgreSQL?

## Criterios de Evaluacion

- Definición clara de los requisitos funcionales y no funcionales del sistema de conciliación.
- Diseño detallado de la máquina de estados del proceso de conciliación.
- Justificación de la elección de la ventana de matching.
- Descripción de cómo manejará la idempotencia y las llegadas duplicadas.
- Evaluación y elección de la estrategia de reprocesamiento.
- Definición del SLA para el lag de conciliación y mecanismo de alerta al equipo de operaciones.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
el comando de build o arranque canonico del stack elegido
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
