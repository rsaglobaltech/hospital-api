package com.example.hospitalapi.pharmacy.domain.entity;

import com.example.hospitalapi.pharmacy.domain.exception.MedicationOutOfStockException;
import com.example.hospitalapi.pharmacy.domain.exception.PharmacyValidationException;
import com.example.hospitalapi.pharmacy.domain.valueobject.BatchId;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    @Test
    void shouldCreateInventory() {
        // Given
        MedicationId medicationId = new MedicationId();
        List<Batch> batches = createBatches(medicationId, 2, false);

        // When
        Inventory inventory = new Inventory(medicationId, batches);

        // Then
        assertEquals(medicationId, inventory.getMedicationId());
        assertEquals(batches.size(), inventory.getBatches().size());
        for (int i = 0; i < batches.size(); i++) {
            assertEquals(batches.get(i), inventory.getBatches().get(i));
        }
    }

    @Test
    void shouldCreateInventoryWithNullBatches() {
        // Given
        MedicationId medicationId = new MedicationId();

        // When
        Inventory inventory = new Inventory(medicationId, null);

        // Then
        assertEquals(medicationId, inventory.getMedicationId());
        assertTrue(inventory.getBatches().isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenMedicationIdIsNull() {
        // Given
        List<Batch> batches = new ArrayList<>();

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Inventory(null, batches);
        });
    }

    @Test
    void shouldAddBatch() {
        // Given
        MedicationId medicationId = new MedicationId();
        Inventory inventory = new Inventory(medicationId, new ArrayList<>());
        Batch batch = createBatch(medicationId, false);

        // When
        inventory.addBatch(batch);

        // Then
        assertEquals(1, inventory.getBatches().size());
        assertEquals(batch, inventory.getBatches().get(0));
    }

    @Test
    void shouldThrowExceptionWhenAddingNullBatch() {
        // Given
        MedicationId medicationId = new MedicationId();
        Inventory inventory = new Inventory(medicationId, new ArrayList<>());

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            inventory.addBatch(null);
        });
    }

    @Test
    void shouldThrowExceptionWhenAddingBatchWithDifferentMedicationId() {
        // Given
        MedicationId medicationId1 = new MedicationId();
        MedicationId medicationId2 = new MedicationId();
        Inventory inventory = new Inventory(medicationId1, new ArrayList<>());
        Batch batch = createBatch(medicationId2, false);

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            inventory.addBatch(batch);
        });
    }

    @Test
    void shouldGetTotalQuantity() {
        // Given
        MedicationId medicationId = new MedicationId();
        List<Batch> batches = new ArrayList<>();
        
        Batch batch1 = createBatch(medicationId, false);
        batch1.updateQuantity(100);
        batches.add(batch1);
        
        Batch batch2 = createBatch(medicationId, true);
        batch2.updateQuantity(50);
        batches.add(batch2);
        
        Inventory inventory = new Inventory(medicationId, batches);

        // When
        int totalQuantity = inventory.getTotalQuantity();

        // Then
        assertEquals(150, totalQuantity);
    }

    @Test
    void shouldGetAvailableQuantity() {
        // Given
        MedicationId medicationId = new MedicationId();
        List<Batch> batches = new ArrayList<>();
        
        Batch batch1 = createBatch(medicationId, false);
        batch1.updateQuantity(100);
        batches.add(batch1);
        
        Batch batch2 = createBatch(medicationId, true);
        batch2.updateQuantity(50);
        batches.add(batch2);
        
        Inventory inventory = new Inventory(medicationId, batches);

        // When
        int availableQuantity = inventory.getAvailableQuantity();

        // Then
        assertEquals(100, availableQuantity);
    }

    @Test
    void shouldReturnTrueWhenHasStock() {
        // Given
        MedicationId medicationId = new MedicationId();
        List<Batch> batches = createBatches(medicationId, 1, false);
        Inventory inventory = new Inventory(medicationId, batches);

        // When & Then
        assertTrue(inventory.hasStock());
    }

    @Test
    void shouldReturnFalseWhenNoStock() {
        // Given
        MedicationId medicationId = new MedicationId();
        List<Batch> batches = createBatches(medicationId, 1, true);
        Inventory inventory = new Inventory(medicationId, batches);

        // When & Then
        assertFalse(inventory.hasStock());
    }

    @Test
    void shouldReturnTrueWhenHasEnoughStock() {
        // Given
        MedicationId medicationId = new MedicationId();
        List<Batch> batches = new ArrayList<>();
        
        Batch batch = createBatch(medicationId, false);
        batch.updateQuantity(100);
        batches.add(batch);
        
        Inventory inventory = new Inventory(medicationId, batches);

        // When & Then
        assertTrue(inventory.hasStock(50));
    }

    @Test
    void shouldReturnFalseWhenNotEnoughStock() {
        // Given
        MedicationId medicationId = new MedicationId();
        List<Batch> batches = new ArrayList<>();
        
        Batch batch = createBatch(medicationId, false);
        batch.updateQuantity(100);
        batches.add(batch);
        
        Inventory inventory = new Inventory(medicationId, batches);

        // When & Then
        assertFalse(inventory.hasStock(150));
    }

    @Test
    void shouldGetExpiredBatches() {
        // Given
        MedicationId medicationId = new MedicationId();
        List<Batch> batches = new ArrayList<>();
        
        Batch batch1 = createBatch(medicationId, false);
        batches.add(batch1);
        
        Batch batch2 = createBatch(medicationId, true);
        batches.add(batch2);
        
        Inventory inventory = new Inventory(medicationId, batches);

        // When
        List<Batch> expiredBatches = inventory.getExpiredBatches();

        // Then
        assertEquals(1, expiredBatches.size());
        assertEquals(batch2, expiredBatches.get(0));
    }

    @Test
    void shouldGetNonExpiredBatches() {
        // Given
        MedicationId medicationId = new MedicationId();
        List<Batch> batches = new ArrayList<>();
        
        Batch batch1 = createBatch(medicationId, false);
        batches.add(batch1);
        
        Batch batch2 = createBatch(medicationId, true);
        batches.add(batch2);
        
        Inventory inventory = new Inventory(medicationId, batches);

        // When
        List<Batch> nonExpiredBatches = inventory.getNonExpiredBatches();

        // Then
        assertEquals(1, nonExpiredBatches.size());
        assertEquals(batch1, nonExpiredBatches.get(0));
    }

    @Test
    void shouldRemoveStock() {
        // Given
        MedicationId medicationId = new MedicationId();
        List<Batch> batches = new ArrayList<>();
        
        Batch batch1 = createBatch(medicationId, false);
        batch1.updateQuantity(50);
        batches.add(batch1);
        
        Batch batch2 = createBatch(medicationId, false);
        batch2.updateQuantity(50);
        batches.add(batch2);
        
        Inventory inventory = new Inventory(medicationId, batches);
        int initialTotalQuantity = inventory.getTotalQuantity();

        // When
        inventory.removeStock(30);

        // Then
        assertEquals(initialTotalQuantity - 30, inventory.getTotalQuantity());
    }

    @Test
    void shouldRemoveStockFromMultipleBatches() {
        // Given
        MedicationId medicationId = new MedicationId();
        List<Batch> batches = new ArrayList<>();
        
        // Create a batch with earlier expiration date
        Batch batch1 = new Batch(
            new BatchId(),
            medicationId,
            "BATCH-1",
            LocalDate.now().minusMonths(2),
            LocalDate.now().plusMonths(1),
            30
        );
        batches.add(batch1);
        
        // Create a batch with later expiration date
        Batch batch2 = new Batch(
            new BatchId(),
            medicationId,
            "BATCH-2",
            LocalDate.now().minusMonths(1),
            LocalDate.now().plusMonths(6),
            30
        );
        batches.add(batch2);
        
        Inventory inventory = new Inventory(medicationId, batches);

        // When
        inventory.removeStock(40);

        // Then
        assertEquals(20, inventory.getTotalQuantity());
        assertEquals(0, batch1.getQuantity().getValue());
        assertEquals(20, batch2.getQuantity().getValue());
    }

    @Test
    void shouldThrowExceptionWhenRemovingNegativeStock() {
        // Given
        MedicationId medicationId = new MedicationId();
        List<Batch> batches = createBatches(medicationId, 1, false);
        Inventory inventory = new Inventory(medicationId, batches);

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            inventory.removeStock(-10);
        });
    }

    @Test
    void shouldThrowExceptionWhenRemovingZeroStock() {
        // Given
        MedicationId medicationId = new MedicationId();
        List<Batch> batches = createBatches(medicationId, 1, false);
        Inventory inventory = new Inventory(medicationId, batches);

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            inventory.removeStock(0);
        });
    }

    @Test
    void shouldThrowExceptionWhenNotEnoughStockToRemove() {
        // Given
        MedicationId medicationId = new MedicationId();
        List<Batch> batches = new ArrayList<>();
        
        Batch batch = createBatch(medicationId, false);
        batch.updateQuantity(50);
        batches.add(batch);
        
        Inventory inventory = new Inventory(medicationId, batches);

        // When & Then
        assertThrows(MedicationOutOfStockException.class, () -> {
            inventory.removeStock(100);
        });
    }

    private List<Batch> createBatches(MedicationId medicationId, int count, boolean expired) {
        List<Batch> batches = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            batches.add(createBatch(medicationId, expired));
        }
        return batches;
    }

    private Batch createBatch(MedicationId medicationId, boolean expired) {
        LocalDate manufacturingDate = LocalDate.now().minusMonths(3);
        LocalDate expirationDate = expired ? 
            LocalDate.now().minusDays(1) : 
            LocalDate.now().plusMonths(6);
        
        return new Batch(
            new BatchId(),
            medicationId,
            "BATCH-" + System.nanoTime(),
            manufacturingDate,
            expirationDate,
            100
        );
    }
}