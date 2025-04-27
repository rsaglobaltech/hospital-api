package com.example.hospitalapi.patient.infrastructure.persistence.repository;

import com.example.hospitalapi.patient.infrastructure.persistence.entity.PatientJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for PatientJpaEntity
 */
@Repository
public interface PatientJpaRepository extends JpaRepository<PatientJpaEntity, String> {
    // Spring Data JPA will provide the implementation
}