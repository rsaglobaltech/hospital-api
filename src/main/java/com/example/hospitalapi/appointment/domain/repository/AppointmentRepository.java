package com.example.hospitalapi.appointment.domain.repository;

import com.example.hospitalapi.appointment.domain.entity.Appointment;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentId;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Appointment entity
 */
public interface AppointmentRepository {
    
    /**
     * Save an appointment
     * @param appointment the appointment to save
     * @return the saved appointment
     */
    Appointment save(Appointment appointment);
    
    /**
     * Find an appointment by ID
     * @param id the appointment ID
     * @return the appointment if found
     */
    Optional<Appointment> findById(AppointmentId id);
    
    /**
     * Find all appointments
     * @return list of all appointments
     */
    List<Appointment> findAll();
    
    /**
     * Find appointments by patient ID
     * @param patientId the patient ID
     * @return list of appointments for the patient
     */
    List<Appointment> findByPatientId(PatientId patientId);
    
    /**
     * Find appointments by doctor ID
     * @param doctorId the doctor ID
     * @return list of appointments for the doctor
     */
    List<Appointment> findByDoctorId(String doctorId);
    
    /**
     * Find appointments by time range
     * @param start the start time
     * @param end the end time
     * @return list of appointments in the time range
     */
    List<Appointment> findByTimeRange(LocalDateTime start, LocalDateTime end);
    
    /**
     * Delete an appointment
     * @param id the appointment ID
     */
    void deleteById(AppointmentId id);
    
    /**
     * Check if an appointment exists
     * @param id the appointment ID
     * @return true if the appointment exists
     */
    boolean existsById(AppointmentId id);
}