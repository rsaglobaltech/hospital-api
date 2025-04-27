package com.example.hospitalapi.pharmacy.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Value object representing a date in the pharmacy domain
 */
@Getter
@EqualsAndHashCode
public class PrescriptionDate {
    private final LocalDate value;

    public PrescriptionDate(LocalDate value) {
        if (value == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        this.value = value;
    }

    public boolean isBefore(PrescriptionDate other) {
        return this.value.isBefore(other.value);
    }

    public boolean isAfter(PrescriptionDate other) {
        return this.value.isAfter(other.value);
    }

    public static PrescriptionDate now() {
        return new PrescriptionDate(LocalDate.now());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}