package com.example.hospitalapi.clinicalrecord.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value object representing a clinical record title
 */
@Getter
@EqualsAndHashCode
public class ClinicalRecordTitle {
    private final String value;

    public ClinicalRecordTitle(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.value = value.trim();
    }

    @Override
    public String toString() {
        return value;
    }
}