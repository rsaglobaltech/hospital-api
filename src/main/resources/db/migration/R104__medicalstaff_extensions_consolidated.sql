-- MedicalStaff bounded context extensions (consolidated version)
-- This file is kept for reference but is not used in the migration process
-- There is no corresponding individual migration file yet

-- Add professional details
ALTER TABLE medical_staff ADD COLUMN years_of_experience INTEGER;
ALTER TABLE medical_staff ADD COLUMN biography TEXT;
ALTER TABLE medical_staff ADD COLUMN profile_image_url VARCHAR(255);
ALTER TABLE medical_staff ADD COLUMN professional_website VARCHAR(255);

-- Add contact preferences
ALTER TABLE medical_staff ADD COLUMN preferred_contact_method VARCHAR(20);
ALTER TABLE medical_staff ADD COLUMN work_email VARCHAR(255);
ALTER TABLE medical_staff ADD COLUMN work_phone VARCHAR(20);
ALTER TABLE medical_staff ADD COLUMN pager_number VARCHAR(20);

-- Add schedule information
ALTER TABLE medical_staff ADD COLUMN available_days VARCHAR(100);
ALTER TABLE medical_staff ADD COLUMN available_hours VARCHAR(100);
ALTER TABLE medical_staff ADD COLUMN max_appointments_per_day INTEGER;
ALTER TABLE medical_staff ADD COLUMN vacation_days_remaining INTEGER DEFAULT 0;

-- Add department and position information
ALTER TABLE medical_staff ADD COLUMN department VARCHAR(100);
ALTER TABLE medical_staff ADD COLUMN position VARCHAR(100);
ALTER TABLE medical_staff ADD COLUMN supervisor_id VARCHAR(36);
ALTER TABLE medical_staff ADD COLUMN is_supervisor BOOLEAN DEFAULT FALSE;

-- Add salary and compensation information
ALTER TABLE medical_staff ADD COLUMN salary_grade VARCHAR(20);
ALTER TABLE medical_staff ADD COLUMN contract_type VARCHAR(50);
ALTER TABLE medical_staff ADD COLUMN contract_start_date DATE;
ALTER TABLE medical_staff ADD COLUMN contract_end_date DATE;

-- Add audit fields
ALTER TABLE medical_staff ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;
ALTER TABLE medical_staff ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;
ALTER TABLE medical_staff ADD COLUMN created_by VARCHAR(100);
ALTER TABLE medical_staff ADD COLUMN updated_by VARCHAR(100);

-- Add indexes for better performance
CREATE INDEX idx_medical_staff_department ON medical_staff(department);
CREATE INDEX idx_medical_staff_position ON medical_staff(position);
CREATE INDEX idx_medical_staff_supervisor_id ON medical_staff(supervisor_id);
CREATE INDEX idx_medical_staff_contract_end_date ON medical_staff(contract_end_date);

-- Add foreign key for supervisor relationship
ALTER TABLE medical_staff ADD CONSTRAINT fk_supervisor 
    FOREIGN KEY (supervisor_id) REFERENCES medical_staff(id);

-- Add extensions to qualifications table
ALTER TABLE qualifications ADD COLUMN verification_status VARCHAR(20) DEFAULT 'PENDING' NOT NULL;
ALTER TABLE qualifications ADD COLUMN verified_by VARCHAR(100);
ALTER TABLE qualifications ADD COLUMN verified_at TIMESTAMP;
ALTER TABLE qualifications ADD COLUMN verification_notes TEXT;
ALTER TABLE qualifications ADD COLUMN attachment_url VARCHAR(255);
ALTER TABLE qualifications ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;
ALTER TABLE qualifications ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;

-- Add index for verification status
CREATE INDEX idx_qualifications_verification_status ON qualifications(verification_status);
