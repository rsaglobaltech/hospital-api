-- Scheduling bounded context
CREATE TABLE available_slots (
    id VARCHAR(36) PRIMARY KEY,
    doctor_id VARCHAR(36) NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    booked BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (doctor_id) REFERENCES medical_staff(id)
);

-- Create indexes for better performance
CREATE INDEX idx_available_slots_doctor_id ON available_slots(doctor_id);
CREATE INDEX idx_available_slots_date ON available_slots(date);
CREATE INDEX idx_available_slots_booked ON available_slots(booked);