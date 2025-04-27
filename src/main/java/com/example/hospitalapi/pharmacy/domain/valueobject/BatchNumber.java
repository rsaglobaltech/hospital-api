package com.example.hospitalapi.pharmacy.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value object representing a medication batch number
 */
@Getter
@EqualsAndHashCode
public class BatchNumber {
    private final String value;

    public BatchNumber(String value) {
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