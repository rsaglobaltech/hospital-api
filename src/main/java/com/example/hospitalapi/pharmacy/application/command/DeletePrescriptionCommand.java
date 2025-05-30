package com.example.hospitalapi.pharmacy.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Command for deleting a prescription
 */
@Getter
@AllArgsConstructor
public class DeletePrescriptionCommand implements Command {
    private final String prescriptionId;
}
