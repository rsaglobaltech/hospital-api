package com.example.hospitalapi.pharmacy.domain.entity;

import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.pharmacy.domain.exception.PharmacyValidationException;
import com.example.hospitalapi.pharmacy.domain.valueobject.BatchNumber;
import com.example.hospitalapi.pharmacy.domain.valueobject.DispensationId;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationName;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationQuantity;
import com.example.hospitalapi.pharmacy.domain.valueobject.Money;
import com.example.hospitalapi.pharmacy.domain.valueobject.PharmacistId;
import com.example.hospitalapi.pharmacy.domain.valueobject.PrescriptionId;
import com.example.hospitalapi.pharmacy.domain.valueobject.PrescriptionNotes;
import com.example.hospitalapi.shared.domain.entity.AggregateRoot;
import com.example.hospitalapi.shared.domain.valueobject.Timestamp;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Entity representing a medication dispensation in the pharmacy system
 */
@Getter
public class Dispensation extends AggregateRoot {
    private final DispensationId id;
    private final PrescriptionId prescriptionId;
    private final PatientId patientId;
    private final PharmacistId pharmacistId;
    private final Timestamp dispensationDateTime;
    private List<DispensationItem> items;
    private Money totalAmount;
    private PrescriptionNotes notes;

    public Dispensation(DispensationId id, PrescriptionId prescriptionId, String patientIdValue, 
                       String pharmacistIdValue, List<DispensationItem> items, String notesValue) {
        if (id == null) {
            throw new PharmacyValidationException("Dispensation ID cannot be null");
        }
        if (prescriptionId == null) {
            throw new PharmacyValidationException("Prescription ID cannot be null");
        }
        if (patientIdValue == null || patientIdValue.trim().isEmpty()) {
            throw new PharmacyValidationException("Patient ID cannot be empty");
        }
        if (pharmacistIdValue == null || pharmacistIdValue.trim().isEmpty()) {
            throw new PharmacyValidationException("Pharmacist ID cannot be empty");
        }
        if (items == null || items.isEmpty()) {
            throw new PharmacyValidationException("Dispensation must have at least one item");
        }

        this.id = id;
        this.prescriptionId = prescriptionId;
        this.patientId = new PatientId(patientIdValue);
        this.pharmacistId = new PharmacistId(pharmacistIdValue);
        this.dispensationDateTime = Timestamp.now();
        this.items = new ArrayList<>(items);
        this.notes = new PrescriptionNotes(notesValue);
        this.totalAmount = calculateTotalAmount();
    }

    public List<DispensationItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void updateNotes(String notesValue) {
        this.notes = new PrescriptionNotes(notesValue);
    }

    private Money calculateTotalAmount() {
        Money total = Money.zero();
        for (DispensationItem item : items) {
            total = total.add(item.getSubtotal());
        }
        return total;
    }

    /**
     * Inner class representing a dispensation item
     */
    @Getter
    public static class DispensationItem {
        private final MedicationId medicationId;
        private final MedicationName medicationName;
        private final MedicationQuantity quantity;
        private final Money unitPrice;
        private final BatchNumber batchNumber;

        public DispensationItem(MedicationId medicationId, String medicationNameValue, 
                               int quantityValue, BigDecimal unitPriceValue, String batchNumberValue) {
            if (medicationId == null) {
                throw new PharmacyValidationException("Medication ID cannot be null");
            }
            if (medicationNameValue == null || medicationNameValue.trim().isEmpty()) {
                throw new PharmacyValidationException("Medication name cannot be empty");
            }
            if (quantityValue <= 0) {
                throw new PharmacyValidationException("Quantity must be greater than zero");
            }
            if (unitPriceValue == null) {
                throw new PharmacyValidationException("Unit price cannot be null");
            }
            if (unitPriceValue.compareTo(BigDecimal.ZERO) < 0) {
                throw new PharmacyValidationException("Unit price cannot be negative");
            }

            this.medicationId = medicationId;
            this.medicationName = new MedicationName(medicationNameValue);
            this.quantity = new MedicationQuantity(quantityValue);
            this.unitPrice = new Money(unitPriceValue);
            this.batchNumber = new BatchNumber(batchNumberValue);
        }

        public Money getSubtotal() {
            return unitPrice.multiply(quantity.getValue());
        }
    }
}
