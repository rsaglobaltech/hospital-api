package com.example.hospitalapi.pharmacy.infrastructure.persistence.repository;

import com.example.hospitalapi.pharmacy.infrastructure.persistence.entity.PrescriptionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for PrescriptionJpaEntity
 */
@Repository
public interface PrescriptionJpaRepository extends JpaRepository<PrescriptionJpaEntity, String> {
    
    /**
     * Find prescriptions by patient ID
     * @param patientId the patient ID
     * @return list of prescriptions for the patient
     */
    List<PrescriptionJpaEntity> findByPatientId(String patientId);
    
    /**
     * Find prescriptions by doctor ID
     * @param doctorId the doctor ID
     * @return list of prescriptions by the doctor
     */
    List<PrescriptionJpaEntity> findByDoctorId(String doctorId);
    
    /**
     * Find valid (not dispensed) prescriptions by patient ID
     * @param patientId the patient ID
     * @param dispensed whether the prescription is dispensed
     * @return list of prescriptions for the patient with the given dispensed status
     */
    List<PrescriptionJpaEntity> findByPatientIdAndDispensed(String patientId, boolean dispensed);
}