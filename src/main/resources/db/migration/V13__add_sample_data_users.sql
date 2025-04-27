-- Sample users
-- Note: Passwords are BCrypt encoded, all passwords are 'password'
INSERT INTO users (id, username, password, email, enabled)
VALUES 
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'admin', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'admin@hospital.com', true),
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'doctor', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'doctor@hospital.com', true),
('cccccccc-cccc-cccc-cccc-cccccccccccc', 'nurse', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'nurse@hospital.com', true),
('dddddddd-dddd-dddd-dddd-dddddddddddd', 'receptionist', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'receptionist@hospital.com', true),
('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'patient', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'patient@example.com', true);

-- Sample user roles
INSERT INTO user_roles (user_id, role)
VALUES 
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'ROLE_ADMIN'),
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'ROLE_DOCTOR'),
('cccccccc-cccc-cccc-cccc-cccccccccccc', 'ROLE_NURSE'),
('dddddddd-dddd-dddd-dddd-dddddddddddd', 'ROLE_RECEPTIONIST'),
('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'ROLE_PATIENT');

-- Admin also has all other roles
INSERT INTO user_roles (user_id, role)
VALUES 
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'ROLE_DOCTOR'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'ROLE_NURSE'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'ROLE_RECEPTIONIST'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'ROLE_PATIENT');