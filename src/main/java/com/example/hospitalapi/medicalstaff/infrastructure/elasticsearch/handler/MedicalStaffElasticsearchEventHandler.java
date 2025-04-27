package com.example.hospitalapi.medicalstaff.infrastructure.elasticsearch.handler;

import com.example.hospitalapi.medicalstaff.domain.entity.MedicalStaff;
import com.example.hospitalapi.medicalstaff.domain.event.MedicalStaffCreatedEvent;
import com.example.hospitalapi.medicalstaff.domain.event.MedicalStaffDeletedEvent;
import com.example.hospitalapi.medicalstaff.domain.event.MedicalStaffUpdatedEvent;
import com.example.hospitalapi.medicalstaff.domain.valueobject.Qualification;
import com.example.hospitalapi.medicalstaff.infrastructure.elasticsearch.document.MedicalStaffDocument;
import com.example.hospitalapi.medicalstaff.infrastructure.elasticsearch.document.QualificationDocument;
import com.example.hospitalapi.medicalstaff.infrastructure.elasticsearch.repository.MedicalStaffElasticsearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Event handler for MedicalStaff domain events that updates the Elasticsearch index
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MedicalStaffElasticsearchEventHandler {
    
    private final MedicalStaffElasticsearchRepository medicalStaffElasticsearchRepository;
    
    /**
     * Handle MedicalStaffCreatedEvent
     * @param event the event to handle
     */
    @EventListener
    public void handle(MedicalStaffCreatedEvent event) {
        log.info("Handling MedicalStaffCreatedEvent for staff with ID: {}", event.getAggregateId());
        MedicalStaff medicalStaff = event.getMedicalStaff();
        MedicalStaffDocument document = mapToDocument(medicalStaff);
        medicalStaffElasticsearchRepository.save(document);
    }
    
    /**
     * Handle MedicalStaffUpdatedEvent
     * @param event the event to handle
     */
    @EventListener
    public void handle(MedicalStaffUpdatedEvent event) {
        log.info("Handling MedicalStaffUpdatedEvent for staff with ID: {}", event.getAggregateId());
        MedicalStaff medicalStaff = event.getMedicalStaff();
        MedicalStaffDocument document = mapToDocument(medicalStaff);
        medicalStaffElasticsearchRepository.save(document);
    }
    
    /**
     * Handle MedicalStaffDeletedEvent
     * @param event the event to handle
     */
    @EventListener
    public void handle(MedicalStaffDeletedEvent event) {
        log.info("Handling MedicalStaffDeletedEvent for staff with ID: {}", event.getAggregateId());
        medicalStaffElasticsearchRepository.deleteById(event.getStaffId().toString());
    }
    
    /**
     * Map a MedicalStaff entity to a MedicalStaffDocument
     * @param medicalStaff the MedicalStaff entity to map
     * @return the mapped MedicalStaffDocument
     */
    private MedicalStaffDocument mapToDocument(MedicalStaff medicalStaff) {
        List<QualificationDocument> qualificationDocuments = mapQualifications(medicalStaff.getQualifications());
        
        return MedicalStaffDocument.builder()
                .id(medicalStaff.getId().getValue().toString())
                .firstName(medicalStaff.getName().getFirstName())
                .lastName(medicalStaff.getName().getLastName())
                .email(medicalStaff.getEmail().toString())
                .phoneNumber(medicalStaff.getPhoneNumber().getValue())
                .dateOfBirth(medicalStaff.getDateOfBirth().getValue())
                .address(medicalStaff.getAddress().getValue())
                .specialty(medicalStaff.getSpecialty())
                .qualifications(qualificationDocuments)
                .active(medicalStaff.isActive())
                .hireDate(medicalStaff.getHireDate().getValue())
                .terminationDate(medicalStaff.getTerminationDate().orElseThrow().getValue())
                .build();
    }
    
    /**
     * Map a list of Qualification entities to a list of QualificationDocument objects
     * @param qualifications the list of Qualification entities to map
     * @return the mapped list of QualificationDocument objects
     */
    private List<QualificationDocument> mapQualifications(List<Qualification> qualifications) {
        return qualifications.stream()
                .map(this::mapQualification)
                .collect(Collectors.toList());
    }
    
    /**
     * Map a Qualification entity to a QualificationDocument
     * @param qualification the Qualification entity to map
     * @return the mapped QualificationDocument
     */
    private QualificationDocument mapQualification(Qualification qualification) {
        return QualificationDocument.builder()
                .degree(qualification.getDegree())
                .institution(qualification.getInstitution())
                .dateObtained(qualification.getDateObtained())
                .licenseNumber(qualification.getLicenseNumber())
                .licenseExpiryDate(qualification.getLicenseExpiryDate())
                .build();
    }
}