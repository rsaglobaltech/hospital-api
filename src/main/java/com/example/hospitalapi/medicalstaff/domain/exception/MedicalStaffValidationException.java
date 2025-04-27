package com.example.hospitalapi.medicalstaff.domain.exception;

/**
 * Exception thrown when medical staff validation fails
 */
public class MedicalStaffValidationException extends MedicalStaffException {

    /**
     * Create a new MedicalStaffValidationException with a message
     * @param message the exception message
     */
    public MedicalStaffValidationException(String message) {
        super(message);
    }

    /**
     * Create a new MedicalStaffValidationException with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    public MedicalStaffValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}