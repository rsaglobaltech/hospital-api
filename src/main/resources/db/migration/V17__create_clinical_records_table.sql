-- Create clinical_records table
CREATE TABLE clinical_records (
    id VARCHAR(36) PRIMARY KEY,
    patient_id VARCHAR(36) NOT NULL,
    doctor_id VARCHAR(36) NOT NULL,
    type VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    attachment_url VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES medical_staff(id)
);

-- Create indexes
CREATE INDEX idx_clinical_records_patient_id ON clinical_records(patient_id);
CREATE INDEX idx_clinical_records_doctor_id ON clinical_records(doctor_id);
CREATE INDEX idx_clinical_records_type ON clinical_records(type);
CREATE INDEX idx_clinical_records_created_at ON clinical_records(created_at);