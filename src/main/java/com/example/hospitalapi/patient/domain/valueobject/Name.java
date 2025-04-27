package com.example.hospitalapi.patient.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value object representing a person's name
 */
@Getter
@EqualsAndHashCode
public class Name {
    private final String firstName;
    private final String lastName;

    public Name(String firstName, String lastName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }
    
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}