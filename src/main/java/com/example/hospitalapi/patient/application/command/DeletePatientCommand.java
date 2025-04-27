package com.example.hospitalapi.patient.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Command to delete a patient
 */
@Data
@AllArgsConstructor
public class DeletePatientCommand implements Command {
    private final String patientId;
}
