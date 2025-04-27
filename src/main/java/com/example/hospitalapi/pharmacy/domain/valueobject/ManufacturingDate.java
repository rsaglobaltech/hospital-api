package com.example.hospitalapi.pharmacy.domain.valueobject;

import com.example.hospitalapi.pharmacy.domain.exception.PharmacyValidationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Value object representing a manufacturing date for a medication
 */
@Getter
@EqualsAndHashCode
public class ManufacturingDate {
    private final LocalDate value;

    public ManufacturingDate(LocalDate value) {
        if (value == null) {
            throw new PharmacyValidationException("Manufacturing date cannot be null");
        }
        if (value.isAfter(LocalDate.now())) {
            throw new PharmacyValidationException("Manufacturing date cannot be in the future");
        }
        this.value = value;
    }

    public boolean isBefore(LocalDate date) {
        return this.value.isBefore(date);
    }

    public boolean isBefore(ExpirationDate expirationDate) {
        return this.value.isBefore(expirationDate.getValue());
    }

    public boolean isAfter(LocalDate date) {
        return this.value.isAfter(date);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}