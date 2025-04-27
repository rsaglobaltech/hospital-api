package com.example.hospitalapi.medicalstaff.domain.exception;

import com.example.hospitalapi.shared.domain.exception.DomainException;

/**
 * Base exception for all medical staff-related exceptions
 */
public abstract class MedicalStaffException extends DomainException {

    /**
     * Create a new medical staff exception with a message
     * @param message the exception message
     */
    protected MedicalStaffException(String message) {
        super(message);
    }

    /**
     * Create a new medical staff exception with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    protected MedicalStaffException(String message, Throwable cause) {
        super(message, cause);
    }
}