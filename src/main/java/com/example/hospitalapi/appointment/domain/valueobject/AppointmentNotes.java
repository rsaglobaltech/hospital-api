package com.example.hospitalapi.appointment.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value object representing notes for an appointment
 */
@Getter
@EqualsAndHashCode
public class AppointmentNotes {
    private final String value;

    public AppointmentNotes(String value) {
        this.value = value != null ? value : "";
    }

    public boolean isEmpty() {
        return value.trim().isEmpty();
    }

    @Override
    public String toString() {
        return value;
    }
}