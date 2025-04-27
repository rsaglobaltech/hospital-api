package com.example.hospitalapi.pharmacy.domain.entity;

import com.example.hospitalapi.pharmacy.domain.exception.PharmacyValidationException;
import com.example.hospitalapi.pharmacy.domain.valueobject.BatchId;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BatchTest {

    @Test
    void shouldCreateBatch() {
        // Given
        BatchId id = new BatchId();
        MedicationId medicationId = new MedicationId();
        String batchNumber = "BATCH-123";
        LocalDate manufacturingDate = LocalDate.now().minusMonths(1);
        LocalDate expirationDate = LocalDate.now().plusMonths(6);
        int quantity = 100;

        // When
        Batch batch = new Batch(id, medicationId, batchNumber, manufacturingDate, expirationDate, quantity);

        // Then
        assertEquals(id, batch.getId());
        assertEquals(medicationId, batch.getMedicationId());
        assertEquals(batchNumber, batch.getBatchNumber().getValue());
        assertEquals(manufacturingDate, batch.getManufacturingDate().getValue());
        assertEquals(expirationDate, batch.getExpirationDate().getValue());
        assertEquals(quantity, batch.getQuantity().getValue());
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        // Given
        MedicationId medicationId = new MedicationId();
        String batchNumber = "BATCH-123";
        LocalDate manufacturingDate = LocalDate.now().minusMonths(1);
        LocalDate expirationDate = LocalDate.now().plusMonths(6);
        int quantity = 100;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Batch(null, medicationId, batchNumber, manufacturingDate, expirationDate, quantity);
        });
    }

    @Test
    void shouldThrowExceptionWhenMedicationIdIsNull() {
        // Given
        BatchId id = new BatchId();
        String batchNumber = "BATCH-123";
        LocalDate manufacturingDate = LocalDate.now().minusMonths(1);
        LocalDate expirationDate = LocalDate.now().plusMonths(6);
        int quantity = 100;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Batch(id, null, batchNumber, manufacturingDate, expirationDate, quantity);
        });
    }

    @Test
    void shouldThrowExceptionWhenBatchNumberIsNull() {
        // Given
        BatchId id = new BatchId();
        MedicationId medicationId = new MedicationId();
        LocalDate manufacturingDate = LocalDate.now().minusMonths(1);
        LocalDate expirationDate = LocalDate.now().plusMonths(6);
        int quantity = 100;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Batch(id, medicationId, null, manufacturingDate, expirationDate, quantity);
        });
    }

    @Test
    void shouldThrowExceptionWhenBatchNumberIsEmpty() {
        // Given
        BatchId id = new BatchId();
        MedicationId medicationId = new MedicationId();
        String batchNumber = "";
        LocalDate manufacturingDate = LocalDate.now().minusMonths(1);
        LocalDate expirationDate = LocalDate.now().plusMonths(6);
        int quantity = 100;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Batch(id, medicationId, batchNumber, manufacturingDate, expirationDate, quantity);
        });
    }

    @Test
    void shouldThrowExceptionWhenManufacturingDateIsNull() {
        // Given
        BatchId id = new BatchId();
        MedicationId medicationId = new MedicationId();
        String batchNumber = "BATCH-123";
        LocalDate expirationDate = LocalDate.now().plusMonths(6);
        int quantity = 100;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Batch(id, medicationId, batchNumber, null, expirationDate, quantity);
        });
    }

    @Test
    void shouldThrowExceptionWhenExpirationDateIsNull() {
        // Given
        BatchId id = new BatchId();
        MedicationId medicationId = new MedicationId();
        String batchNumber = "BATCH-123";
        LocalDate manufacturingDate = LocalDate.now().minusMonths(1);
        int quantity = 100;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Batch(id, medicationId, batchNumber, manufacturingDate, null, quantity);
        });
    }

    @Test
    void shouldThrowExceptionWhenExpirationDateIsBeforeManufacturingDate() {
        // Given
        BatchId id = new BatchId();
        MedicationId medicationId = new MedicationId();
        String batchNumber = "BATCH-123";
        LocalDate manufacturingDate = LocalDate.now().minusMonths(1);
        LocalDate expirationDate = LocalDate.now().minusMonths(2);
        int quantity = 100;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Batch(id, medicationId, batchNumber, manufacturingDate, expirationDate, quantity);
        });
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsNegative() {
        // Given
        BatchId id = new BatchId();
        MedicationId medicationId = new MedicationId();
        String batchNumber = "BATCH-123";
        LocalDate manufacturingDate = LocalDate.now().minusMonths(1);
        LocalDate expirationDate = LocalDate.now().plusMonths(6);
        int quantity = -1;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Batch(id, medicationId, batchNumber, manufacturingDate, expirationDate, quantity);
        });
    }

    @Test
    void shouldUpdateBatchNumber() {
        // Given
        Batch batch = createValidBatch();
        String newBatchNumber = "NEW-BATCH-456";

        // When
        batch.updateBatchNumber(newBatchNumber);

        // Then
        assertEquals(newBatchNumber, batch.getBatchNumber().getValue());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingBatchNumberToNull() {
        // Given
        Batch batch = createValidBatch();

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            batch.updateBatchNumber(null);
        });
    }

    @Test
    void shouldThrowExceptionWhenUpdatingBatchNumberToEmpty() {
        // Given
        Batch batch = createValidBatch();

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            batch.updateBatchNumber("");
        });
    }

    @Test
    void shouldUpdateExpirationDate() {
        // Given
        Batch batch = createValidBatch();
        LocalDate newExpirationDate = LocalDate.now().plusYears(1);

        // When
        batch.updateExpirationDate(newExpirationDate);

        // Then
        assertEquals(newExpirationDate, batch.getExpirationDate().getValue());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingExpirationDateToNull() {
        // Given
        Batch batch = createValidBatch();

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            batch.updateExpirationDate(null);
        });
    }

    @Test
    void shouldThrowExceptionWhenUpdatingExpirationDateBeforeManufacturingDate() {
        // Given
        Batch batch = createValidBatch();
        LocalDate newExpirationDate = LocalDate.now().minusYears(1);

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            batch.updateExpirationDate(newExpirationDate);
        });
    }

    @Test
    void shouldUpdateManufacturingDate() {
        // Given
        Batch batch = createValidBatch();
        LocalDate newManufacturingDate = LocalDate.now().minusYears(1);

        // When
        batch.updateManufacturingDate(newManufacturingDate);

        // Then
        assertEquals(newManufacturingDate, batch.getManufacturingDate().getValue());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingManufacturingDateToNull() {
        // Given
        Batch batch = createValidBatch();

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            batch.updateManufacturingDate(null);
        });
    }

    @Test
    void shouldThrowExceptionWhenUpdatingManufacturingDateAfterExpirationDate() {
        // Given
        Batch batch = createValidBatch();
        LocalDate newManufacturingDate = LocalDate.now().plusYears(1);

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            batch.updateManufacturingDate(newManufacturingDate);
        });
    }

    @Test
    void shouldUpdateQuantity() {
        // Given
        Batch batch = createValidBatch();
        int newQuantity = 200;

        // When
        batch.updateQuantity(newQuantity);

        // Then
        assertEquals(newQuantity, batch.getQuantity().getValue());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingQuantityToNegative() {
        // Given
        Batch batch = createValidBatch();

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            batch.updateQuantity(-1);
        });
    }

    @Test
    void shouldAddQuantity() {
        // Given
        Batch batch = createValidBatch();
        int initialQuantity = batch.getQuantity().getValue();
        int quantityToAdd = 50;

        // When
        batch.addQuantity(quantityToAdd);

        // Then
        assertEquals(initialQuantity + quantityToAdd, batch.getQuantity().getValue());
    }

    @Test
    void shouldThrowExceptionWhenAddingNegativeQuantity() {
        // Given
        Batch batch = createValidBatch();

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            batch.addQuantity(-1);
        });
    }

    @Test
    void shouldRemoveQuantity() {
        // Given
        Batch batch = createValidBatch();
        int initialQuantity = batch.getQuantity().getValue();
        int quantityToRemove = 50;

        // When
        batch.removeQuantity(quantityToRemove);

        // Then
        assertEquals(initialQuantity - quantityToRemove, batch.getQuantity().getValue());
    }

    @Test
    void shouldThrowExceptionWhenRemovingNegativeQuantity() {
        // Given
        Batch batch = createValidBatch();

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            batch.removeQuantity(-1);
        });
    }

    @Test
    void shouldThrowExceptionWhenRemovingMoreThanAvailableQuantity() {
        // Given
        Batch batch = createValidBatch();
        int initialQuantity = batch.getQuantity().getValue();
        int quantityToRemove = initialQuantity + 1;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            batch.removeQuantity(quantityToRemove);
        });
    }

    @Test
    void shouldReturnTrueWhenBatchIsExpired() {
        // Given
        BatchId id = new BatchId();
        MedicationId medicationId = new MedicationId();
        String batchNumber = "BATCH-123";
        LocalDate manufacturingDate = LocalDate.now().minusMonths(12);
        LocalDate expirationDate = LocalDate.now().minusDays(1);
        int quantity = 100;
        Batch batch = new Batch(id, medicationId, batchNumber, manufacturingDate, expirationDate, quantity);

        // When & Then
        assertTrue(batch.isExpired());
    }

    @Test
    void shouldReturnFalseWhenBatchIsNotExpired() {
        // Given
        Batch batch = createValidBatch();

        // When & Then
        assertFalse(batch.isExpired());
    }

    @Test
    void shouldReturnTrueWhenBatchHasStock() {
        // Given
        Batch batch = createValidBatch();

        // When & Then
        assertTrue(batch.hasStock());
    }

    @Test
    void shouldReturnFalseWhenBatchHasNoStock() {
        // Given
        BatchId id = new BatchId();
        MedicationId medicationId = new MedicationId();
        String batchNumber = "BATCH-123";
        LocalDate manufacturingDate = LocalDate.now().minusMonths(1);
        LocalDate expirationDate = LocalDate.now().plusMonths(6);
        int quantity = 100;
        Batch batch = new Batch(id, medicationId, batchNumber, manufacturingDate, expirationDate, quantity);
        batch.removeQuantity(99);

        // When
        batch.removeQuantity(1);

        // Then
        assertThrows(PharmacyValidationException.class, () -> {
            batch.removeQuantity(1);
        });
    }

    private Batch createValidBatch() {
        BatchId id = new BatchId();
        MedicationId medicationId = new MedicationId();
        String batchNumber = "BATCH-123";
        LocalDate manufacturingDate = LocalDate.now().minusMonths(1);
        LocalDate expirationDate = LocalDate.now().plusMonths(6);
        int quantity = 100;
        return new Batch(id, medicationId, batchNumber, manufacturingDate, expirationDate, quantity);
    }
}