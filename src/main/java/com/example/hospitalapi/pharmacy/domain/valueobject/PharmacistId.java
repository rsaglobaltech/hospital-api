package com.example.hospitalapi.pharmacy.domain.valueobject;

import com.example.hospitalapi.shared.domain.valueobject.Id;

import java.util.UUID;

/**
 * Value object representing a pharmacist's unique identifier
 */
public class PharmacistId extends Id {
    
    public PharmacistId() {
        super();
    }
    
    public PharmacistId(UUID value) {
        super(value);
    }
    
    public PharmacistId(String value) {
        super(value);
    }
}