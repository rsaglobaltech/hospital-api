package com.example.hospitalapi.scheduling.domain.exception;

/**
 * Exception thrown when available slot validation fails
 */
public class AvailableSlotValidationException extends SchedulingException {

    /**
     * Create a new AvailableSlotValidationException with a message
     * @param message the exception message
     */
    public AvailableSlotValidationException(String message) {
        super(message);
    }

    /**
     * Create a new AvailableSlotValidationException with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    public AvailableSlotValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}