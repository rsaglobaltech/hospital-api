package com.example.hospitalapi.notification.domain.valueobject;

/**
 * Enum representing the type of recipient for a notification
 */
public enum RecipientType {
    PATIENT("Patient"),
    MEDICAL_STAFF("Medical Staff"),
    USER("User");

    private final String displayName;

    RecipientType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}