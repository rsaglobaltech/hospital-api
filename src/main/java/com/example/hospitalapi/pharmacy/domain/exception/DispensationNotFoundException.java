package com.example.hospitalapi.pharmacy.domain.exception;

/**
 * Exception thrown when a dispensation is not found
 */
public class DispensationNotFoundException extends PharmacyException {
    
    public DispensationNotFoundException(String dispensationId) {
        super("Dispensation with ID " + dispensationId + " not found");
    }
}