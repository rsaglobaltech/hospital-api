package com.example.hospitalapi.medicalstaff.domain.valueobject;

import com.example.hospitalapi.shared.domain.valueobject.Id;

import java.util.UUID;

/**
 * Value object representing a medical staff member's unique identifier
 */
public class StaffId extends Id {
    
    public StaffId() {
        super();
    }
    
    public StaffId(UUID value) {
        super(value);
    }
    
    public StaffId(String value) {
        super(value);
    }
}