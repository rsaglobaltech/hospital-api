package com.example.hospitalapi.patient.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value object representing a patient's address
 */
@Getter
@EqualsAndHashCode
public class Address {
    private final String value;

    public Address(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
        this.value = value.trim();
    }

    @Override
    public String toString() {
        return value;
    }
}