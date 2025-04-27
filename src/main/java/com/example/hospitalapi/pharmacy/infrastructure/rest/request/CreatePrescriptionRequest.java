package com.example.hospitalapi.pharmacy.infrastructure.rest.request;

import com.example.hospitalapi.pharmacy.application.command.CreatePrescriptionCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Request object for creating a new prescription
 */
@Data
public class CreatePrescriptionRequest {
    
    @NotBlank(message = "Patient ID is required")
    private String patientId;
    
    @NotBlank(message = "Doctor ID is required")
    private String doctorId;
    
    @NotNull(message = "Issue date is required")
    private LocalDate issueDate;
    
    @NotNull(message = "Expiration date is required")
    private LocalDate expirationDate;
    
    @NotEmpty(message = "At least one prescription item is required")
    @Valid
    private List<PrescriptionItemRequest> items;
    
    private String notes;
    
    /**
     * Request object for a prescription item
     */
    @Data
    public static class PrescriptionItemRequest {
        
        @NotBlank(message = "Medication ID is required")
        private String medicationId;
        
        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be positive")
        private Integer quantity;
        
        private String instructions;
    }
    
    /**
     * Convert the request to a command
     * @return the command
     */
    public CreatePrescriptionCommand toCommand() {
        List<CreatePrescriptionCommand.PrescriptionItemCommand> itemCommands = items.stream()
                .map(item -> CreatePrescriptionCommand.PrescriptionItemCommand.builder()
                        .medicationId(item.getMedicationId())
                        .quantity(item.getQuantity())
                        .instructions(item.getInstructions())
                        .build())
                .collect(Collectors.toList());
        
        return CreatePrescriptionCommand.builder()
                .patientId(patientId)
                .doctorId(doctorId)
                .issueDate(issueDate)
                .expirationDate(expirationDate)
                .items(itemCommands)
                .notes(notes)
                .build();
    }
}