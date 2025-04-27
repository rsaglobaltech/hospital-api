package com.example.hospitalapi.pharmacy.domain.valueobject;

import com.example.hospitalapi.shared.domain.valueobject.Id;

import java.util.UUID;

/**
 * Value object representing a prescription's unique identifier
 */
public class PrescriptionId extends Id {
    
    public PrescriptionId() {
        super();
    }
    
    public PrescriptionId(UUID value) {
        super(value);
    }
    
    public PrescriptionId(String value) {
        super(value);
    }
}