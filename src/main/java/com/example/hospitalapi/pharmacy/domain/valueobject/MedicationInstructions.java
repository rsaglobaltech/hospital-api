package com.example.hospitalapi.pharmacy.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value object representing instructions for medication
 */
@Getter
@EqualsAndHashCode
public class MedicationInstructions {
    private final String value;

    public MedicationInstructions(String value) {
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