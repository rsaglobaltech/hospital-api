package com.example.hospitalapi.patient.domain.repository;

import com.example.hospitalapi.patient.domain.entity.Patient;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Patient entity
 */
public interface PatientRepository {
    
    /**
     * Save a patient
     * @param patient the patient to save
     * @return the saved patient
     */
    Patient save(Patient patient);
    
    /**
     * Find a patient by ID
     * @param id the patient ID
     * @return the patient if found
     */
    Optional<Patient> findById(PatientId id);
    
    /**
     * Find all patients
     * @return list of all patients
     */
    List<Patient> findAll();
    
    /**
     * Delete a patient
     * @param id the patient ID
     */
    void deleteById(PatientId id);
    
    /**
     * Check if a patient exists
     * @param id the patient ID
     * @return true if the patient exists
     */
    boolean existsById(PatientId id);
}