package com.example.hospitalapi.pharmacy.application.command;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

/**
 * Command for updating an existing prescription
 */
@Getter
@Builder
public class UpdatePrescriptionCommand {
    private final String prescriptionId;
    private final LocalDate expirationDate;
    private final List<PrescriptionItemCommand> items;
    private final String notes;
    
    /**
     * Command for a prescription item
     */
    @Getter
    @Builder
    public static class PrescriptionItemCommand {
        private final String medicationId;
        private final int quantity;
        private final String instructions;
    }
}