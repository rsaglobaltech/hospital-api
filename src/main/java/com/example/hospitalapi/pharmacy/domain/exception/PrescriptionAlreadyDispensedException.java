package com.example.hospitalapi.pharmacy.domain.exception;

/**
 * Exception thrown when a prescription has already been dispensed
 */
public class PrescriptionAlreadyDispensedException extends PharmacyException {
    
    public PrescriptionAlreadyDispensedException(String prescriptionId) {
        super("Prescription with ID " + prescriptionId + " has already been dispensed");
    }
}