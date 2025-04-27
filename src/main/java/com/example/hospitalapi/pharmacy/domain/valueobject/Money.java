package com.example.hospitalapi.pharmacy.domain.valueobject;

import com.example.hospitalapi.pharmacy.domain.exception.PharmacyValidationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Value object representing a monetary amount
 */
@Getter
@EqualsAndHashCode
public class Money {
    private final BigDecimal value;

    public Money(BigDecimal value) {
        if (value == null) {
            throw new PharmacyValidationException("Money amount cannot be null");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new PharmacyValidationException("Money amount cannot be negative");
        }
        // Ensure the value has exactly 2 decimal places
        this.value = value.setScale(2, RoundingMode.HALF_UP);
    }

    public Money(double value) {
        this(BigDecimal.valueOf(value));
    }

    public Money add(Money other) {
        return new Money(this.value.add(other.value));
    }

    public Money subtract(Money other) {
        BigDecimal result = this.value.subtract(other.value);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new PharmacyValidationException("Result of subtraction would be negative");
        }
        return new Money(result);
    }

    public Money multiply(int quantity) {
        return new Money(this.value.multiply(BigDecimal.valueOf(quantity)));
    }

    public boolean isGreaterThan(Money other) {
        return this.value.compareTo(other.value) > 0;
    }

    public boolean isLessThan(Money other) {
        return this.value.compareTo(other.value) < 0;
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}