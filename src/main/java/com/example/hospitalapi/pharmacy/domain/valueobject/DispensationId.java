package com.example.hospitalapi.pharmacy.domain.valueobject;

import com.example.hospitalapi.shared.domain.valueobject.Id;

import java.util.UUID;

/**
 * Value object representing a dispensation's unique identifier
 */
public class DispensationId extends Id {
    
    public DispensationId() {
        super();
    }
    
    public DispensationId(UUID value) {
        super(value);
    }
    
    public DispensationId(String value) {
        super(value);
    }
}