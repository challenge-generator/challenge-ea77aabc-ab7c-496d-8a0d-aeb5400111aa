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