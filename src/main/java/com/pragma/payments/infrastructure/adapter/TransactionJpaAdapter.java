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