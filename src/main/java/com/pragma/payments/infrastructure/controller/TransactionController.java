package com.pragma.payments.infrastructure.controller;

import com.pragma.payments.application.service.TransactionService;
import com.pragma.payments.domain.model.PaymentTransaction;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);
    private static final String IDEMPOTENCY_HEADER = "X-Idempotency-Key";

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> createTransaction(
            @RequestHeader(value = IDEMPOTENCY_HEADER, required = false) String idempotencyKey,
            @Valid @RequestBody TransactionRequest request) {

        log.info("Recibida solicitud de transacción: operationNumber={}, channel={}, amount={}",
                request.operationNumber(), request.channel(), request.amount());

        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            log.debug("Clave de idempotencia proporcionada: {}", idempotencyKey);
        }

        PaymentTransaction transaction = transactionService.processTransaction(
                request.operationNumber(),
                request.channel(),
                request.amount(),
                request.currency(),
                request.originatorAccount(),
                request.beneficiaryAccount(),
                request.reference(),
                idempotencyKey,
                request.metadata()
        );

        TransactionResponse response = toResponse(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable UUID transactionId) {
        log.info("Consultando transacción: transactionId={}", transactionId);

        return transactionService.findById(transactionId)
                .map(transaction -> ResponseEntity.ok(toResponse(transaction)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Transacción no encontrada: " + transactionId));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionResponse>> getTransactions(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String channel,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        log.info("Consultando transacciones: status={}, channel={}, page={}, size={}",
                status, channel, page, size);

        List<PaymentTransaction> transactions = transactionService.findTransactions(status, channel, page, size);
        List<TransactionResponse> responses = transactions.stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping(value = "/idempotency/{idempotencyKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> getByIdempotencyKey(@PathVariable String idempotencyKey) {
        log.info("Consultando por clave de idempotencia: idempotencyKey={}", idempotencyKey);

        return transactionService.findByIdempotencyKey(idempotencyKey)
                .map(transaction -> ResponseEntity.ok(toResponse(transaction)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe transacción con clave de idempotencia: " + idempotencyKey));
    }

    @PatchMapping(value = "/{transactionId}/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStatus(
            @PathVariable UUID transactionId,
            @Valid @RequestBody StatusUpdateRequest request) {

        log.info("Actualizando estado de transacción: transactionId={}, newStatus={}",
                transactionId, request.status());

        transactionService.updateTransactionStatus(transactionId, request.status());
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "timestamp", LocalDateTime.now().toString(),
                "service", "transaction-controller"
        ));
    }

    private TransactionResponse toResponse(PaymentTransaction transaction) {
        return new TransactionResponse(
                transaction.transactionId().toString(),
                transaction.operationNumber(),
                transaction.channel(),
                transaction.amount(),
                transaction.currency(),
                transaction.createdAt().toString(),
                transaction.status().name(),
                transaction.originatorAccount(),
                transaction.beneficiaryAccount(),
                transaction.reference(),
                transaction.fraudCheckId(),
                transaction.metadata()
        );
    }

    public record TransactionRequest(
            @NotBlank(message = "El número de operación es obligatorio")
            String operationNumber,
            @NotBlank(message = "El canal es obligatorio")
            String channel,
            @NotNull(message = "El monto es obligatorio")
            @Positive(message = "El monto debe ser positivo")
            BigDecimal amount,
            @NotBlank(message = "La moneda es obligatoria")
            String currency,
            @NotBlank(message = "La cuenta del originador es obligatoria")
            String originatorAccount,
            @NotBlank(message = "La cuenta del beneficiario es obligatoria")
            String beneficiaryAccount,
            String reference,
            String metadata
    ) {}

    public record TransactionResponse(
            String transactionId,
            String operationNumber,
            String channel,
            BigDecimal amount,
            String currency,
            String createdAt,
            String status,
            String originatorAccount,
            String beneficiaryAccount,
            String reference,
            String fraudCheckId,
            String metadata
    ) {}

    public record StatusUpdateRequest(
            @NotBlank(message = "El estado es obligatorio")
            String status
    ) {}
}