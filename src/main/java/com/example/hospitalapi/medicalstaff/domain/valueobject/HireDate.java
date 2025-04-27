package com.example.hospitalapi.medicalstaff.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Value object representing the hire date of a medical staff member
 */
@Getter
@EqualsAndHashCode
public class HireDate {
    private final LocalDate value;

    public HireDate(LocalDate value) {
        if (value == null) {
            throw new IllegalArgumentException("Hire date cannot be null");
        }
        if (value.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Hire date cannot be in the future");
        }
        this.value = value;
    }

    public boolean isBefore(LocalDate date) {
        return this.value.isBefore(date);
    }

    public boolean isAfter(LocalDate date) {
        return this.value.isAfter(date);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}