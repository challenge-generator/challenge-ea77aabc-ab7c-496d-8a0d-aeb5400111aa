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