package com.example.hospitalapi.patient.domain.exception;

import com.example.hospitalapi.shared.domain.exception.DomainException;

/**
 * Base exception for all patient-related exceptions
 */
public abstract class PatientException extends DomainException {

    /**
     * Create a new patient exception with a message
     * @param message the exception message
     */
    protected PatientException(String message) {
        super(message);
    }

    /**
     * Create a new patient exception with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    protected PatientException(String message, Throwable cause) {
        super(message, cause);
    }
}