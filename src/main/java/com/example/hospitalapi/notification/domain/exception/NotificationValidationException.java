package com.example.hospitalapi.notification.domain.exception;

/**
 * Exception thrown when notification validation fails
 */
public class NotificationValidationException extends NotificationException {

    /**
     * Create a new NotificationValidationException with a message
     * @param message the exception message
     */
    public NotificationValidationException(String message) {
        super(message);
    }

    /**
     * Create a new NotificationValidationException with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    public NotificationValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}