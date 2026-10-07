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