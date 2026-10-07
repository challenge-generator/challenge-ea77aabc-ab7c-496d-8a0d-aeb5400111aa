# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Implementación de Control de Transacciones en Sistema de Pagos**.

| | |
|---|---|
| Tema | Diseño e Implementación de un Sistema de Transacciones Robusto y Eficiente |
| Nivel | advanced-l3 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.5.6 |
| Patron arquitectonico | hexagonal/clean con manejo explícito de transacciones |
| Tiempo estimado | 30 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-web n/a
- org.springframework.boot:spring-boot-starter-data-jpa n/a
- org.springframework.boot:spring-boot-starter-validation n/a
- org.springframework.boot:spring-boot-starter-actuator n/a
- org.springframework.boot:spring-boot-starter-aop n/a
- org.springframework.boot:spring-boot-starter-test n/a
- org.postgresql:postgresql 42.7.3
- io.github.resilience4j:resilience4j-spring-boot3 2.2.0
- org.flywaydb:flyway-core 10.7.1
- org.springdoc:springdoc-openapi-starter-webmvc-ui 2.5.0
- org.projectlombok:lombok 1.18.32

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Definición y Modelado de Transacciones**: Modelo de transacciones con propiedades ACID y idempotencia definidas.
- **Fase 2 — Implementación de Control de Transacciones**: Sistema de pagos con control de transacciones implementado y optimizado.
- **Fase 3 — Validación y Pruebas de Transacciones**: Sistema de transacciones validado y probado, con reportes de pruebas y escenarios de fallo simulados.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Superficie de practica (NO completes)

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs. No toques la logica que el reto pide completar.

- [ ] `src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- [ ] `src/test/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapterTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controller/TransactionControllerTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.

## Lo que falta y tenes que completar

### 1. Referencias colgando (90)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/pragma/payments/infrastructure/config/GlobalExceptionHandler.java` — `com.pragma.payments.application.service.exception.TransactionNotFoundException`
      El import com.pragma.payments.application.service.exception.TransactionNotFoundException usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/pragma/payments/infrastructure/config/GlobalExceptionHandler.java` — `com.pragma.payments.application.service.exception.DuplicateIdempotencyKeyException`
      El import com.pragma.payments.application.service.exception.DuplicateIdempotencyKeyException usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/pragma/payments/infrastructure/config/GlobalExceptionHandler.java` — `com.pragma.payments.application.service.exception.TransactionTimeoutException`
      El import com.pragma.payments.application.service.exception.TransactionTimeoutException usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/pragma/payments/infrastructure/config/GlobalExceptionHandler.java` — `com.pragma.payments.application.service.exception.TransactionFailedException`
      El import com.pragma.payments.application.service.exception.TransactionFailedException usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/pragma/payments/domain/port/FraudDetectionPort.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `reactor.core.scheduler`
      El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `org.hibernate.annotations`
      El import org.hibernate.annotations pertenece a org.hibernate.annotations, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/config/GlobalExceptionHandler.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/pragma/payments/infrastructure/controller/TransactionControllerTest.java` — `com.fasterxml.jackson`
      El import com.fasterxml.jackson.databind.ObjectMapper pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/Application.java` — `ApplicationProperties.getCircuitBreaker`
      Se invoca `getCircuitBreaker` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/Application.java` — `ApplicationProperties.getRetry`
      Se invoca `getRetry` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/Application.java` — `ApplicationProperties.getTimeout`
      Se invoca `getTimeout` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `PaymentTransaction.operationNumber`
      Se invoca `operationNumber` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `PaymentTransaction.transactionId`
      Se invoca `transactionId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `FraudCheckResult.blocked`
      Se invoca `blocked` sobre `FraudCheckResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `FraudCheckResult.reason`
      Se invoca `reason` sobre `FraudCheckResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `FraudCheckResult.checkId`
      Se invoca `checkId` sobre `FraudCheckResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `PaymentTransaction.amount`
      Se invoca `amount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `PaymentTransaction.currency`
      Se invoca `currency` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.transactionId`
      Se invoca `transactionId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionJpaRepository.save`
      Se invoca `save` sobre `TransactionJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionEntity.getTransactionId`
      Se invoca `getTransactionId` sobre `TransactionEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionJpaRepository.findById`
      Se invoca `findById` sobre `TransactionJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionStatus.name`
      Se invoca `name` sobre `TransactionStatus`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionEntity.setTransactionId`
      Se invoca `setTransactionId` sobre `TransactionEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.operationNumber`
      Se invoca `operationNumber` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.channel`
      Se invoca `channel` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.amount`
      Se invoca `amount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.currency`
      Se invoca `currency` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.createdAt`
      Se invoca `createdAt` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.status`
      Se invoca `status` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.originatorAccount`
      Se invoca `originatorAccount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.beneficiaryAccount`
      Se invoca `beneficiaryAccount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.reference`
      Se invoca `reference` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.fraudCheckId`
      Se invoca `fraudCheckId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.metadata`
      Se invoca `metadata` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `PaymentTransaction.transactionId`
      Se invoca `transactionId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `PaymentTransaction.amount`
      Se invoca `amount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `PaymentTransaction.currency`
      Se invoca `currency` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `PaymentTransaction.channel`
      Se invoca `channel` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `PaymentTransaction.reference`
      Se invoca `reference` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.transactionId`
      Se invoca `transactionId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.operationNumber`
      Se invoca `operationNumber` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.channel`
      Se invoca `channel` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.amount`
      Se invoca `amount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.currency`
      Se invoca `currency` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.createdAt`
      Se invoca `createdAt` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.status`
      Se invoca `status` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.originatorAccount`
      Se invoca `originatorAccount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.beneficiaryAccount`
      Se invoca `beneficiaryAccount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.reference`
      Se invoca `reference` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.fraudCheckId`
      Se invoca `fraudCheckId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.metadata`
      Se invoca `metadata` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `StatusUpdateRequest.operationNumber`
      Se invoca `operationNumber` sobre `StatusUpdateRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `StatusUpdateRequest.channel`
      Se invoca `channel` sobre `StatusUpdateRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `StatusUpdateRequest.amount`
      Se invoca `amount` sobre `StatusUpdateRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `TransactionService.processTransaction`
      Se invoca `processTransaction` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `StatusUpdateRequest.currency`
      Se invoca `currency` sobre `StatusUpdateRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `StatusUpdateRequest.originatorAccount`
      Se invoca `originatorAccount` sobre `StatusUpdateRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `StatusUpdateRequest.beneficiaryAccount`
      Se invoca `beneficiaryAccount` sobre `StatusUpdateRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `StatusUpdateRequest.reference`
      Se invoca `reference` sobre `StatusUpdateRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `StatusUpdateRequest.metadata`
      Se invoca `metadata` sobre `StatusUpdateRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `TransactionService.findById`
      Se invoca `findById` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `TransactionService.findTransactions`
      Se invoca `findTransactions` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `StatusUpdateRequest.status`
      Se invoca `status` sobre `StatusUpdateRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.transactionId`
      Se invoca `transactionId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.operationNumber`
      Se invoca `operationNumber` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.channel`
      Se invoca `channel` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.amount`
      Se invoca `amount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.currency`
      Se invoca `currency` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.createdAt`
      Se invoca `createdAt` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.status`
      Se invoca `status` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.originatorAccount`
      Se invoca `originatorAccount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.beneficiaryAccount`
      Se invoca `beneficiaryAccount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.reference`
      Se invoca `reference` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.fraudCheckId`
      Se invoca `fraudCheckId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.metadata`
      Se invoca `metadata` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java` — `PaymentTransaction.status`
      Se invoca `status` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java` — `TransactionService.updateStatus`
      Se invoca `updateStatus` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapterTest.java` — `PaymentTransaction.transactionId`
      Se invoca `transactionId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapterTest.java` — `PaymentTransaction.operationNumber`
      Se invoca `operationNumber` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controller/TransactionControllerTest.java` — `PaymentTransaction.transactionId`
      Se invoca `transactionId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controller/TransactionControllerTest.java` — `TransactionService.getTransaction`
      Se invoca `getTransaction` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (19)

- `pom.xml`
- `src/main/java/com/pragma/payments/Application.java`
- `src/main/resources/application.yml`
- `src/main/java/com/pragma/payments/domain/model/PaymentTransaction.java`
- `src/main/java/com/pragma/payments/domain/port/TransactionRepositoryPort.java`
- `src/main/java/com/pragma/payments/domain/port/FraudDetectionPort.java`
- `src/main/java/com/pragma/payments/infrastructure/repository/TransactionJpaRepository.java`
- `src/main/java/com/pragma/payments/application/service/TransactionService.java`
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java`
- `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java`
- `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java`
- `src/main/java/com/pragma/payments/infrastructure/config/TransactionConfig.java`
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java`
- `src/main/java/com/pragma/payments/infrastructure/config/GlobalExceptionHandler.java`
- `src/main/resources/db/migration/V1__Create_transaction_table.sql`
- `src/main/resources/db/migration/V2__Create_stored_procedures.sql`
- `src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java`
- `src/test/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapterTest.java`
- `src/test/java/com/pragma/payments/infrastructure/controller/TransactionControllerTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/pragma/payments`
- `src/main/java/com/pragma/payments/domain`
- `src/main/java/com/pragma/payments/domain/model`
- `src/main/java/com/pragma/payments/domain/port`
- `src/main/java/com/pragma/payments/application`
- `src/main/java/com/pragma/payments/application/service`
- `src/main/java/com/pragma/payments/infrastructure`
- `src/main/java/com/pragma/payments/infrastructure/adapter`
- `src/main/java/com/pragma/payments/infrastructure/config`
- `src/main/java/com/pragma/payments/infrastructure/controller`
- `src/main/java/com/pragma/payments/infrastructure/entity`
- `src/main/java/com/pragma/payments/infrastructure/repository`
- `src/main/resources`
- `src/test/java/com/pragma/payments`

## Verificacion

```bash
mvn clean compile
```

El comando tiene que pasar SIN implementar los archivos de la superficie de practica: solo andamiaje.

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean con manejo explícito de transacciones**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Desarrollador, Tecnología Java, Advanced
- Brecha que el reto ataca: Implementa control de transacciones (TCL) en RDBMS y en el framework, aplicando optimizaciones mediante procedimientos almacenados, índices, etc. Asimismo, asegura la aplicación del modelo ACID (Atomicidad, Consistencia, Aislamiento y Durabilidad) en las transacciones.
- Mision: Candidato con experiencia avanzada en backend

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
