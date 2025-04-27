package com.example.hospitalapi.patient.infrastructure.elasticsearch.repository;

import com.example.hospitalapi.patient.infrastructure.elasticsearch.document.PatientDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Elasticsearch repository for Patient documents
 */
@Repository
public interface PatientElasticsearchRepository extends ElasticsearchRepository<PatientDocument, String> {
    
    /**
     * Find patients by first name
     */
    List<PatientDocument> findByFirstNameContainingIgnoreCase(String firstName);
    
    /**
     * Find patients by last name
     */
    List<PatientDocument> findByLastNameContainingIgnoreCase(String lastName);
    
    /**
     * Find patients by email
     */
    PatientDocument findByEmail(String email);
    
    /**
     * Find patients by phone number
     */
    PatientDocument findByPhoneNumber(String phoneNumber);
    
    /**
     * Find patients by status
     */
    List<PatientDocument> findByStatus(String status);
    
    /**
     * Find patients by insurance provider
     */
    List<PatientDocument> findByInsuranceProvider(String insuranceProvider);
    
    /**
     * Find patients by insurance expiry date before a given date
     */
    List<PatientDocument> findByInsuranceExpiryDateBefore(LocalDate date);
    
    /**
     * Find patients by preferred language
     */
    List<PatientDocument> findByPreferredLanguage(String preferredLanguage);
    
    /**
     * Find patients by preferred communication method
     */
    List<PatientDocument> findByPreferredCommunicationMethod(String preferredCommunicationMethod);
}