package com.example.hospitalapi.pharmacy.domain.entity;

import com.example.hospitalapi.pharmacy.domain.exception.MedicationOutOfStockException;
import com.example.hospitalapi.pharmacy.domain.exception.PharmacyValidationException;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entity representing the pharmacy inventory
 */
@Getter
public class Inventory {
    private final MedicationId medicationId;
    private List<Batch> batches;

    public Inventory(MedicationId medicationId, List<Batch> batches) {
        if (medicationId == null) {
            throw new PharmacyValidationException("Medication ID cannot be null");
        }

        this.medicationId = medicationId;
        this.batches = batches != null ? new ArrayList<>(batches) : new ArrayList<>();
    }

    public List<Batch> getBatches() {
        return Collections.unmodifiableList(batches);
    }

    public void addBatch(Batch batch) {
        if (batch == null) {
            throw new PharmacyValidationException("Batch cannot be null");
        }
        if (!batch.getMedicationId().equals(this.medicationId)) {
            throw new PharmacyValidationException("Batch medication ID does not match inventory medication ID");
        }

        this.batches.add(batch);
    }

    public int getTotalQuantity() {
        return batches.stream()
                .mapToInt(batch -> batch.getQuantity().getValue())
                .sum();
    }

    public int getAvailableQuantity() {
        return batches.stream()
                .filter(batch -> !batch.isExpired())
                .mapToInt(batch -> batch.getQuantity().getValue())
                .sum();
    }

    public boolean hasStock() {
        return getAvailableQuantity() > 0;
    }

    public boolean hasStock(int requiredQuantity) {
        return getAvailableQuantity() >= requiredQuantity;
    }

    public List<Batch> getExpiredBatches() {
        return batches.stream()
                .filter(Batch::isExpired)
                .collect(Collectors.toList());
    }

    public List<Batch> getNonExpiredBatches() {
        return batches.stream()
                .filter(batch -> !batch.isExpired())
                .collect(Collectors.toList());
    }

    public void removeStock(int quantity) {
        if (quantity <= 0) {
            throw new PharmacyValidationException("Quantity to remove must be greater than zero");
        }

        if (!hasStock(quantity)) {
            throw new MedicationOutOfStockException(
                    medicationId.toString(), quantity, getAvailableQuantity());
        }

        int remainingQuantity = quantity;
        List<Batch> nonExpiredBatches = getNonExpiredBatches();

        // Sort batches by expiration date (oldest first)
        nonExpiredBatches.sort((b1, b2) -> b1.getExpirationDate().getValue().compareTo(b2.getExpirationDate().getValue()));

        for (Batch batch : nonExpiredBatches) {
            if (remainingQuantity <= 0) {
                break;
            }

            int quantityToRemove = Math.min(batch.getQuantity().getValue(), remainingQuantity);
            batch.removeQuantity(quantityToRemove);
            remainingQuantity -= quantityToRemove;
        }
    }
}
