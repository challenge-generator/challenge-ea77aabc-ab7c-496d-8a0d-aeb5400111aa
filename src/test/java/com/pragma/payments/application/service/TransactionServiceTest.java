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