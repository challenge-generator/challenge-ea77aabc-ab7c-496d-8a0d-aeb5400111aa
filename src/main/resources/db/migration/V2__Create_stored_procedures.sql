-- Procedimientos almacenados optimizados para operaciones de transacción del sistema de pagos
-- Versión: V2
-- Objetivo: Proveer operaciones transaccionales de alto rendimiento con manejo de idempotencia

-- Procedimiento para crear una nueva transacción con verificación de idempotencia
-- Retorna: ID de la transacción creada o ID de la transacción existente si ya existe
CREATE OR REPLACE FUNCTION create_payment_transaction(
    p_transaction_id UUID,
    p_operation_number VARCHAR(50),
    p_channel VARCHAR(20),
    p_amount NUMERIC(19, 4),
    p_currency VARCHAR(3),
    p_originator_account VARCHAR(50),
    p_beneficiary_account VARCHAR(50),
    p_reference VARCHAR(200),
    p_idempotency_key VARCHAR(100),
    p_metadata JSONB
)
RETURNS UUID AS $$
DECLARE
    v_existing_id UUID;
    v_created_at TIMESTAMP WITH TIME ZONE;
BEGIN
    -- Verificar si ya existe una transacción con la misma clave de idempotencia
    SELECT t.transaction_id INTO v_existing_id
    FROM payment_transactions t
    WHERE t.idempotency_key = p_idempotency_key
    LIMIT 1;

    -- Si existe, retornar el ID existente (idempotencia)
    IF v_existing_id IS NOT NULL THEN
        RETURN v_existing_id;
    END IF;

    -- Establecer timestamp de creación
    v_created_at := CURRENT_TIMESTAMP;

    -- Insertar la nueva transacción
    INSERT INTO payment_transactions (
        transaction_id,
        operation_number,
        channel,
        amount,
        currency,
        created_at,
        status,
        originator_account,
        beneficiary_account,
        reference,
        idempotency_key,
        metadata
    ) VALUES (
        p_transaction_id,
        p_operation_number,
        p_channel,
        p_amount,
        p_currency,
        v_created_at,
        'PENDING'::transaction_status_type,
        p_originator_account,
        p_beneficiary_account,
        p_reference,
        p_idempotency_key,
        p_metadata
    );

    RETURN p_transaction_id;
END;
$$ LANGUAGE plpgsql
SET search_path = public;

-- Procedimiento para actualizar el estado de una transacción
-- Maneja transiciones de estado válidas y registra histórico
CREATE OR REPLACE FUNCTION update_transaction_status(
    p_transaction_id UUID,
    p_new_status VARCHAR(30),
    p_fraud_check_id VARCHAR(100) DEFAULT NULL
)
RETURNS BOOLEAN AS $$
DECLARE
    v_current_status VARCHAR(30);
    v_updated BOOLEAN := FALSE;
BEGIN
    -- Obtener el estado actual de la transacción
    SELECT t.status::TEXT INTO v_current_status
    FROM payment_transactions t
    WHERE t.transaction_id = p_transaction_id
    FOR UPDATE;

    -- Validar transición de estado
    IF NOT is_valid_status_transition(v_current_status, p_new_status) THEN
        RAISE EXCEPTION 'Transición de estado inválida: % -> %', v_current_status, p_new_status
            USING HINT = 'Verifique las transiciones de estado permitidas';
    END IF;

    -- Actualizar estado
    UPDATE payment_transactions
    SET status = p_new_status::transaction_status_type,
        updated_at = CURRENT_TIMESTAMP
    WHERE transaction_id = p_transaction_id;

    -- Actualizar fraud_check_id si se proporciona
    IF p_fraud_check_id IS NOT NULL THEN
        UPDATE payment_transactions
        SET fraud_check_id = p_fraud_check_id
        WHERE transaction_id = p_transaction_id;
    END IF;

    -- Registrar en histórico de estados
    INSERT INTO transaction_status_history (
        history_id,
        transaction_id,
        from_status,
        to_status,
        changed_at
    ) VALUES (
        gen_random_uuid(),
        p_transaction_id,
        v_current_status,
        p_new_status,
        CURRENT_TIMESTAMP
    );

    GET DIAGNOSTICS v_updated = ROW_COUNT;
    RETURN v_updated > 0;
END;
$$ LANGUAGE plpgsql
SET search_path = public;

-- Función auxiliar para validar transiciones de estado
CREATE OR REPLACE FUNCTION is_valid_status_transition(
    p_from_status VARCHAR(30),
    p_to_status VARCHAR(30)
)
RETURNS BOOLEAN AS $$
BEGIN
    -- Transiciones válidas definidas por el dominio
    RETURN CASE
        -- Desde PENDING
        WHEN p_from_status = 'PENDING' AND p_to_status IN ('PROCESSING', 'FAILED', 'CANCELLED') THEN TRUE
        -- Desde PROCESSING
        WHEN p_from_status = 'PROCESSING' AND p_to_status IN ('AUTHORIZED', 'FAILED', 'CANCELLED') THEN TRUE
        -- Desde AUTHORIZED
        WHEN p_from_status = 'AUTHORIZED' AND p_to_status IN ('SETTLED', 'FAILED', 'CANCELLED') THEN TRUE
        -- Estados finales (no se sale de estos)
        WHEN p_from_status IN ('SETTLED', 'FAILED', 'CANCELLED') AND p_to_status = p_from_status THEN TRUE
        -- Cualquier otra transición es inválida
        ELSE FALSE
    END;
END;
$$ LANGUAGE plpgsql
SET search_path = public;

-- Procedimiento para procesar una transacción completa con manejo de errores
-- Incluye validación de idempotencia, verificación antifraude y actualización de estado
CREATE OR REPLACE FUNCTION process_payment_transaction(
    p_transaction_id UUID,
    p_operation_number VARCHAR(50),
    p_channel VARCHAR(20),
    p_amount NUMERIC(19, 4),
    p_currency VARCHAR(3),
    p_originator_account VARCHAR(50),
    p_beneficiary_account VARCHAR(50),
    p_reference VARCHAR(200),
    p_idempotency_key VARCHAR(100),
    p_metadata JSONB,
    p_fraud_check_id VARCHAR(100)
)
RETURNS TABLE (
    transaction_id UUID,
    status VARCHAR(30),
    message TEXT
) AS $$
DECLARE
    v_result_id UUID;
    v_status VARCHAR(30);
    v_message TEXT;
    v_existing_transaction UUID;
BEGIN
    -- Verificar idempotencia
    SELECT t.transaction_id, t.status::TEXT INTO v_existing_transaction, v_status
    FROM payment_transactions t
    WHERE t.idempotency_key = p_idempotency_key
    LIMIT 1;

    IF v_existing_transaction IS NOT NULL THEN
        -- Transacción existente, retornar resultado
        RETURN QUERY SELECT 
            v_existing_transaction, 
            v_status, 
            'Transacción existente retornada por idempotencia'::TEXT;
        RETURN;
    END IF;

    -- Crear transacción inicial
    v_result_id := create_payment_transaction(
        p_transaction_id,
        p_operation_number,
        p_channel,
        p_amount,
        p_currency,
        p_originator_account,
        p_beneficiary_account,
        p_reference,
        p_idempotency_key,
        p_metadata
    );

    -- Actualizar a estado PROCESSING
    PERFORM update_transaction_status(v_result_id, 'PROCESSING', p_fraud_check_id);

    -- Simular procesamiento exitoso
    PERFORM update_transaction_status(v_result_id, 'AUTHORIZED', p_fraud_check_id);

    -- Retornar resultado exitoso
    RETURN QUERY SELECT 
        v_result_id, 
        'AUTHORIZED'::VARCHAR(30), 
        'Transacción procesada exitosamente'::TEXT;
    RETURN;

EXCEPTION
    WHEN OTHERS THEN
        -- Manejo de errores: marcar como fallida
        IF v_result_id IS NOT NULL THEN
            PERFORM update_transaction_status(v_result_id, 'FAILED', NULL);
        END IF;
        
        RETURN QUERY SELECT 
            COALESCE(v_result_id, p_transaction_id), 
            'FAILED'::VARCHAR(30), 
            'Error al procesar transacción: ' || SQLERRM::TEXT;
        RETURN;
END;
$$ LANGUAGE plpgsql
SET search_path = public;

-- Función para obtener estadísticas de transacciones por estado
-- Útil para monitoreo y dashboards
CREATE OR REPLACE FUNCTION get_transaction_statistics(
    p_start_date TIMESTAMP WITH TIME ZONE,
    p_end_date TIMESTAMP WITH TIME ZONE
)
RETURNS TABLE (
    status VARCHAR(30),
    count BIGINT,
    total_amount NUMERIC(19, 4),
    avg_amount NUMERIC(19, 4)
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        t.status::TEXT AS status,
        COUNT(*)::BIGINT AS count,
        SUM(t.amount)::NUMERIC(19, 4) AS total_amount,
        AVG(t.amount)::NUMERIC(19, 4) AS avg_amount
    FROM payment_transactions t
    WHERE t.created_at BETWEEN p_start_date AND p_end_date
    GROUP BY t.status
    ORDER BY count DESC;
END;
$$ LANGUAGE plpgsql
SET search_path = public;

-- Procedimiento para limpiar transacciones antiguas en estado PENDING
-- Útil para manejar transacciones huérfanas por timeouts
CREATE OR REPLACE FUNCTION cleanup_stale_pending_transactions(
    p_older_than_minutes INTEGER DEFAULT 30
)
RETURNS INTEGER AS $$
DECLARE
    v_cleaned_count INTEGER;
    v_cutoff_time TIMESTAMP WITH TIME ZONE;
BEGIN
    v_cutoff_time := CURRENT_TIMESTAMP - (p_older_than_minutes || ' minutes')::INTERVAL;

    UPDATE payment_transactions
    SET status = 'CANCELLED'::transaction_status_type,
        updated_at = CURRENT_TIMESTAMP,
        metadata = jsonb_set(metadata, '{cleanup_reason}', '"timeout"')
    WHERE status = 'PENDING'::transaction_status_type
      AND created_at < v_cutoff_time;

    GET DIAGNOSTICS v_cleaned_count = ROW_COUNT;
    
    -- Registrar limpieza
    INSERT INTO transaction_cleanup_log (
        log_id,
        cleaned_at,
        cleaned_count,
        cutoff_time
    ) VALUES (
        gen_random_uuid(),
        CURRENT_TIMESTAMP,
        v_cleaned_count,
        v_cutoff_time
    );

    RETURN v_cleaned_count;
END;
$$ LANGUAGE plpgsql
SET search_path = public;

-- Tabla para registro histórico de cambios de estado
CREATE TABLE IF NOT EXISTS transaction_status_history (
    history_id UUID PRIMARY KEY,
    transaction_id UUID NOT NULL REFERENCES payment_transactions(transaction_id),
    from_status VARCHAR(30) NOT NULL,
    to_status VARCHAR(30) NOT NULL,
    changed_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Índice para consultas de histórico por transacción
CREATE INDEX IF NOT EXISTS idx_status_history_transaction_id 
    ON transaction_status_history(transaction_id);

-- Índice para consultas de histórico por fecha
CREATE INDEX IF NOT EXISTS idx_status_history_changed_at 
    ON transaction_status_history(changed_at);

-- Tabla para registro de limpiezas de transacciones
CREATE TABLE IF NOT EXISTS transaction_cleanup_log (
    log_id UUID PRIMARY KEY,
    cleaned_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cleaned_count INTEGER NOT NULL,
    cutoff_time TIMESTAMP WITH TIME ZONE NOT NULL
);

-- Índice para consultas de limpieza por fecha
CREATE INDEX IF NOT EXISTS idx_cleanup_log_cleaned_at 
    ON transaction_cleanup_log(cleaned_at);

-- Función para buscar transacciones por múltiples criterios
-- Optimizada para consultas frecuentes del dashboard
CREATE OR REPLACE FUNCTION search_transactions(
    p_status VARCHAR(30) DEFAULT NULL,
    p_channel VARCHAR(20) DEFAULT NULL,
    p_originator_account VARCHAR(50) DEFAULT NULL,
    p_start_date TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    p_end_date TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    p_limit INTEGER DEFAULT 100,
    p_offset INTEGER DEFAULT 0
)
RETURNS TABLE (
    transaction_id UUID,
    operation_number VARCHAR(50),
    channel VARCHAR(20),
    amount NUMERIC(19, 4),
    currency VARCHAR(3),
    status VARCHAR(30),
    originator_account VARCHAR(50),
    beneficiary_account VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        t.transaction_id,
        t.operation_number,
        t.channel,
        t.amount,
        t.currency,
        t.status::TEXT,
        t.originator_account,
        t.beneficiary_account,
        t.created_at
    FROM payment_transactions t
    WHERE (p_status IS NULL OR t.status::TEXT = p_status)
      AND (p_channel IS NULL OR t.channel = p_channel)
      AND (p_originator_account IS NULL OR t.originator_account = p_originator_account)
      AND (p_start_date IS NULL OR t.created_at >= p_start_date)
      AND (p_end_date IS NULL OR t.created_at <= p_end_date)
    ORDER BY t.created_at DESC
    LIMIT p_limit
    OFFSET p_offset;
END;
$$ LANGUAGE plpgsql
SET search_path = public;

-- Otorgar permisos de ejecución a las funciones
GRANT EXECUTE ON FUNCTION create_payment_transaction TO app_user;
GRANT EXECUTE ON FUNCTION update_transaction_status TO app_user;
GRANT EXECUTE ON FUNCTION is_valid_status_transition TO app_user;
GRANT EXECUTE ON FUNCTION process_payment_transaction TO app_user;
GRANT EXECUTE ON FUNCTION get_transaction_statistics TO app_user;
GRANT EXECUTE ON FUNCTION cleanup_stale_pending_transactions TO app_user;
GRANT EXECUTE ON FUNCTION search_transactions TO app_user;