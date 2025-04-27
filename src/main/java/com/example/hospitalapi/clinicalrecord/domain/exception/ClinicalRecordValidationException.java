package com.example.hospitalapi.clinicalrecord.domain.exception;

/**
 * Exception thrown when a clinical record validation fails
 */
public class ClinicalRecordValidationException extends ClinicalRecordException {

    /**
     * Create a new ClinicalRecordValidationException with the specified message
     * @param message the error message
     */
    public ClinicalRecordValidationException(String message) {
        super(message);
    }

    /**
     * Create a new ClinicalRecordValidationException with the specified message and cause
     * @param message the error message
     * @param cause the cause of the exception
     */
    public ClinicalRecordValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
