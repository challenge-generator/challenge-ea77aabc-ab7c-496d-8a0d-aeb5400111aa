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