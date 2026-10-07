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