package com.example.hospitalapi.pharmacy.domain.exception;

/**
 * Exception thrown when validation fails in the pharmacy domain
 */
public class PharmacyValidationException extends PharmacyException {
    
    public PharmacyValidationException(String message) {
        super(message);
    }
    
    public PharmacyValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}