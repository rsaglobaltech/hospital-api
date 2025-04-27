package com.example.hospitalapi.pharmacy.infrastructure.persistence.repository;

import com.example.hospitalapi.pharmacy.infrastructure.persistence.entity.BatchJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * JPA repository for BatchJpaEntity
 */
@Repository
public interface BatchJpaRepository extends JpaRepository<BatchJpaEntity, String> {
    
    /**
     * Find batches by medication ID
     * @param medicationId the medication ID
     * @return list of batches for the medication
     */
    List<BatchJpaEntity> findByMedicationId(String medicationId);
    
    /**
     * Find batches by batch number
     * @param batchNumber the batch number
     * @return list of batches with the batch number
     */
    List<BatchJpaEntity> findByBatchNumber(String batchNumber);
    
    /**
     * Find batches that expire before a given date
     * @param date the date
     * @return list of batches that expire before the date
     */
    List<BatchJpaEntity> findByExpirationDateBefore(LocalDate date);
    
    /**
     * Find batches with stock (quantity > 0)
     * @param quantity the minimum quantity
     * @return list of batches with quantity greater than the specified value
     */
    List<BatchJpaEntity> findByQuantityGreaterThan(int quantity);
    
    /**
     * Find batches by medication ID with stock (quantity > 0)
     * @param medicationId the medication ID
     * @param quantity the minimum quantity
     * @return list of batches for the medication with quantity greater than the specified value
     */
    List<BatchJpaEntity> findByMedicationIdAndQuantityGreaterThan(String medicationId, int quantity);
}