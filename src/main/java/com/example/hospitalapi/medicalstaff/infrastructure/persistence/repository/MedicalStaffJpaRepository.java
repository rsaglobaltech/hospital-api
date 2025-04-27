package com.example.hospitalapi.medicalstaff.infrastructure.persistence.repository;

import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
import com.example.hospitalapi.medicalstaff.infrastructure.persistence.entity.MedicalStaffJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for MedicalStaffJpaEntity
 */
@Repository
public interface MedicalStaffJpaRepository extends JpaRepository<MedicalStaffJpaEntity, String> {
    
    /**
     * Find medical staff members by specialty
     */
    List<MedicalStaffJpaEntity> findBySpecialty(Specialty specialty);
    
    /**
     * Find active medical staff members
     */
    List<MedicalStaffJpaEntity> findByActiveTrue();
    
    /**
     * Find inactive medical staff members
     */
    List<MedicalStaffJpaEntity> findByActiveFalse();
}