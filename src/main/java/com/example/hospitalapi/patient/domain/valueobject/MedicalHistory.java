package com.example.hospitalapi.patient.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value object representing a patient's medical history
 */
@Getter
@EqualsAndHashCode
public class MedicalHistory {
    private final String value;

    public MedicalHistory(String value) {
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