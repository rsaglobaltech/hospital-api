-- MedicalStaff bounded context
CREATE TABLE medical_staff (
    id VARCHAR(36) PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL,
    date_of_birth DATE NOT NULL,
    address VARCHAR(255) NOT NULL,
    specialty VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL,
    hire_date DATE NOT NULL,
    termination_date DATE
);