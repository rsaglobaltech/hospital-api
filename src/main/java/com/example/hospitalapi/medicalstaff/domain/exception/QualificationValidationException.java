package com.example.hospitalapi.medicalstaff.domain.exception;

/**
 * Exception thrown when qualification validation fails
 */
public class QualificationValidationException extends MedicalStaffException {

    /**
     * Create a new QualificationValidationException with a message
     * @param message the exception message
     */
    public QualificationValidationException(String message) {
        super(message);
    }

    /**
     * Create a new QualificationValidationException with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    public QualificationValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}