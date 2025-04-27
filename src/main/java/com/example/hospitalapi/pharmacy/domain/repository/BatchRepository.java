package com.example.hospitalapi.pharmacy.domain.repository;

import com.example.hospitalapi.pharmacy.domain.entity.Batch;
import com.example.hospitalapi.pharmacy.domain.valueobject.BatchId;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Batch entity
 */
public interface BatchRepository {
    
    /**
     * Save a batch
     * @param batch the batch to save
     * @return the saved batch
     */
    Batch save(Batch batch);
    
    /**
     * Find a batch by ID
     * @param id the batch ID
     * @return the batch if found
     */
    Optional<Batch> findById(BatchId id);
    
    /**
     * Find all batches
     * @return list of all batches
     */
    List<Batch> findAll();
    
    /**
     * Delete a batch
     * @param id the batch ID
     */
    void deleteById(BatchId id);
    
    /**
     * Check if a batch exists
     * @param id the batch ID
     * @return true if the batch exists
     */
    boolean existsById(BatchId id);
    
    /**
     * Find batches by medication ID
     * @param medicationId the medication ID
     * @return list of batches for the medication
     */
    List<Batch> findByMedicationId(MedicationId medicationId);
    
    /**
     * Find batches by batch number
     * @param batchNumber the batch number
     * @return list of batches with the batch number
     */
    List<Batch> findByBatchNumber(String batchNumber);
    
    /**
     * Find batches that expire before a given date
     * @param date the date
     * @return list of batches that expire before the date
     */
    List<Batch> findByExpirationDateBefore(LocalDate date);
    
    /**
     * Find batches with stock (quantity > 0)
     * @return list of batches with stock
     */
    List<Batch> findByQuantityGreaterThan(int quantity);
    
    /**
     * Find batches by medication ID with stock (quantity > 0)
     * @param medicationId the medication ID
     * @return list of batches for the medication with stock
     */
    List<Batch> findByMedicationIdAndQuantityGreaterThan(MedicationId medicationId, int quantity);
}