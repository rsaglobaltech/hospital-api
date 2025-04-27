package com.example.hospitalapi.pharmacy.domain.valueobject;

import com.example.hospitalapi.pharmacy.domain.exception.PharmacyValidationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value object representing a medication name
 */
@Getter
@EqualsAndHashCode
public class MedicationName {
    private final String value;

    public MedicationName(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new PharmacyValidationException("Medication name cannot be empty");
        }
        this.value = value.trim();
    }

    @Override
    public String toString() {
        return value;
    }
}