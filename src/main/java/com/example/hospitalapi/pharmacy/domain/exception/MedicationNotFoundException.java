package com.example.hospitalapi.pharmacy.domain.exception;

/**
 * Exception thrown when a medication is not found
 */
public class MedicationNotFoundException extends PharmacyException {
    
    public MedicationNotFoundException(String medicationId) {
        super("Medication with ID " + medicationId + " not found");
    }
}