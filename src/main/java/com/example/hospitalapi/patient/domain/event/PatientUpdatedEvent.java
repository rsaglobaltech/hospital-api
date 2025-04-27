package com.example.hospitalapi.patient.domain.event;

import com.example.hospitalapi.patient.domain.entity.Patient;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when a patient is updated
 */
@Getter
public class PatientUpdatedEvent extends BaseDomainEvent {
    
    private final Patient patient;
    
    /**
     * Create a new PatientUpdatedEvent
     * @param patient the patient that was updated
     */
    public PatientUpdatedEvent(Patient patient) {
        super(patient.getId().toString());
        this.patient = patient;
    }
}