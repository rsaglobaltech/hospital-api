package com.example.hospitalapi.pharmacy.domain.repository;

import com.example.hospitalapi.pharmacy.domain.entity.Prescription;
import com.example.hospitalapi.pharmacy.domain.valueobject.PrescriptionId;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Prescription entity
 */
public interface PrescriptionRepository {
    
    /**
     * Save a prescription
     * @param prescription the prescription to save
     * @return the saved prescription
     */
    Prescription save(Prescription prescription);
    
    /**
     * Find a prescription by ID
     * @param id the prescription ID
     * @return the prescription if found
     */
    Optional<Prescription> findById(PrescriptionId id);
    
    /**
     * Find all prescriptions
     * @return list of all prescriptions
     */
    List<Prescription> findAll();
    
    /**
     * Delete a prescription
     * @param id the prescription ID
     */
    void deleteById(PrescriptionId id);
    
    /**
     * Check if a prescription exists
     * @param id the prescription ID
     * @return true if the prescription exists
     */
    boolean existsById(PrescriptionId id);
    
    /**
     * Find prescriptions by patient ID
     * @param patientId the patient ID
     * @return list of prescriptions for the patient
     */
    List<Prescription> findByPatientId(String patientId);
    
    /**
     * Find prescriptions by doctor ID
     * @param doctorId the doctor ID
     * @return list of prescriptions by the doctor
     */
    List<Prescription> findByDoctorId(String doctorId);
    
    /**
     * Find valid (not expired and not dispensed) prescriptions by patient ID
     * @param patientId the patient ID
     * @return list of valid prescriptions for the patient
     */
    List<Prescription> findValidByPatientId(String patientId);
}