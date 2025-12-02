-- =========================
-- SCRIPT DE INSERCIÓN DE DATOS
-- Base de Datos: dbFinanzas
-- Las tablas ya son creadas por Hibernate
-- =========================

-- =========================
-- INSERTAR FRECUENCIAS DE CAPITALIZACIÓN
-- =========================

INSERT INTO capitalization_frequency (name, periods_per_year)
VALUES ('Diaria', 360);

INSERT INTO capitalization_frequency (name, periods_per_year)
VALUES ('Semanal', 52);

INSERT INTO capitalization_frequency (name, periods_per_year)
VALUES ('Quincenal', 24);

INSERT INTO capitalization_frequency (name, periods_per_year)
VALUES ('Mensual', 12);

INSERT INTO capitalization_frequency (name, periods_per_year)
VALUES ('Bimestral', 6);

INSERT INTO capitalization_frequency (name, periods_per_year)
VALUES ('Trimestral', 4);

INSERT INTO capitalization_frequency (name, periods_per_year)
VALUES ('Cuatrimestral', 3);

INSERT INTO capitalization_frequency (name, periods_per_year)
VALUES ('Semestral', 2);

INSERT INTO capitalization_frequency (name, periods_per_year)
VALUES ('Anual', 1);

-- =========================
-- INSERTAR ESTADOS CIVILES
-- =========================

INSERT INTO marital_status (status_name)
VALUES ('SOLTERO');

INSERT INTO marital_status (status_name)
VALUES ('CASADO');

INSERT INTO marital_status (status_name)
VALUES ('VIUDO');

-- =========================
-- INSERTAR ENTIDADES FINANCIERAS
-- =========================

INSERT INTO financial_entity (name, code, down_payment_percentage, active)
VALUES ('Banco de Crédito del Perú', 'BCP', 10.00, true);

INSERT INTO financial_entity (name, code, down_payment_percentage, active)
VALUES ('BBVA Perú', 'BBVA', 9.00, true);

INSERT INTO financial_entity (name, code, down_payment_percentage, active)
VALUES ('Interbank', 'INTERBANK', 8.50, true);

INSERT INTO financial_entity (name, code, down_payment_percentage, active)
VALUES ( 'Scotiabank Perú', 'SCOTIABANK', 11.00, true);

INSERT INTO financial_entity (name, code, down_payment_percentage, active)
VALUES ('Banco Pichincha', 'PICHINCHA', 9.50, true);

INSERT INTO financial_entity (name, code, down_payment_percentage, active)
VALUES ( 'Caja Arequipa', 'CAJA_AREQUIPA', 7.50, true);

-- =========================
-- INSERTAR MONEDAS
-- =========================

INSERT INTO currency (code, name, symbol)
VALUES ('PEN', 'Sol Peruano', 'S/');

INSERT INTO currency (code, name, symbol)
VALUES ('USD', 'Dólar Americano', '$');

-- =========================
-- INSERTAR TIPOS DE PROPIEDAD
-- =========================

INSERT INTO property_type (name)
VALUES ('Casa');

INSERT INTO property_type (name)
VALUES ('Departamento');

INSERT INTO property_type (name)
VALUES ('Terreno');

INSERT INTO property_type (name)
VALUES ('Local Comercial');

INSERT INTO property_type (name)
VALUES ('Oficina');

-- =========================
-- INSERTAR ESTADOS DE PROPIEDAD
-- =========================

INSERT INTO property_status (name)
VALUES ('Disponible');

INSERT INTO property_status (name)
VALUES ('Reservada');

INSERT INTO property_status (name)
VALUES ('Vendida');

INSERT INTO property_status (name)
VALUES ('En Construcción');

-- =========================
-- INSERTAR USUARIOS DE PRUEBA (ASESORES)
-- =========================

INSERT INTO user_app (names, surnames, email, username, password)
VALUES ('Juan Carlos', 'Pérez García', 'juan.perez@finanzas.com', 'jperez', 'password123');

INSERT INTO user_app (names, surnames, email, username, password)
VALUES ('María Elena', 'Rodríguez López', 'maria.rodriguez@finanzas.com', 'mrodriguez', 'password123');

INSERT INTO user_app (names, surnames, email, username, password)
VALUES ('Carlos Alberto', 'Sánchez Torres', 'carlos.sanchez@finanzas.com', 'csanchez', 'password123');

-- =========================
-- INSERTAR CLIENTES DE PRUEBA
-- =========================

INSERT INTO client (names, surnames, dni, email, phone_number, monthly_income, occupation, marital_status_id, current_address)
VALUES ('Roberto',
        'Gómez Martínez',
        '12345678',
        'roberto.gomez@email.com',
        '987654321',
        5000.00,
        'Ingeniero de Sistemas',
        1,
        'Av. Larco 1234, Miraflores, Lima');

INSERT INTO client (names, surnames, dni, email, phone_number, monthly_income, occupation, marital_status_id, current_address)
VALUES ('Ana María',
        'Fernández Silva',
        '87654321',
        'ana.fernandez@email.com',
        '912345678',
        8000.00,
        'Gerente de Ventas',
        2,
        'Calle Los Pinos 567, San Isidro, Lima');

INSERT INTO client (names, surnames, dni, email, phone_number, monthly_income, occupation, marital_status_id, current_address)
VALUES ('Luis Antonio',
        'Torres Ramírez',
        '11223344',
        'luis.torres@email.com',
        '965432187',
        6500.00,
        'Contador Público',
        1,
        'Jr. Huancavelica 890, Cercado de Lima');

INSERT INTO client (names, surnames, dni, email, phone_number, monthly_income, occupation, marital_status_id, current_address)
VALUES ('Patricia Isabel',
        'Vargas Morales',
        '99887766',
        'patricia.vargas@email.com',
        '923456789',
        12000.00,
        'Médico Cirujano',
        2,
        'Av. Javier Prado 2345, San Borja, Lima');

INSERT INTO client (names, surnames, dni, email, phone_number, monthly_income, occupation, marital_status_id, current_address)
VALUES ('Miguel Ángel',
        'Castillo Huamán',
        '55443322',
        'miguel.castillo@email.com',
        '934567890',
        4500.00,
        'Profesor Universitario',
        2,
        'Av. Universitaria 678, Los Olivos, Lima');

-- =========================
-- INSERTAR PROPIEDADES DE PRUEBA
-- =========================

INSERT INTO property (code, address, aream2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
VALUES ('PROP-001',
        'Av. Conquistadores 123, San Isidro',
        120.50,
        3,
        2,
        450000.00,
        2,
        1,
        1);

INSERT INTO property (code, address, aream2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
VALUES ('PROP-002',
        'Calle Las Palmeras 456, Surco',
        200.00,
        4,
        3,
        650000.00,
        1,
        1,
        1);

INSERT INTO property (code, address, aream2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
VALUES ('PROP-003',
        'Av. Benavides 789, Miraflores',
        85.00,
        2,
        1,
        320000.00,
        2,
        1,
        1);

INSERT INTO property (code, address, aream2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
VALUES ('PROP-004',
        'Av. Salaverry 234, Jesús María',
        150.00,
        0,
        2,
        280000.00,
        5,
        2,
        1);

INSERT INTO property (code, address, aream2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
VALUES ('PROP-005',
        'Calle Los Sauces 567, La Molina',
        250.00,
        0,
        0,
        180000.00,
        3,
        1,
        1);

INSERT INTO property (code, address, aream2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
VALUES ('PROP-006',
        'Jr. de la Unión 890, Cercado de Lima',
        95.00,
        0,
        1,
        350000.00,
        4,
        1,
        2);

INSERT INTO property (code, address, aream2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
VALUES ('PROP-007',
        'Av. República de Panamá 1234, Barranco',
        110.00,
        2,
        2,
        420000.00,
        2,
        1,
        1);

INSERT INTO property (code, address, aream2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
VALUES ('PROP-008',
        'Calle Recavarren 789, Miraflores',
        180.00,
        3,
        3,
        750000.00,
        1,
        2,
        1);

INSERT INTO property (code, address, aream2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
VALUES ('PROP-009',
        'Av. El Golf 345, San Isidro',
        95.00,
        2,
        2,
        580000.00,
        2,
        1,
        4);

INSERT INTO property (code, address, aream2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
VALUES ('PROP-010',
        'Jr. Washington 678, Cercado de Lima',
        120.00,
        0,
        1,
        400000.00,
        4,
        1,
        1);

INSERT INTO property (code, address, aream2, bedrooms, bathrooms, sale_price, property_type_id, currency_id, status_id)
VALUES('PROP-011',
        'Av. La Encalada 2100, Surco',
        300.00,
        5,
        4,
        950000.00,
       1,
       1,
       1);