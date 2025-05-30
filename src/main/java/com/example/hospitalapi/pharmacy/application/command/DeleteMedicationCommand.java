package com.example.hospitalapi.pharmacy.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * Command for deleting a medication
 */
@Getter
@AllArgsConstructor
@Data
public class DeleteMedicationCommand implements Command {
    private final String medicationId;
}
