-- Admissions table for analytics bounded context
CREATE TABLE admissions (
    id VARCHAR(36) PRIMARY KEY,
    patient_id VARCHAR(36) NOT NULL,
    admission_date TIMESTAMP NOT NULL,
    discharge_date TIMESTAMP,
    length_of_stay INT,
    department VARCHAR(100) NOT NULL,
    diagnosis_code VARCHAR(50),
    is_readmission BOOLEAN DEFAULT FALSE,
    discharge_disposition VARCHAR(100),
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- Create indexes
CREATE INDEX idx_admissions_patient_id ON admissions(patient_id);
CREATE INDEX idx_admissions_admission_date ON admissions(admission_date);
CREATE INDEX idx_admissions_discharge_date ON admissions(discharge_date);
CREATE INDEX idx_admissions_department ON admissions(department);
CREATE INDEX idx_admissions_is_readmission ON admissions(is_readmission);