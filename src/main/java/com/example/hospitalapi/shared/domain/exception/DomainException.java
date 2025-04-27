package com.example.hospitalapi.shared.domain.exception;

/**
 * Base exception for all domain exceptions in the system
 */
public abstract class DomainException extends RuntimeException {

    /**
     * Create a new domain exception with a message
     * @param message the exception message
     */
    protected DomainException(String message) {
        super(message);
    }

    /**
     * Create a new domain exception with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    protected DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}