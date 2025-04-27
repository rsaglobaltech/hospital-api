package com.example.hospitalapi.security.domain.exception;

/**
 * Exception thrown when user validation fails
 */
public class UserValidationException extends SecurityException {

    /**
     * Create a new UserValidationException with a message
     * @param message the exception message
     */
    public UserValidationException(String message) {
        super(message);
    }

    /**
     * Create a new UserValidationException with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    public UserValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}