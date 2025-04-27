package com.example.hospitalapi.pharmacy.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Command for deleting a prescription
 */
@Getter
@AllArgsConstructor
public class DeletePrescriptionCommand {
    private final String prescriptionId;
}