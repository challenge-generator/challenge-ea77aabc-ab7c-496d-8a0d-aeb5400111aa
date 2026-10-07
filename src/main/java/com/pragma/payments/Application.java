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