package com.example.hospitalapi.clinicalrecord.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value object representing a clinical record description
 */
@Getter
@EqualsAndHashCode
public class ClinicalRecordDescription {
    private final String value;

    public ClinicalRecordDescription(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        this.value = value.trim();
    }

    @Override
    public String toString() {
        return value;
    }
}