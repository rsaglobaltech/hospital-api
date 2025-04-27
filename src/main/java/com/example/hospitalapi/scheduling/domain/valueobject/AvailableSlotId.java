package com.example.hospitalapi.scheduling.domain.valueobject;

import com.example.hospitalapi.shared.domain.valueobject.Id;

import java.util.UUID;

/**
 * Value object representing an available slot ID
 */
public class AvailableSlotId extends Id {
    
    /**
     * Create a new available slot ID with a random UUID
     */
    public AvailableSlotId() {
        super();
    }
    
    /**
     * Create a new available slot ID with the specified UUID
     * @param value the UUID
     */
    public AvailableSlotId(UUID value) {
        super(value);
    }
    
    /**
     * Create a new available slot ID with the specified UUID string
     * @param value the UUID string
     */
    public AvailableSlotId(String value) {
        super(value);
    }
}