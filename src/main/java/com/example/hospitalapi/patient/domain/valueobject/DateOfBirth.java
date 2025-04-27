package com.example.hospitalapi.patient.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;

/**
 * Value object representing a patient's date of birth
 */
@Getter
@EqualsAndHashCode
public class DateOfBirth {
    private final LocalDate value;

    public DateOfBirth(LocalDate value) {
        if (value == null) {
            throw new IllegalArgumentException("Date of birth cannot be null");
        }
        if (value.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future");
        }
        this.value = value;
    }

    /**
     * Calculates the age of the patient based on the date of birth
     * @return the age in years
     */
    public int getAge() {
        return Period.between(value, LocalDate.now()).getYears();
    }

    /**
     * Checks if the patient is an adult (18 years or older)
     * @return true if the patient is an adult, false otherwise
     */
    public boolean isAdult() {
        return getAge() >= 18;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}