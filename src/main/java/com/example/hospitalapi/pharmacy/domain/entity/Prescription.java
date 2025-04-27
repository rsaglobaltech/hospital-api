package com.example.hospitalapi.pharmacy.domain.entity;

import com.example.hospitalapi.medicalstaff.domain.valueobject.DoctorId;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.pharmacy.domain.exception.PharmacyValidationException;
import com.example.hospitalapi.pharmacy.domain.exception.PrescriptionAlreadyDispensedException;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationInstructions;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationQuantity;
import com.example.hospitalapi.pharmacy.domain.valueobject.PrescriptionDate;
import com.example.hospitalapi.pharmacy.domain.valueobject.PrescriptionId;
import com.example.hospitalapi.pharmacy.domain.valueobject.PrescriptionNotes;
import com.example.hospitalapi.shared.domain.entity.AggregateRoot;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Entity representing a prescription in the pharmacy system
 */
@Getter
public class Prescription extends AggregateRoot {
    private final PrescriptionId id;
    private final PatientId patientId;
    private final DoctorId doctorId;
    private final PrescriptionDate issueDate;
    private PrescriptionDate expirationDate;
    private List<PrescriptionItem> items;
    private boolean dispensed;
    private PrescriptionDate dispensedDate;
    private PrescriptionNotes notes;

    public Prescription(PrescriptionId id, PatientId patientId, DoctorId doctorId, 
                       LocalDate issueDateValue, LocalDate expirationDateValue, List<PrescriptionItem> items, String notesValue) {
        if (id == null) {
            throw new PharmacyValidationException("Prescription ID cannot be null");
        }
        if (patientId == null) {
            throw new PharmacyValidationException("Patient ID cannot be null");
        }
        if (doctorId == null) {
            throw new PharmacyValidationException("Doctor ID cannot be null");
        }
        if (issueDateValue == null) {
            throw new PharmacyValidationException("Issue date cannot be null");
        }
        if (expirationDateValue == null) {
            throw new PharmacyValidationException("Expiration date cannot be null");
        }
        if (expirationDateValue.isBefore(issueDateValue)) {
            throw new PharmacyValidationException("Expiration date cannot be before issue date");
        }
        if (items == null || items.isEmpty()) {
            throw new PharmacyValidationException("Prescription must have at least one item");
        }

        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.issueDate = new PrescriptionDate(issueDateValue);
        this.expirationDate = new PrescriptionDate(expirationDateValue);
        this.items = new ArrayList<>(items);
        this.notes = new PrescriptionNotes(notesValue);
        this.dispensed = false;
        this.dispensedDate = null;
    }

    public List<PrescriptionItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void updateExpirationDate(LocalDate expirationDateValue) {
        if (expirationDateValue == null) {
            throw new PharmacyValidationException("Expiration date cannot be null");
        }
        PrescriptionDate newExpirationDate = new PrescriptionDate(expirationDateValue);
        if (newExpirationDate.isBefore(this.issueDate)) {
            throw new PharmacyValidationException("Expiration date cannot be before issue date");
        }
        this.expirationDate = newExpirationDate;
    }

    public void updateNotes(String notesValue) {
        this.notes = new PrescriptionNotes(notesValue);
    }

    public void markAsDispensed() {
        if (this.dispensed) {
            throw new PrescriptionAlreadyDispensedException(this.id.toString());
        }
        this.dispensed = true;
        this.dispensedDate = PrescriptionDate.now();
    }

    public boolean isExpired() {
        return PrescriptionDate.now().isAfter(expirationDate);
    }

    public boolean isValid() {
        return !isExpired() && !dispensed;
    }

    /**
     * Inner class representing a prescription item
     */
    @Getter
    public static class PrescriptionItem {
        private final MedicationId medicationId;
        private MedicationQuantity quantity;
        private MedicationInstructions instructions;

        public PrescriptionItem(MedicationId medicationId, int quantityValue, String instructionsValue) {
            if (medicationId == null) {
                throw new PharmacyValidationException("Medication ID cannot be null");
            }

            this.medicationId = medicationId;
            this.quantity = new MedicationQuantity(quantityValue);
            this.instructions = new MedicationInstructions(instructionsValue);
        }

        public void updateQuantity(int quantityValue) {
            this.quantity = new MedicationQuantity(quantityValue);
        }

        public void updateInstructions(String instructionsValue) {
            this.instructions = new MedicationInstructions(instructionsValue);
        }
    }
}
