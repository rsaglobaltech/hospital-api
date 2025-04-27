package com.example.hospitalapi.pharmacy.domain.entity;

import com.example.hospitalapi.pharmacy.domain.exception.PharmacyValidationException;
import com.example.hospitalapi.pharmacy.domain.valueobject.DispensationId;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import com.example.hospitalapi.pharmacy.domain.valueobject.PrescriptionId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DispensationTest {

    @Test
    void shouldCreateDispensation() {
        // Given
        DispensationId id = new DispensationId();
        PrescriptionId prescriptionId = new PrescriptionId();
        String patientId = "patient-123";
        String pharmacistId = "pharmacist-456";
        List<Dispensation.DispensationItem> items = createValidDispensationItems();
        String notes = "Take with food";

        // When
        Dispensation dispensation = new Dispensation(id, prescriptionId, patientId, pharmacistId, items, notes);

        // Then
        assertEquals(id, dispensation.getId());
        assertEquals(prescriptionId, dispensation.getPrescriptionId());
        assertEquals(patientId, dispensation.getPatientId().toString());
        assertEquals(pharmacistId, dispensation.getPharmacistId().toString());
        assertEquals(notes, dispensation.getNotes().getValue());
        assertEquals(items.size(), dispensation.getItems().size());
        
        // Calculate expected total amount
        BigDecimal expectedTotal = BigDecimal.ZERO;
        for (Dispensation.DispensationItem item : items) {
            expectedTotal = expectedTotal.add(item.getUnitPrice().getValue().multiply(BigDecimal.valueOf(item.getQuantity().getValue())));
        }
        assertEquals(expectedTotal.setScale(2), dispensation.getTotalAmount().getValue());
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        // Given
        PrescriptionId prescriptionId = new PrescriptionId();
        String patientId = "patient-123";
        String pharmacistId = "pharmacist-456";
        List<Dispensation.DispensationItem> items = createValidDispensationItems();
        String notes = "Take with food";

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Dispensation(null, prescriptionId, patientId, pharmacistId, items, notes);
        });
    }

    @Test
    void shouldThrowExceptionWhenPrescriptionIdIsNull() {
        // Given
        DispensationId id = new DispensationId();
        String patientId = "patient-123";
        String pharmacistId = "pharmacist-456";
        List<Dispensation.DispensationItem> items = createValidDispensationItems();
        String notes = "Take with food";

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Dispensation(id, null, patientId, pharmacistId, items, notes);
        });
    }

    @Test
    void shouldThrowExceptionWhenPatientIdIsNull() {
        // Given
        DispensationId id = new DispensationId();
        PrescriptionId prescriptionId = new PrescriptionId();
        String pharmacistId = "pharmacist-456";
        List<Dispensation.DispensationItem> items = createValidDispensationItems();
        String notes = "Take with food";

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Dispensation(id, prescriptionId, null, pharmacistId, items, notes);
        });
    }

    @Test
    void shouldThrowExceptionWhenPatientIdIsEmpty() {
        // Given
        DispensationId id = new DispensationId();
        PrescriptionId prescriptionId = new PrescriptionId();
        String patientId = "";
        String pharmacistId = "pharmacist-456";
        List<Dispensation.DispensationItem> items = createValidDispensationItems();
        String notes = "Take with food";

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Dispensation(id, prescriptionId, patientId, pharmacistId, items, notes);
        });
    }

    @Test
    void shouldThrowExceptionWhenPharmacistIdIsNull() {
        // Given
        DispensationId id = new DispensationId();
        PrescriptionId prescriptionId = new PrescriptionId();
        String patientId = "patient-123";
        List<Dispensation.DispensationItem> items = createValidDispensationItems();
        String notes = "Take with food";

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Dispensation(id, prescriptionId, patientId, null, items, notes);
        });
    }

    @Test
    void shouldThrowExceptionWhenPharmacistIdIsEmpty() {
        // Given
        DispensationId id = new DispensationId();
        PrescriptionId prescriptionId = new PrescriptionId();
        String patientId = "patient-123";
        String pharmacistId = "";
        List<Dispensation.DispensationItem> items = createValidDispensationItems();
        String notes = "Take with food";

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Dispensation(id, prescriptionId, patientId, pharmacistId, items, notes);
        });
    }

    @Test
    void shouldThrowExceptionWhenItemsIsNull() {
        // Given
        DispensationId id = new DispensationId();
        PrescriptionId prescriptionId = new PrescriptionId();
        String patientId = "patient-123";
        String pharmacistId = "pharmacist-456";
        String notes = "Take with food";

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Dispensation(id, prescriptionId, patientId, pharmacistId, null, notes);
        });
    }

    @Test
    void shouldThrowExceptionWhenItemsIsEmpty() {
        // Given
        DispensationId id = new DispensationId();
        PrescriptionId prescriptionId = new PrescriptionId();
        String patientId = "patient-123";
        String pharmacistId = "pharmacist-456";
        List<Dispensation.DispensationItem> items = new ArrayList<>();
        String notes = "Take with food";

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Dispensation(id, prescriptionId, patientId, pharmacistId, items, notes);
        });
    }

    @Test
    void shouldUpdateNotes() {
        // Given
        Dispensation dispensation = createValidDispensation();
        String newNotes = "New instructions: Take after meals";

        // When
        dispensation.updateNotes(newNotes);

        // Then
        assertEquals(newNotes, dispensation.getNotes().getValue());
    }

    @Test
    void shouldCreateDispensationItem() {
        // Given
        MedicationId medicationId = new MedicationId();
        String medicationName = "Aspirin";
        int quantity = 30;
        BigDecimal unitPrice = new BigDecimal("5.99");
        String batchNumber = "BATCH-123";

        // When
        Dispensation.DispensationItem item = new Dispensation.DispensationItem(
            medicationId, medicationName, quantity, unitPrice, batchNumber);

        // Then
        assertEquals(medicationId, item.getMedicationId());
        assertEquals(medicationName, item.getMedicationName().getValue());
        assertEquals(quantity, item.getQuantity().getValue());
        assertEquals(unitPrice.setScale(2), item.getUnitPrice().getValue());
        assertEquals(batchNumber, item.getBatchNumber().getValue());
        
        // Check subtotal calculation
        BigDecimal expectedSubtotal = unitPrice.multiply(BigDecimal.valueOf(quantity)).setScale(2);
        assertEquals(expectedSubtotal, item.getSubtotal().getValue());
    }

    @Test
    void shouldThrowExceptionWhenMedicationIdIsNull() {
        // Given
        String medicationName = "Aspirin";
        int quantity = 30;
        BigDecimal unitPrice = new BigDecimal("5.99");
        String batchNumber = "BATCH-123";

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Dispensation.DispensationItem(null, medicationName, quantity, unitPrice, batchNumber);
        });
    }

    @Test
    void shouldThrowExceptionWhenMedicationNameIsNull() {
        // Given
        MedicationId medicationId = new MedicationId();
        int quantity = 30;
        BigDecimal unitPrice = new BigDecimal("5.99");
        String batchNumber = "BATCH-123";

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Dispensation.DispensationItem(medicationId, null, quantity, unitPrice, batchNumber);
        });
    }

    @Test
    void shouldThrowExceptionWhenMedicationNameIsEmpty() {
        // Given
        MedicationId medicationId = new MedicationId();
        String medicationName = "";
        int quantity = 30;
        BigDecimal unitPrice = new BigDecimal("5.99");
        String batchNumber = "BATCH-123";

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Dispensation.DispensationItem(medicationId, medicationName, quantity, unitPrice, batchNumber);
        });
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsZero() {
        // Given
        MedicationId medicationId = new MedicationId();
        String medicationName = "Aspirin";
        int quantity = 0;
        BigDecimal unitPrice = new BigDecimal("5.99");
        String batchNumber = "BATCH-123";

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Dispensation.DispensationItem(medicationId, medicationName, quantity, unitPrice, batchNumber);
        });
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsNegative() {
        // Given
        MedicationId medicationId = new MedicationId();
        String medicationName = "Aspirin";
        int quantity = -1;
        BigDecimal unitPrice = new BigDecimal("5.99");
        String batchNumber = "BATCH-123";

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Dispensation.DispensationItem(medicationId, medicationName, quantity, unitPrice, batchNumber);
        });
    }

    @Test
    void shouldThrowExceptionWhenUnitPriceIsNull() {
        // Given
        MedicationId medicationId = new MedicationId();
        String medicationName = "Aspirin";
        int quantity = 30;
        String batchNumber = "BATCH-123";

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Dispensation.DispensationItem(medicationId, medicationName, quantity, null, batchNumber);
        });
    }

    @Test
    void shouldThrowExceptionWhenUnitPriceIsNegative() {
        // Given
        MedicationId medicationId = new MedicationId();
        String medicationName = "Aspirin";
        int quantity = 30;
        BigDecimal unitPrice = new BigDecimal("-1.00");
        String batchNumber = "BATCH-123";

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Dispensation.DispensationItem(medicationId, medicationName, quantity, unitPrice, batchNumber);
        });
    }

    private List<Dispensation.DispensationItem> createValidDispensationItems() {
        List<Dispensation.DispensationItem> items = new ArrayList<>();
        items.add(new Dispensation.DispensationItem(
            new MedicationId(), "Aspirin", 30, new BigDecimal("5.99"), "BATCH-123"));
        items.add(new Dispensation.DispensationItem(
            new MedicationId(), "Ibuprofen", 20, new BigDecimal("7.50"), "BATCH-456"));
        return items;
    }

    private Dispensation createValidDispensation() {
        DispensationId id = new DispensationId();
        PrescriptionId prescriptionId = new PrescriptionId();
        String patientId = "patient-123";
        String pharmacistId = "pharmacist-456";
        List<Dispensation.DispensationItem> items = createValidDispensationItems();
        String notes = "Take with food";
        return new Dispensation(id, prescriptionId, patientId, pharmacistId, items, notes);
    }
}