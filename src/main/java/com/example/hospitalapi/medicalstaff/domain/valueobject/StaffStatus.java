package com.example.hospitalapi.medicalstaff.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value object representing the status of a medical staff member
 */
@Getter
@EqualsAndHashCode
public class StaffStatus {
    private final boolean active;

    public StaffStatus(boolean active) {
        this.active = active;
    }

    public static StaffStatus active() {
        return new StaffStatus(true);
    }

    public static StaffStatus inactive() {
        return new StaffStatus(false);
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return active ? "Active" : "Inactive";
    }
}