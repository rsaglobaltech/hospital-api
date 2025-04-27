package com.example.hospitalapi.patient.domain.exception;

/**
 * Exception thrown when patient validation fails
 */
public class PatientValidationException extends PatientException {

    /**
     * Create a new PatientValidationException with a message
     * @param message the exception message
     */
    public PatientValidationException(String message) {
        super(message);
    }

    /**
     * Create a new PatientValidationException with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    public PatientValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}