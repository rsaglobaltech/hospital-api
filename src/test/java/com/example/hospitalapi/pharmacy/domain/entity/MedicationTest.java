package com.example.hospitalapi.pharmacy.domain.entity;

import com.example.hospitalapi.pharmacy.domain.exception.PharmacyValidationException;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MedicationTest {

    @Test
    void shouldCreateMedication() {
        // Given
        MedicationId id = new MedicationId();
        String name = "Aspirin";
        String description = "Pain reliever";
        String dosageForm = "Tablet";
        String strength = "500mg";
        String manufacturer = "Bayer";
        BigDecimal price = new BigDecimal("5.99");
        boolean requiresPrescription = false;

        // When
        Medication medication = new Medication(id, name, description, dosageForm, strength, manufacturer, price, requiresPrescription);

        // Then
        assertEquals(id, medication.getId());
        assertEquals(name, medication.getName());
        assertEquals(description, medication.getDescription());
        assertEquals(dosageForm, medication.getDosageForm());
        assertEquals(strength, medication.getStrength());
        assertEquals(manufacturer, medication.getManufacturer());
        assertEquals(price, medication.getPrice());
        assertEquals(requiresPrescription, medication.isRequiresPrescription());
    }

    @Test
    void shouldCreateMedicationWithNullDescription() {
        // Given
        MedicationId id = new MedicationId();
        String name = "Aspirin";
        String description = null;
        String dosageForm = "Tablet";
        String strength = "500mg";
        String manufacturer = "Bayer";
        BigDecimal price = new BigDecimal("5.99");
        boolean requiresPrescription = false;

        // When
        Medication medication = new Medication(id, name, description, dosageForm, strength, manufacturer, price, requiresPrescription);

        // Then
        assertEquals("", medication.getDescription());
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        // Given
        String name = "Aspirin";
        String description = "Pain reliever";
        String dosageForm = "Tablet";
        String strength = "500mg";
        String manufacturer = "Bayer";
        BigDecimal price = new BigDecimal("5.99");
        boolean requiresPrescription = false;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Medication(null, name, description, dosageForm, strength, manufacturer, price, requiresPrescription);
        });
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        // Given
        MedicationId id = new MedicationId();
        String name = null;
        String description = "Pain reliever";
        String dosageForm = "Tablet";
        String strength = "500mg";
        String manufacturer = "Bayer";
        BigDecimal price = new BigDecimal("5.99");
        boolean requiresPrescription = false;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Medication(id, name, description, dosageForm, strength, manufacturer, price, requiresPrescription);
        });
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        // Given
        MedicationId id = new MedicationId();
        String name = "";
        String description = "Pain reliever";
        String dosageForm = "Tablet";
        String strength = "500mg";
        String manufacturer = "Bayer";
        BigDecimal price = new BigDecimal("5.99");
        boolean requiresPrescription = false;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Medication(id, name, description, dosageForm, strength, manufacturer, price, requiresPrescription);
        });
    }

    @Test
    void shouldThrowExceptionWhenDosageFormIsNull() {
        // Given
        MedicationId id = new MedicationId();
        String name = "Aspirin";
        String description = "Pain reliever";
        String dosageForm = null;
        String strength = "500mg";
        String manufacturer = "Bayer";
        BigDecimal price = new BigDecimal("5.99");
        boolean requiresPrescription = false;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Medication(id, name, description, dosageForm, strength, manufacturer, price, requiresPrescription);
        });
    }

    @Test
    void shouldThrowExceptionWhenDosageFormIsEmpty() {
        // Given
        MedicationId id = new MedicationId();
        String name = "Aspirin";
        String description = "Pain reliever";
        String dosageForm = "";
        String strength = "500mg";
        String manufacturer = "Bayer";
        BigDecimal price = new BigDecimal("5.99");
        boolean requiresPrescription = false;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Medication(id, name, description, dosageForm, strength, manufacturer, price, requiresPrescription);
        });
    }

    @Test
    void shouldThrowExceptionWhenStrengthIsNull() {
        // Given
        MedicationId id = new MedicationId();
        String name = "Aspirin";
        String description = "Pain reliever";
        String dosageForm = "Tablet";
        String strength = null;
        String manufacturer = "Bayer";
        BigDecimal price = new BigDecimal("5.99");
        boolean requiresPrescription = false;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Medication(id, name, description, dosageForm, strength, manufacturer, price, requiresPrescription);
        });
    }

    @Test
    void shouldThrowExceptionWhenStrengthIsEmpty() {
        // Given
        MedicationId id = new MedicationId();
        String name = "Aspirin";
        String description = "Pain reliever";
        String dosageForm = "Tablet";
        String strength = "";
        String manufacturer = "Bayer";
        BigDecimal price = new BigDecimal("5.99");
        boolean requiresPrescription = false;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Medication(id, name, description, dosageForm, strength, manufacturer, price, requiresPrescription);
        });
    }

    @Test
    void shouldThrowExceptionWhenManufacturerIsNull() {
        // Given
        MedicationId id = new MedicationId();
        String name = "Aspirin";
        String description = "Pain reliever";
        String dosageForm = "Tablet";
        String strength = "500mg";
        String manufacturer = null;
        BigDecimal price = new BigDecimal("5.99");
        boolean requiresPrescription = false;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Medication(id, name, description, dosageForm, strength, manufacturer, price, requiresPrescription);
        });
    }

    @Test
    void shouldThrowExceptionWhenManufacturerIsEmpty() {
        // Given
        MedicationId id = new MedicationId();
        String name = "Aspirin";
        String description = "Pain reliever";
        String dosageForm = "Tablet";
        String strength = "500mg";
        String manufacturer = "";
        BigDecimal price = new BigDecimal("5.99");
        boolean requiresPrescription = false;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Medication(id, name, description, dosageForm, strength, manufacturer, price, requiresPrescription);
        });
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNull() {
        // Given
        MedicationId id = new MedicationId();
        String name = "Aspirin";
        String description = "Pain reliever";
        String dosageForm = "Tablet";
        String strength = "500mg";
        String manufacturer = "Bayer";
        BigDecimal price = null;
        boolean requiresPrescription = false;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Medication(id, name, description, dosageForm, strength, manufacturer, price, requiresPrescription);
        });
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        // Given
        MedicationId id = new MedicationId();
        String name = "Aspirin";
        String description = "Pain reliever";
        String dosageForm = "Tablet";
        String strength = "500mg";
        String manufacturer = "Bayer";
        BigDecimal price = new BigDecimal("-1.00");
        boolean requiresPrescription = false;

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            new Medication(id, name, description, dosageForm, strength, manufacturer, price, requiresPrescription);
        });
    }

    @Test
    void shouldUpdateName() {
        // Given
        Medication medication = createValidMedication();
        String newName = "New Aspirin";

        // When
        medication.updateName(newName);

        // Then
        assertEquals(newName, medication.getName());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNameToNull() {
        // Given
        Medication medication = createValidMedication();

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            medication.updateName(null);
        });
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNameToEmpty() {
        // Given
        Medication medication = createValidMedication();

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            medication.updateName("");
        });
    }

    @Test
    void shouldUpdateDescription() {
        // Given
        Medication medication = createValidMedication();
        String newDescription = "New description";

        // When
        medication.updateDescription(newDescription);

        // Then
        assertEquals(newDescription, medication.getDescription());
    }

    @Test
    void shouldUpdateDescriptionToEmpty() {
        // Given
        Medication medication = createValidMedication();

        // When
        medication.updateDescription(null);

        // Then
        assertEquals("", medication.getDescription());
    }

    @Test
    void shouldUpdateDosageForm() {
        // Given
        Medication medication = createValidMedication();
        String newDosageForm = "Capsule";

        // When
        medication.updateDosageForm(newDosageForm);

        // Then
        assertEquals(newDosageForm, medication.getDosageForm());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingDosageFormToNull() {
        // Given
        Medication medication = createValidMedication();

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            medication.updateDosageForm(null);
        });
    }

    @Test
    void shouldThrowExceptionWhenUpdatingDosageFormToEmpty() {
        // Given
        Medication medication = createValidMedication();

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            medication.updateDosageForm("");
        });
    }

    @Test
    void shouldUpdateStrength() {
        // Given
        Medication medication = createValidMedication();
        String newStrength = "1000mg";

        // When
        medication.updateStrength(newStrength);

        // Then
        assertEquals(newStrength, medication.getStrength());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingStrengthToNull() {
        // Given
        Medication medication = createValidMedication();

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            medication.updateStrength(null);
        });
    }

    @Test
    void shouldThrowExceptionWhenUpdatingStrengthToEmpty() {
        // Given
        Medication medication = createValidMedication();

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            medication.updateStrength("");
        });
    }

    @Test
    void shouldUpdateManufacturer() {
        // Given
        Medication medication = createValidMedication();
        String newManufacturer = "New Manufacturer";

        // When
        medication.updateManufacturer(newManufacturer);

        // Then
        assertEquals(newManufacturer, medication.getManufacturer());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingManufacturerToNull() {
        // Given
        Medication medication = createValidMedication();

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            medication.updateManufacturer(null);
        });
    }

    @Test
    void shouldThrowExceptionWhenUpdatingManufacturerToEmpty() {
        // Given
        Medication medication = createValidMedication();

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            medication.updateManufacturer("");
        });
    }

    @Test
    void shouldUpdatePrice() {
        // Given
        Medication medication = createValidMedication();
        BigDecimal newPrice = new BigDecimal("10.99");

        // When
        medication.updatePrice(newPrice);

        // Then
        assertEquals(newPrice, medication.getPrice());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingPriceToNull() {
        // Given
        Medication medication = createValidMedication();

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            medication.updatePrice(null);
        });
    }

    @Test
    void shouldThrowExceptionWhenUpdatingPriceToNegative() {
        // Given
        Medication medication = createValidMedication();
        BigDecimal newPrice = new BigDecimal("-1.00");

        // When & Then
        assertThrows(PharmacyValidationException.class, () -> {
            medication.updatePrice(newPrice);
        });
    }

    @Test
    void shouldUpdateRequiresPrescription() {
        // Given
        Medication medication = createValidMedication();
        boolean newRequiresPrescription = !medication.isRequiresPrescription();

        // When
        medication.updateRequiresPrescription(newRequiresPrescription);

        // Then
        assertEquals(newRequiresPrescription, medication.isRequiresPrescription());
    }

    private Medication createValidMedication() {
        MedicationId id = new MedicationId();
        String name = "Aspirin";
        String description = "Pain reliever";
        String dosageForm = "Tablet";
        String strength = "500mg";
        String manufacturer = "Bayer";
        BigDecimal price = new BigDecimal("5.99");
        boolean requiresPrescription = false;
        
        return new Medication(id, name, description, dosageForm, strength, manufacturer, price, requiresPrescription);
    }
}