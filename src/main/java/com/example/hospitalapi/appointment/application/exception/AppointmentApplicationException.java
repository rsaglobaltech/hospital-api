package com.example.hospitalapi.appointment.application.exception;

import com.example.hospitalapi.shared.application.exception.ApplicationException;

/**
 * Base exception for all appointment application-related exceptions
 */
public abstract class AppointmentApplicationException extends ApplicationException {

    /**
     * Create a new appointment application exception with a message
     * @param message the exception message
     */
    protected AppointmentApplicationException(String message) {
        super(message);
    }

    /**
     * Create a new appointment application exception with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    protected AppointmentApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}