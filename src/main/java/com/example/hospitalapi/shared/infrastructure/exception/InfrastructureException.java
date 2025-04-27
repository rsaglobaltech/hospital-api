package com.example.hospitalapi.shared.infrastructure.exception;

import com.example.hospitalapi.shared.domain.exception.DomainException;

/**
 * Base exception for all infrastructure-related exceptions
 */
public abstract class InfrastructureException extends DomainException {

    /**
     * Create a new infrastructure exception with a message
     * @param message the exception message
     */
    protected InfrastructureException(String message) {
        super(message);
    }

    /**
     * Create a new infrastructure exception with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    protected InfrastructureException(String message, Throwable cause) {
        super(message, cause);
    }
}