package com.example.hospitalapi.patient.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.regex.Pattern;

/**
 * Value object representing a phone number
 */
@Getter
@EqualsAndHashCode
public class PhoneNumber {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{10,15}$");
    
    private final String value;

    public PhoneNumber(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        
        String trimmedValue = value.trim().replaceAll("\\s+", "");
        if (!PHONE_PATTERN.matcher(trimmedValue).matches()) {
            throw new IllegalArgumentException("Invalid phone number format: " + value);
        }
        
        this.value = trimmedValue;
    }
    
    @Override
    public String toString() {
        return value;
    }
}