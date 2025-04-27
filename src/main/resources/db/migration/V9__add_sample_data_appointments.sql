-- Sample appointments
INSERT INTO appointments (id, patient_id, doctor_id, start_time, end_time, reason, notes, status)
VALUES 
('55555555-5555-5555-5555-555555555555', '11111111-1111-1111-1111-111111111111', '33333333-3333-3333-3333-333333333333', '2023-06-15 10:00:00', '2023-06-15 11:00:00', 'Annual check-up', 'Patient has a history of high blood pressure', 'SCHEDULED'),
('66666666-6666-6666-6666-666666666666', '22222222-2222-2222-2222-222222222222', '44444444-4444-4444-4444-444444444444', '2023-06-20 14:00:00', '2023-06-20 15:00:00', 'Headache consultation', null, 'SCHEDULED'),
('dddddddd-dddd-dddd-dddd-dddddddddddd', '33333333-3333-3333-3333-444444444444', '55555555-5555-5555-5555-666666666666', '2023-06-25 09:30:00', '2023-06-25 10:30:00', 'Knee pain', 'Patient reports pain after jogging', 'SCHEDULED'),
('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', '44444444-4444-4444-4444-555555555555', '66666666-6666-6666-6666-777777777777', '2023-06-30 13:00:00', '2023-06-30 14:00:00', 'Routine pediatric check-up', 'Growth and development assessment', 'SCHEDULED'),
('ffffffff-ffff-ffff-ffff-ffffffffffff', '11111111-1111-1111-1111-111111111111', '44444444-4444-4444-4444-444444444444', '2023-07-05 11:00:00', '2023-07-05 12:00:00', 'Neurological consultation', 'Follow-up on previous headaches', 'SCHEDULED'),
('00000000-0000-0000-0000-000000000000', '22222222-2222-2222-2222-222222222222', '33333333-3333-3333-3333-333333333333', '2023-07-10 15:30:00', '2023-07-10 16:30:00', 'Heart palpitations', 'Patient reports occasional rapid heartbeat', 'SCHEDULED');