package com.example.hospitalapi.patient.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Command to create a new patient
 */
@Data
@Builder
public class CreatePatientCommand implements Command {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final LocalDate dateOfBirth;
    private final String address;
}
