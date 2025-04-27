package com.example.hospitalapi.medicalstaff.application.exception;

import com.example.hospitalapi.shared.application.exception.ApplicationException;

/**
 * Base exception for all medical staff application-related exceptions
 */
public abstract class MedicalStaffApplicationException extends ApplicationException {

    /**
     * Create a new medical staff application exception with a message
     * @param message the exception message
     */
    protected MedicalStaffApplicationException(String message) {
        super(message);
    }

    /**
     * Create a new medical staff application exception with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    protected MedicalStaffApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}