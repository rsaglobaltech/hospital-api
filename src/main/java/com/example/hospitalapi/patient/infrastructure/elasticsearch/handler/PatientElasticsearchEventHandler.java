package com.example.hospitalapi.patient.infrastructure.elasticsearch.handler;

import com.example.hospitalapi.patient.domain.entity.Patient;
import com.example.hospitalapi.patient.domain.event.PatientCreatedEvent;
import com.example.hospitalapi.patient.domain.event.PatientDeletedEvent;
import com.example.hospitalapi.patient.domain.event.PatientUpdatedEvent;
import com.example.hospitalapi.patient.infrastructure.elasticsearch.document.PatientDocument;
import com.example.hospitalapi.patient.infrastructure.elasticsearch.repository.PatientElasticsearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Event handler for Patient domain events that updates the Elasticsearch index
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PatientElasticsearchEventHandler {
    
    private final PatientElasticsearchRepository patientElasticsearchRepository;
    
    /**
     * Handle PatientCreatedEvent
     * @param event the event to handle
     */
    @EventListener
    public void handle(PatientCreatedEvent event) {
        log.info("Handling PatientCreatedEvent for patient with ID: {}", event.getAggregateId());
        Patient patient = event.getPatient();
        PatientDocument document = mapToDocument(patient);
        patientElasticsearchRepository.save(document);
    }
    
    /**
     * Handle PatientUpdatedEvent
     * @param event the event to handle
     */
    @EventListener
    public void handle(PatientUpdatedEvent event) {
        log.info("Handling PatientUpdatedEvent for patient with ID: {}", event.getAggregateId());
        Patient patient = event.getPatient();
        PatientDocument document = mapToDocument(patient);
        patientElasticsearchRepository.save(document);
    }
    
    /**
     * Handle PatientDeletedEvent
     * @param event the event to handle
     */
    @EventListener
    public void handle(PatientDeletedEvent event) {
        log.info("Handling PatientDeletedEvent for patient with ID: {}", event.getAggregateId());
        patientElasticsearchRepository.deleteById(event.getPatientId().toString());
    }
    
    /**
     * Map a Patient entity to a PatientDocument
     * @param patient the Patient entity to map
     * @return the mapped PatientDocument
     */
    private PatientDocument mapToDocument(Patient patient) {
        return PatientDocument.builder()
                .id(patient.getId().toString())
                .firstName(patient.getName().getFirstName())
                .lastName(patient.getName().getLastName())
                .email(patient.getEmail().toString())
                .phoneNumber(patient.getPhoneNumber().toString())
                .dateOfBirth(patient.getDateOfBirth().getValue())
                .address(patient.getAddress().getValue())
                .medicalHistory(patient.getMedicalHistory().getValue())
                .status("ACTIVE") // Default status
                .build();
    }
}