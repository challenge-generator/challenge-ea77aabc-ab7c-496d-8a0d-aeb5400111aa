# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- `src/test/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapterTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- `src/test/java/com/pragma/payments/infrastructure/controller/TransactionControllerTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/pragma/payments/infrastructure/config/GlobalExceptionHandler.java` — `com.pragma.payments.application.service.exception.TransactionNotFoundException`: El import com.pragma.payments.application.service.exception.TransactionNotFoundException usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/payments/infrastructure/config/GlobalExceptionHandler.java` — `com.pragma.payments.application.service.exception.DuplicateIdempotencyKeyException`: El import com.pragma.payments.application.service.exception.DuplicateIdempotencyKeyException usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/payments/infrastructure/config/GlobalExceptionHandler.java` — `com.pragma.payments.application.service.exception.TransactionTimeoutException`: El import com.pragma.payments.application.service.exception.TransactionTimeoutException usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/payments/infrastructure/config/GlobalExceptionHandler.java` — `com.pragma.payments.application.service.exception.TransactionFailedException`: El import com.pragma.payments.application.service.exception.TransactionFailedException usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/payments/domain/port/FraudDetectionPort.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `reactor.core.scheduler`: El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `org.hibernate.annotations`: El import org.hibernate.annotations pertenece a org.hibernate.annotations, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/config/GlobalExceptionHandler.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/pragma/payments/infrastructure/controller/TransactionControllerTest.java` — `com.fasterxml.jackson`: El import com.fasterxml.jackson.databind.ObjectMapper pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/Application.java` — `ApplicationProperties.getCircuitBreaker`: Se invoca `getCircuitBreaker` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/Application.java` — `ApplicationProperties.getRetry`: Se invoca `getRetry` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/Application.java` — `ApplicationProperties.getTimeout`: Se invoca `getTimeout` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `PaymentTransaction.operationNumber`: Se invoca `operationNumber` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `PaymentTransaction.transactionId`: Se invoca `transactionId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `FraudCheckResult.blocked`: Se invoca `blocked` sobre `FraudCheckResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `FraudCheckResult.reason`: Se invoca `reason` sobre `FraudCheckResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `FraudCheckResult.checkId`: Se invoca `checkId` sobre `FraudCheckResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `PaymentTransaction.amount`: Se invoca `amount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `PaymentTransaction.currency`: Se invoca `currency` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.transactionId`: Se invoca `transactionId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionJpaRepository.save`: Se invoca `save` sobre `TransactionJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionEntity.getTransactionId`: Se invoca `getTransactionId` sobre `TransactionEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionJpaRepository.findById`: Se invoca `findById` sobre `TransactionJpaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionStatus.name`: Se invoca `name` sobre `TransactionStatus`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionEntity.setTransactionId`: Se invoca `setTransactionId` sobre `TransactionEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.operationNumber`: Se invoca `operationNumber` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.channel`: Se invoca `channel` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.amount`: Se invoca `amount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.currency`: Se invoca `currency` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.createdAt`: Se invoca `createdAt` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.status`: Se invoca `status` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.originatorAccount`: Se invoca `originatorAccount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.beneficiaryAccount`: Se invoca `beneficiaryAccount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.reference`: Se invoca `reference` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.fraudCheckId`: Se invoca `fraudCheckId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java` — `PaymentTransaction.metadata`: Se invoca `metadata` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `PaymentTransaction.transactionId`: Se invoca `transactionId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `PaymentTransaction.amount`: Se invoca `amount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `PaymentTransaction.currency`: Se invoca `currency` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `PaymentTransaction.channel`: Se invoca `channel` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `PaymentTransaction.reference`: Se invoca `reference` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.transactionId`: Se invoca `transactionId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.operationNumber`: Se invoca `operationNumber` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.channel`: Se invoca `channel` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.amount`: Se invoca `amount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.currency`: Se invoca `currency` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.createdAt`: Se invoca `createdAt` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.status`: Se invoca `status` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.originatorAccount`: Se invoca `originatorAccount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.beneficiaryAccount`: Se invoca `beneficiaryAccount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.reference`: Se invoca `reference` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.fraudCheckId`: Se invoca `fraudCheckId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java` — `PaymentTransaction.metadata`: Se invoca `metadata` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `StatusUpdateRequest.operationNumber`: Se invoca `operationNumber` sobre `StatusUpdateRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `StatusUpdateRequest.channel`: Se invoca `channel` sobre `StatusUpdateRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `StatusUpdateRequest.amount`: Se invoca `amount` sobre `StatusUpdateRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `TransactionService.processTransaction`: Se invoca `processTransaction` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `StatusUpdateRequest.currency`: Se invoca `currency` sobre `StatusUpdateRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `StatusUpdateRequest.originatorAccount`: Se invoca `originatorAccount` sobre `StatusUpdateRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `StatusUpdateRequest.beneficiaryAccount`: Se invoca `beneficiaryAccount` sobre `StatusUpdateRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `StatusUpdateRequest.reference`: Se invoca `reference` sobre `StatusUpdateRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `StatusUpdateRequest.metadata`: Se invoca `metadata` sobre `StatusUpdateRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `TransactionService.findById`: Se invoca `findById` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `TransactionService.findTransactions`: Se invoca `findTransactions` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `StatusUpdateRequest.status`: Se invoca `status` sobre `StatusUpdateRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.transactionId`: Se invoca `transactionId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.operationNumber`: Se invoca `operationNumber` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.channel`: Se invoca `channel` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.amount`: Se invoca `amount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.currency`: Se invoca `currency` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.createdAt`: Se invoca `createdAt` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.status`: Se invoca `status` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.originatorAccount`: Se invoca `originatorAccount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.beneficiaryAccount`: Se invoca `beneficiaryAccount` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.reference`: Se invoca `reference` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.fraudCheckId`: Se invoca `fraudCheckId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `PaymentTransaction.metadata`: Se invoca `metadata` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java` — `PaymentTransaction.status`: Se invoca `status` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java` — `TransactionService.updateStatus`: Se invoca `updateStatus` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapterTest.java` — `PaymentTransaction.transactionId`: Se invoca `transactionId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapterTest.java` — `PaymentTransaction.operationNumber`: Se invoca `operationNumber` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/controller/TransactionControllerTest.java` — `PaymentTransaction.transactionId`: Se invoca `transactionId` sobre `PaymentTransaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/controller/TransactionControllerTest.java` — `TransactionService.getTransaction`: Se invoca `getTransaction` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollador, Tecnología Java, Advanced

### Brecha de conocimiento
Implementa control de transacciones (TCL) en RDBMS y en el framework, aplicando optimizaciones mediante procedimientos almacenados, índices, etc. Asimismo, asegura la aplicación del modelo ACID (Atomicidad, Consistencia, Aislamiento y Durabilidad) en las transacciones.

### Misión / candidato
Candidato con experiencia avanzada en backend

### Reto
- Tema: Diseño e Implementación de un Sistema de Transacciones Robusto y Eficiente
- Seniority: advanced-l3
- Tipo: practical
- Título: Implementación de Control de Transacciones en Sistema de Pagos
- Tiempo estimado: 30 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Definición y Modelado de Transacciones — objetivo: Definir y modelar las transacciones necesarias para el sistema de pagos, asegurando que cumplan con el modelo ACID y sean idempotentes. — entregable (NO resolver): Modelo de transacciones con propiedades ACID y idempotencia definidas.
- Fase 2: Implementación de Control de Transacciones — objetivo: Implementar el control de transacciones en el sistema de pagos, aplicando optimizaciones mediante procedimientos almacenados e índices. — entregable (NO resolver): Sistema de pagos con control de transacciones implementado y optimizado.
- Fase 3: Validación y Pruebas de Transacciones — objetivo: Validar y probar el sistema de transacciones para asegurar su robustez y eficiencia. — entregable (NO resolver): Sistema de transacciones validado y probado, con reportes de pruebas y escenarios de fallo simulados.

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
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.pragma</groupId>
    <artifactId>payments</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>payments</name>
    <description>Sistema de pagos con control transaccional robusto</description>

    <properties>
        <java.version>21</java.version>
        <resilience4j.version>2.2.0</resilience4j.version>
        <springdoc-openapi.version>2.5.0</springdoc-openapi.version>
        <flyway.version>10.7.1</flyway.version>
        <lombok.version>1.18.32</lombok.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

        <!-- Resilience4j -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <!-- Database -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.3</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-core</artifactId>
            <version>${flyway.version}</version>
        </dependency>

        <!-- Documentation -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>${springdoc-openapi.version}</version>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
            <scope>provided</scope>
        </dependency>

        <!-- Testing -->
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
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/pragma/payments/Application.java ===
package com.pragma.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.retry.annotation.EnableRetry;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableRetry
@EnableConfigurationProperties
public class Application {
    private final ApplicationProperties properties;

    public Application(ApplicationProperties properties) {
        this.properties = properties;
        validateProperties();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(properties.getCircuitBreaker().getFailureRateThreshold())
                .waitDurationInOpenState(Duration.ofMillis(properties.getCircuitBreaker().getWaitDurationInOpenState()))
                .slidingWindowSize(properties.getCircuitBreaker().getSlidingWindowSize())
                .recordExceptions(
                    TimeoutException.class,
                    org.springframework.transaction.TransactionSystemException.class,
                    org.springframework.dao.DataAccessResourceFailureException.class
                )
                .build();
        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(properties.getRetry().getMaxAttempts())
                .waitDuration(Duration.ofMillis(properties.getRetry().getWaitDuration()))
                .retryExceptions(
                    TimeoutException.class,
                    org.springframework.transaction.TransactionTimeoutException.class
                )
                .build();
        return RetryRegistry.of(config);
    }

    @Bean
    public TimeLimiterRegistry timeLimiterRegistry() {
        TimeLimiterConfig config = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(properties.getTimeout().getTransactionTimeout()))
                .build();
        return TimeLimiterRegistry.of(config);
    }

    private void validateProperties() {
        if (properties.getCircuitBreaker().getFailureRateThreshold() <= 0 ||
            properties.getCircuitBreaker().getFailureRateThreshold() > 100) {
            throw new IllegalStateException("failureRateThreshold must be between 1 and 100");
        }
        if (properties.getRetry().getMaxAttempts() < 1) {
            throw new IllegalStateException("maxAttempts must be at least 1");
        }
        if (properties.getTimeout().getTransactionTimeout() <= 0) {
            throw new IllegalStateException("transactionTimeout must be positive");
        }
    }

    public record CircuitBreakerProperties(
        int failureRateThreshold,
        int waitDurationInOpenState,
        int slidingWindowSize
    ) {}

    public record RetryProperties(
        int maxAttempts,
        int waitDuration
    ) {}

    public record TimeoutProperties(
        int transactionTimeout
    ) {}

    public record ApplicationProperties(
        CircuitBreakerProperties circuitBreaker,
        RetryProperties retry,
        TimeoutProperties timeout,
        String defaultCurrency
    ) {}
}

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: payments-system
  datasource:
    url: jdbc:postgresql://localhost:5432/payments_db
    username: payments_user
    password: payments_password
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 20
      minimum-idle: 10
      idle-timeout: 30000
      max-lifetime: 600000
      connection-timeout: 30000
      pool-name: payments-pool
      auto-commit: false
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        jdbc:
          time_zone: UTC
          batch_size: 50
          fetch_size: 50
        order_inserts: true
        order_updates: true
        connection:
          provider_disables_autocommit: true
    open-in-view: false
  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true
    validate-on-migrate: true
    out-of-order: false
    schemas: public
    table: flyway_schema_history
  transaction:
    default-timeout: 5
    rollback-on-commit-failure: true

server:
  port: 8080
  servlet:
    context-path: /api/v1
  error:
    include-message: always
    include-binding-errors: always
    include-stacktrace: never

management:
  endpoints:
    web:
      exposure:
        include: health,metrics,prometheus,info
  endpoint:
    health:
      show-details: always
      probes:
        enabled: true
  health:
    circuitbreakers:
      enabled: true
    ratelimiters:
      enabled: true

resilience4j:
  circuitbreaker:
    configs:
      default:
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 5000
        failureRateThreshold: 50
        eventConsumerBufferSize: 10
        recordExceptions:
          - org.springframework.transaction.TransactionSystemException
          - java.util.concurrent.TimeoutException
          - org.springframework.dao.DataAccessResourceFailureException
    instances:
      fraudDetection:
        baseConfig: default
        failureRateThreshold: 60
        waitDurationInOpenState: 10000
      coreBanking:
        baseConfig: default
        failureRateThreshold: 70
        waitDurationInOpenState: 15000
  retry:
    configs:
      default:
        maxAttempts: 3
        waitDuration: 500
        retryExceptions:
          - java.util.concurrent.TimeoutException
          - org.springframework.transaction.TransactionTimeoutException
    instances:
      fraudDetection:
        baseConfig: default
      coreBanking:
        baseConfig: default
  timelimiter:
    configs:
      default:
        timeoutDuration: 2s
    instances:
      fraudDetection:
        timeoutDuration: 1500ms
      coreBanking:
        timeoutDuration: 3000ms

app:
  default-currency: COP
  transaction:
    idempotency:
      enabled: true
      cache-ttl: 3600
      cache-size: 10000
    validation:
      min-amount: 100
      max-amount: 10000000
      allowed-channels:
        - MOBILE
        - WEB
        - POS
        - ATM
  circuit-breaker:
    failure-rate-threshold: 50
    wait-duration-in-open-state: 5000
    sliding-window-size: 10
  retry:
    max-attempts: 3
    wait-duration: 500
  timeout:
    transaction-timeout: 5000

// === ARCHIVO: src/main/java/com/pragma/payments/domain/model/PaymentTransaction.java ===
package com.pragma.payments.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder(toBuilder = true)
public class PaymentTransaction {
    private final UUID transactionId;

    @NotNull
    @Size(min = 1, max = 64)
    private final String operationNumber;

    @NotNull
    @Size(min = 1, max = 32)
    private final String channel;

    @NotNull
    @Positive
    private final BigDecimal amount;

    @NotNull
    @Size(min = 3, max = 3)
    private final String currency;

    @NotNull
    private final LocalDateTime createdAt;

    @NotNull
    private final TransactionStatus status;

    @NotNull
    @Size(min = 1, max = 255)
    private final String originatorAccount;

    @NotNull
    @Size(min = 1, max = 255)
    private final String beneficiaryAccount;

    @Size(max = 255)
    private final String reference;

    @Size(max = 255)
    private final String fraudCheckId;

    @Size(max = 2048)
    private final String metadata;

    public enum TransactionStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED,
        REVERSED,
        TIMEOUT
    }

    public PaymentTransaction withStatus(TransactionStatus newStatus) {
        return this.toBuilder().status(newStatus).build();
    }

    public PaymentTransaction withFraudCheckId(String fraudCheckId) {
        return this.toBuilder().fraudCheckId(fraudCheckId).build();
    }

    public boolean isIdempotencyKeyUnique() {
        return operationNumber != null && channel != null;
    }

    public String generateIdempotencyKey() {
        return operationNumber + "|" + channel;
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/port/TransactionRepositoryPort.java ===
package com.pragma.payments.domain.port;


import com.pragma.payments.domain.model.TransactionStatus;
import com.pragma.payments.domain.model.PaymentTransaction;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepositoryPort {
    PaymentTransaction save(PaymentTransaction transaction);
    
    Optional<PaymentTransaction> findById(UUID transactionId);
    
    Optional<PaymentTransaction> findByIdempotencyKey(String idempotencyKey);
    
    boolean existsByIdempotencyKey(String idempotencyKey);
    
    void updateStatus(UUID transactionId, PaymentTransaction.TransactionStatus status);
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/port/FraudDetectionPort.java ===
package com.pragma.payments.domain.port;

import com.pragma.payments.domain.model.PaymentTransaction;
import reactor.core.publisher.Mono;

public interface FraudDetectionPort {
    Mono<FraudCheckResult> checkForFraud(PaymentTransaction transaction);
    
    record FraudCheckResult(
        String fraudCheckId,
        boolean isFraudulent,
        String riskLevel,
        String details
    ) {
        public boolean isAllowed() {
            return !isFraudulent;
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/repository/TransactionJpaRepository.java ===
package com.pragma.payments.infrastructure.repository;

import com.pragma.payments.infrastructure.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, UUID> {

    @Query(value = "SELECT * FROM transactions WHERE idempotency_key = :idempotencyKey LIMIT 1", nativeQuery = true)
    TransactionEntity findByIdempotencyKey(@Param("idempotencyKey") String idempotencyKey);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM transactions WHERE idempotency_key = :idempotencyKey)", nativeQuery = true)
    boolean existsByIdempotencyKey(@Param("idempotencyKey") String idempotencyKey);

    @Modifying
    @Query(value = "UPDATE transactions SET status = :status, updated_at = :updatedAt WHERE transaction_id = :transactionId", nativeQuery = true)
    void updateTransactionStatus(@Param("transactionId") UUID transactionId, @Param("status") String status, @Param("updatedAt") LocalDateTime updatedAt);

    @Query(value = "SELECT * FROM transactions WHERE originator_account = :accountNumber AND created_at BETWEEN :startDate AND :endDate ORDER BY created_at DESC", nativeQuery = true)
    List<TransactionEntity> findByOriginatorAccountAndDateRange(@Param("accountNumber") String accountNumber, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM transactions WHERE beneficiary_account = :accountNumber AND created_at BETWEEN :startDate AND :endDate ORDER BY created_at DESC", nativeQuery = true)
    List<TransactionEntity> findByBeneficiaryAccountAndDateRange(@Param("accountNumber") String accountNumber, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM transactions WHERE amount >= :minAmount AND amount <= :maxAmount AND created_at BETWEEN :startDate AND :endDate ORDER BY amount DESC", nativeQuery = true)
    List<TransactionEntity> findByAmountRangeAndDateRange(@Param("minAmount") BigDecimal minAmount, @Param("maxAmount") BigDecimal maxAmount, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM transactions WHERE status = :status AND created_at < :thresholdDate ORDER BY created_at ASC LIMIT :limit", nativeQuery = true)
    List<TransactionEntity> findStaleTransactions(@Param("status") String status, @Param("thresholdDate") LocalDateTime thresholdDate, @Param("limit") int limit);

    @Modifying
    @Query(value = "CALL sp_process_transaction_batch(:batchId, :totalCount)", nativeQuery = true)
    void executeStoredProcedureForBatch(@Param("batchId") String batchId, @Param("totalCount") int totalCount);

    @Query(value = "SELECT COUNT(*) FROM transactions WHERE status = :status AND DATE(created_at) = CURRENT_DATE", nativeQuery = true)
    long countByStatusForToday(@Param("status") String status);

    @Query(value = "SELECT * FROM transactions WHERE channel = :channel AND status = :status ORDER BY created_at DESC LIMIT :limit", nativeQuery = true)
    List<TransactionEntity> findRecentByChannelAndStatus(@Param("channel") String channel, @Param("status") String status, @Param("limit") int limit);
}

// === ARCHIVO: src/main/java/com/pragma/payments/application/service/TransactionService.java ===
package com.pragma.payments.application.service;

import com.pragma.payments.domain.model.PaymentTransaction;
import com.pragma.payments.domain.model.PaymentTransaction.TransactionStatus;
import com.pragma.payments.domain.port.FraudDetectionPort;
import com.pragma.payments.domain.port.FraudDetectionPort.FraudCheckResult;
import com.pragma.payments.domain.port.TransactionRepositoryPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final int CIRCUIT_BREAKER_FAILURE_THRESHOLD = 5;

    private final TransactionRepositoryPort transactionRepository;
    private final FraudDetectionPort fraudDetectionPort;

    public TransactionService(TransactionRepositoryPort transactionRepository, FraudDetectionPort fraudDetectionPort) {
        this.transactionRepository = transactionRepository;
        this.fraudDetectionPort = fraudDetectionPort;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, isolation = Transactional.Isolation.READ_COMMITTED)
    public PaymentTransaction createTransaction(PaymentTransaction transaction) {
        log.info("Iniciando proceso de creación de transacción con número de operación: {}", transaction.operationNumber());

        if (!transaction.isIdempotencyKeyUnique()) {
            String idempotencyKey = transaction.generateIdempotencyKey();
            Optional<PaymentTransaction> existingTransaction = transactionRepository.findByIdempotencyKey(idempotencyKey);
            if (existingTransaction.isPresent()) {
                log.warn("Transacción duplicada detectada para clave de idempotencia: {}", idempotencyKey);
                return existingTransaction.get();
            }
        }

        PaymentTransaction validatedTransaction = validateTransactionLimits(transaction);
        PaymentTransaction savedTransaction = transactionRepository.save(validatedTransaction);
        log.info("Transacción creada exitosamente con ID: {}", savedTransaction.transactionId());

        return savedTransaction;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, isolation = Transactional.Isolation.SERIALIZABLE)
    public PaymentTransaction processTransactionWithFraudCheck(PaymentTransaction transaction) {
        log.info("Procesando transacción {} con verificación antifraude", transaction.transactionId());

        PaymentTransaction savedTransaction = transactionRepository.save(transaction);

        FraudCheckResult fraudResult = checkFraudWithResilience(savedTransaction);

        if (fraudResult.blocked()) {
            log.warn("Transacción {} bloqueada por sistema antifraude. Razón: {}", transaction.transactionId(), fraudResult.reason());
            PaymentTransaction blockedTransaction = savedTransaction.withStatus(TransactionStatus.FRAUD_REJECTED);
            transactionRepository.updateStatus(savedTransaction.transactionId(), TransactionStatus.FRAUD_REJECTED);
            return blockedTransaction;
        }

        PaymentTransaction approvedTransaction = savedTransaction.withFraudCheckId(fraudResult.checkId());
        transactionRepository.updateStatus(savedTransaction.transactionId(), TransactionStatus.APPROVED);
        log.info("Transacción {} aprobada después de verificación antifraude", transaction.transactionId());

        return approvedTransaction;
    }

    @CircuitBreaker(name = "fraudDetection", fallbackMethod = "fraudCheckFallback")
    @Retry(name = "fraudDetection", maxAttempts = MAX_RETRY_ATTEMPTS)
    private FraudCheckResult checkFraudWithResilience(PaymentTransaction transaction) {
        log.debug("Invocando verificación antifraude para transacción: {}", transaction.transactionId());
        FraudCheckResult result = fraudDetectionPort.checkForFraud(transaction).block();
        return result != null ? result : FraudCheckResult.noCheck("FALLBACK");
    }

    private FraudCheckResult fraudCheckFallback(PaymentTransaction transaction, Throwable ex) {
        log.error("Fallback activado para verificación antifraude en transacción {}. Error: {}", transaction.transactionId(), ex.getMessage());
        return FraudCheckResult.approved("FALLBACK_APPROVED");
    }

    public Optional<PaymentTransaction> findTransactionById(UUID transactionId) {
        log.debug("Buscando transacción por ID: {}", transactionId);
        return transactionRepository.findById(transactionId);
    }

    public Optional<PaymentTransaction> findByIdempotencyKey(String idempotencyKey) {
        log.debug("Buscando transacción por clave de idempotencia: {}", idempotencyKey);
        return transactionRepository.findByIdempotencyKey(idempotencyKey);
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void updateTransactionStatus(UUID transactionId, TransactionStatus newStatus) {
        log.info("Actualizando estado de transacción {} a {}", transactionId, newStatus);
        transactionRepository.updateStatus(transactionId, newStatus);
    }

    public List<PaymentTransaction> findTransactionsByAccountAndDateRange(String accountNumber, LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Consultando transacciones para cuenta {} entre {} y {}", accountNumber, startDate, endDate);
        return List.of();
    }

    public List<PaymentTransaction> findTransactionsByAmountRange(BigDecimal minAmount, BigDecimal maxAmount, LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Consultando transacciones por rango de monto {} - {} entre {} y {}", minAmount, maxAmount, startDate, endDate);
        return List.of();
    }

    public long countTransactionsByStatusForToday(TransactionStatus status) {
        log.debug("Contando transacciones con estado {} para hoy", status);
        return 0L;
    }

    private PaymentTransaction validateTransactionLimits(PaymentTransaction transaction) {
        if (transaction.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto de la transacción debe ser mayor a cero");
        }

        BigDecimal maxSingleTransaction = new BigDecimal("100000.00");
        if (transaction.amount().compareTo(maxSingleTransaction) > 0) {
            throw new IllegalArgumentException("El monto excede el límite máximo por transacción");
        }

        if (transaction.currency() == null || transaction.currency().isBlank()) {
            throw new IllegalArgumentException("La moneda de la transacción es obligatoria");
        }

        return transaction;
    }

    public boolean validateIdempotency(String idempotencyKey) {
        return transactionRepository.existsByIdempotencyKey(idempotencyKey);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapter.java ===
package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.PaymentTransaction;
import com.pragma.payments.domain.model.PaymentTransaction.TransactionStatus;
import com.pragma.payments.domain.port.TransactionRepositoryPort;
import com.pragma.payments.infrastructure.entity.TransactionEntity;
import com.pragma.payments.infrastructure.repository.TransactionJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class TransactionJpaAdapter implements TransactionRepositoryPort {

    private static final Logger log = LoggerFactory.getLogger(TransactionJpaAdapter.class);

    private final TransactionJpaRepository transactionJpaRepository;

    public TransactionJpaAdapter(TransactionJpaRepository transactionJpaRepository) {
        this.transactionJpaRepository = transactionJpaRepository;
    }

    @Override
    public PaymentTransaction save(PaymentTransaction transaction) {
        log.debug("Guardando transacción con ID: {}", transaction.transactionId());
        TransactionEntity entity = toEntity(transaction);
        TransactionEntity savedEntity = transactionJpaRepository.save(entity);
        log.info("Transacción {} guardada exitosamente en base de datos", savedEntity.getTransactionId());
        return toDomain(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentTransaction> findById(UUID transactionId) {
        log.debug("Consultando transacción por ID: {}", transactionId);
        return transactionJpaRepository.findById(transactionId).map(this::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentTransaction> findByIdempotencyKey(String idempotencyKey) {
        log.debug("Consultando transacción por clave de idempotencia: {}", idempotencyKey);
        TransactionEntity entity = transactionJpaRepository.findByIdempotencyKey(idempotencyKey);
        return Optional.ofNullable(entity).map(this::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByIdempotencyKey(String idempotencyKey) {
        log.debug("Verificando existencia de clave de idempotencia: {}", idempotencyKey);
        return transactionJpaRepository.existsByIdempotencyKey(idempotencyKey);
    }

    @Override
    public void updateStatus(UUID transactionId, TransactionStatus status) {
        log.info("Actualizando estado de transacción {} a {}", transactionId, status);
        transactionJpaRepository.updateTransactionStatus(transactionId, status.name(), LocalDateTime.now());
        log.debug("Estado de transacción {} actualizado exitosamente", transactionId);
    }

    private TransactionEntity toEntity(PaymentTransaction domain) {
        TransactionEntity entity = new TransactionEntity();
        entity.setTransactionId(domain.transactionId());
        entity.setOperationNumber(domain.operationNumber());
        entity.setChannel(domain.channel());
        entity.setAmount(domain.amount());
        entity.setCurrency(domain.currency());
        entity.setCreatedAt(domain.createdAt());
        entity.setStatus(domain.status().name());
        entity.setOriginatorAccount(domain.originatorAccount());
        entity.setBeneficiaryAccount(domain.beneficiaryAccount());
        entity.setReference(domain.reference());
        entity.setFraudCheckId(domain.fraudCheckId());
        entity.setMetadata(domain.metadata());
        entity.setIdempotencyKey(domain.generateIdempotencyKey());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }

    private PaymentTransaction toDomain(TransactionEntity entity) {
        return new PaymentTransaction(
            entity.getTransactionId(),
            entity.getOperationNumber(),
            entity.getChannel(),
            entity.getAmount(),
            entity.getCurrency(),
            entity.getCreatedAt(),
            TransactionStatus.valueOf(entity.getStatus()),
            entity.getOriginatorAccount(),
            entity.getBeneficiaryAccount(),
            entity.getReference(),
            entity.getFraudCheckId(),
            entity.getMetadata()
        );
    }

    public void executeBatchProcessing(String batchId, int totalCount) {
        log.info("Ejecutando procedimiento almacenado para lote: {} con {} transacciones", batchId, totalCount);
        transactionJpaRepository.executeStoredProcedureForBatch(batchId, totalCount);
        log.info("Procedimiento almacenado ejecutado exitosamente para lote: {}", batchId);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java ===
package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.PaymentTransaction;
import com.pragma.payments.domain.port.FraudDetectionPort;
import com.pragma.payments.domain.port.FraudDetectionPort.FraudCheckResult;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.springboot3.circuitbreaker.annotation.CircuitBreakerName;
import io.github.resilience4j.springboot3.retry.annotation.RetryName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FraudDetectionAdapter implements FraudDetectionPort {

    private static final Logger log = LoggerFactory.getLogger(FraudDetectionAdapter.class);
    private static final String CIRCUIT_BREAKER_NAME = "fraudDetection";
    private static final String RETRY_NAME = "fraudDetectionRetry";
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(2);
    private static final int MAX_RETRY_ATTEMPTS = 3;

    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final RetryRegistry retryRegistry;
    private final Map<String, CircuitBreaker> circuitBreakers = new ConcurrentHashMap<>();
    private final Map<String, Retry> retries = new ConcurrentHashMap<>();

    public FraudDetectionAdapter(CircuitBreakerRegistry circuitBreakerRegistry, 
                                  RetryRegistry retryRegistry) {
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        this.retryRegistry = retryRegistry;
        initializeCircuitBreaker();
        initializeRetry();
    }

    private void initializeCircuitBreaker() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(30))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();
        
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME, config);
        circuitBreaker.getEventPublisher()
                .onStateTransition(event -> log.warn("CircuitBreaker '{}' transitioned to state: {}",
                        event.getStateTransition().getFromState(),
                        event.getStateTransition().getToState()))
                .onFailureRateExceeded(event -> log.error("CircuitBreaker '{}' failure rate exceeded: {}%",
                        event.getCircuitBreakerName(),
                        event.getFailureRate()));
        
        circuitBreakers.put(CIRCUIT_BREAKER_NAME, circuitBreaker);
    }

    private void initializeRetry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(MAX_RETRY_ATTEMPTS)
                .waitDuration(Duration.ofMillis(500))
                .retryExceptions(Exception.class)
                .ignoreExceptions()
                .build();
        
        Retry retry = retryRegistry.retry(RETRY_NAME, config);
        retry.getEventPublisher()
                .onRetry(event -> log.info("Retry '{}' attempt {} for operation",
                        event.getRetryName(),
                        event.getAttemptNumber()))
                .onSuccess(event -> log.debug("Retry '{}' succeeded after {} attempts",
                        event.getRetryName(),
                        event.getNumberOfAttempts()))
                .onError(event -> log.error("Retry '{}' failed after {} attempts with exception",
                        event.getRetryName(),
                        event.getNumberOfAttempts(),
                        event.getException()));
        
        retries.put(RETRY_NAME, retry);
    }

    @Override
    @RetryName(RETRY_NAME)
    @CircuitBreakerName(CIRCUIT_BREAKER_NAME)
    public Mono<FraudCheckResult> checkForFraud(PaymentTransaction transaction) {
        log.debug("Iniciando verificación antifraude para transacción: {}", transaction.transactionId());
        
        return Mono.fromCallable(() -> performFraudCheck(transaction))
                .timeout(DEFAULT_TIMEOUT)
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(result -> log.info("Verificación antifraude completada para transacción {}: {}",
                        transaction.transactionId(), result.approved()))
                .doOnError(error -> log.error("Error en verificación antifraude para transacción {}: {}",
                        transaction.transactionId(), error.getMessage(), error))
                .onErrorResume(TimeoutException.class, error -> {
                    log.error("Timeout al verificar antifraude para transacción: {}", transaction.transactionId());
                    return Mono.just(new FraudCheckResult(false, "TIMEOUT", "Timeout en verificación antifraude"));
                })
                .onErrorResume(Exception.class, error -> {
                    log.error("Error general en verificación antifraude: {}", error.getMessage());
                    return Mono.just(new FraudCheckResult(false, "ERROR", error.getMessage()));
                });
    }

    private FraudCheckResult performFraudCheck(PaymentTransaction transaction) {
        log.debug("Ejecutando verificación de fraude para amount: {}, currency: {}",
                transaction.amount(), transaction.currency());
        
        boolean approved = evaluateRisk(transaction);
        String riskScore = calculateRiskScore(transaction);
        
        if (!approved) {
            return new FraudCheckResult(false, "RISK_HIGH", "Transacción rechazada por alto riesgo");
        }
        
        return new FraudCheckResult(true, riskScore, "Verificación exitosa");
    }

    private boolean evaluateRisk(PaymentTransaction transaction) {
        if (transaction.amount().compareTo(BigDecimal.valueOf(10000)) > 0) {
            log.debug("Monto superior a threshold, evaluando detalladamente");
            return transaction.amount().compareTo(BigDecimal.valueOf(50000)) <= 0;
        }
        
        if ("INTERNATIONAL".equals(transaction.channel())) {
            log.debug("Canal internacional requiere evaluación adicional");
            return transaction.amount().compareTo(BigDecimal.valueOf(30000)) <= 0;
        }
        
        return true;
    }

    private String calculateRiskScore(PaymentTransaction transaction) {
        BigDecimal riskScore = BigDecimal.ZERO;
        
        if (transaction.amount().compareTo(BigDecimal.valueOf(5000)) > 0) {
            riskScore = riskScore.add(BigDecimal.valueOf(20));
        }
        
        if ("INTERNATIONAL".equals(transaction.channel())) {
            riskScore = riskScore.add(BigDecimal.valueOf(30));
        }
        
        if (transaction.reference() != null && transaction.reference().contains("SUSPICIOUS")) {
            riskScore = riskScore.add(BigDecimal.valueOf(50));
        }
        
        if (riskScore.compareTo(BigDecimal.valueOf(50)) >= 0) {
            return "HIGH";
        } else if (riskScore.compareTo(BigDecimal.valueOf(25)) >= 0) {
            return "MEDIUM";
        }
        
        return "LOW";
    }

    public CircuitBreaker getCircuitBreaker() {
        return circuitBreakers.get(CIRCUIT_BREAKER_NAME);
    }

    public Retry getRetry() {
        return retries.get(RETRY_NAME);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/entity/TransactionEntity.java ===
package com.pragma.payments.infrastructure.entity;

import com.pragma.payments.domain.model.PaymentTransaction;
import com.pragma.payments.domain.model.PaymentTransaction.TransactionStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions", indexes = {
    @Index(name = "idx_transaction_idempotency_key", columnList = "idempotency_key"),
    @Index(name = "idx_transaction_status", columnList = "status"),
    @Index(name = "idx_transaction_created_at", columnList = "created_at"),
    @Index(name = "idx_transaction_operation_number", columnList = "operation_number, channel"),
    @Index(name = "idx_transaction_reference", columnList = "reference"),
    @Index(name = "idx_transaction_originator", columnList = "originator_account"),
    @Index(name = "idx_transaction_beneficiary", columnList = "beneficiary_account")
})
@SQLDelete(sql = "UPDATE transactions SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class TransactionEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "operation_number", nullable = false, length = 50)
    private String operationNumber;

    @Column(name = "channel", nullable = false, length = 20)
    private String channel;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private TransactionStatus status;

    @Column(name = "originator_account", nullable = false, length = 34)
    private String originatorAccount;

    @Column(name = "beneficiary_account", nullable = false, length = 34)
    private String beneficiaryAccount;

    @Column(name = "reference", length = 100)
    private String reference;

    @Column(name = "fraud_check_id", length = 50)
    private String fraudCheckId;

    @Column(name = "idempotency_key", nullable = false, unique = true, length = 100)
    private String idempotencyKey;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Version
    @Column(name = "version")
    private Long version;

    @Column(name = "error_code", length = 20)
    private String errorCode;

    @Column(name = "error_message", length = 500)
    private String errorMessage;

    public TransactionEntity() {
    }

    public static TransactionEntity fromDomain(PaymentTransaction transaction) {
        TransactionEntity entity = new TransactionEntity();
        entity.id = transaction.transactionId();
        entity.operationNumber = transaction.operationNumber();
        entity.channel = transaction.channel();
        entity.amount = transaction.amount();
        entity.currency = transaction.currency();
        entity.createdAt = transaction.createdAt();
        entity.status = transaction.status();
        entity.originatorAccount = transaction.originatorAccount();
        entity.beneficiaryAccount = transaction.beneficiaryAccount();
        entity.reference = transaction.reference();
        entity.fraudCheckId = transaction.fraudCheckId();
        entity.idempotencyKey = transaction.generateIdempotencyKey();
        entity.metadata = transaction.metadata();
        return entity;
    }

    public PaymentTransaction toDomain() {
        return new PaymentTransaction(
                id,
                operationNumber,
                channel,
                amount,
                currency,
                createdAt,
                status,
                originatorAccount,
                beneficiaryAccount,
                reference,
                fraudCheckId,
                metadata
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOperationNumber() {
        return operationNumber;
    }

    public void setOperationNumber(String operationNumber) {
        this.operationNumber = operationNumber;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getOriginatorAccount() {
        return originatorAccount;
    }

    public void setOriginatorAccount(String originatorAccount) {
        this.originatorAccount = originatorAccount;
    }

    public String getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(String beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getFraudCheckId() {
        return fraudCheckId;
    }

    public void setFraudCheckId(String fraudCheckId) {
        this.fraudCheckId = fraudCheckId;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/config/TransactionConfig.java ===
package com.pragma.payments.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    @Value("${transaction.isolation.level:ISOLATION_READ_COMMITTED}")
    private String isolationLevel;

    @Value("${transaction.timeout.seconds:30}")
    private int transactionTimeoutSeconds;

    @Value("${transaction.propagation.required:REQUIRED}")
    private String propagationBehavior;

    @Value("${transaction.retry.max-attempts:3}")
    private int maxRetryAttempts;

    @Value("${transaction.retry.backoff-ms:1000}")
    private long retryBackoffMs;

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        transactionManager.setDefaultTimeout(transactionTimeoutSeconds);
        return transactionManager;
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.setPropagationBehavior(translatePropagation(propagationBehavior));
        template.setIsolationLevel(translateIsolationLevel(isolationLevel));
        template.setTimeout(transactionTimeoutSeconds);
        template.setReadOnly(false);
        return template;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    private int translatePropagation(String propagation) {
        return switch (propagation.toUpperCase()) {
            case "REQUIRED" -> Propagation.REQUIRED;
            case "REQUIRES_NEW" -> Propagation.REQUIRES_NEW;
            case "NESTED" -> Propagation.NESTED;
            case "SUPPORTS" -> Propagation.SUPPORTS;
            case "NOT_SUPPORTED" -> Propagation.NOT_SUPPORTED;
            case "MANDATORY" -> Propagation.MANDATORY;
            case "NEVER" -> Propagation.NEVER;
            default -> Propagation.REQUIRED;
        };
    }

    private int translateIsolationLevel(String isolation) {
        return switch (isolation.toUpperCase()) {
            case "ISOLATION_DEFAULT" -> TransactionDefinition.Isolation.ISOLATION_DEFAULT;
            case "ISOLATION_READ_UNCOMMITTED" -> TransactionDefinition.Isolation.ISOLATION_READ_UNCOMMITTED;
            case "ISOLATION_READ_COMMITTED" -> TransactionDefinition.Isolation.ISOLATION_READ_COMMITTED;
            case "ISOLATION_REPEATABLE_READ" -> TransactionDefinition.Isolation.ISOLATION_REPEATABLE_READ;
            case "ISOLATION_SERIALIZABLE" -> TransactionDefinition.Isolation.ISOLATION_SERIALIZABLE;
            default -> TransactionDefinition.Isolation.ISOLATION_READ_COMMITTED;
        };
    }

    @Transactional(propagation = Propagation.REQUIRED, 
                   isolation = TransactionDefinition.Isolation.ISOLATION_READ_COMMITTED,
                   timeoutString = "${transaction.timeout.seconds}",
                   rollbackFor = Exception.class)
    public interface TransactionalOperations {
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
                   isolation = TransactionDefinition.Isolation.ISOLATION_SERIALIZABLE,
                   timeoutString = "${transaction.timeout.seconds}",
                   rollbackFor = Exception.class)
    public interface IsolatedTransactionalOperations {
    }

    @Transactional(propagation = Propagation.NESTED,
                   isolation = TransactionDefinition.Isolation.ISOLATION_READ_COMMITTED,
                   timeoutString = "${transaction.timeout.seconds}",
                   rollbackFor = Exception.class)
    public interface NestedTransactionalOperations {
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java ===
package com.pragma.payments.infrastructure.controller;

import com.pragma.payments.application.service.TransactionService;
import com.pragma.payments.domain.model.PaymentTransaction;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);
    private static final String IDEMPOTENCY_HEADER = "X-Idempotency-Key";

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> createTransaction(
            @RequestHeader(value = IDEMPOTENCY_HEADER, required = false) String idempotencyKey,
            @Valid @RequestBody TransactionRequest request) {

        log.info("Recibida solicitud de transacción: operationNumber={}, channel={}, amount={}",
                request.operationNumber(), request.channel(), request.amount());

        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            log.debug("Clave de idempotencia proporcionada: {}", idempotencyKey);
        }

        PaymentTransaction transaction = transactionService.processTransaction(
                request.operationNumber(),
                request.channel(),
                request.amount(),
                request.currency(),
                request.originatorAccount(),
                request.beneficiaryAccount(),
                request.reference(),
                idempotencyKey,
                request.metadata()
        );

        TransactionResponse response = toResponse(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable UUID transactionId) {
        log.info("Consultando transacción: transactionId={}", transactionId);

        return transactionService.findById(transactionId)
                .map(transaction -> ResponseEntity.ok(toResponse(transaction)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Transacción no encontrada: " + transactionId));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionResponse>> getTransactions(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String channel,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        log.info("Consultando transacciones: status={}, channel={}, page={}, size={}",
                status, channel, page, size);

        List<PaymentTransaction> transactions = transactionService.findTransactions(status, channel, page, size);
        List<TransactionResponse> responses = transactions.stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping(value = "/idempotency/{idempotencyKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> getByIdempotencyKey(@PathVariable String idempotencyKey) {
        log.info("Consultando por clave de idempotencia: idempotencyKey={}", idempotencyKey);

        return transactionService.findByIdempotencyKey(idempotencyKey)
                .map(transaction -> ResponseEntity.ok(toResponse(transaction)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe transacción con clave de idempotencia: " + idempotencyKey));
    }

    @PatchMapping(value = "/{transactionId}/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStatus(
            @PathVariable UUID transactionId,
            @Valid @RequestBody StatusUpdateRequest request) {

        log.info("Actualizando estado de transacción: transactionId={}, newStatus={}",
                transactionId, request.status());

        transactionService.updateTransactionStatus(transactionId, request.status());
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "timestamp", LocalDateTime.now().toString(),
                "service", "transaction-controller"
        ));
    }

    private TransactionResponse toResponse(PaymentTransaction transaction) {
        return new TransactionResponse(
                transaction.transactionId().toString(),
                transaction.operationNumber(),
                transaction.channel(),
                transaction.amount(),
                transaction.currency(),
                transaction.createdAt().toString(),
                transaction.status().name(),
                transaction.originatorAccount(),
                transaction.beneficiaryAccount(),
                transaction.reference(),
                transaction.fraudCheckId(),
                transaction.metadata()
        );
    }

    public record TransactionRequest(
            @NotBlank(message = "El número de operación es obligatorio")
            String operationNumber,
            @NotBlank(message = "El canal es obligatorio")
            String channel,
            @NotNull(message = "El monto es obligatorio")
            @Positive(message = "El monto debe ser positivo")
            BigDecimal amount,
            @NotBlank(message = "La moneda es obligatoria")
            String currency,
            @NotBlank(message = "La cuenta del originador es obligatoria")
            String originatorAccount,
            @NotBlank(message = "La cuenta del beneficiario es obligatoria")
            String beneficiaryAccount,
            String reference,
            String metadata
    ) {}

    public record TransactionResponse(
            String transactionId,
            String operationNumber,
            String channel,
            BigDecimal amount,
            String currency,
            String createdAt,
            String status,
            String originatorAccount,
            String beneficiaryAccount,
            String reference,
            String fraudCheckId,
            String metadata
    ) {}

    public record StatusUpdateRequest(
            @NotBlank(message = "El estado es obligatorio")
            String status
    ) {}
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/config/GlobalExceptionHandler.java ===
package com.pragma.payments.infrastructure.config;

import com.pragma.payments.application.service.exception.TransactionNotFoundException;
import com.pragma.payments.application.service.exception.DuplicateIdempotencyKeyException;
import com.pragma.payments.application.service.exception.TransactionTimeoutException;
import com.pragma.payments.application.service.exception.TransactionFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String ERROR_CATEGORY = "errorCategory";
    private static final String ERROR_CODE = "errorCode";

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTransactionNotFound(
            TransactionNotFoundException ex, WebRequest request) {
        log.warn("Transacción no encontrada: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "TRANSACTION_NOT_FOUND",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now().toString(),
                Map.of(ERROR_CATEGORY, "CLIENT_ERROR", ERROR_CODE, "TXN_001")
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateIdempotencyKeyException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateIdempotencyKey(
            DuplicateIdempotencyKeyException ex, WebRequest request) {
        log.warn("Clave de idempotencia duplicada: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "DUPLICATE_IDEMPOTENCY_KEY",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now().toString(),
                Map.of(ERROR_CATEGORY, "CLIENT_ERROR", ERROR_CODE, "TXN_002")
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(TransactionTimeoutException.class)
    public ResponseEntity<ErrorResponse> handleTransactionTimeout(
            TransactionTimeoutException ex, WebRequest request) {
        log.error("Timeout en transacción: {}", ex.getMessage(), ex);

        ErrorResponse error = new ErrorResponse(
                HttpStatus.GATEWAY_TIMEOUT.value(),
                "TRANSACTION_TIMEOUT",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now().toString(),
                Map.of(ERROR_CATEGORY, "TIMEOUT_ERROR", ERROR_CODE, "TXN_003")
        );
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(error);
    }

    @ExceptionHandler(TransactionFailedException.class)
    public ResponseEntity<ErrorResponse> handleTransactionFailed(
            TransactionFailedException ex, WebRequest request) {
        log.error("Falló la transacción: {}", ex.getMessage(), ex);

        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "TRANSACTION_FAILED",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now().toString(),
                Map.of(ERROR_CATEGORY, "BUSINESS_ERROR", ERROR_CODE, "TXN_004")
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex, WebRequest request) {
        log.warn("Error de validación: {}", ex.getMessage());

        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null
                                ? error.getDefaultMessage()
                                : "Valor inválido",
                        (existing, replacement) -> existing
                ));

        ValidationErrorResponse error = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                "Los datos proporcionados no son válidos",
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now().toString(),
                fieldErrors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.warn("Tipo de argumento inválido: {}", ex.getMessage());

        String message = String.format("El parámetro '%s' tiene un valor inválido: '%s'",
                ex.getName(), ex.getValue());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "INVALID_ARGUMENT_TYPE",
                message,
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now().toString(),
                Map.of(ERROR_CATEGORY, "CLIENT_ERROR", ERROR_CODE, "TXN_005")
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex, WebRequest request) {
        log.warn("Argumento ilegal: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "ILLEGAL_ARGUMENT",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now().toString(),
                Map.of(ERROR_CATEGORY, "CLIENT_ERROR", ERROR_CODE, "TXN_006")
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {
        log.error("Error interno del servidor: {}", ex.getMessage(), ex);

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                "Ocurrió un error inesperado. Por favor, contacte al administrador.",
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now().toString(),
                Map.of(ERROR_CATEGORY, "SERVER_ERROR", ERROR_CODE, "TXN_999")
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    public record ErrorResponse(
            int status,
            String error,
            String message,
            String path,
            String timestamp,
            Map<String, String> details
    ) {}

    public record ValidationErrorResponse(
            int status,
            String error,
            String message,
            String path,
            String timestamp,
            Map<String, String> fieldErrors
    ) {}
}

// === ARCHIVO: src/main/resources/db/migration/V1__Create_transaction_table.sql ===
-- =====================================================
-- Tabla de transacciones para el sistema de pagos
-- Cumplimiento ACID y soporte para idempotencia
-- =====================================================

-- Tabla principal de transacciones
CREATE TABLE IF NOT EXISTS payment_transactions (
    transaction_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    operation_number VARCHAR(100) NOT NULL,
    channel VARCHAR(50) NOT NULL,
    amount DECIMAL(19, 4) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(30) NOT NULL,
    originator_account VARCHAR(50) NOT NULL,
    beneficiary_account VARCHAR(50) NOT NULL,
    reference VARCHAR(200),
    fraud_check_id VARCHAR(100),
    idempotency_key VARCHAR(100) UNIQUE,
    metadata JSONB,

    -- Constraints de negocio
    CONSTRAINT chk_amount_positive CHECK (amount > 0),
    CONSTRAINT chk_currency_format CHECK (currency ~ '^[A-Z]{3}$'),
    CONSTRAINT chk_status_valid CHECK (status IN (
        'PENDING',
        'PROCESSING',
        'AUTHORIZED',
        'CAPTURED',
        'FAILED',
        'REJECTED',
        'TIMEOUT',
        'CANCELLED',
        'REFUNDED'
    ))
);

-- =====================================================
-- Índices para optimizar consultas frecuentes
-- =====================================================

-- Índice para búsqueda por número de operación y canal (caso de uso principal de idempotencia)
CREATE INDEX idx_transaction_op_number_channel
    ON payment_transactions(operation_number, channel);

-- Índice para búsqueda por clave de idempotencia (búsqueda rápida)
CREATE INDEX idx_transaction_idempotency_key
    ON payment_transactions(idempotency_key)
    WHERE idempotency_key IS NOT NULL;

-- Índice para consultas por estado (dashboard, monitoreo)
CREATE INDEX idx_transaction_status
    ON payment_transactions(status, created_at DESC);

-- Índice para consultas por cuenta de originador (conciliación)
CREATE INDEX idx_transaction_originator
    ON payment_transactions(originator_account, created_at DESC);

-- Índice para consultas por cuenta de beneficiario
CREATE INDEX idx_transaction_beneficiary
    ON payment_transactions(beneficiary_account, created_at DESC);

-- Índice para consultas por fecha (reportes, auditoría)
CREATE INDEX idx_transaction_created_at
    ON payment_transactions(created_at DESC);

-- Índice compuesto para consultas analíticas por canal y período
CREATE INDEX idx_transaction_channel_created
    ON payment_transactions(channel, created_at DESC);

-- =====================================================
-- Comments para documentación del esquema
-- =====================================================

COMMENT ON TABLE payment_transactions IS 'Almacena todas las transacciones del sistema de pagos con soporte ACID';
COMMENT ON COLUMN payment_transactions.transaction_id IS 'Identificador único universal de la transacción';
COMMENT ON COLUMN payment_transactions.operation_number IS 'Número de operación único dentro del canal';
COMMENT ON COLUMN payment_transactions.channel IS 'Canal por el cual se origina la transacción (API, WEB, MOBILE, BATCH)';
COMMENT ON COLUMN payment_transactions.amount IS 'Monto de la transacción con precisión de 4 decimales';
COMMENT ON COLUMN payment_transactions.currency IS 'Código ISO 4217 de la moneda (USD, EUR, MXN)';
COMMENT ON COLUMN payment_transactions.status IS 'Estado actual de la transacción en el ciclo de vida';
COMMENT ON COLUMN payment_transactions.originator_account IS 'Cuenta que inicia el débito';
COMMENT ON COLUMN payment_transactions.beneficiary_account IS 'Cuenta que recibe el crédito';
COMMENT ON COLUMN payment_transactions.reference IS 'Referencia externa o identificador del sistema origen';
COMMENT ON COLUMN payment_transactions.fraud_check_id IS 'Identificador de la verificación antifraude';
COMMENT ON COLUMN payment_transactions.idempotency_key IS 'Clave para garantizar idempotencia: operation_number + channel';
COMMENT ON COLUMN payment_transactions.metadata IS 'Datos adicionales en formato JSON para extensibilidad';

-- =====================================================
-- Función para actualizar timestamp automáticamente
-- =====================================================

CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger para actualizar updated_at en cada modificación
CREATE TRIGGER update_payment_transactions_updated_at
    BEFORE UPDATE ON payment_transactions
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- =====================================================
-- Función para generar clave de idempotencia
-- =====================================================

CREATE OR REPLACE FUNCTION generate_idempotency_key(p_operation_number VARCHAR, p_channel VARCHAR)
RETURNS VARCHAR AS $$
BEGIN
    RETURN CONCAT(p_channel, '_', p_operation_number, '_', CURRENT_DATE);
END;
$$ LANGUAGE plpgsql IMMUTABLE;

-- =====================================================
-- Secuencia para números de operación si se requiere
-- =====================================================

CREATE SEQUENCE IF NOT EXISTS transaction_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO CYCLE;

-- =====================================================
-- Tabla de auditoría de cambios de estado
-- =====================================================

CREATE TABLE IF NOT EXISTS transaction_status_audit (
    audit_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    transaction_id UUID NOT NULL REFERENCES payment_transactions(transaction_id),
    previous_status VARCHAR(30),
    new_status VARCHAR(30) NOT NULL,
    changed_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    changed_by VARCHAR(100),
    reason TEXT
);

CREATE INDEX idx_transaction_status_audit_transaction
    ON transaction_status_audit(transaction_id, changed_at DESC);

COMMENT ON TABLE transaction_status_audit IS 'Historial de cambios de estado para auditoría y trazabilidad';

// === ARCHIVO: src/main/resources/db/migration/V2__Create_stored_procedures.sql ===
-- Procedimientos almacenados optimizados para operaciones de transacción del sistema de pagos
-- Versión: V2
-- Objetivo: Proveer operaciones transaccionales de alto rendimiento con manejo de idempotencia

-- Procedimiento para crear una nueva transacción con verificación de idempotencia
-- Retorna: ID de la transacción creada o ID de la transacción existente si ya existe
CREATE OR REPLACE FUNCTION create_payment_transaction(
    p_transaction_id UUID,
    p_operation_number VARCHAR(50),
    p_channel VARCHAR(20),
    p_amount NUMERIC(19, 4),
    p_currency VARCHAR(3),
    p_originator_account VARCHAR(50),
    p_beneficiary_account VARCHAR(50),
    p_reference VARCHAR(200),
    p_idempotency_key VARCHAR(100),
    p_metadata JSONB
)
RETURNS UUID AS $$
DECLARE
    v_existing_id UUID;
    v_created_at TIMESTAMP WITH TIME ZONE;
BEGIN
    -- Verificar si ya existe una transacción con la misma clave de idempotencia
    SELECT t.transaction_id INTO v_existing_id
    FROM payment_transactions t
    WHERE t.idempotency_key = p_idempotency_key
    LIMIT 1;

    -- Si existe, retornar el ID existente (idempotencia)
    IF v_existing_id IS NOT NULL THEN
        RETURN v_existing_id;
    END IF;

    -- Establecer timestamp de creación
    v_created_at := CURRENT_TIMESTAMP;

    -- Insertar la nueva transacción
    INSERT INTO payment_transactions (
        transaction_id,
        operation_number,
        channel,
        amount,
        currency,
        created_at,
        status,
        originator_account,
        beneficiary_account,
        reference,
        idempotency_key,
        metadata
    ) VALUES (
        p_transaction_id,
        p_operation_number,
        p_channel,
        p_amount,
        p_currency,
        v_created_at,
        'PENDING'::transaction_status_type,
        p_originator_account,
        p_beneficiary_account,
        p_reference,
        p_idempotency_key,
        p_metadata
    );

    RETURN p_transaction_id;
END;
$$ LANGUAGE plpgsql
SET search_path = public;

-- Procedimiento para actualizar el estado de una transacción
-- Maneja transiciones de estado válidas y registra histórico
CREATE OR REPLACE FUNCTION update_transaction_status(
    p_transaction_id UUID,
    p_new_status VARCHAR(30),
    p_fraud_check_id VARCHAR(100) DEFAULT NULL
)
RETURNS BOOLEAN AS $$
DECLARE
    v_current_status VARCHAR(30);
    v_updated BOOLEAN := FALSE;
BEGIN
    -- Obtener el estado actual de la transacción
    SELECT t.status::TEXT INTO v_current_status
    FROM payment_transactions t
    WHERE t.transaction_id = p_transaction_id
    FOR UPDATE;

    -- Validar transición de estado
    IF NOT is_valid_status_transition(v_current_status, p_new_status) THEN
        RAISE EXCEPTION 'Transición de estado inválida: % -> %', v_current_status, p_new_status
            USING HINT = 'Verifique las transiciones de estado permitidas';
    END IF;

    -- Actualizar estado
    UPDATE payment_transactions
    SET status = p_new_status::transaction_status_type,
        updated_at = CURRENT_TIMESTAMP
    WHERE transaction_id = p_transaction_id;

    -- Actualizar fraud_check_id si se proporciona
    IF p_fraud_check_id IS NOT NULL THEN
        UPDATE payment_transactions
        SET fraud_check_id = p_fraud_check_id
        WHERE transaction_id = p_transaction_id;
    END IF;

    -- Registrar en histórico de estados
    INSERT INTO transaction_status_history (
        history_id,
        transaction_id,
        from_status,
        to_status,
        changed_at
    ) VALUES (
        gen_random_uuid(),
        p_transaction_id,
        v_current_status,
        p_new_status,
        CURRENT_TIMESTAMP
    );

    GET DIAGNOSTICS v_updated = ROW_COUNT;
    RETURN v_updated > 0;
END;
$$ LANGUAGE plpgsql
SET search_path = public;

-- Función auxiliar para validar transiciones de estado
CREATE OR REPLACE FUNCTION is_valid_status_transition(
    p_from_status VARCHAR(30),
    p_to_status VARCHAR(30)
)
RETURNS BOOLEAN AS $$
BEGIN
    -- Transiciones válidas definidas por el dominio
    RETURN CASE
        -- Desde PENDING
        WHEN p_from_status = 'PENDING' AND p_to_status IN ('PROCESSING', 'FAILED', 'CANCELLED') THEN TRUE
        -- Desde PROCESSING
        WHEN p_from_status = 'PROCESSING' AND p_to_status IN ('AUTHORIZED', 'FAILED', 'CANCELLED') THEN TRUE
        -- Desde AUTHORIZED
        WHEN p_from_status = 'AUTHORIZED' AND p_to_status IN ('SETTLED', 'FAILED', 'CANCELLED') THEN TRUE
        -- Estados finales (no se sale de estos)
        WHEN p_from_status IN ('SETTLED', 'FAILED', 'CANCELLED') AND p_to_status = p_from_status THEN TRUE
        -- Cualquier otra transición es inválida
        ELSE FALSE
    END;
END;
$$ LANGUAGE plpgsql
SET search_path = public;

-- Procedimiento para procesar una transacción completa con manejo de errores
-- Incluye validación de idempotencia, verificación antifraude y actualización de estado
CREATE OR REPLACE FUNCTION process_payment_transaction(
    p_transaction_id UUID,
    p_operation_number VARCHAR(50),
    p_channel VARCHAR(20),
    p_amount NUMERIC(19, 4),
    p_currency VARCHAR(3),
    p_originator_account VARCHAR(50),
    p_beneficiary_account VARCHAR(50),
    p_reference VARCHAR(200),
    p_idempotency_key VARCHAR(100),
    p_metadata JSONB,
    p_fraud_check_id VARCHAR(100)
)
RETURNS TABLE (
    transaction_id UUID,
    status VARCHAR(30),
    message TEXT
) AS $$
DECLARE
    v_result_id UUID;
    v_status VARCHAR(30);
    v_message TEXT;
    v_existing_transaction UUID;
BEGIN
    -- Verificar idempotencia
    SELECT t.transaction_id, t.status::TEXT INTO v_existing_transaction, v_status
    FROM payment_transactions t
    WHERE t.idempotency_key = p_idempotency_key
    LIMIT 1;

    IF v_existing_transaction IS NOT NULL THEN
        -- Transacción existente, retornar resultado
        RETURN QUERY SELECT 
            v_existing_transaction, 
            v_status, 
            'Transacción existente retornada por idempotencia'::TEXT;
        RETURN;
    END IF;

    -- Crear transacción inicial
    v_result_id := create_payment_transaction(
        p_transaction_id,
        p_operation_number,
        p_channel,
        p_amount,
        p_currency,
        p_originator_account,
        p_beneficiary_account,
        p_reference,
        p_idempotency_key,
        p_metadata
    );

    -- Actualizar a estado PROCESSING
    PERFORM update_transaction_status(v_result_id, 'PROCESSING', p_fraud_check_id);

    -- Simular procesamiento exitoso
    PERFORM update_transaction_status(v_result_id, 'AUTHORIZED', p_fraud_check_id);

    -- Retornar resultado exitoso
    RETURN QUERY SELECT 
        v_result_id, 
        'AUTHORIZED'::VARCHAR(30), 
        'Transacción procesada exitosamente'::TEXT;
    RETURN;

EXCEPTION
    WHEN OTHERS THEN
        -- Manejo de errores: marcar como fallida
        IF v_result_id IS NOT NULL THEN
            PERFORM update_transaction_status(v_result_id, 'FAILED', NULL);
        END IF;
        
        RETURN QUERY SELECT 
            COALESCE(v_result_id, p_transaction_id), 
            'FAILED'::VARCHAR(30), 
            'Error al procesar transacción: ' || SQLERRM::TEXT;
        RETURN;
END;
$$ LANGUAGE plpgsql
SET search_path = public;

-- Función para obtener estadísticas de transacciones por estado
-- Útil para monitoreo y dashboards
CREATE OR REPLACE FUNCTION get_transaction_statistics(
    p_start_date TIMESTAMP WITH TIME ZONE,
    p_end_date TIMESTAMP WITH TIME ZONE
)
RETURNS TABLE (
    status VARCHAR(30),
    count BIGINT,
    total_amount NUMERIC(19, 4),
    avg_amount NUMERIC(19, 4)
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        t.status::TEXT AS status,
        COUNT(*)::BIGINT AS count,
        SUM(t.amount)::NUMERIC(19, 4) AS total_amount,
        AVG(t.amount)::NUMERIC(19, 4) AS avg_amount
    FROM payment_transactions t
    WHERE t.created_at BETWEEN p_start_date AND p_end_date
    GROUP BY t.status
    ORDER BY count DESC;
END;
$$ LANGUAGE plpgsql
SET search_path = public;

-- Procedimiento para limpiar transacciones antiguas en estado PENDING
-- Útil para manejar transacciones huérfanas por timeouts
CREATE OR REPLACE FUNCTION cleanup_stale_pending_transactions(
    p_older_than_minutes INTEGER DEFAULT 30
)
RETURNS INTEGER AS $$
DECLARE
    v_cleaned_count INTEGER;
    v_cutoff_time TIMESTAMP WITH TIME ZONE;
BEGIN
    v_cutoff_time := CURRENT_TIMESTAMP - (p_older_than_minutes || ' minutes')::INTERVAL;

    UPDATE payment_transactions
    SET status = 'CANCELLED'::transaction_status_type,
        updated_at = CURRENT_TIMESTAMP,
        metadata = jsonb_set(metadata, '{cleanup_reason}', '"timeout"')
    WHERE status = 'PENDING'::transaction_status_type
      AND created_at < v_cutoff_time;

    GET DIAGNOSTICS v_cleaned_count = ROW_COUNT;
    
    -- Registrar limpieza
    INSERT INTO transaction_cleanup_log (
        log_id,
        cleaned_at,
        cleaned_count,
        cutoff_time
    ) VALUES (
        gen_random_uuid(),
        CURRENT_TIMESTAMP,
        v_cleaned_count,
        v_cutoff_time
    );

    RETURN v_cleaned_count;
END;
$$ LANGUAGE plpgsql
SET search_path = public;

-- Tabla para registro histórico de cambios de estado
CREATE TABLE IF NOT EXISTS transaction_status_history (
    history_id UUID PRIMARY KEY,
    transaction_id UUID NOT NULL REFERENCES payment_transactions(transaction_id),
    from_status VARCHAR(30) NOT NULL,
    to_status VARCHAR(30) NOT NULL,
    changed_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Índice para consultas de histórico por transacción
CREATE INDEX IF NOT EXISTS idx_status_history_transaction_id 
    ON transaction_status_history(transaction_id);

-- Índice para consultas de histórico por fecha
CREATE INDEX IF NOT EXISTS idx_status_history_changed_at 
    ON transaction_status_history(changed_at);

-- Tabla para registro de limpiezas de transacciones
CREATE TABLE IF NOT EXISTS transaction_cleanup_log (
    log_id UUID PRIMARY KEY,
    cleaned_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cleaned_count INTEGER NOT NULL,
    cutoff_time TIMESTAMP WITH TIME ZONE NOT NULL
);

-- Índice para consultas de limpieza por fecha
CREATE INDEX IF NOT EXISTS idx_cleanup_log_cleaned_at 
    ON transaction_cleanup_log(cleaned_at);

-- Función para buscar transacciones por múltiples criterios
-- Optimizada para consultas frecuentes del dashboard
CREATE OR REPLACE FUNCTION search_transactions(
    p_status VARCHAR(30) DEFAULT NULL,
    p_channel VARCHAR(20) DEFAULT NULL,
    p_originator_account VARCHAR(50) DEFAULT NULL,
    p_start_date TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    p_end_date TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    p_limit INTEGER DEFAULT 100,
    p_offset INTEGER DEFAULT 0
)
RETURNS TABLE (
    transaction_id UUID,
    operation_number VARCHAR(50),
    channel VARCHAR(20),
    amount NUMERIC(19, 4),
    currency VARCHAR(3),
    status VARCHAR(30),
    originator_account VARCHAR(50),
    beneficiary_account VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        t.transaction_id,
        t.operation_number,
        t.channel,
        t.amount,
        t.currency,
        t.status::TEXT,
        t.originator_account,
        t.beneficiary_account,
        t.created_at
    FROM payment_transactions t
    WHERE (p_status IS NULL OR t.status::TEXT = p_status)
      AND (p_channel IS NULL OR t.channel = p_channel)
      AND (p_originator_account IS NULL OR t.originator_account = p_originator_account)
      AND (p_start_date IS NULL OR t.created_at >= p_start_date)
      AND (p_end_date IS NULL OR t.created_at <= p_end_date)
    ORDER BY t.created_at DESC
    LIMIT p_limit
    OFFSET p_offset;
END;
$$ LANGUAGE plpgsql
SET search_path = public;

-- Otorgar permisos de ejecución a las funciones
GRANT EXECUTE ON FUNCTION create_payment_transaction TO app_user;
GRANT EXECUTE ON FUNCTION update_transaction_status TO app_user;
GRANT EXECUTE ON FUNCTION is_valid_status_transition TO app_user;
GRANT EXECUTE ON FUNCTION process_payment_transaction TO app_user;
GRANT EXECUTE ON FUNCTION get_transaction_statistics TO app_user;
GRANT EXECUTE ON FUNCTION cleanup_stale_pending_transactions TO app_user;
GRANT EXECUTE ON FUNCTION search_transactions TO app_user;

// === ARCHIVO: src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java ===
package com.pragma.payments.application.service;

import com.pragma.payments.domain.model.PaymentTransaction;
import com.pragma.payments.domain.model.PaymentTransaction.TransactionStatus;
import com.pragma.payments.domain.port.TransactionRepositoryPort;
import com.pragma.payments.domain.port.FraudDetectionPort;
import com.pragma.payments.domain.port.FraudDetectionPort.FraudCheckResult;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Disabled("Superficie de práctica - completar implementación")
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepositoryPort transactionRepository;

    @Mock
    private FraudDetectionPort fraudDetectionPort;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void shouldCreateTransactionSuccessfully() {
        // Given: una transacción válida con todos los datos requeridos
        PaymentTransaction transaction = createValidTransaction();
        when(transactionRepository.existsByIdempotencyKey(any())).thenReturn(false);
        when(fraudDetectionPort.checkForFraud(any())).thenReturn(Mono.just(FraudCheckResult.approved()));
        when(transactionRepository.save(any())).thenReturn(transaction);

        // When: se invoca el servicio de creación
        PaymentTransaction result = transactionService.createTransaction(transaction);

        // Then: la transacción se crea con estado APPROVED
        assertNotNull(result);
        assertEquals(TransactionStatus.APPROVED, result.status());
        verify(transactionRepository).save(any());
    }

    @Test
    void shouldRejectTransactionDueToFraud() {
        // Given: una transacción que falla la verificación de fraude
        PaymentTransaction transaction = createValidTransaction();
        when(transactionRepository.existsByIdempotencyKey(any())).thenReturn(false);
        when(fraudDetectionPort.checkForFraud(any())).thenReturn(Mono.just(FraudCheckResult.rejected("HIGH_RISK")));
        when(transactionRepository.save(any())).thenReturn(transaction);

        // When: se invoca el servicio
        PaymentTransaction result = transactionService.createTransaction(transaction);

        // Then: la transacción se rechaza
        assertEquals(TransactionStatus.REJECTED, result.status());
    }

    @Test
    void shouldHandleIdempotencyCorrectly() {
        // Given: una clave de idempotencia existente
        String idempotencyKey = "OP123-CHANNEL1";
        PaymentTransaction existingTransaction = createValidTransaction();
        when(transactionRepository.findByIdempotencyKey(idempotencyKey))
            .thenReturn(Optional.of(existingTransaction));

        // When: se intenta crear con la misma clave
        PaymentTransaction result = transactionService.createTransaction(createValidTransaction());

        // Then: retorna la transacción existente sin crear duplicados
        assertNotNull(result);
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void shouldHandleFraudCheckTimeout() {
        // Given: una transacción donde el servicio de fraude timing out
        PaymentTransaction transaction = createValidTransaction();
        when(transactionRepository.existsByIdempotencyKey(any())).thenReturn(false);
        when(fraudDetectionPort.checkForFraud(any()))
            .thenReturn(Mono.error(new RuntimeException("Fraud service timeout")));

        // When/Then: el servicio debe manejar el timeout gracefully
        assertThrows(RuntimeException.class, () -> transactionService.createTransaction(transaction));
    }

    @Test
    void shouldUpdateTransactionStatus() {
        // Given: una transacción existente
        UUID transactionId = UUID.randomUUID();
        PaymentTransaction transaction = createValidTransaction();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

        // When: se actualiza el estado
        transactionService.updateStatus(transactionId, TransactionStatus.COMPLETED);

        // Then: el estado se actualiza en persistencia
        verify(transactionRepository).updateStatus(transactionId, TransactionStatus.COMPLETED);
    }

    @Test
    void shouldFailWhenTransactionNotFound() {
        // Given: un ID de transacción que no existe
        UUID transactionId = UUID.randomUUID();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        // When/Then: debe lanzar excepción
        assertThrows(RuntimeException.class, 
            () -> transactionService.updateStatus(transactionId, TransactionStatus.COMPLETED));
    }

    private PaymentTransaction createValidTransaction() {
        return new PaymentTransaction(
            UUID.randomUUID(),
            "OP123",
            "CHANNEL1",
            new BigDecimal("1000.00"),
            "USD",
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            "ACC123",
            "ACC456",
            "REF001",
            null,
            null
        );
    }
}

// === ARCHIVO: src/test/java/com/pragma/payments/infrastructure/adapter/TransactionJpaAdapterTest.java ===
package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.PaymentTransaction;
import com.pragma.payments.domain.model.PaymentTransaction.TransactionStatus;
import com.pragma.payments.infrastructure.entity.TransactionEntity;
import com.pragma.payments.infrastructure.repository.TransactionJpaRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Disabled("Superficie de práctica - completar implementación")
@DataJpaTest
@ActiveProfiles("test")
class TransactionJpaAdapterTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TransactionJpaRepository jpaRepository;

    @Autowired
    private TransactionJpaAdapter adapter;

    @Test
    void shouldSaveTransactionWithACIDProperties() {
        // Given: una transacción válida
        PaymentTransaction transaction = createTestTransaction();

        // When: se persiste la transacción
        PaymentTransaction saved = adapter.save(transaction);

        // Then: se persiste con ID generado y timestamps
        assertNotNull(saved);
        assertNotNull(saved.transactionId());
        
        TransactionEntity entity = entityManager.find(TransactionEntity.class, saved.transactionId());
        assertThat(entity).isNotNull();
        assertThat(entity.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldFindTransactionById() {
        // Given: una transacción persistida
        PaymentTransaction transaction = createTestTransaction();
        PaymentTransaction saved = adapter.save(transaction);
        entityManager.flush();
        entityManager.clear();

        // When: se busca por ID
        Optional<PaymentTransaction> found = adapter.findById(saved.transactionId());

        // Then: se encuentra la transacción
        assertThat(found).isPresent();
        assertThat(found.get().transactionId()).isEqualTo(saved.transactionId());
        assertThat(found.get().operationNumber()).isEqualTo(saved.operationNumber());
    }

    @Test
    void shouldFindTransactionByIdempotencyKey() {
        // Given: una transacción con clave de idempotencia
        String idempotencyKey = "TEST-KEY-123";
        PaymentTransaction transaction = createTestTransaction();
        PaymentTransaction saved = adapter.save(transaction);
        entityManager.flush();
        entityManager.clear();

        // When: se busca por clave de idempotencia
        Optional<PaymentTransaction> found = adapter.findByIdempotencyKey(idempotencyKey);

        // Then: se encuentra la transacción
        assertThat(found).isPresent();
        assertThat(found.get().transactionId()).isEqualTo(saved.transactionId());
    }

    @Test
    void shouldReturnFalseWhenIdempotencyKeyNotExists() {
        // Given: no existe la clave de idempotencia
        String nonExistentKey = "NON-EXISTENT-KEY";

        // When: se verifica existencia
        boolean exists = adapter.existsByIdempotencyKey(nonExistentKey);

        // Then: retorna false
        assertThat(exists).isFalse();
    }

    @Test
    void shouldReturnTrueWhenIdempotencyKeyExists() {
        // Given: una transacción con clave de idempotencia
        PaymentTransaction transaction = createTestTransaction();
        adapter.save(transaction);
        entityManager.flush();

        // When: se verifica existencia
        boolean exists = adapter.existsByIdempotencyKey("TEST-KEY-123");

        // Then: retorna true
        assertThat(exists).isTrue();
    }

    @Test
    void shouldUpdateTransactionStatus() {
        // Given: una transacción persistida
        PaymentTransaction transaction = createTestTransaction();
        PaymentTransaction saved = adapter.save(transaction);
        entityManager.flush();
        entityManager.clear();

        // When: se actualiza el estado
        adapter.updateStatus(saved.transactionId(), TransactionStatus.COMPLETED);
        entityManager.flush();

        // Then: el estado se actualiza
        TransactionEntity entity = entityManager.find(TransactionEntity.class, saved.transactionId());
        assertThat(entity.getStatus()).isEqualTo(TransactionStatus.COMPLETED.name());
    }

    @Test
    void shouldUseIndexForIdempotencyKeyQuery() {
        // Given: múltiples transacciones persistidas
        for (int i = 0; i < 100; i++) {
            PaymentTransaction transaction = new PaymentTransaction(
                UUID.randomUUID(),
                "OP" + i,
                "CHANNEL",
                new BigDecimal("100.00"),
                "USD",
                LocalDateTime.now(),
                TransactionStatus.PENDING,
                "ACC1",
                "ACC2",
                "REF" + i,
                null,
                null
            );
            adapter.save(transaction);
        }
        entityManager.flush();

        // When: se busca por clave de idempotencia existente
        long startTime = System.currentTimeMillis();
        Optional<PaymentTransaction> found = adapter.findByIdempotencyKey("TEST-KEY-50");
        long queryTime = System.currentTimeMillis() - startTime;

        // Then: la consulta es rápida (menos de 100ms con índice)
        assertThat(found).isPresent();
        assertThat(queryTime).isLessThan(100);
    }

    private PaymentTransaction createTestTransaction() {
        return new PaymentTransaction(
            UUID.randomUUID(),
            "OP123",
            "CHANNEL1",
            new BigDecimal("1000.00"),
            "USD",
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            "ACC123",
            "ACC456",
            "REF001",
            null,
            "TEST-KEY-123"
        );
    }
}

// === ARCHIVO: src/test/java/com/pragma/payments/infrastructure/controller/TransactionControllerTest.java ===
package com.pragma.payments.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.payments.application.service.TransactionService;
import com.pragma.payments.domain.model.PaymentTransaction;
import com.pragma.payments.domain.model.PaymentTransaction.TransactionStatus;
import com.pragma.payments.infrastructure.config.GlobalExceptionHandler;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled("Superficie de práctica - completar implementación")
@WebMvcTest(TransactionController.class)
@Import(GlobalExceptionHandler.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @Test
    void shouldCreateTransactionWhenValidRequest() throws Exception {
        // Given: una solicitud de transacción válida
        PaymentTransaction request = createValidRequest();
        PaymentTransaction response = createValidResponse();
        
        when(transactionService.createTransaction(any())).thenReturn(response);

        // When: se envía POST al endpoint
        mockMvc.perform(post("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            // Then: respuesta 201 Created con la transacción
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.transactionId").value(response.transactionId().toString()))
            .andExpect(jsonPath("$.status").value(TransactionStatus.APPROVED.name()));
    }

    @Test
    void shouldReturn400WhenMissingRequiredFields() throws Exception {
        // Given: solicitud con campos requeridos faltantes
        String invalidRequest = "{\"channel\":\"CH1\"}";

        // When: se envía POST
        mockMvc.perform(post("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
            // Then: respuesta 400 Bad Request
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn409WhenIdempotencyKeyConflict() throws Exception {
        // Given: solicitud con clave de idempotencia duplicada
        PaymentTransaction request = createValidRequest();
        
        when(transactionService.createTransaction(any()))
            .thenThrow(new RuntimeException("Idempotency key already exists"));

        // When: se envía POST
        mockMvc.perform(post("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            // Then: respuesta 409 Conflict
            .andExpect(status().isConflict());
    }

    @Test
    void shouldReturn500WhenInternalErrorOccurs() throws Exception {
        // Given: solicitud que causa error interno
        PaymentTransaction request = createValidRequest();
        
        when(transactionService.createTransaction(any()))
            .thenThrow(new RuntimeException("Database connection failed"));

        // When: se envía POST
        mockMvc.perform(post("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            // Then: respuesta 500 Internal Server Error
            .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldGetTransactionById() throws Exception {
        // Given: una transacción existente
        UUID transactionId = UUID.randomUUID();
        PaymentTransaction transaction = createValidResponse();
        
        when(transactionService.getTransaction(transactionId)).thenReturn(transaction);

        // When: se envía GET
        mockMvc.perform(get("/api/v1/transactions/{id}", transactionId))
            // Then: respuesta 200 OK con la transacción
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.transactionId").value(transactionId.toString()));
    }

    @Test
    void shouldReturn404WhenTransactionNotFound() throws Exception {
        // Given: ID de transacción que no existe
        UUID transactionId = UUID.randomUUID();
        
        when(transactionService.getTransaction(transactionId))
            .thenThrow(new RuntimeException("Transaction not found"));

        // When: se envía GET
        mockMvc.perform(get("/api/v1/transactions/{id}", transactionId))
            // Then: respuesta 404 Not Found
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateTransactionStatus() throws Exception {
        // Given: una transacción existente
        UUID transactionId = UUID.randomUUID();
        
        doNothing().when(transactionService).updateStatus(transactionId, TransactionStatus.COMPLETED);

        // When: se envía PATCH
        mockMvc.perform(patch("/api/v1/transactions/{id}/status", transactionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"COMPLETED\""))
            // Then: respuesta 204 No Content
            .andExpect(status().isNoContent());
        
        verify(transactionService).updateStatus(transactionId, TransactionStatus.COMPLETED);
    }

    @Test
    void shouldReturnValidationErrorForInvalidAmount() throws Exception {
        // Given: solicitud con monto inválido
        String invalidRequest = "{\"operationNumber\":\"OP1\",\"channel\":\"CH1\",\"amount\":-100}";

        // When: se envía POST
        mockMvc.perform(post("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
            // Then: respuesta 400 con errores de validación
            .andExpect(status().isBadRequest());
    }

    private PaymentTransaction createValidRequest() {
        return new PaymentTransaction(
            null,
            "OP123",
            "CHANNEL1",
            new BigDecimal("1000.00"),
            "USD",
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            "ACC123",
            "ACC456",
            "REF001",
            null,
            null
        );
    }

    private PaymentTransaction createValidResponse() {
        return new PaymentTransaction(
            UUID.randomUUID(),
            "OP123",
            "CHANNEL1",
            new BigDecimal("1000.00"),
            "USD",
            LocalDateTime.now(),
            TransactionStatus.APPROVED,
            "ACC123",
            "ACC456",
            "REF001",
            null,
            null
        );
    }
}
```
