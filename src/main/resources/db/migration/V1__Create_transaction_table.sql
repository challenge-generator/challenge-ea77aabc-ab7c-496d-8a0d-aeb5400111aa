-- =====================================================
-- Tabla de transacciones para el sistema de pagos
-- Cumplimiento ACID y soporte para idempotencia
-- =====================================================

-- Tabla principal de transacciones
CREATE TABLE IF NOT EXISTS payment_transactions (
    transaction_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    operation_number VARCHAR(100) NOT NULL,
    channel VARCHAR(50) NOT NULL,
    amount DECIMAL(19, 4) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(30) NOT NULL,
    originator_account VARCHAR(50) NOT NULL,
    beneficiary_account VARCHAR(50) NOT NULL,
    reference VARCHAR(200),
    fraud_check_id VARCHAR(100),
    idempotency_key VARCHAR(100) UNIQUE,
    metadata JSONB,

    -- Constraints de negocio
    CONSTRAINT chk_amount_positive CHECK (amount > 0),
    CONSTRAINT chk_currency_format CHECK (currency ~ '^[A-Z]{3}$'),
    CONSTRAINT chk_status_valid CHECK (status IN (
        'PENDING',
        'PROCESSING',
        'AUTHORIZED',
        'CAPTURED',
        'FAILED',
        'REJECTED',
        'TIMEOUT',
        'CANCELLED',
        'REFUNDED'
    ))
);

-- =====================================================
-- Índices para optimizar consultas frecuentes
-- =====================================================

-- Índice para búsqueda por número de operación y canal (caso de uso principal de idempotencia)
CREATE INDEX idx_transaction_op_number_channel
    ON payment_transactions(operation_number, channel);

-- Índice para búsqueda por clave de idempotencia (búsqueda rápida)
CREATE INDEX idx_transaction_idempotency_key
    ON payment_transactions(idempotency_key)
    WHERE idempotency_key IS NOT NULL;

-- Índice para consultas por estado (dashboard, monitoreo)
CREATE INDEX idx_transaction_status
    ON payment_transactions(status, created_at DESC);

-- Índice para consultas por cuenta de originador (conciliación)
CREATE INDEX idx_transaction_originator
    ON payment_transactions(originator_account, created_at DESC);

-- Índice para consultas por cuenta de beneficiario
CREATE INDEX idx_transaction_beneficiary
    ON payment_transactions(beneficiary_account, created_at DESC);

-- Índice para consultas por fecha (reportes, auditoría)
CREATE INDEX idx_transaction_created_at
    ON payment_transactions(created_at DESC);

-- Índice compuesto para consultas analíticas por canal y período
CREATE INDEX idx_transaction_channel_created
    ON payment_transactions(channel, created_at DESC);

-- =====================================================
-- Comments para documentación del esquema
-- =====================================================

COMMENT ON TABLE payment_transactions IS 'Almacena todas las transacciones del sistema de pagos con soporte ACID';
COMMENT ON COLUMN payment_transactions.transaction_id IS 'Identificador único universal de la transacción';
COMMENT ON COLUMN payment_transactions.operation_number IS 'Número de operación único dentro del canal';
COMMENT ON COLUMN payment_transactions.channel IS 'Canal por el cual se origina la transacción (API, WEB, MOBILE, BATCH)';
COMMENT ON COLUMN payment_transactions.amount IS 'Monto de la transacción con precisión de 4 decimales';
COMMENT ON COLUMN payment_transactions.currency IS 'Código ISO 4217 de la moneda (USD, EUR, MXN)';
COMMENT ON COLUMN payment_transactions.status IS 'Estado actual de la transacción en el ciclo de vida';
COMMENT ON COLUMN payment_transactions.originator_account IS 'Cuenta que inicia el débito';
COMMENT ON COLUMN payment_transactions.beneficiary_account IS 'Cuenta que recibe el crédito';
COMMENT ON COLUMN payment_transactions.reference IS 'Referencia externa o identificador del sistema origen';
COMMENT ON COLUMN payment_transactions.fraud_check_id IS 'Identificador de la verificación antifraude';
COMMENT ON COLUMN payment_transactions.idempotency_key IS 'Clave para garantizar idempotencia: operation_number + channel';
COMMENT ON COLUMN payment_transactions.metadata IS 'Datos adicionales en formato JSON para extensibilidad';

-- =====================================================
-- Función para actualizar timestamp automáticamente
-- =====================================================

CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger para actualizar updated_at en cada modificación
CREATE TRIGGER update_payment_transactions_updated_at
    BEFORE UPDATE ON payment_transactions
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- =====================================================
-- Función para generar clave de idempotencia
-- =====================================================

CREATE OR REPLACE FUNCTION generate_idempotency_key(p_operation_number VARCHAR, p_channel VARCHAR)
RETURNS VARCHAR AS $$
BEGIN
    RETURN CONCAT(p_channel, '_', p_operation_number, '_', CURRENT_DATE);
END;
$$ LANGUAGE plpgsql IMMUTABLE;

-- =====================================================
-- Secuencia para números de operación si se requiere
-- =====================================================

CREATE SEQUENCE IF NOT EXISTS transaction_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO CYCLE;

-- =====================================================
-- Tabla de auditoría de cambios de estado
-- =====================================================

CREATE TABLE IF NOT EXISTS transaction_status_audit (
    audit_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    transaction_id UUID NOT NULL REFERENCES payment_transactions(transaction_id),
    previous_status VARCHAR(30),
    new_status VARCHAR(30) NOT NULL,
    changed_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    changed_by VARCHAR(100),
    reason TEXT
);

CREATE INDEX idx_transaction_status_audit_transaction
    ON transaction_status_audit(transaction_id, changed_at DESC);

COMMENT ON TABLE transaction_status_audit IS 'Historial de cambios de estado para auditoría y trazabilidad';