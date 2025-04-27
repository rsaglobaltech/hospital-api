package com.example.hospitalapi.appointment.domain.exception;

/**
 * Exception thrown when appointment validation fails
 */
public class AppointmentValidationException extends AppointmentException {

    /**
     * Create a new AppointmentValidationException with a message
     * @param message the exception message
     */
    public AppointmentValidationException(String message) {
        super(message);
    }

    /**
     * Create a new AppointmentValidationException with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    public AppointmentValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}