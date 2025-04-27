package com.example.hospitalapi.patient.application.exception;

import com.example.hospitalapi.shared.application.exception.ApplicationException;

/**
 * Base exception for all patient application-related exceptions
 */
public abstract class PatientApplicationException extends ApplicationException {

    /**
     * Create a new patient application exception with a message
     * @param message the exception message
     */
    protected PatientApplicationException(String message) {
        super(message);
    }

    /**
     * Create a new patient application exception with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    protected PatientApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}