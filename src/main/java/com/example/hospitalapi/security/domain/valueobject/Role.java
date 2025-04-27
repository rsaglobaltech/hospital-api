package com.example.hospitalapi.security.domain.valueobject;

/**
 * Enum representing user roles in the system
 */
public enum Role {
    ROLE_ADMIN("Administrator"),
    ROLE_DOCTOR("Doctor"),
    ROLE_NURSE("Nurse"),
    ROLE_RECEPTIONIST("Receptionist"),
    ROLE_PATIENT("Patient");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Get the Spring Security authority name
     * @return the authority name
     */
    public String getAuthority() {
        return name();
    }
}