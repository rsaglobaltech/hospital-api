package com.example.hospitalapi.security.domain.valueobject;

import com.example.hospitalapi.shared.domain.valueobject.Id;

import java.util.UUID;

/**
 * Value object representing a user ID
 */
public class UserId extends Id {
    
    /**
     * Create a new user ID with a random UUID
     */
    public UserId() {
        super();
    }
    
    /**
     * Create a new user ID with the specified UUID
     * @param value the UUID
     */
    public UserId(UUID value) {
        super(value);
    }
    
    /**
     * Create a new user ID with the specified UUID string
     * @param value the UUID string
     */
    public UserId(String value) {
        super(value);
    }
}