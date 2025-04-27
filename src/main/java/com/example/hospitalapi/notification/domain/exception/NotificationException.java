package com.example.hospitalapi.notification.domain.exception;

import com.example.hospitalapi.shared.domain.exception.DomainException;

/**
 * Base exception for all notification-related exceptions
 */
public abstract class NotificationException extends DomainException {

    /**
     * Create a new notification exception with a message
     * @param message the exception message
     */
    protected NotificationException(String message) {
        super(message);
    }

    /**
     * Create a new notification exception with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    protected NotificationException(String message, Throwable cause) {
        super(message, cause);
    }
}