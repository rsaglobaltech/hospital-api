package com.example.hospitalapi.scheduling.domain.exception;

import com.example.hospitalapi.scheduling.domain.valueobject.AvailableSlotId;

/**
 * Exception thrown when an available slot is not found
 */
public class AvailableSlotNotFoundException extends SchedulingException {

    /**
     * Create a new AvailableSlotNotFoundException with an available slot ID
     * @param availableSlotId the ID of the available slot that was not found
     */
    public AvailableSlotNotFoundException(AvailableSlotId availableSlotId) {
        super("Available slot not found with ID: " + availableSlotId);
    }

    /**
     * Create a new AvailableSlotNotFoundException with a message
     * @param message the exception message
     */
    public AvailableSlotNotFoundException(String message) {
        super(message);
    }
}