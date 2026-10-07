# Implementación de Control de Transacciones en Sistema de Pagos

En el dominio de los sistemas de pago, las transacciones deben ser gestionadas de manera robusta y eficiente para asegurar la integridad de los datos y la confiabilidad del sistema. El sistema debe manejar transacciones que involucran múltiples actores: originador de créditos, motor antifraude, buró de riesgos, core bancario, gateway de pagos, sistema de liquidación, agente de retención y consolidador contable. Cada transacción debe cumplir con el modelo ACID y ser idempotente por número de operación y canal. El sistema debe procesar un volumen de 1 500 solicitudes por segundo en hora pico y mantener una latencia inferior a 500 ms. Los fallos comunes incluyen timeouts del buró superior a 2 segundos, respuestas 5xx del core y pérdida de conexión durante la escritura.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Diseño e Implementación de un Sistema de Transacciones Robusto y Eficiente |
| **Nivel** | advanced-l3 |
| **Tipo** | practical |
| **Tiempo estimado** | 30 horas |

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

### Fase 1: Definición y Modelado de Transacciones

**Objetivo:** Definir y modelar las transacciones necesarias para el sistema de pagos, asegurando que cumplan con el modelo ACID y sean idempotentes.

**Tiempo estimado:** 8 horas

**Instrucciones:**

- Identificar las transacciones clave en el sistema de pagos.
- Modelar cada transacción asegurando que cumpla con las propiedades de ACID.
- Asegurar la idempotencia de las transacciones por número de operación y canal.

**Entregable:** Modelo de transacciones con propiedades ACID y idempotencia definidas.

<details>
<summary>Pistas de conocimiento</summary>

- Considera el impacto de cada propiedad ACID en el diseño de las transacciones.
- Evalúa cómo la idempotencia afecta la consistencia y la recuperación de transacciones.

</details>

### Fase 2: Implementación de Control de Transacciones

**Objetivo:** Implementar el control de transacciones en el sistema de pagos, aplicando optimizaciones mediante procedimientos almacenados e índices.

**Tiempo estimado:** 10 horas

**Instrucciones:**

- Implementar el control de transacciones en el RDBMS y en el framework.
- Aplicar optimizaciones mediante procedimientos almacenados e índices.
- Asegurar que las transacciones cumplan con el modelo ACID y sean idempotentes.

**Entregable:** Sistema de pagos con control de transacciones implementado y optimizado.

<details>
<summary>Pistas de conocimiento</summary>

- Explora diferentes estrategias de optimización para mejorar el rendimiento de las transacciones.
- Considera el uso de índices para acelerar las consultas y reducir la latencia.

</details>

### Fase 3: Validación y Pruebas de Transacciones

**Objetivo:** Validar y probar el sistema de transacciones para asegurar su robustez y eficiencia.

**Tiempo estimado:** 10 horas

**Instrucciones:**

- Realizar pruebas unitarias y de integración para validar el sistema de transacciones.
- Simular diferentes escenarios de fallo para asegurar la robustez del sistema.
- Asegurar que el sistema cumple con los requisitos de volumen y latencia.

**Entregable:** Sistema de transacciones validado y probado, con reportes de pruebas y escenarios de fallo simulados.

<details>
<summary>Pistas de conocimiento</summary>

- Diseña pruebas que cubran diferentes casos de uso y escenarios de fallo.
- Utiliza herramientas de simulación para probar el sistema bajo carga y condiciones de fallo.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es el control de transacciones y por qué es importante en el sistema de pagos?
- **paraQueSirve**: ¿Para qué sirve el modelo ACID en las transacciones del sistema de pagos?
- **comoSeUsa**: ¿Cómo se pueden aplicar optimizaciones mediante procedimientos almacenados e índices en el sistema de transacciones?
- **erroresComunes**: ¿Cuáles son los errores comunes en las transacciones del sistema de pagos y cómo se pueden manejar?
- **queDecisionesImplica**: ¿Qué decisiones implica la implementación del control de transacciones en el sistema de pagos?

## Criterios de Evaluacion

- Definición y modelado de transacciones que cumplan con el modelo ACID y sean idempotentes.
- Implementación de control de transacciones con optimizaciones mediante procedimientos almacenados e índices.
- Validación y pruebas del sistema de transacciones para asegurar su robustez y eficiencia.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
