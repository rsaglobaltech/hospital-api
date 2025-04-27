package com.example.hospitalapi.pharmacy.domain.valueobject;

import com.example.hospitalapi.pharmacy.domain.exception.PharmacyValidationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Value object representing an expiration date for a medication
 */
@Getter
@EqualsAndHashCode
public class ExpirationDate {
    private final LocalDate value;

    public ExpirationDate(LocalDate value) {
        if (value == null) {
            throw new PharmacyValidationException("Expiration date cannot be null");
        }
        this.value = value;
    }

    public boolean isBefore(LocalDate date) {
        return this.value.isBefore(date);
    }

    public boolean isBefore(ManufacturingDate manufacturingDate) {
        return this.value.isBefore(manufacturingDate.getValue());
    }

    public boolean isAfter(LocalDate date) {
        return this.value.isAfter(date);
    }

    public boolean isAfter(ManufacturingDate manufacturingDate) {
        return this.value.isAfter(manufacturingDate.getValue());
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}