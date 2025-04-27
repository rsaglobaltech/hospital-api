package com.example.hospitalapi.appointment.domain.exception;

import com.example.hospitalapi.shared.domain.exception.DomainException;

/**
 * Base exception for all appointment-related exceptions
 */
public abstract class AppointmentException extends DomainException {

    /**
     * Create a new appointment exception with a message
     * @param message the exception message
     */
    protected AppointmentException(String message) {
        super(message);
    }

    /**
     * Create a new appointment exception with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    protected AppointmentException(String message, Throwable cause) {
        super(message, cause);
    }
}