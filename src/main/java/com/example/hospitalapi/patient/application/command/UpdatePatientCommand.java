package com.example.hospitalapi.patient.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Command to update an existing patient
 */
@Data
@Builder
public class UpdatePatientCommand implements Command {
    private final String patientId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final LocalDate dateOfBirth;
    private final String address;
    private final String medicalHistory;
}
