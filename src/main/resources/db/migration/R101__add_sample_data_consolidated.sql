-- Sample data for testing purposes (consolidated version)
-- This file is kept for reference but is not used in the migration process
-- The individual migration files (V6__add_sample_data_patients.sql, etc.) are used instead

-- Sample patients
INSERT INTO patients (id, first_name, last_name, email, phone_number, date_of_birth, address, medical_history)
VALUES 
('11111111-1111-1111-1111-111111111111', 'John', 'Doe', 'john.doe@example.com', '+1234567890', '1990-01-01', '123 Main St, City, Country', 'No known allergies'),
('22222222-2222-2222-2222-222222222222', 'Jane', 'Smith', 'jane.smith@example.com', '+9876543210', '1985-05-15', '456 Oak St, Town, Country', 'Allergic to penicillin');

-- Sample medical staff
INSERT INTO medical_staff (id, first_name, last_name, email, phone_number, date_of_birth, address, specialty, active, hire_date, termination_date)
VALUES 
('33333333-3333-3333-3333-333333333333', 'Dr. Robert', 'Johnson', 'robert.johnson@hospital.com', '+1122334455', '1975-03-10', '789 Pine St, City, Country', 'CARDIOLOGY', true, '2010-01-15', null),
('44444444-4444-4444-4444-444444444444', 'Dr. Emily', 'Williams', 'emily.williams@hospital.com', '+5566778899', '1980-07-22', '101 Elm St, Town, Country', 'NEUROLOGY', true, '2015-03-20', null);

-- Sample qualifications
INSERT INTO qualifications (id, degree, institution, date_obtained, license_number, license_expiry_date, staff_id)
VALUES 
('77777777-7777-7777-7777-777777777777', 'MD', 'Harvard Medical School', '2000-05-15', 'ML123456', '2025-05-15', '33333333-3333-3333-3333-333333333333'),
('88888888-8888-8888-8888-888888888888', 'PhD', 'Stanford University', '2005-06-20', 'PL789012', '2026-06-20', '33333333-3333-3333-3333-333333333333'),
('99999999-9999-9999-9999-999999999999', 'MD', 'Johns Hopkins University', '2005-05-10', 'ML654321', '2025-05-10', '44444444-4444-4444-4444-444444444444');

-- Sample appointments
INSERT INTO appointments (id, patient_id, doctor_id, start_time, end_time, reason, notes, status)
VALUES 
('55555555-5555-5555-5555-555555555555', '11111111-1111-1111-1111-111111111111', '33333333-3333-3333-3333-333333333333', '2023-06-15 10:00:00', '2023-06-15 11:00:00', 'Annual check-up', 'Patient has a history of high blood pressure', 'SCHEDULED'),
('66666666-6666-6666-6666-666666666666', '22222222-2222-2222-2222-222222222222', '44444444-4444-4444-4444-444444444444', '2023-06-20 14:00:00', '2023-06-20 15:00:00', 'Headache consultation', null, 'SCHEDULED');
