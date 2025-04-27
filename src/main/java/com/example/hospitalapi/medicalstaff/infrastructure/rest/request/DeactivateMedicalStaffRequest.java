package com.example.hospitalapi.medicalstaff.infrastructure.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Request DTO for deactivating a medical staff member
 */
public class DeactivateMedicalStaffRequest {

    @NotNull(message = "Termination date is required")
    @Schema(description = "Date when staff member's employment was terminated", example = "2023-06-30")
    private LocalDate terminationDate;

    // Default constructor
    public DeactivateMedicalStaffRequest() {
    }

    // Getter
    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    // Setter
    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }
}
