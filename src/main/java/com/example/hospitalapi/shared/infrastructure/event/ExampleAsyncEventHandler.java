package com.example.hospitalapi.shared.infrastructure.event;

import com.example.hospitalapi.patient.domain.event.PatientCreatedEvent;
import com.example.hospitalapi.patient.domain.event.PatientDeletedEvent;
import com.example.hospitalapi.patient.domain.event.PatientUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Example of an asynchronous event handler using the AsyncEventListener annotation.
 * This class demonstrates how to use the AsyncEventListener annotation to create
 * asynchronous event handlers.
 */
@Component
@Slf4j
public class ExampleAsyncEventHandler {

    /**
     * Handle PatientCreatedEvent asynchronously
     * @param event the event to handle
     */
    @AsyncEventListener
    public void handlePatientCreated(PatientCreatedEvent event) {
        log.info("Asynchronously handling PatientCreatedEvent for patient with ID: {}", event.getAggregateId());
        // Perform some async processing here
    }

    /**
     * Handle PatientUpdatedEvent asynchronously
     * @param event the event to handle
     */
    @AsyncEventListener
    public void handlePatientUpdated(PatientUpdatedEvent event) {
        log.info("Asynchronously handling PatientUpdatedEvent for patient with ID: {}", event.getAggregateId());
        // Perform some async processing here
    }

    /**
     * Handle PatientDeletedEvent asynchronously
     * @param event the event to handle
     */
    @AsyncEventListener
    public void handlePatientDeleted(PatientDeletedEvent event) {
        log.info("Asynchronously handling PatientDeletedEvent for patient with ID: {}", event.getAggregateId());
        // Perform some async processing here
    }
}
