package com.example.hospitalapi.medicalstaff.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Value object representing the termination date of a medical staff member
 */
@Getter
@EqualsAndHashCode
public class TerminationDate {
    private final LocalDate value;

    public TerminationDate(LocalDate value) {
        if (value == null) {
            throw new IllegalArgumentException("Termination date cannot be null");
        }
        this.value = value;
    }

    public boolean isBefore(HireDate hireDate) {
        return this.value.isBefore(hireDate.getValue());
    }

    public boolean isAfter(HireDate hireDate) {
        return this.value.isAfter(hireDate.getValue());
    }

    public static TerminationDate none() {
        throw new UnsupportedOperationException("TerminationDate cannot be null, use Optional<TerminationDate> instead");
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
