package com.pragma.payments.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    @Value("${transaction.isolation.level:ISOLATION_READ_COMMITTED}")
    private String isolationLevel;

    @Value("${transaction.timeout.seconds:30}")
    private int transactionTimeoutSeconds;

    @Value("${transaction.propagation.required:REQUIRED}")
    private String propagationBehavior;

    @Value("${transaction.retry.max-attempts:3}")
    private int maxRetryAttempts;

    @Value("${transaction.retry.backoff-ms:1000}")
    private long retryBackoffMs;

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        transactionManager.setDefaultTimeout(transactionTimeoutSeconds);
        return transactionManager;
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.setPropagationBehavior(translatePropagation(propagationBehavior));
        template.setIsolationLevel(translateIsolationLevel(isolationLevel));
        template.setTimeout(transactionTimeoutSeconds);
        template.setReadOnly(false);
        return template;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    private int translatePropagation(String propagation) {
        return switch (propagation.toUpperCase()) {
            case "REQUIRED" -> Propagation.REQUIRED;
            case "REQUIRES_NEW" -> Propagation.REQUIRES_NEW;
            case "NESTED" -> Propagation.NESTED;
            case "SUPPORTS" -> Propagation.SUPPORTS;
            case "NOT_SUPPORTED" -> Propagation.NOT_SUPPORTED;
            case "MANDATORY" -> Propagation.MANDATORY;
            case "NEVER" -> Propagation.NEVER;
            default -> Propagation.REQUIRED;
        };
    }

    private int translateIsolationLevel(String isolation) {
        return switch (isolation.toUpperCase()) {
            case "ISOLATION_DEFAULT" -> TransactionDefinition.Isolation.ISOLATION_DEFAULT;
            case "ISOLATION_READ_UNCOMMITTED" -> TransactionDefinition.Isolation.ISOLATION_READ_UNCOMMITTED;
            case "ISOLATION_READ_COMMITTED" -> TransactionDefinition.Isolation.ISOLATION_READ_COMMITTED;
            case "ISOLATION_REPEATABLE_READ" -> TransactionDefinition.Isolation.ISOLATION_REPEATABLE_READ;
            case "ISOLATION_SERIALIZABLE" -> TransactionDefinition.Isolation.ISOLATION_SERIALIZABLE;
            default -> TransactionDefinition.Isolation.ISOLATION_READ_COMMITTED;
        };
    }

    @Transactional(propagation = Propagation.REQUIRED, 
                   isolation = TransactionDefinition.Isolation.ISOLATION_READ_COMMITTED,
                   timeoutString = "${transaction.timeout.seconds}",
                   rollbackFor = Exception.class)
    public interface TransactionalOperations {
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
                   isolation = TransactionDefinition.Isolation.ISOLATION_SERIALIZABLE,
                   timeoutString = "${transaction.timeout.seconds}",
                   rollbackFor = Exception.class)
    public interface IsolatedTransactionalOperations {
    }

    @Transactional(propagation = Propagation.NESTED,
                   isolation = TransactionDefinition.Isolation.ISOLATION_READ_COMMITTED,
                   timeoutString = "${transaction.timeout.seconds}",
                   rollbackFor = Exception.class)
    public interface NestedTransactionalOperations {
    }
}