package com.pragma.payments.infrastructure.repository;

import com.pragma.payments.infrastructure.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, UUID> {

    @Query(value = "SELECT * FROM transactions WHERE idempotency_key = :idempotencyKey LIMIT 1", nativeQuery = true)
    TransactionEntity findByIdempotencyKey(@Param("idempotencyKey") String idempotencyKey);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM transactions WHERE idempotency_key = :idempotencyKey)", nativeQuery = true)
    boolean existsByIdempotencyKey(@Param("idempotencyKey") String idempotencyKey);

    @Modifying
    @Query(value = "UPDATE transactions SET status = :status, updated_at = :updatedAt WHERE transaction_id = :transactionId", nativeQuery = true)
    void updateTransactionStatus(@Param("transactionId") UUID transactionId, @Param("status") String status, @Param("updatedAt") LocalDateTime updatedAt);

    @Query(value = "SELECT * FROM transactions WHERE originator_account = :accountNumber AND created_at BETWEEN :startDate AND :endDate ORDER BY created_at DESC", nativeQuery = true)
    List<TransactionEntity> findByOriginatorAccountAndDateRange(@Param("accountNumber") String accountNumber, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM transactions WHERE beneficiary_account = :accountNumber AND created_at BETWEEN :startDate AND :endDate ORDER BY created_at DESC", nativeQuery = true)
    List<TransactionEntity> findByBeneficiaryAccountAndDateRange(@Param("accountNumber") String accountNumber, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM transactions WHERE amount >= :minAmount AND amount <= :maxAmount AND created_at BETWEEN :startDate AND :endDate ORDER BY amount DESC", nativeQuery = true)
    List<TransactionEntity> findByAmountRangeAndDateRange(@Param("minAmount") BigDecimal minAmount, @Param("maxAmount") BigDecimal maxAmount, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM transactions WHERE status = :status AND created_at < :thresholdDate ORDER BY created_at ASC LIMIT :limit", nativeQuery = true)
    List<TransactionEntity> findStaleTransactions(@Param("status") String status, @Param("thresholdDate") LocalDateTime thresholdDate, @Param("limit") int limit);

    @Modifying
    @Query(value = "CALL sp_process_transaction_batch(:batchId, :totalCount)", nativeQuery = true)
    void executeStoredProcedureForBatch(@Param("batchId") String batchId, @Param("totalCount") int totalCount);

    @Query(value = "SELECT COUNT(*) FROM transactions WHERE status = :status AND DATE(created_at) = CURRENT_DATE", nativeQuery = true)
    long countByStatusForToday(@Param("status") String status);

    @Query(value = "SELECT * FROM transactions WHERE channel = :channel AND status = :status ORDER BY created_at DESC LIMIT :limit", nativeQuery = true)
    List<TransactionEntity> findRecentByChannelAndStatus(@Param("channel") String channel, @Param("status") String status, @Param("limit") int limit);
}