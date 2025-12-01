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
-- INSERTAR ESTADOS CIVILES (Tabla: marital_status)
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
-- INSERTAR ENTIDADES FINANCIERAS (Tabla: financial_entity)
-- =========================

-- BCP (10% de cuota inicial)
INSERT INTO financial_entity (name, code, down_payment_percentage, active)
SELECT 'Banco de Crédito del Perú', 'BCP', 10.00, true
    WHERE NOT EXISTS (
    SELECT 1 FROM financial_entity WHERE code = 'BCP'
);

-- BBVA (9% de cuota inicial)
INSERT INTO financial_entity (name, code, down_payment_percentage, active)
SELECT 'BBVA Perú', 'BBVA', 9.00, true
    WHERE NOT EXISTS (
    SELECT 1 FROM financial_entity WHERE code = 'BBVA'
);

-- INTERBANK (8.5% de cuota inicial)
INSERT INTO financial_entity (name, code, down_payment_percentage, active)
SELECT 'Interbank', 'INTERBANK', 8.50, true
    WHERE NOT EXISTS (
    SELECT 1 FROM financial_entity WHERE code = 'INTERBANK'
);

-- SCOTIABANK (11% de cuota inicial)
INSERT INTO financial_entity (name, code, down_payment_percentage, active)
SELECT 'Scotiabank Perú', 'SCOTIABANK', 11.00, true
    WHERE NOT EXISTS (
    SELECT 1 FROM financial_entity WHERE code = 'SCOTIABANK'
);