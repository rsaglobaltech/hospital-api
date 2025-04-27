package com.example.hospitalapi.pharmacy.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value object representing notes for a prescription
 */
@Getter
@EqualsAndHashCode
public class PrescriptionNotes {
    private final String value;

    public PrescriptionNotes(String value) {
        this.value = value != null ? value.trim() : "";
    }

    public boolean isEmpty() {
        return value.trim().isEmpty();
    }

    @Override
    public String toString() {
        return value;
    }
}