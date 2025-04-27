package com.example.hospitalapi.pharmacy.infrastructure.persistence.repository;

import com.example.hospitalapi.pharmacy.infrastructure.persistence.entity.PrescriptionItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for PrescriptionItemJpaEntity
 */
@Repository
public interface PrescriptionItemJpaRepository extends JpaRepository<PrescriptionItemJpaEntity, Long> {
    
    /**
     * Find prescription items by prescription ID
     * @param prescriptionId the prescription ID
     * @return list of prescription items for the prescription
     */
    List<PrescriptionItemJpaEntity> findByPrescriptionId(String prescriptionId);
    
    /**
     * Find prescription items by medication ID
     * @param medicationId the medication ID
     * @return list of prescription items for the medication
     */
    List<PrescriptionItemJpaEntity> findByMedicationId(String medicationId);
    
    /**
     * Delete prescription items by prescription ID
     * @param prescriptionId the prescription ID
     */
    void deleteByPrescriptionId(String prescriptionId);
}