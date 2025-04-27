-- Pharmacy bounded context

-- Medications table
CREATE TABLE medications (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    dosage_form VARCHAR(100) NOT NULL,
    strength VARCHAR(100) NOT NULL,
    manufacturer VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    requires_prescription BOOLEAN NOT NULL DEFAULT FALSE
);

-- Batches table
CREATE TABLE batches (
    id VARCHAR(36) PRIMARY KEY,
    medication_id VARCHAR(36) NOT NULL,
    batch_number VARCHAR(100) NOT NULL,
    manufacturing_date DATE NOT NULL,
    expiration_date DATE NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (medication_id) REFERENCES medications(id)
);

-- Prescriptions table
CREATE TABLE prescriptions (
    id VARCHAR(36) PRIMARY KEY,
    patient_id VARCHAR(36) NOT NULL,
    doctor_id VARCHAR(36) NOT NULL,
    issue_date DATE NOT NULL,
    expiration_date DATE NOT NULL,
    dispensed BOOLEAN NOT NULL DEFAULT FALSE,
    dispensed_date DATE,
    notes TEXT,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- Prescription items table
CREATE TABLE prescription_items (
    id SERIAL PRIMARY KEY,
    prescription_id VARCHAR(36) NOT NULL,
    medication_id VARCHAR(36) NOT NULL,
    quantity INT NOT NULL,
    instructions TEXT,
    FOREIGN KEY (prescription_id) REFERENCES prescriptions(id),
    FOREIGN KEY (medication_id) REFERENCES medications(id)
);

-- Dispensations table
CREATE TABLE dispensations (
    id VARCHAR(36) PRIMARY KEY,
    prescription_id VARCHAR(36) NOT NULL,
    patient_id VARCHAR(36) NOT NULL,
    pharmacist_id VARCHAR(36) NOT NULL,
    dispensation_date_time TIMESTAMP NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    notes TEXT,
    FOREIGN KEY (prescription_id) REFERENCES prescriptions(id),
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- Dispensation items table
CREATE TABLE dispensation_items (
    id SERIAL PRIMARY KEY,
    dispensation_id VARCHAR(36) NOT NULL,
    medication_id VARCHAR(36) NOT NULL,
    medication_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    batch_number VARCHAR(100),
    FOREIGN KEY (dispensation_id) REFERENCES dispensations(id),
    FOREIGN KEY (medication_id) REFERENCES medications(id)
);

-- Create indexes
CREATE INDEX idx_medications_name ON medications(name);
CREATE INDEX idx_batches_medication_id ON batches(medication_id);
CREATE INDEX idx_batches_expiration_date ON batches(expiration_date);
CREATE INDEX idx_prescriptions_patient_id ON prescriptions(patient_id);
CREATE INDEX idx_prescriptions_doctor_id ON prescriptions(doctor_id);
CREATE INDEX idx_prescription_items_prescription_id ON prescription_items(prescription_id);
CREATE INDEX idx_prescription_items_medication_id ON prescription_items(medication_id);
CREATE INDEX idx_dispensations_prescription_id ON dispensations(prescription_id);
CREATE INDEX idx_dispensations_patient_id ON dispensations(patient_id);
CREATE INDEX idx_dispensation_items_dispensation_id ON dispensation_items(dispensation_id);
CREATE INDEX idx_dispensation_items_medication_id ON dispensation_items(medication_id);