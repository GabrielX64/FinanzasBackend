-- =========================
-- SCRIPT DE CREACIÓN DE TABLAS
-- Base de Datos: dbFinanzas
-- =========================

-- Eliminar tablas existentes (opcional - descomentar si necesitas recrear)
-- DROP TABLE IF EXISTS loan_installment CASCADE;
-- DROP TABLE IF EXISTS loan CASCADE;
-- DROP TABLE IF EXISTS property CASCADE;
-- DROP TABLE IF EXISTS client CASCADE;
-- DROP TABLE IF EXISTS user_app CASCADE;
-- DROP TABLE IF EXISTS email_otp CASCADE;
-- DROP TABLE IF EXISTS financial_entity CASCADE;
-- DROP TABLE IF EXISTS marital_status CASCADE;
-- DROP TABLE IF EXISTS capitalization_frequency CASCADE;
-- DROP TABLE IF EXISTS property_type CASCADE;
-- DROP TABLE IF EXISTS property_status CASCADE;
-- DROP TABLE IF EXISTS currency CASCADE;

-- =========================
-- TABLAS DE CATÁLOGO
-- =========================

-- Tabla: capitalization_frequency
--CREATE TABLE IF NOT EXISTS capitalization_frequency (capitalization_frequency_id BIGSERIAL PRIMARY KEY,
--                                                     name VARCHAR(50) NOT NULL UNIQUE, periods_per_year INTEGER NOT NULL);
--
---- Tabla: marital_status
--CREATE TABLE IF NOT EXISTS marital_status (marital_status_id BIGSERIAL PRIMARY KEY,
--                                           status_name VARCHAR(50) NOT NULL UNIQUE);
--
---- Tabla: financial_entity
--CREATE TABLE IF NOT EXISTS financial_entity (financial_entity_id BIGSERIAL PRIMARY KEY,
--                                             name VARCHAR(255) NOT NULL,
--    code VARCHAR(50) NOT NULL UNIQUE,
--    down_payment_percentage NUMERIC(5,2) NOT NULL,
--    active BOOLEAN DEFAULT true);
--
---- Tabla: currency
--CREATE TABLE IF NOT EXISTS currency (currency_id BIGSERIAL PRIMARY KEY,
--                                     code VARCHAR(10) NOT NULL UNIQUE,
--    name VARCHAR(100) NOT NULL,
--    symbol VARCHAR(10) NOT NULL
--    );
--
---- Tabla: property_type
--CREATE TABLE IF NOT EXISTS property_type (property_type_id BIGSERIAL PRIMARY KEY,
--                                          name VARCHAR(100) NOT NULL UNIQUE);
--
---- Tabla: property_status
--CREATE TABLE IF NOT EXISTS property_status (status_id BIGSERIAL PRIMARY KEY,
--                                            name VARCHAR(100) NOT NULL UNIQUE);
--
---- =========================
---- TABLAS PRINCIPALES
---- =========================
--
---- Tabla: user_app
--CREATE TABLE IF NOT EXISTS user_app (user_app_id BIGSERIAL PRIMARY KEY,
--                                     names VARCHAR(255) NOT NULL,
--    surnames VARCHAR(255) NOT NULL,
--    email VARCHAR(255) NOT NULL UNIQUE,
--    username VARCHAR(100) NOT NULL UNIQUE,
--    password VARCHAR(255) NOT NULL
--    );
--
---- Tabla: client
--CREATE TABLE IF NOT EXISTS client (client_id BIGSERIAL PRIMARY KEY,
--                                   names VARCHAR(255) NOT NULL,
--    surnames VARCHAR(255) NOT NULL,
--    dni VARCHAR(8) NOT NULL UNIQUE,
--    email VARCHAR(255) NOT NULL UNIQUE,
--    phone_number VARCHAR(20),
--    monthly_income NUMERIC(12,2),
--    occupation VARCHAR(255),
--    marital_status BIGINT,
--    current_address TEXT,
--    CONSTRAINT fk_client_marital_status
--    FOREIGN KEY (marital_status)
--    REFERENCES marital_status(marital_status_id)
--    ON DELETE SET NULL
--    );
--
---- Tabla: email_otp
--CREATE TABLE IF NOT EXISTS email_otp (id BIGSERIAL PRIMARY KEY,
--                                      email VARCHAR(255) NOT NULL,
--    otp INTEGER NOT NULL,
--    expiration_time TIMESTAMP NOT NULL,
--    used BOOLEAN DEFAULT false
--    );
--
---- Tabla: property
--CREATE TABLE IF NOT EXISTS property (property_id BIGSERIAL PRIMARY KEY,
--                                     code VARCHAR(50) NOT NULL UNIQUE,
--    address TEXT NOT NULL,
--    area_m2 DOUBLE PRECISION,
--    bedrooms INTEGER,
--    bathrooms INTEGER,
--    sale_price NUMERIC(12,2) NOT NULL,
--    property_type_id BIGINT NOT NULL,
--    currency_id BIGINT NOT NULL,
--    status_id BIGINT NOT NULL,
--    CONSTRAINT fk_property_type
--    FOREIGN KEY (property_type_id)
--    REFERENCES property_type(property_type_id)
--    ON DELETE RESTRICT,
--    CONSTRAINT fk_property_currency
--    FOREIGN KEY (currency_id)
--    REFERENCES currency(currency_id)
--    ON DELETE RESTRICT,
--    CONSTRAINT fk_property_status
--    FOREIGN KEY (status_id)
--    REFERENCES property_status(status_id)
--    ON DELETE RESTRICT
--    );
--
---- Tabla: loan
--CREATE TABLE IF NOT EXISTS loan (loan_id BIGSERIAL PRIMARY KEY,
--                                 client_id BIGINT NOT NULL,
--                                 asesor_id BIGINT,
--                                 financial_entity_id BIGINT,
--                                 property_id BIGINT,
--                                 principal NUMERIC(12,2) NOT NULL,
--    property_price NUMERIC(12,2),
--    down_payment NUMERIC(12,2),
--    down_payment_percentage NUMERIC(5,2),
--    tea NUMERIC(8,6),
--    rate_type VARCHAR(10),
--    tnp NUMERIC(8,6),
--    capitalization_frequency_id BIGINT,
--    years INTEGER NOT NULL,
--    frequency_per_year INTEGER DEFAULT 12,
--    total_grace INTEGER DEFAULT 0,
--    partial_grace INTEGER DEFAULT 0,
--    cok NUMERIC(8,6),
--    tir NUMERIC(8,6),
--    tcea NUMERIC(8,6),
--    van NUMERIC(15,2),
--    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--    CONSTRAINT fk_loan_client
--    FOREIGN KEY (client_id)
--    REFERENCES client(client_id)
--    ON DELETE CASCADE,
--    CONSTRAINT fk_loan_asesor
--    FOREIGN KEY (asesor_id)
--    REFERENCES user_app(user_app_id)
--    ON DELETE SET NULL,
--    CONSTRAINT fk_loan_financial_entity
--    FOREIGN KEY (financial_entity_id)
--    REFERENCES financial_entity(financial_entity_id)
--    ON DELETE SET NULL,
--    CONSTRAINT fk_loan_capitalization
--    FOREIGN KEY (capitalization_frequency_id)
--    REFERENCES capitalization_frequency(capitalization_frequency_id)
--    ON DELETE SET NULL,
--    CONSTRAINT fk_loan_property
--    FOREIGN KEY (property_id)
--    REFERENCES property(property_id)
--    ON DELETE SET NULL
--    );
--
---- Tabla: loan_installment
--CREATE TABLE IF NOT EXISTS loan_installment (id BIGSERIAL PRIMARY KEY,
--                                             loan_id BIGINT NOT NULL,
--                                            period INTEGER NOT NULL,
--                                            initial_balance NUMERIC(12,2),
--    interest NUMERIC(12,2),
--    amortization NUMERIC(12,2),
--    fee NUMERIC(12,2),
--    final_balance NUMERIC(12,2),
--    cash_flow NUMERIC(12,2),
--    CONSTRAINT fk_installment_loan
--    FOREIGN KEY (loan_id)
--    REFERENCES loan(loan_id)
--    ON DELETE CASCADE
--    );

-- =========================
-- LIMPIAR DATOS EXISTENTES (OPCIONAL - COMENTAR SI NO DESEAS BORRAR)
-- =========================
-- TRUNCATE TABLE loan_installment CASCADE;
-- TRUNCATE TABLE loan CASCADE;
-- TRUNCATE TABLE client CASCADE;
-- TRUNCATE TABLE user_app CASCADE;
-- TRUNCATE TABLE financial_entity CASCADE;
-- TRUNCATE TABLE marital_status CASCADE;
-- TRUNCATE TABLE capitalization_frequency CASCADE;
-- TRUNCATE TABLE property CASCADE;
-- TRUNCATE TABLE property_type CASCADE;
-- TRUNCATE TABLE property_status CASCADE;
-- TRUNCATE TABLE currency CASCADE;
-- TRUNCATE TABLE email_otp CASCADE;
-- =========================
-- INSERTAR FRECUENCIAS BASE
-- =========================

-- DIARIA (360 días)
INSERT INTO capitalization_frequency (name, periods_per_year)
SELECT 'Diaria', 360
    WHERE NOT EXISTS (
    SELECT 1 FROM capitalization_frequency WHERE name = 'Diaria'
);

-- SEMANAL (52)
INSERT INTO capitalization_frequency (name, periods_per_year)
SELECT 'Semanal', 52
    WHERE NOT EXISTS (
    SELECT 1 FROM capitalization_frequency WHERE name = 'Semanal'
);

-- QUINCENAL (24)
INSERT INTO capitalization_frequency (name, periods_per_year)
SELECT 'Quincenal', 24
    WHERE NOT EXISTS (
    SELECT 1 FROM capitalization_frequency WHERE name = 'Quincenal'
);

-- MENSUAL (12)
INSERT INTO capitalization_frequency (name, periods_per_year)
SELECT 'Mensual', 12
    WHERE NOT EXISTS (
    SELECT 1 FROM capitalization_frequency WHERE name = 'Mensual'
);

-- BIMESTRAL (6)
INSERT INTO capitalization_frequency (name, periods_per_year)
SELECT 'Bimestral', 6
    WHERE NOT EXISTS (
    SELECT 1 FROM capitalization_frequency WHERE name = 'Bimestral'
);

-- TRIMESTRAL (4)
INSERT INTO capitalization_frequency (name, periods_per_year)
SELECT 'Trimestral', 4
    WHERE NOT EXISTS (
    SELECT 1 FROM capitalization_frequency WHERE name = 'Trimestral'
);

-- CUATRIMESTRAL (3)
INSERT INTO capitalization_frequency (name, periods_per_year)
SELECT 'Cuatrimestral', 3
    WHERE NOT EXISTS (
    SELECT 1 FROM capitalization_frequency WHERE name = 'Cuatrimestral'
);

-- SEMESTRAL (2)
INSERT INTO capitalization_frequency (name, periods_per_year)
SELECT 'Semestral', 2
    WHERE NOT EXISTS (
    SELECT 1 FROM capitalization_frequency WHERE name = 'Semestral'
);

-- ANUAL (1)
INSERT INTO capitalization_frequency (name, periods_per_year)
SELECT 'Anual', 1
    WHERE NOT EXISTS (
    SELECT 1 FROM capitalization_frequency WHERE name = 'Anual'
);

-- =========================
-- INSERTAR ESTADOS CIVILES
-- =========================

INSERT INTO marital_status (status_name)
SELECT 'SOLTERO'
    WHERE NOT EXISTS (
    SELECT 1 FROM marital_status WHERE status_name = 'SOLTERO'
);

INSERT INTO marital_status (status_name)
SELECT 'CASADO'
    WHERE NOT EXISTS (
    SELECT 1 FROM marital_status WHERE status_name = 'CASADO'
);

INSERT INTO marital_status (status_name)
SELECT 'DIVORCIADO'
    WHERE NOT EXISTS (
    SELECT 1 FROM marital_status WHERE status_name = 'DIVORCIADO'
);

INSERT INTO marital_status (status_name)
SELECT 'VIUDO'
    WHERE NOT EXISTS (
    SELECT 1 FROM marital_status WHERE status_name = 'VIUDO'
);

INSERT INTO marital_status (status_name)
SELECT 'CONVIVIENTE'
    WHERE NOT EXISTS (
    SELECT 1 FROM marital_status WHERE status_name = 'CONVIVIENTE'
);

-- =========================
-- INSERTAR ENTIDADES FINANCIERAS
-- =========================

INSERT INTO financial_entity (name, code, down_payment_percentage, active)
SELECT 'Banco de Crédito del Perú', 'BCP', 10.00, true
    WHERE NOT EXISTS (
    SELECT 1 FROM financial_entity WHERE code = 'BCP'
);

INSERT INTO financial_entity (name, code, down_payment_percentage, active)
SELECT 'BBVA Perú', 'BBVA', 9.00, true
    WHERE NOT EXISTS (
    SELECT 1 FROM financial_entity WHERE code = 'BBVA'
);

INSERT INTO financial_entity (name, code, down_payment_percentage, active)
SELECT 'Interbank', 'INTERBANK', 8.50, true
    WHERE NOT EXISTS (
    SELECT 1 FROM financial_entity WHERE code = 'INTERBANK'
);

INSERT INTO financial_entity (name, code, down_payment_percentage, active)
SELECT 'Scotiabank Perú', 'SCOTIABANK', 11.00, true
    WHERE NOT EXISTS (
    SELECT 1 FROM financial_entity WHERE code = 'SCOTIABANK'
);

INSERT INTO financial_entity (name, code, down_payment_percentage, active)
SELECT 'Banco Pichincha', 'PICHINCHA', 9.50, true
    WHERE NOT EXISTS (
    SELECT 1 FROM financial_entity WHERE code = 'PICHINCHA'
);

INSERT INTO financial_entity (name, code, down_payment_percentage, active)
SELECT 'Caja Arequipa', 'CAJA_AREQUIPA', 7.50, true
    WHERE NOT EXISTS (
    SELECT 1 FROM financial_entity WHERE code = 'CAJA_AREQUIPA'
);

-- =========================
-- INSERTAR MONEDAS
-- =========================

INSERT INTO currency (code, name, symbol)
SELECT 'PEN', 'Sol Peruano', 'S/'
    WHERE NOT EXISTS (
    SELECT 1 FROM currency WHERE code = 'PEN'
);

INSERT INTO currency (code, name, symbol)
SELECT 'USD', 'Dólar Americano', '$'
    WHERE NOT EXISTS (
    SELECT 1 FROM currency WHERE code = 'USD'
);

-- =========================
-- INSERTAR TIPOS DE PROPIEDAD
-- =========================

INSERT INTO property_type (name)
SELECT 'Casa'
    WHERE NOT EXISTS (
    SELECT 1 FROM property_type WHERE name = 'Casa'
);

INSERT INTO property_type (name)
SELECT 'Departamento'
    WHERE NOT EXISTS (
    SELECT 1 FROM property_type WHERE name = 'Departamento'
);

INSERT INTO property_type (name)
SELECT 'Terreno'
    WHERE NOT EXISTS (
    SELECT 1 FROM property_type WHERE name = 'Terreno'
);

INSERT INTO property_type (name)
SELECT 'Local Comercial'
    WHERE NOT EXISTS (
    SELECT 1 FROM property_type WHERE name = 'Local Comercial'
);

INSERT INTO property_type (name)
SELECT 'Oficina'
    WHERE NOT EXISTS (
    SELECT 1 FROM property_type WHERE name = 'Oficina'
);

-- =========================
-- INSERTAR ESTADOS DE PROPIEDAD
-- =========================

INSERT INTO property_status (name)
SELECT 'Disponible'
    WHERE NOT EXISTS (
    SELECT 1 FROM property_status WHERE name = 'Disponible'
);

INSERT INTO property_status (name)
SELECT 'Reservada'
    WHERE NOT EXISTS (
    SELECT 1 FROM property_status WHERE name = 'Reservada'
);

INSERT INTO property_status (name)
SELECT 'Vendida'
    WHERE NOT EXISTS (
    SELECT 1 FROM property_status WHERE name = 'Vendida'
);

INSERT INTO property_status (name)
SELECT 'En Construcción'
    WHERE NOT EXISTS (
    SELECT 1 FROM property_status WHERE name = 'En Construcción'
);

-- =========================
-- INSERTAR USUARIOS DE PRUEBA (ASESORES)
-- =========================

INSERT INTO user_app (names, surnames, email, username, password)
SELECT 'Juan Carlos', 'Pérez García', 'juan.perez@finanzas.com', 'jperez', 'password123'
    WHERE NOT EXISTS (
    SELECT 1 FROM user_app WHERE email = 'juan.perez@finanzas.com'
);

INSERT INTO user_app (names, surnames, email, username, password)
SELECT 'María Elena', 'Rodríguez López', 'maria.rodriguez@finanzas.com', 'mrodriguez', 'password123'
    WHERE NOT EXISTS (
    SELECT 1 FROM user_app WHERE email = 'maria.rodriguez@finanzas.com'
);

INSERT INTO user_app (names, surnames, email, username, password)
SELECT 'Carlos Alberto', 'Sánchez Torres', 'carlos.sanchez@finanzas.com', 'csanchez', 'password123'
    WHERE NOT EXISTS (
    SELECT 1 FROM user_app WHERE email = 'carlos.sanchez@finanzas.com'
);

-- =========================
-- INSERTAR CLIENTES DE PRUEBA
-- =========================

INSERT INTO client (names, surnames, dni, email, phone_number, monthly_income, occupation, marital_status, current_address)
SELECT
    'Roberto',
    'Gómez Martínez',
    '12345678',
    'roberto.gomez@email.com',
    '987654321',
    5000.00,
    'Ingeniero de Sistemas',
    (SELECT marital_status_id FROM marital_status WHERE status_name = 'SOLTERO' LIMIT 1),
    'Av. Larco 1234, Miraflores, Lima'
WHERE NOT EXISTS (
    SELECT 1 FROM client WHERE dni = '12345678'
    );

INSERT INTO client (names, surnames, dni, email, phone_number, monthly_income, occupation, marital_status, current_address)
SELECT
    'Ana María',
    'Fernández Silva',
    '87654321',
    'ana.fernandez@email.com',
    '912345678',
    8000.00,
    'Gerente de Ventas',
    (SELECT marital_status_id FROM marital_status WHERE status_name = 'CASADO' LIMIT 1),
    'Calle Los Pinos 567, San Isidro, Lima'
WHERE NOT EXISTS (
    SELECT 1 FROM client WHERE dni = '87654321'
    );

INSERT INTO client (names, surnames, dni, email, phone_number, monthly_income, occupation, marital_status, current_address)
SELECT
    'Luis Antonio',
    'Torres Ramírez',
    '11223344',
    'luis.torres@email.com',
    '965432187',
    6500.00,
    'Contador Público',
    (SELECT marital_status_id FROM marital_status WHERE status_name = 'DIVORCIADO' LIMIT 1),
    'Jr. Huancavelica 890, Cercado de Lima'
WHERE NOT EXISTS (
    SELECT 1 FROM client WHERE dni = '11223344'
    );

INSERT INTO client (names, surnames, dni, email, phone_number, monthly_income, occupation, marital_status, current_address)
SELECT
    'Patricia Isabel',
    'Vargas Morales',
    '99887766',
    'patricia.vargas@email.com',
    '923456789',
    12000.00,
    'Médico Cirujano',
    (SELECT marital_status_id FROM marital_status WHERE status_name = 'CASADO' LIMIT 1),
    'Av. Javier Prado 2345, San Borja, Lima'
WHERE NOT EXISTS (
    SELECT 1 FROM client WHERE dni = '99887766'
    );

INSERT INTO client (names, surnames, dni, email, phone_number, monthly_income, occupation, marital_status, current_address)
SELECT
    'Miguel Ángel',
    'Castillo Huamán',
    '55443322',
    'miguel.castillo@email.com',
    '934567890',
    4500.00,
    'Profesor Universitario',
    (SELECT marital_status_id FROM marital_status WHERE status_name = 'CONVIVIENTE' LIMIT 1),
    'Av. Universitaria 678, Los Olivos, Lima'
WHERE NOT EXISTS (
    SELECT 1 FROM client WHERE dni = '55443322'
    );

-- =========================
-- INSERTAR PROPIEDADES DE PRUEBA
-- =========================

INSERT INTO property (code, address, area_m2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
SELECT
    'PROP-001',
    'Av. Conquistadores 123, San Isidro',
    120.50,
    3,
    2,
    450000.00,
    (SELECT property_type_id FROM property_type WHERE name = 'Departamento' LIMIT 1),
    (SELECT currency_id FROM currency WHERE code = 'PEN' LIMIT 1),
    (SELECT status_id FROM property_status WHERE name = 'Disponible' LIMIT 1)
WHERE NOT EXISTS (
    SELECT 1 FROM property WHERE code = 'PROP-001'
    );

INSERT INTO property (code, address, area_m2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
SELECT
    'PROP-002',
    'Calle Las Palmeras 456, Surco',
    200.00,
    4,
    3,
    650000.00,
    (SELECT property_type_id FROM property_type WHERE name = 'Casa' LIMIT 1),
    (SELECT currency_id FROM currency WHERE code = 'PEN' LIMIT 1),
    (SELECT status_id FROM property_status WHERE name = 'Disponible' LIMIT 1)
WHERE NOT EXISTS (
    SELECT 1 FROM property WHERE code = 'PROP-002'
    );

INSERT INTO property (code, address, area_m2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
SELECT
    'PROP-003',
    'Av. Benavides 789, Miraflores',
    85.00,
    2,
    1,
    320000.00,
    (SELECT property_type_id FROM property_type WHERE name = 'Departamento' LIMIT 1),
    (SELECT currency_id FROM currency WHERE code = 'PEN' LIMIT 1),
    (SELECT status_id FROM property_status WHERE name = 'Disponible' LIMIT 1)
WHERE NOT EXISTS (
    SELECT 1 FROM property WHERE code = 'PROP-003'
    );

INSERT INTO property (code, address, area_m2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
SELECT
    'PROP-004',
    'Av. Salaverry 234, Jesús María',
    150.00,
    0,
    2,
    280000.00,
    (SELECT property_type_id FROM property_type WHERE name = 'Oficina' LIMIT 1),
    (SELECT currency_id FROM currency WHERE code = 'USD' LIMIT 1),
    (SELECT status_id FROM property_status WHERE name = 'Disponible' LIMIT 1)
WHERE NOT EXISTS (
    SELECT 1 FROM property WHERE code = 'PROP-004'
    );

INSERT INTO property (code, address, area_m2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
SELECT
    'PROP-005',
    'Calle Los Sauces 567, La Molina',
    250.00,
    0,
    0,
    180000.00,
    (SELECT property_type_id FROM property_type WHERE name = 'Terreno' LIMIT 1),
    (SELECT currency_id FROM currency WHERE code = 'PEN' LIMIT 1),
    (SELECT status_id FROM property_status WHERE name = 'Disponible' LIMIT 1)
WHERE NOT EXISTS (
    SELECT 1 FROM property WHERE code = 'PROP-005'
    );

INSERT INTO property (code, address, area_m2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
SELECT
    'PROP-006',
    'Jr. de la Unión 890, Cercado de Lima',
    95.00,
    0,
    1,
    350000.00,
    (SELECT property_type_id FROM property_type WHERE name = 'Local Comercial' LIMIT 1),
    (SELECT currency_id FROM currency WHERE code = 'PEN' LIMIT 1),
    (SELECT status_id FROM property_status WHERE name = 'Reservada' LIMIT 1)
WHERE NOT EXISTS (
    SELECT 1 FROM property WHERE code = 'PROP-006'
    );


INSERT INTO property (code, address, area_m2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
SELECT
    'PROP-007',
    'Av. República de Panamá 1234, Barranco',
    110.00,
    2,
    2,
    420000.00,
    (SELECT property_type_id FROM property_type WHERE name = 'Departamento' LIMIT 1),
    (SELECT currency_id FROM currency WHERE code = 'PEN' LIMIT 1),
    (SELECT status_id FROM property_status WHERE name = 'Disponible' LIMIT 1)
WHERE NOT EXISTS (
    SELECT 1 FROM property WHERE code = 'PROP-007'
    );

INSERT INTO property (code, address, area_m2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
SELECT
    'PROP-008',
    'Calle Recavarren 789, Miraflores',
    180.00,
    3,
    3,
    750000.00,
    (SELECT property_type_id FROM property_type WHERE name = 'Casa' LIMIT 1),
    (SELECT currency_id FROM currency WHERE code = 'USD' LIMIT 1),
    (SELECT status_id FROM property_status WHERE name = 'Disponible' LIMIT 1)
WHERE NOT EXISTS (
    SELECT 1 FROM property WHERE code = 'PROP-008'
    );

INSERT INTO property (code, address, area_m2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
SELECT
    'PROP-009',
    'Av. El Golf 345, San Isidro',
    95.00,
    2,
    2,
    580000.00,
    (SELECT property_type_id FROM property_type WHERE name = 'Departamento' LIMIT 1),
    (SELECT currency_id FROM currency WHERE code = 'PEN' LIMIT 1),
    (SELECT status_id FROM property_status WHERE name = 'En Construcción' LIMIT 1)
WHERE NOT EXISTS (
    SELECT 1 FROM property WHERE code = 'PROP-009'
    );

INSERT INTO property (code, address, area_m2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
SELECT
    'PROP-010',
    'Jr. Washington 678, Cercado de Lima',
    120.00,
    0,
    1,
    400000.00,
    (SELECT property_type_id FROM property_type WHERE name = 'Local Comercial' LIMIT 1),
    (SELECT currency_id FROM currency WHERE code = 'PEN' LIMIT 1),
    (SELECT status_id FROM property_status WHERE name = 'Disponible' LIMIT 1)
WHERE NOT EXISTS (
    SELECT 1 FROM property WHERE code = 'PROP-010'
    );

INSERT INTO property (code, address, area_m2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
SELECT
    'PROP-011',
    'Av. La Encalada 2100, Surco',
    300.00,
    5,
    4,
    950000.00,
    (SELECT property_type_id FROM property_type WHERE name = 'Casa' LIMIT 1),
    (SELECT currency_id FROM currency WHERE code = 'PEN' LIMIT 1),
    (SELECT status_id FROM property_status WHERE name = 'Disponible' LIMIT 1)
WHERE NOT EXISTS (
    SELECT 1 FROM property WHERE code = 'PROP-011'
    );

-- =========================
-- MENSAJES DE CONFIRMACIÓN
-- =========================

DO $$
BEGIN
    RAISE NOTICE '=========================================';
    RAISE NOTICE 'DATOS INICIALES INSERTADOS CORRECTAMENTE';
    RAISE NOTICE '=========================================';
    RAISE NOTICE 'Frecuencias de Capitalización: %', (SELECT COUNT(*) FROM capitalization_frequency);
    RAISE NOTICE 'Estados Civiles: %', (SELECT COUNT(*) FROM marital_status);
    RAISE NOTICE 'Entidades Financieras: %', (SELECT COUNT(*) FROM financial_entity);
    RAISE NOTICE 'Monedas: %', (SELECT COUNT(*) FROM currency);
    RAISE NOTICE 'Tipos de Propiedad: %', (SELECT COUNT(*) FROM property_type);
    RAISE NOTICE 'Estados de Propiedad: %', (SELECT COUNT(*) FROM property_status);
    RAISE NOTICE 'Usuarios (Asesores): %', (SELECT COUNT(*) FROM user_app);
    RAISE NOTICE 'Clientes: %', (SELECT COUNT(*) FROM client);
    RAISE NOTICE 'Propiedades: %', (SELECT COUNT(*) FROM property);
    RAISE NOTICE '=========================================';
END $$;