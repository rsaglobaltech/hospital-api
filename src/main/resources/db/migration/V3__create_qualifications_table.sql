-- Qualifications for medical staff
CREATE TABLE qualifications (
    id VARCHAR(36) PRIMARY KEY,
    degree VARCHAR(255) NOT NULL,
    institution VARCHAR(255) NOT NULL,
    date_obtained DATE NOT NULL,
    license_number VARCHAR(50) NOT NULL,
    license_expiry_date DATE NOT NULL,
    staff_id VARCHAR(36) NOT NULL,
    FOREIGN KEY (staff_id) REFERENCES medical_staff(id)
);