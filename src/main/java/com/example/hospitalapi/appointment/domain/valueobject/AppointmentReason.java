package com.example.hospitalapi.appointment.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value object representing the reason for an appointment
 */
@Getter
@EqualsAndHashCode
public class AppointmentReason {
    private final String value;

    public AppointmentReason(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Reason cannot be empty");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}