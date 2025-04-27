package com.example.hospitalapi.clinicalrecord.infrastructure.persistence.repository;

import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordType;
import com.example.hospitalapi.clinicalrecord.infrastructure.persistence.entity.ClinicalRecordJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * JPA repository for ClinicalRecord entity
 */
@Repository
public interface ClinicalRecordJpaRepository extends JpaRepository<ClinicalRecordJpaEntity, String> {
    
    /**
     * Find clinical records by patient ID
     * @param patientId the patient ID
     * @return list of clinical records for the patient
     */
    List<ClinicalRecordJpaEntity> findByPatientId(String patientId);
    
    /**
     * Find clinical records by doctor ID
     * @param doctorId the doctor ID
     * @return list of clinical records for the doctor
     */
    List<ClinicalRecordJpaEntity> findByDoctorId(String doctorId);
    
    /**
     * Find clinical records by type
     * @param type the clinical record type
     * @return list of clinical records of the specified type
     */
    List<ClinicalRecordJpaEntity> findByType(ClinicalRecordType type);
    
    /**
     * Find clinical records by patient ID and type
     * @param patientId the patient ID
     * @param type the clinical record type
     * @return list of clinical records for the patient and of the specified type
     */
    List<ClinicalRecordJpaEntity> findByPatientIdAndType(String patientId, ClinicalRecordType type);
    
    /**
     * Find clinical records by patient ID and created date range
     * @param patientId the patient ID
     * @param startDate the start date
     * @param endDate the end date
     * @return list of clinical records for the patient and created within the date range
     */
    List<ClinicalRecordJpaEntity> findByPatientIdAndCreatedAtBetween(String patientId, LocalDateTime startDate, LocalDateTime endDate);
}