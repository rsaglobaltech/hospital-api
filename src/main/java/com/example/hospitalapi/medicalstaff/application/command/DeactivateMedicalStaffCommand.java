package com.example.hospitalapi.medicalstaff.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * Command to deactivate a medical staff member
 */
@AllArgsConstructor
public class DeactivateMedicalStaffCommand implements Command {
    private final String staffId;
    private final LocalDate terminationDate;

    public String getStaffId() {
        return staffId;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }
}
