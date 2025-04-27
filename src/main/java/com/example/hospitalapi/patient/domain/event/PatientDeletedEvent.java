package com.example.hospitalapi.patient.domain.event;

import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when a patient is deleted
 */
@Getter
public class PatientDeletedEvent extends BaseDomainEvent {
    
    private final PatientId patientId;
    
    /**
     * Create a new PatientDeletedEvent
     * @param patientId the ID of the patient that was deleted
     */
    public PatientDeletedEvent(PatientId patientId) {
        super(patientId.toString());
        this.patientId = patientId;
    }
}