package com.pragma.payments.infrastructure.config;

import com.pragma.payments.application.service.exception.TransactionNotFoundException;
import com.pragma.payments.application.service.exception.DuplicateIdempotencyKeyException;
import com.pragma.payments.application.service.exception.TransactionTimeoutException;
import com.pragma.payments.application.service.exception.TransactionFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String ERROR_CATEGORY = "errorCategory";
    private static final String ERROR_CODE = "errorCode";

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTransactionNotFound(
            TransactionNotFoundException ex, WebRequest request) {
        log.warn("Transacción no encontrada: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "TRANSACTION_NOT_FOUND",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now().toString(),
                Map.of(ERROR_CATEGORY, "CLIENT_ERROR", ERROR_CODE, "TXN_001")
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateIdempotencyKeyException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateIdempotencyKey(
            DuplicateIdempotencyKeyException ex, WebRequest request) {
        log.warn("Clave de idempotencia duplicada: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "DUPLICATE_IDEMPOTENCY_KEY",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now().toString(),
                Map.of(ERROR_CATEGORY, "CLIENT_ERROR", ERROR_CODE, "TXN_002")
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(TransactionTimeoutException.class)
    public ResponseEntity<ErrorResponse> handleTransactionTimeout(
            TransactionTimeoutException ex, WebRequest request) {
        log.error("Timeout en transacción: {}", ex.getMessage(), ex);

        ErrorResponse error = new ErrorResponse(
                HttpStatus.GATEWAY_TIMEOUT.value(),
                "TRANSACTION_TIMEOUT",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now().toString(),
                Map.of(ERROR_CATEGORY, "TIMEOUT_ERROR", ERROR_CODE, "TXN_003")
        );
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(error);
    }

    @ExceptionHandler(TransactionFailedException.class)
    public ResponseEntity<ErrorResponse> handleTransactionFailed(
            TransactionFailedException ex, WebRequest request) {
        log.error("Falló la transacción: {}", ex.getMessage(), ex);

        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "TRANSACTION_FAILED",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now().toString(),
                Map.of(ERROR_CATEGORY, "BUSINESS_ERROR", ERROR_CODE, "TXN_004")
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex, WebRequest request) {
        log.warn("Error de validación: {}", ex.getMessage());

        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null
                                ? error.getDefaultMessage()
                                : "Valor inválido",
                        (existing, replacement) -> existing
                ));

        ValidationErrorResponse error = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                "Los datos proporcionados no son válidos",
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now().toString(),
                fieldErrors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.warn("Tipo de argumento inválido: {}", ex.getMessage());

        String message = String.format("El parámetro '%s' tiene un valor inválido: '%s'",
                ex.getName(), ex.getValue());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "INVALID_ARGUMENT_TYPE",
                message,
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now().toString(),
                Map.of(ERROR_CATEGORY, "CLIENT_ERROR", ERROR_CODE, "TXN_005")
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex, WebRequest request) {
        log.warn("Argumento ilegal: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "ILLEGAL_ARGUMENT",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now().toString(),
                Map.of(ERROR_CATEGORY, "CLIENT_ERROR", ERROR_CODE, "TXN_006")
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {
        log.error("Error interno del servidor: {}", ex.getMessage(), ex);

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                "Ocurrió un error inesperado. Por favor, contacte al administrador.",
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now().toString(),
                Map.of(ERROR_CATEGORY, "SERVER_ERROR", ERROR_CODE, "TXN_999")
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    public record ErrorResponse(
            int status,
            String error,
            String message,
            String path,
            String timestamp,
            Map<String, String> details
    ) {}

    public record ValidationErrorResponse(
            int status,
            String error,
            String message,
            String path,
            String timestamp,
            Map<String, String> fieldErrors
    ) {}
}