package com.example.hospitalapi.pharmacy.domain.valueobject;

import com.example.hospitalapi.shared.domain.valueobject.Id;

import java.util.UUID;

/**
 * Value object representing a medication's unique identifier
 */
public class MedicationId extends Id {
    
    public MedicationId() {
        super();
    }
    
    public MedicationId(UUID value) {
        super(value);
    }
    
    public MedicationId(String value) {
        super(value);
    }
}