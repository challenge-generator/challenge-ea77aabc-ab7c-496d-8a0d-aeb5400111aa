package com.pragma.payments.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.payments.application.service.TransactionService;
import com.pragma.payments.domain.model.PaymentTransaction;
import com.pragma.payments.domain.model.PaymentTransaction.TransactionStatus;
import com.pragma.payments.infrastructure.config.GlobalExceptionHandler;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled("Superficie de práctica - completar implementación")
@WebMvcTest(TransactionController.class)
@Import(GlobalExceptionHandler.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @Test
    void shouldCreateTransactionWhenValidRequest() throws Exception {
        // Given: una solicitud de transacción válida
        PaymentTransaction request = createValidRequest();
        PaymentTransaction response = createValidResponse();
        
        when(transactionService.createTransaction(any())).thenReturn(response);

        // When: se envía POST al endpoint
        mockMvc.perform(post("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            // Then: respuesta 201 Created con la transacción
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.transactionId").value(response.transactionId().toString()))
            .andExpect(jsonPath("$.status").value(TransactionStatus.APPROVED.name()));
    }

    @Test
    void shouldReturn400WhenMissingRequiredFields() throws Exception {
        // Given: solicitud con campos requeridos faltantes
        String invalidRequest = "{\"channel\":\"CH1\"}";

        // When: se envía POST
        mockMvc.perform(post("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
            // Then: respuesta 400 Bad Request
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn409WhenIdempotencyKeyConflict() throws Exception {
        // Given: solicitud con clave de idempotencia duplicada
        PaymentTransaction request = createValidRequest();
        
        when(transactionService.createTransaction(any()))
            .thenThrow(new RuntimeException("Idempotency key already exists"));

        // When: se envía POST
        mockMvc.perform(post("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            // Then: respuesta 409 Conflict
            .andExpect(status().isConflict());
    }

    @Test
    void shouldReturn500WhenInternalErrorOccurs() throws Exception {
        // Given: solicitud que causa error interno
        PaymentTransaction request = createValidRequest();
        
        when(transactionService.createTransaction(any()))
            .thenThrow(new RuntimeException("Database connection failed"));

        // When: se envía POST
        mockMvc.perform(post("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            // Then: respuesta 500 Internal Server Error
            .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldGetTransactionById() throws Exception {
        // Given: una transacción existente
        UUID transactionId = UUID.randomUUID();
        PaymentTransaction transaction = createValidResponse();
        
        when(transactionService.getTransaction(transactionId)).thenReturn(transaction);

        // When: se envía GET
        mockMvc.perform(get("/api/v1/transactions/{id}", transactionId))
            // Then: respuesta 200 OK con la transacción
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.transactionId").value(transactionId.toString()));
    }

    @Test
    void shouldReturn404WhenTransactionNotFound() throws Exception {
        // Given: ID de transacción que no existe
        UUID transactionId = UUID.randomUUID();
        
        when(transactionService.getTransaction(transactionId))
            .thenThrow(new RuntimeException("Transaction not found"));

        // When: se envía GET
        mockMvc.perform(get("/api/v1/transactions/{id}", transactionId))
            // Then: respuesta 404 Not Found
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateTransactionStatus() throws Exception {
        // Given: una transacción existente
        UUID transactionId = UUID.randomUUID();
        
        doNothing().when(transactionService).updateStatus(transactionId, TransactionStatus.COMPLETED);

        // When: se envía PATCH
        mockMvc.perform(patch("/api/v1/transactions/{id}/status", transactionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"COMPLETED\""))
            // Then: respuesta 204 No Content
            .andExpect(status().isNoContent());
        
        verify(transactionService).updateStatus(transactionId, TransactionStatus.COMPLETED);
    }

    @Test
    void shouldReturnValidationErrorForInvalidAmount() throws Exception {
        // Given: solicitud con monto inválido
        String invalidRequest = "{\"operationNumber\":\"OP1\",\"channel\":\"CH1\",\"amount\":-100}";

        // When: se envía POST
        mockMvc.perform(post("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
            // Then: respuesta 400 con errores de validación
            .andExpect(status().isBadRequest());
    }

    private PaymentTransaction createValidRequest() {
        return new PaymentTransaction(
            null,
            "OP123",
            "CHANNEL1",
            new BigDecimal("1000.00"),
            "USD",
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            "ACC123",
            "ACC456",
            "REF001",
            null,
            null
        );
    }

    private PaymentTransaction createValidResponse() {
        return new PaymentTransaction(
            UUID.randomUUID(),
            "OP123",
            "CHANNEL1",
            new BigDecimal("1000.00"),
            "USD",
            LocalDateTime.now(),
            TransactionStatus.APPROVED,
            "ACC123",
            "ACC456",
            "REF001",
            null,
            null
        );
    }
}