package com.example.hospitalapi.scheduling.domain.exception;

import com.example.hospitalapi.shared.domain.exception.DomainException;

/**
 * Base exception for all scheduling-related exceptions
 */
public abstract class SchedulingException extends DomainException {

    /**
     * Create a new scheduling exception with a message
     * @param message the exception message
     */
    protected SchedulingException(String message) {
        super(message);
    }

    /**
     * Create a new scheduling exception with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    protected SchedulingException(String message, Throwable cause) {
        super(message, cause);
    }
}