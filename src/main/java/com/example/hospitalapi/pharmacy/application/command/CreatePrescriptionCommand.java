package com.example.hospitalapi.pharmacy.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

/**
 * Command for creating a new prescription
 */
@Getter
@Builder
public class CreatePrescriptionCommand implements Command {
    private final String patientId;
    private final String doctorId;
    private final LocalDate issueDate;
    private final LocalDate expirationDate;
    private final List<PrescriptionItemCommand> items;
    private final String notes;

    /**
     * Command for a prescription item
     */
    @Getter
    @Builder
    public static class PrescriptionItemCommand {
        private final String medicationId;
        private final int quantity;
        private final String instructions;
    }
}
