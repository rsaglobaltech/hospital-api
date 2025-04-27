package com.example.hospitalapi.clinicalrecord.domain.repository;

import com.example.hospitalapi.clinicalrecord.domain.entity.ClinicalRecord;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordId;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordType;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for ClinicalRecord entity
 */
public interface ClinicalRecordRepository {
    
    /**
     * Save a clinical record
     * @param clinicalRecord the clinical record to save
     * @return the saved clinical record
     */
    ClinicalRecord save(ClinicalRecord clinicalRecord);
    
    /**
     * Find a clinical record by ID
     * @param id the clinical record ID
     * @return the clinical record if found
     */
    Optional<ClinicalRecord> findById(ClinicalRecordId id);
    
    /**
     * Find all clinical records
     * @return list of all clinical records
     */
    List<ClinicalRecord> findAll();
    
    /**
     * Find clinical records by patient ID
     * @param patientId the patient ID
     * @return list of clinical records for the patient
     */
    List<ClinicalRecord> findByPatientId(PatientId patientId);
    
    /**
     * Find clinical records by doctor ID
     * @param doctorId the doctor ID
     * @return list of clinical records for the doctor
     */
    List<ClinicalRecord> findByDoctorId(String doctorId);
    
    /**
     * Find clinical records by type
     * @param type the clinical record type
     * @return list of clinical records of the specified type
     */
    List<ClinicalRecord> findByType(ClinicalRecordType type);
    
    /**
     * Find clinical records by patient ID and type
     * @param patientId the patient ID
     * @param type the clinical record type
     * @return list of clinical records for the patient and of the specified type
     */
    List<ClinicalRecord> findByPatientIdAndType(PatientId patientId, ClinicalRecordType type);
    
    /**
     * Find clinical records by patient ID and created date range
     * @param patientId the patient ID
     * @param startDate the start date
     * @param endDate the end date
     * @return list of clinical records for the patient and created within the date range
     */
    List<ClinicalRecord> findByPatientIdAndCreatedAtBetween(PatientId patientId, LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Delete a clinical record
     * @param id the clinical record ID
     */
    void deleteById(ClinicalRecordId id);
    
    /**
     * Check if a clinical record exists
     * @param id the clinical record ID
     * @return true if the clinical record exists
     */
    boolean existsById(ClinicalRecordId id);
}