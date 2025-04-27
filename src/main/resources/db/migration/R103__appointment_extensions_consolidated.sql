-- Appointment bounded context extensions (consolidated version)
-- This file is kept for reference but is not used in the migration process
-- There is no corresponding individual migration file yet

-- Add appointment type and priority
ALTER TABLE appointments ADD COLUMN appointment_type VARCHAR(50);
ALTER TABLE appointments ADD COLUMN priority VARCHAR(20) DEFAULT 'NORMAL' NOT NULL;

-- Add room and location information
ALTER TABLE appointments ADD COLUMN room_number VARCHAR(20);
ALTER TABLE appointments ADD COLUMN floor VARCHAR(10);
ALTER TABLE appointments ADD COLUMN building VARCHAR(50);
ALTER TABLE appointments ADD COLUMN location_notes TEXT;

-- Add billing information
ALTER TABLE appointments ADD COLUMN billing_status VARCHAR(20) DEFAULT 'PENDING' NOT NULL;
ALTER TABLE appointments ADD COLUMN insurance_claim_number VARCHAR(50);
ALTER TABLE appointments ADD COLUMN cost DECIMAL(10, 2);
ALTER TABLE appointments ADD COLUMN paid_amount DECIMAL(10, 2) DEFAULT 0.00;

-- Add follow-up information
ALTER TABLE appointments ADD COLUMN follow_up_needed BOOLEAN DEFAULT FALSE;
ALTER TABLE appointments ADD COLUMN follow_up_appointment_id VARCHAR(36);
ALTER TABLE appointments ADD COLUMN follow_up_instructions TEXT;

-- Add reminder settings
ALTER TABLE appointments ADD COLUMN reminder_sent BOOLEAN DEFAULT FALSE;
ALTER TABLE appointments ADD COLUMN reminder_time TIMESTAMP;

-- Add audit fields
ALTER TABLE appointments ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;
ALTER TABLE appointments ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;
ALTER TABLE appointments ADD COLUMN created_by VARCHAR(100);
ALTER TABLE appointments ADD COLUMN updated_by VARCHAR(100);
ALTER TABLE appointments ADD COLUMN cancelled_by VARCHAR(100);
ALTER TABLE appointments ADD COLUMN cancelled_at TIMESTAMP;
ALTER TABLE appointments ADD COLUMN cancellation_reason TEXT;

-- Add indexes for better performance
CREATE INDEX idx_appointments_appointment_type ON appointments(appointment_type);
CREATE INDEX idx_appointments_priority ON appointments(priority);
CREATE INDEX idx_appointments_billing_status ON appointments(billing_status);
CREATE INDEX idx_appointments_follow_up_needed ON appointments(follow_up_needed);
CREATE INDEX idx_appointments_follow_up_appointment_id ON appointments(follow_up_appointment_id);

-- Add foreign key for follow-up appointments
ALTER TABLE appointments ADD CONSTRAINT fk_follow_up_appointment 
    FOREIGN KEY (follow_up_appointment_id) REFERENCES appointments(id);
