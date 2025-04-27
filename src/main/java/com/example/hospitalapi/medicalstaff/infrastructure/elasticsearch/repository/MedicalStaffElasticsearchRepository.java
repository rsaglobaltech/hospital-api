package com.example.hospitalapi.medicalstaff.infrastructure.elasticsearch.repository;

import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
import com.example.hospitalapi.medicalstaff.infrastructure.elasticsearch.document.MedicalStaffDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Elasticsearch repository for MedicalStaff documents
 */
@Repository
public interface MedicalStaffElasticsearchRepository extends ElasticsearchRepository<MedicalStaffDocument, String> {
    
    /**
     * Find medical staff by first name
     */
    List<MedicalStaffDocument> findByFirstNameContainingIgnoreCase(String firstName);
    
    /**
     * Find medical staff by last name
     */
    List<MedicalStaffDocument> findByLastNameContainingIgnoreCase(String lastName);
    
    /**
     * Find medical staff by email
     */
    MedicalStaffDocument findByEmail(String email);
    
    /**
     * Find medical staff by specialty
     */
    List<MedicalStaffDocument> findBySpecialty(Specialty specialty);
    
    /**
     * Find active medical staff
     */
    List<MedicalStaffDocument> findByActiveTrue();
    
    /**
     * Find inactive medical staff
     */
    List<MedicalStaffDocument> findByActiveFalse();
    
    /**
     * Find medical staff by department
     */
    List<MedicalStaffDocument> findByDepartment(String department);
    
    /**
     * Find medical staff by position
     */
    List<MedicalStaffDocument> findByPosition(String position);
    
    /**
     * Find medical staff by supervisor ID
     */
    List<MedicalStaffDocument> findBySupervisorId(String supervisorId);
    
    /**
     * Find medical staff who are supervisors
     */
    List<MedicalStaffDocument> findByIsSupervisorTrue();
    
    /**
     * Find medical staff by contract type
     */
    List<MedicalStaffDocument> findByContractType(String contractType);
    
    /**
     * Find medical staff with contracts ending before a given date
     */
    List<MedicalStaffDocument> findByContractEndDateBefore(LocalDate date);
    
    /**
     * Find medical staff with qualifications expiring before a given date
     */
    List<MedicalStaffDocument> findByQualificationsLicenseExpiryDateBefore(LocalDate date);
}