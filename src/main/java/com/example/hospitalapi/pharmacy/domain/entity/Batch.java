package com.example.hospitalapi.pharmacy.domain.entity;

import com.example.hospitalapi.pharmacy.domain.exception.PharmacyValidationException;
import com.example.hospitalapi.pharmacy.domain.valueobject.BatchId;
import com.example.hospitalapi.pharmacy.domain.valueobject.BatchNumber;
import com.example.hospitalapi.pharmacy.domain.valueobject.ExpirationDate;
import com.example.hospitalapi.pharmacy.domain.valueobject.ManufacturingDate;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationQuantity;
import com.example.hospitalapi.shared.domain.entity.AggregateRoot;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Entity representing a batch/lot of medication in the pharmacy system
 */
@Getter
public class Batch extends AggregateRoot {
    private final BatchId id;
    private final MedicationId medicationId;
    private BatchNumber batchNumber;
    private ExpirationDate expirationDate;
    private ManufacturingDate manufacturingDate;
    private MedicationQuantity quantity;

    public Batch(BatchId id, MedicationId medicationId, String batchNumberValue, 
                LocalDate manufacturingDateValue, LocalDate expirationDateValue, int quantityValue) {
        if (id == null) {
            throw new PharmacyValidationException("Batch ID cannot be null");
        }
        if (medicationId == null) {
            throw new PharmacyValidationException("Medication ID cannot be null");
        }
        if (batchNumberValue == null || batchNumberValue.trim().isEmpty()) {
            throw new PharmacyValidationException("Batch number cannot be empty");
        }
        if (manufacturingDateValue == null) {
            throw new PharmacyValidationException("Manufacturing date cannot be null");
        }
        if (expirationDateValue == null) {
            throw new PharmacyValidationException("Expiration date cannot be null");
        }
        if (expirationDateValue.isBefore(manufacturingDateValue)) {
            throw new PharmacyValidationException("Expiration date cannot be before manufacturing date");
        }
        if (quantityValue < 0) {
            throw new PharmacyValidationException("Quantity cannot be negative");
        }

        this.id = id;
        this.medicationId = medicationId;
        this.batchNumber = new BatchNumber(batchNumberValue);
        this.manufacturingDate = new ManufacturingDate(manufacturingDateValue);
        this.expirationDate = new ExpirationDate(expirationDateValue);
        this.quantity = new MedicationQuantity(quantityValue);
    }

    public void updateBatchNumber(String batchNumberValue) {
        if (batchNumberValue == null || batchNumberValue.trim().isEmpty()) {
            throw new PharmacyValidationException("Batch number cannot be empty");
        }
        this.batchNumber = new BatchNumber(batchNumberValue);
    }

    public void updateExpirationDate(LocalDate expirationDateValue) {
        if (expirationDateValue == null) {
            throw new PharmacyValidationException("Expiration date cannot be null");
        }
        ExpirationDate newExpirationDate = new ExpirationDate(expirationDateValue);
        if (newExpirationDate.isBefore(this.manufacturingDate.getValue())) {
            throw new PharmacyValidationException("Expiration date cannot be before manufacturing date");
        }
        this.expirationDate = newExpirationDate;
    }

    public void updateManufacturingDate(LocalDate manufacturingDateValue) {
        if (manufacturingDateValue == null) {
            throw new PharmacyValidationException("Manufacturing date cannot be null");
        }
        ManufacturingDate newManufacturingDate = new ManufacturingDate(manufacturingDateValue);
        if (this.expirationDate.isBefore(newManufacturingDate.getValue())) {
            throw new PharmacyValidationException("Expiration date cannot be before manufacturing date");
        }
        this.manufacturingDate = newManufacturingDate;
    }

    public void updateQuantity(int quantityValue) {
        if (quantityValue < 0) {
            throw new PharmacyValidationException("Quantity cannot be negative");
        }
        this.quantity = new MedicationQuantity(quantityValue);
    }

    public void addQuantity(int quantityToAddValue) {
        if (quantityToAddValue < 0) {
            throw new PharmacyValidationException("Quantity to add cannot be negative");
        }
        MedicationQuantity quantityToAdd = new MedicationQuantity(quantityToAddValue);
        this.quantity = this.quantity.add(quantityToAdd);
    }

    public void removeQuantity(int quantityToRemoveValue) {
        if (quantityToRemoveValue < 0) {
            throw new PharmacyValidationException("Quantity to remove cannot be negative");
        }
        MedicationQuantity quantityToRemove = new MedicationQuantity(quantityToRemoveValue);
        if (this.quantity.isLessThan(quantityToRemove)) {
            throw new PharmacyValidationException("Cannot remove more than available quantity");
        }
        this.quantity = this.quantity.subtract(quantityToRemove);
    }

    public boolean isExpired() {
        return this.expirationDate.isExpired();
    }

    public boolean hasStock() {
        return !this.quantity.isLessThan(new MedicationQuantity(1));
    }
}
