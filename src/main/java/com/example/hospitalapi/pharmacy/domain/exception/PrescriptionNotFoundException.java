package com.example.hospitalapi.pharmacy.domain.exception;

/**
 * Exception thrown when a prescription is not found
 */
public class PrescriptionNotFoundException extends PharmacyException {
    
    public PrescriptionNotFoundException(String prescriptionId) {
        super("Prescription with ID " + prescriptionId + " not found");
    }
}