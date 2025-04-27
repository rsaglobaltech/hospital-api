package com.example.hospitalapi.pharmacy.application.command;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Command for creating a new dispensation
 */
@Getter
@Builder
public class CreateDispensationCommand {
    private final String prescriptionId;
    private final String patientId;
    private final String pharmacistId;
    private final List<DispensationItemCommand> items;
    private final String notes;
    
    /**
     * Command for a dispensation item
     */
    @Getter
    @Builder
    public static class DispensationItemCommand {
        private final String medicationId;
        private final String medicationName;
        private final int quantity;
        private final BigDecimal unitPrice;
        private final String batchNumber;
    }
}