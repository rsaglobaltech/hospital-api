package com.example.hospitalapi.medicalstaff.domain.valueobject;

import com.example.hospitalapi.shared.domain.valueobject.Id;

import java.util.UUID;

/**
 * Value object representing a qualification ID
 */
public class QualificationId extends Id {
    
    public QualificationId() {
        super();
    }
    
    public QualificationId(UUID value) {
        super(value);
    }
    
    public QualificationId(String value) {
        super(value);
    }
}