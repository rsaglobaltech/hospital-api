package com.example.hospitalapi.pharmacy.domain.entity;

import com.example.hospitalapi.pharmacy.domain.exception.PharmacyValidationException;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Entity representing a medication in the pharmacy system
 */
@Getter
public class Medication {
    private final MedicationId id;
    private String name;
    private String description;
    private String dosageForm;
    private String strength;
    private String manufacturer;
    private BigDecimal price;
    private boolean requiresPrescription;
    
    public Medication(MedicationId id, String name, String description, String dosageForm, 
                     String strength, String manufacturer, BigDecimal price, boolean requiresPrescription) {
        if (id == null) {
            throw new PharmacyValidationException("Medication ID cannot be null");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new PharmacyValidationException("Medication name cannot be empty");
        }
        if (dosageForm == null || dosageForm.trim().isEmpty()) {
            throw new PharmacyValidationException("Dosage form cannot be empty");
        }
        if (strength == null || strength.trim().isEmpty()) {
            throw new PharmacyValidationException("Strength cannot be empty");
        }
        if (manufacturer == null || manufacturer.trim().isEmpty()) {
            throw new PharmacyValidationException("Manufacturer cannot be empty");
        }
        if (price == null) {
            throw new PharmacyValidationException("Price cannot be null");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new PharmacyValidationException("Price cannot be negative");
        }
        
        this.id = id;
        this.name = name.trim();
        this.description = description != null ? description.trim() : "";
        this.dosageForm = dosageForm.trim();
        this.strength = strength.trim();
        this.manufacturer = manufacturer.trim();
        this.price = price;
        this.requiresPrescription = requiresPrescription;
    }
    
    public void updateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new PharmacyValidationException("Medication name cannot be empty");
        }
        this.name = name.trim();
    }
    
    public void updateDescription(String description) {
        this.description = description != null ? description.trim() : "";
    }
    
    public void updateDosageForm(String dosageForm) {
        if (dosageForm == null || dosageForm.trim().isEmpty()) {
            throw new PharmacyValidationException("Dosage form cannot be empty");
        }
        this.dosageForm = dosageForm.trim();
    }
    
    public void updateStrength(String strength) {
        if (strength == null || strength.trim().isEmpty()) {
            throw new PharmacyValidationException("Strength cannot be empty");
        }
        this.strength = strength.trim();
    }
    
    public void updateManufacturer(String manufacturer) {
        if (manufacturer == null || manufacturer.trim().isEmpty()) {
            throw new PharmacyValidationException("Manufacturer cannot be empty");
        }
        this.manufacturer = manufacturer.trim();
    }
    
    public void updatePrice(BigDecimal price) {
        if (price == null) {
            throw new PharmacyValidationException("Price cannot be null");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new PharmacyValidationException("Price cannot be negative");
        }
        this.price = price;
    }
    
    public void updateRequiresPrescription(boolean requiresPrescription) {
        this.requiresPrescription = requiresPrescription;
    }
}