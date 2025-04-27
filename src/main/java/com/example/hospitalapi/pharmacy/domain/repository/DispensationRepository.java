package com.example.hospitalapi.pharmacy.domain.repository;

import com.example.hospitalapi.pharmacy.domain.entity.Dispensation;
import com.example.hospitalapi.pharmacy.domain.valueobject.DispensationId;
import com.example.hospitalapi.pharmacy.domain.valueobject.PrescriptionId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Dispensation entity
 */
public interface DispensationRepository {
    
    /**
     * Save a dispensation
     * @param dispensation the dispensation to save
     * @return the saved dispensation
     */
    Dispensation save(Dispensation dispensation);
    
    /**
     * Find a dispensation by ID
     * @param id the dispensation ID
     * @return the dispensation if found
     */
    Optional<Dispensation> findById(DispensationId id);
    
    /**
     * Find all dispensations
     * @return list of all dispensations
     */
    List<Dispensation> findAll();
    
    /**
     * Delete a dispensation
     * @param id the dispensation ID
     */
    void deleteById(DispensationId id);
    
    /**
     * Check if a dispensation exists
     * @param id the dispensation ID
     * @return true if the dispensation exists
     */
    boolean existsById(DispensationId id);
    
    /**
     * Find dispensations by patient ID
     * @param patientId the patient ID
     * @return list of dispensations for the patient
     */
    List<Dispensation> findByPatientId(String patientId);
    
    /**
     * Find dispensations by prescription ID
     * @param prescriptionId the prescription ID
     * @return list of dispensations for the prescription
     */
    List<Dispensation> findByPrescriptionId(PrescriptionId prescriptionId);
    
    /**
     * Find dispensations by pharmacist ID
     * @param pharmacistId the pharmacist ID
     * @return list of dispensations by the pharmacist
     */
    List<Dispensation> findByPharmacistId(String pharmacistId);
    
    /**
     * Find dispensations by date range
     * @param startDate the start date
     * @param endDate the end date
     * @return list of dispensations in the date range
     */
    List<Dispensation> findByDispensationDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}