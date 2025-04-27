package com.example.hospitalapi.pharmacy.infrastructure.persistence.repository;

import com.example.hospitalapi.pharmacy.infrastructure.persistence.entity.DispensationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * JPA repository for DispensationJpaEntity
 */
@Repository
public interface DispensationJpaRepository extends JpaRepository<DispensationJpaEntity, String> {
    
    /**
     * Find dispensations by patient ID
     * @param patientId the patient ID
     * @return list of dispensations for the patient
     */
    List<DispensationJpaEntity> findByPatientId(String patientId);
    
    /**
     * Find dispensations by prescription ID
     * @param prescriptionId the prescription ID
     * @return list of dispensations for the prescription
     */
    List<DispensationJpaEntity> findByPrescriptionId(String prescriptionId);
    
    /**
     * Find dispensations by pharmacist ID
     * @param pharmacistId the pharmacist ID
     * @return list of dispensations by the pharmacist
     */
    List<DispensationJpaEntity> findByPharmacistId(String pharmacistId);
    
    /**
     * Find dispensations by date range
     * @param startDate the start date
     * @param endDate the end date
     * @return list of dispensations in the date range
     */
    List<DispensationJpaEntity> findByDispensationDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}