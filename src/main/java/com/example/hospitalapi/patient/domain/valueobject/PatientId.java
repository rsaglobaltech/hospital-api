package com.example.hospitalapi.patient.domain.valueobject;

import com.example.hospitalapi.shared.domain.valueobject.Id;

import java.util.UUID;

/**
 * Value object representing a patient's unique identifier
 */
public class PatientId extends Id {
    
    public PatientId() {
        super();
    }
    
    public PatientId(UUID value) {
        super(value);
    }
    
    public PatientId(String value) {
        super(value);
    }
}