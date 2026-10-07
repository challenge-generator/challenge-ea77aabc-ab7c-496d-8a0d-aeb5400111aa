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