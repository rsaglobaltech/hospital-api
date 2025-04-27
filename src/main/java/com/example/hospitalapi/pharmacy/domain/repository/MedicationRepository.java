package com.example.hospitalapi.pharmacy.domain.repository;

import com.example.hospitalapi.pharmacy.domain.entity.Medication;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Medication entity
 */
public interface MedicationRepository {
    
    /**
     * Save a medication
     * @param medication the medication to save
     * @return the saved medication
     */
    Medication save(Medication medication);
    
    /**
     * Find a medication by ID
     * @param id the medication ID
     * @return the medication if found
     */
    Optional<Medication> findById(MedicationId id);
    
    /**
     * Find all medications
     * @return list of all medications
     */
    List<Medication> findAll();
    
    /**
     * Delete a medication
     * @param id the medication ID
     */
    void deleteById(MedicationId id);
    
    /**
     * Check if a medication exists
     * @param id the medication ID
     * @return true if the medication exists
     */
    boolean existsById(MedicationId id);
    
    /**
     * Find medications by name (partial match)
     * @param name the name to search for
     * @return list of medications matching the name
     */
    List<Medication> findByNameContaining(String name);
}