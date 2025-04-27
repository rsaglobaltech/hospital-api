package com.example.hospitalapi.pharmacy.infrastructure.persistence.repository;

import com.example.hospitalapi.pharmacy.infrastructure.persistence.entity.DispensationItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for DispensationItemJpaEntity
 */
@Repository
public interface DispensationItemJpaRepository extends JpaRepository<DispensationItemJpaEntity, Long> {
    
    /**
     * Find dispensation items by dispensation ID
     * @param dispensationId the dispensation ID
     * @return list of dispensation items for the dispensation
     */
    List<DispensationItemJpaEntity> findByDispensationId(String dispensationId);
    
    /**
     * Find dispensation items by medication ID
     * @param medicationId the medication ID
     * @return list of dispensation items for the medication
     */
    List<DispensationItemJpaEntity> findByMedicationId(String medicationId);
    
    /**
     * Delete dispensation items by dispensation ID
     * @param dispensationId the dispensation ID
     */
    void deleteByDispensationId(String dispensationId);
}