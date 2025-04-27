-- Sample data for pharmacy bounded context

-- Sample medications
INSERT INTO medications (id, name, description, dosage_form, strength, manufacturer, price, requires_prescription)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'Amoxicillin', 'Antibiotic used to treat bacterial infections', 'Capsule', '500mg', 'Generic Pharma', 15.99, true),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d480', 'Ibuprofen', 'Nonsteroidal anti-inflammatory drug', 'Tablet', '200mg', 'Pain Relief Inc', 8.99, false),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d481', 'Lisinopril', 'ACE inhibitor used to treat high blood pressure', 'Tablet', '10mg', 'Heart Health Labs', 12.50, true),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d482', 'Loratadine', 'Antihistamine for allergy relief', 'Tablet', '10mg', 'Allergy Care', 9.99, false),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d483', 'Metformin', 'Oral diabetes medicine', 'Tablet', '500mg', 'Diabetes Control', 14.75, true);

-- Sample batches
INSERT INTO batches (id, medication_id, batch_number, manufacturing_date, expiration_date, quantity)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d484', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'AMOX-2023-001', '2023-01-15', '2025-01-15', 500),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d485', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'AMOX-2023-002', '2023-03-10', '2025-03-10', 750),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d486', 'f47ac10b-58cc-4372-a567-0e02b2c3d480', 'IBU-2023-001', '2023-02-20', '2026-02-20', 1000),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d487', 'f47ac10b-58cc-4372-a567-0e02b2c3d481', 'LIS-2023-001', '2023-01-05', '2025-01-05', 300),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d488', 'f47ac10b-58cc-4372-a567-0e02b2c3d482', 'LOR-2023-001', '2023-04-12', '2026-04-12', 800),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d489', 'f47ac10b-58cc-4372-a567-0e02b2c3d483', 'MET-2023-001', '2023-03-25', '2025-03-25', 450);

-- Sample prescriptions (using patient IDs from existing data)
INSERT INTO prescriptions (id, patient_id, doctor_id, issue_date, expiration_date, dispensed, dispensed_date, notes)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d490', '11111111-1111-1111-1111-111111111111', '123e4567-e89b-12d3-a456-426614174010', '2023-05-10', '2023-06-10', true, '2023-05-11', 'Take with food'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d491', '22222222-2222-2222-2222-222222222222', '123e4567-e89b-12d3-a456-426614174011', '2023-05-15', '2023-06-15', true, '2023-05-16', 'Take as directed'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d492', '33333333-3333-3333-3333-444444444444', '123e4567-e89b-12d3-a456-426614174012', '2023-05-20', '2023-06-20', false, null, 'Take before bedtime');

-- Sample prescription items
INSERT INTO prescription_items (prescription_id, medication_id, quantity, instructions)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d490', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 30, 'Take 1 capsule 3 times daily with food'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d490', 'f47ac10b-58cc-4372-a567-0e02b2c3d482', 30, 'Take 1 tablet daily'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d491', 'f47ac10b-58cc-4372-a567-0e02b2c3d481', 60, 'Take 1 tablet twice daily'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d491', 'f47ac10b-58cc-4372-a567-0e02b2c3d483', 60, 'Take 1 tablet twice daily with meals'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d492', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 20, 'Take 1 capsule twice daily with food');

-- Sample dispensations
INSERT INTO dispensations (id, prescription_id, patient_id, pharmacist_id, dispensation_date_time, total_amount, notes)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d493', 'f47ac10b-58cc-4372-a567-0e02b2c3d490', '11111111-1111-1111-1111-111111111111', '123e4567-e89b-12d3-a456-426614174020', '2023-05-11 10:30:00', 25.98, 'Patient counseled on proper use'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d494', 'f47ac10b-58cc-4372-a567-0e02b2c3d491', '22222222-2222-2222-2222-222222222222', '123e4567-e89b-12d3-a456-426614174021', '2023-05-16 14:45:00', 27.25, 'Patient has questions about side effects');

-- Sample dispensation items
INSERT INTO dispensation_items (dispensation_id, medication_id, medication_name, quantity, unit_price, batch_number)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d493', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'Amoxicillin', 30, 15.99, 'AMOX-2023-001'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d493', 'f47ac10b-58cc-4372-a567-0e02b2c3d482', 'Loratadine', 30, 9.99, 'LOR-2023-001'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d494', 'f47ac10b-58cc-4372-a567-0e02b2c3d481', 'Lisinopril', 60, 12.50, 'LIS-2023-001'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d494', 'f47ac10b-58cc-4372-a567-0e02b2c3d483', 'Metformin', 60, 14.75, 'MET-2023-001');
