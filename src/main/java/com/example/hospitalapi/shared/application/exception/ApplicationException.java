package com.example.hospitalapi.shared.application.exception;

import com.example.hospitalapi.shared.domain.exception.DomainException;

/**
 * Base exception for all application-related exceptions
 */
public abstract class ApplicationException extends DomainException {

    /**
     * Create a new application exception with a message
     * @param message the exception message
     */
    protected ApplicationException(String message) {
        super(message);
    }

    /**
     * Create a new application exception with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    protected ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}