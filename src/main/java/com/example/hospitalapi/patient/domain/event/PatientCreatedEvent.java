package com.example.hospitalapi.patient.domain.event;

import com.example.hospitalapi.patient.domain.entity.Patient;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when a patient is created
 */
@Getter
public class PatientCreatedEvent extends BaseDomainEvent {
    
    private final Patient patient;
    
    /**
     * Create a new PatientCreatedEvent
     * @param patient the patient that was created
     */
    public PatientCreatedEvent(Patient patient) {
        super(patient.getId().toString());
        this.patient = patient;
    }
}