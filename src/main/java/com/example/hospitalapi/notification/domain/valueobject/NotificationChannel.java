package com.example.hospitalapi.notification.domain.valueobject;

/**
 * Enum representing the channel through which a notification is sent
 */
public enum NotificationChannel {
    EMAIL("Email"),
    SMS("SMS");

    private final String displayName;

    NotificationChannel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}