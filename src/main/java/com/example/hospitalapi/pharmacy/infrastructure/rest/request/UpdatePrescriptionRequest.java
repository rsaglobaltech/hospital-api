package com.example.hospitalapi.pharmacy.infrastructure.rest.request;

import com.example.hospitalapi.pharmacy.application.command.UpdatePrescriptionCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Request object for updating an existing prescription
 */
@Data
public class UpdatePrescriptionRequest {
    
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
        
        @NotNull(message = "Medication ID is required")
        private String medicationId;
        
        @NotNull(message = "Quantity is required")
        private Integer quantity;
        
        private String instructions;
    }
    
    /**
     * Convert the request to a command
     * @param prescriptionId the ID of the prescription to update
     * @return the command
     */
    public UpdatePrescriptionCommand toCommand(String prescriptionId) {
        List<UpdatePrescriptionCommand.PrescriptionItemCommand> itemCommands = items.stream()
                .map(item -> UpdatePrescriptionCommand.PrescriptionItemCommand.builder()
                        .medicationId(item.getMedicationId())
                        .quantity(item.getQuantity())
                        .instructions(item.getInstructions())
                        .build())
                .collect(Collectors.toList());
        
        return UpdatePrescriptionCommand.builder()
                .prescriptionId(prescriptionId)
                .expirationDate(expirationDate)
                .items(itemCommands)
                .notes(notes)
                .build();
    }
}