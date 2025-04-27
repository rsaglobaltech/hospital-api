package com.example.hospitalapi.medicalstaff.application.command;

import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
import com.example.hospitalapi.shared.domain.bus.Command;
import lombok.Builder;
import lombok.Data;

/**
 * Command to update an existing medical staff member
 */
@Data
@Builder
public class UpdateMedicalStaffCommand implements Command {
    private final String staffId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final String address;
    private final Specialty specialty;
}