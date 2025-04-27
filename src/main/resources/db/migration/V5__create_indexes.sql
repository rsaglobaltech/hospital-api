-- Create indexes for better performance
CREATE INDEX idx_appointments_patient_id ON appointments(patient_id);
CREATE INDEX idx_appointments_doctor_id ON appointments(doctor_id);
CREATE INDEX idx_appointments_start_time ON appointments(start_time);
CREATE INDEX idx_qualifications_staff_id ON qualifications(staff_id);
CREATE INDEX idx_medical_staff_specialty ON medical_staff(specialty);
CREATE INDEX idx_medical_staff_active ON medical_staff(active);