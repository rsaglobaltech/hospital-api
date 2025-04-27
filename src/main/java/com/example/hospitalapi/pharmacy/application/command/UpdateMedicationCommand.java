package com.example.hospitalapi.pharmacy.application.command;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Command for updating an existing medication
 */
@Getter
@Builder
public class UpdateMedicationCommand {
    private final String medicationId;
    private final String name;
    private final String description;
    private final String dosageForm;
    private final String strength;
    private final String manufacturer;
    private final BigDecimal price;
    private final boolean requiresPrescription;
}