-- =========================
-- INSERTAR FRECUENCIAS BASE
-- =========================

-- DIARIA (360 días)
INSERT INTO capitalization_frequency (name, periods_per_year)
SELECT 'Diaria', 360
    WHERE NOT EXISTS (
    SELECT 1 FROM capitalization_frequency WHERE name = 'Diaria (360)'
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

INSERT INTO estado_civil (nombre, descripcion)
SELECT 'SOLTERO', 'Persona soltera'
    WHERE NOT EXISTS (
    SELECT 1 FROM estado_civil WHERE nombre = 'SOLTERO'
);

INSERT INTO estado_civil (nombre, descripcion)
SELECT 'CASADO', 'Persona casada'
    WHERE NOT EXISTS (
    SELECT 1 FROM estado_civil WHERE nombre = 'CASADO'
);

INSERT INTO estado_civil (nombre, descripcion)
SELECT 'DIVORCIADO', 'Persona divorciada'
    WHERE NOT EXISTS (
    SELECT 1 FROM estado_civil WHERE nombre = 'DIVORCIADO'
);

INSERT INTO estado_civil (nombre, descripcion)
SELECT 'VIUDO', 'Persona viuda'
    WHERE NOT EXISTS (
    SELECT 1 FROM estado_civil WHERE nombre = 'VIUDO'
);

INSERT INTO estado_civil (nombre, descripcion)
SELECT 'CONVIVIENTE', 'Persona en convivencia'
    WHERE NOT EXISTS (
    SELECT 1 FROM estado_civil WHERE nombre = 'CONVIVIENTE'
);

-- =========================
-- INSERTAR ENTIDADES FINANCIERAS
-- =========================

-- BCP (10% de cuota inicial)
INSERT INTO entidad_financiera (nombre, codigo, porcentaje_cuota_inicial, activo)
SELECT 'Banco de Crédito del Perú', 'BCP', 10.00, true
    WHERE NOT EXISTS (
    SELECT 1 FROM entidad_financiera WHERE codigo = 'BCP'
);

-- BBVA (9% de cuota inicial)
INSERT INTO entidad_financiera (nombre, codigo, porcentaje_cuota_inicial, activo)
SELECT 'BBVA Perú', 'BBVA', 9.00, true
    WHERE NOT EXISTS (
    SELECT 1 FROM entidad_financiera WHERE codigo = 'BBVA'
);

-- INTERBANK (8.5% de cuota inicial)
INSERT INTO entidad_financiera (nombre, codigo, porcentaje_cuota_inicial, activo)
SELECT 'Interbank', 'INTERBANK', 8.50, true
    WHERE NOT EXISTS (
    SELECT 1 FROM entidad_financiera WHERE codigo = 'INTERBANK'
);

-- SCOTIABANK (11% de cuota inicial)
INSERT INTO entidad_financiera (nombre, codigo, porcentaje_cuota_inicial, activo)
SELECT 'Scotiabank Perú', 'SCOTIABANK', 11.00, true
    WHERE NOT EXISTS (
    SELECT 1 FROM entidad_financiera WHERE codigo = 'SCOTIABANK'
);