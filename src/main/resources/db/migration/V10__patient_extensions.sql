-- Patient bounded context extensions

-- Add insurance information to patients
ALTER TABLE patients ADD COLUMN insurance_provider VARCHAR(100);
ALTER TABLE patients ADD COLUMN insurance_policy_number VARCHAR(50);
ALTER TABLE patients ADD COLUMN insurance_expiry_date DATE;

-- Add emergency contact information
ALTER TABLE patients ADD COLUMN emergency_contact_name VARCHAR(255);
ALTER TABLE patients ADD COLUMN emergency_contact_phone VARCHAR(20);
ALTER TABLE patients ADD COLUMN emergency_contact_relationship VARCHAR(50);

-- Add patient preferences
ALTER TABLE patients ADD COLUMN preferred_language VARCHAR(50);
ALTER TABLE patients ADD COLUMN preferred_communication_method VARCHAR(20);
ALTER TABLE patients ADD COLUMN special_needs TEXT;

-- Add patient status
ALTER TABLE patients ADD COLUMN status VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL;

-- Add audit fields
ALTER TABLE patients ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;
ALTER TABLE patients ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;
ALTER TABLE patients ADD COLUMN created_by VARCHAR(100);
ALTER TABLE patients ADD COLUMN updated_by VARCHAR(100);

-- Add indexes for better performance
CREATE INDEX idx_patients_status ON patients(status);
CREATE INDEX idx_patients_insurance_expiry_date ON patients(insurance_expiry_date);
CREATE INDEX idx_patients_preferred_language ON patients(preferred_language);