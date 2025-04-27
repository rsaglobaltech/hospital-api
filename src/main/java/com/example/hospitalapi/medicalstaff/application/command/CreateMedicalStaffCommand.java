package com.example.hospitalapi.medicalstaff.application.command;

import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
import com.example.hospitalapi.shared.domain.bus.Command;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Command to create a new medical staff member
 */
@Data
@Builder
public class CreateMedicalStaffCommand implements Command {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final LocalDate dateOfBirth;
    private final String address;
    private final Specialty specialty;
    private final String degree;
    private final String institution;
    private final LocalDate dateObtained;
    private final String licenseNumber;
    private final LocalDate licenseExpiryDate;
    private final LocalDate hireDate;
}