package com.example.hospitalapi.pharmacy.domain.valueobject;

import com.example.hospitalapi.pharmacy.domain.exception.PharmacyValidationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value object representing a quantity of medication
 */
@Getter
@EqualsAndHashCode
public class MedicationQuantity {
    private final int value;

    public MedicationQuantity(int value) {
        if (value <= 0) {
            throw new PharmacyValidationException("Quantity must be greater than zero");
        }
        this.value = value;
    }

    public MedicationQuantity add(MedicationQuantity other) {
        return new MedicationQuantity(this.value + other.value);
    }

    public MedicationQuantity subtract(MedicationQuantity other) {
        int result = this.value - other.value;
        if (result <= 0) {
            throw new PharmacyValidationException("Result of subtraction would be zero or negative");
        }
        return new MedicationQuantity(result);
    }

    public boolean isGreaterThan(MedicationQuantity other) {
        return this.value > other.value;
    }

    public boolean isLessThan(MedicationQuantity other) {
        return this.value < other.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}