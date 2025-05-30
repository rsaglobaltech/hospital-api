package com.example.hospitalapi.pharmacy.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Command for creating a new medication
 */
@Getter
@Builder
public class CreateMedicationCommand implements Command {
    private final String name;
    private final String description;
    private final String dosageForm;
    private final String strength;
    private final String manufacturer;
    private final BigDecimal price;
    private final boolean requiresPrescription;
}
