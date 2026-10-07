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