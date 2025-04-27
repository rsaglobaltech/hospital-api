package com.example.hospitalapi.pharmacy.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Command for deleting a medication
 */
@Getter
@AllArgsConstructor
public class DeleteMedicationCommand {
    private final String medicationId;
}