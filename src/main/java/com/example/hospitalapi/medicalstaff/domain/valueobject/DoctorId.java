package com.example.hospitalapi.medicalstaff.domain.valueobject;

import com.example.hospitalapi.shared.domain.valueobject.Id;

import java.util.UUID;

/**
 * Value object representing a doctor's unique identifier
 */
public class DoctorId extends Id {
    
    public DoctorId() {
        super();
    }
    
    public DoctorId(UUID value) {
        super(value);
    }
    
    public DoctorId(String value) {
        super(value);
    }
}