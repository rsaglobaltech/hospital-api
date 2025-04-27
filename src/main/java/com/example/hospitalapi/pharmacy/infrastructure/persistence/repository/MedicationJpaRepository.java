package com.example.hospitalapi.pharmacy.infrastructure.persistence.repository;

import com.example.hospitalapi.pharmacy.infrastructure.persistence.entity.MedicationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for MedicationJpaEntity
 */
@Repository
public interface MedicationJpaRepository extends JpaRepository<MedicationJpaEntity, String> {
    
    /**
     * Find medications by name containing the given string (case-insensitive)
     * @param name the name to search for
     * @return list of medications matching the name
     */
    List<MedicationJpaEntity> findByNameContainingIgnoreCase(String name);
}