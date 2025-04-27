package com.example.hospitalapi.pharmacy.domain.valueobject;

import com.example.hospitalapi.shared.domain.valueobject.Id;

import java.util.UUID;

/**
 * Value object representing a medication batch's unique identifier
 */
public class BatchId extends Id {
    
    public BatchId() {
        super();
    }
    
    public BatchId(UUID value) {
        super(value);
    }
    
    public BatchId(String value) {
        super(value);
    }
}