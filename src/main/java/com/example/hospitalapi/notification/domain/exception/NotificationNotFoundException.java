package com.example.hospitalapi.notification.domain.exception;

import com.example.hospitalapi.notification.domain.valueobject.NotificationId;

/**
 * Exception thrown when a notification is not found
 */
public class NotificationNotFoundException extends NotificationException {

    /**
     * Create a new NotificationNotFoundException with a notification ID
     * @param notificationId the ID of the notification that was not found
     */
    public NotificationNotFoundException(NotificationId notificationId) {
        super("Notification not found with ID: " + notificationId);
    }

    /**
     * Create a new NotificationNotFoundException with a message
     * @param message the exception message
     */
    public NotificationNotFoundException(String message) {
        super(message);
    }
}