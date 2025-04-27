-- Sample data for inventory table
INSERT INTO inventory (medication_id, total_quantity, available_quantity, low_stock_threshold)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 1250, 1250, 200),  -- Amoxicillin
    ('f47ac10b-58cc-4372-a567-0e02b2c3d480', 1000, 1000, 150),  -- Ibuprofen
    ('f47ac10b-58cc-4372-a567-0e02b2c3d481', 300, 300, 50),     -- Lisinopril
    ('f47ac10b-58cc-4372-a567-0e02b2c3d482', 800, 800, 100),    -- Loratadine
    ('f47ac10b-58cc-4372-a567-0e02b2c3d483', 450, 450, 75);     -- Metformin