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
