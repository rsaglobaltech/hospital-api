package com.example.hospitalapi.notification.domain.valueobject;

/**
 * Enum representing the status of a notification
 */
public enum NotificationStatus {
    PENDING("Pending"),
    SENT("Sent"),
    FAILED("Failed"),
    CANCELLED("Cancelled");

    private final String displayName;

    NotificationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}