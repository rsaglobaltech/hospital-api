package com.example.hospitalapi.pharmacy.domain.exception;

/**
 * Exception thrown when a medication is out of stock
 */
public class MedicationOutOfStockException extends PharmacyException {
    
    public MedicationOutOfStockException(String medicationId) {
        super("Medication with ID " + medicationId + " is out of stock");
    }
    
    public MedicationOutOfStockException(String medicationId, int requestedQuantity, int availableQuantity) {
        super("Insufficient stock for medication with ID " + medicationId + 
              ". Requested: " + requestedQuantity + ", Available: " + availableQuantity);
    }
}