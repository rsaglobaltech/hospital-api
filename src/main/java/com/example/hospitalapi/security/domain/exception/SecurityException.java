package com.example.hospitalapi.security.domain.exception;

import com.example.hospitalapi.shared.domain.exception.DomainException;

/**
 * Base exception for all security-related exceptions
 */
public abstract class SecurityException extends DomainException {

    /**
     * Create a new security exception with a message
     * @param message the exception message
     */
    protected SecurityException(String message) {
        super(message);
    }

    /**
     * Create a new security exception with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    protected SecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}