package com.example.hospitalapi.notification.domain.valueobject;

import com.example.hospitalapi.shared.domain.valueobject.Id;

import java.util.UUID;

/**
 * Value object representing a notification ID
 */
public class NotificationId extends Id {
    
    /**
     * Create a new notification ID with a random UUID
     */
    public NotificationId() {
        super();
    }
    
    /**
     * Create a new notification ID with the specified UUID
     * @param value the UUID
     */
    public NotificationId(UUID value) {
        super(value);
    }
    
    /**
     * Create a new notification ID with the specified UUID string
     * @param value the UUID string
     */
    public NotificationId(String value) {
        super(value);
    }
}