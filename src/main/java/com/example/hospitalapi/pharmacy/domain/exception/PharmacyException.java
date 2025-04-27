package com.example.hospitalapi.pharmacy.domain.exception;

import com.example.hospitalapi.shared.domain.exception.DomainException;

/**
 * Base exception for all pharmacy domain exceptions
 */
public abstract class PharmacyException extends DomainException {
    
    protected PharmacyException(String message) {
        super(message);
    }
    
    protected PharmacyException(String message, Throwable cause) {
        super(message, cause);
    }
}