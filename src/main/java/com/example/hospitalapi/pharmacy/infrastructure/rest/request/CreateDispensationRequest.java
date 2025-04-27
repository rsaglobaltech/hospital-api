package com.example.hospitalapi.pharmacy.infrastructure.rest.request;

import com.example.hospitalapi.pharmacy.application.command.CreateDispensationCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Request object for creating a new dispensation
 */
@Data
public class CreateDispensationRequest {
    
    @NotBlank(message = "Prescription ID is required")
    private String prescriptionId;
    
    @NotBlank(message = "Patient ID is required")
    private String patientId;
    
    @NotBlank(message = "Pharmacist ID is required")
    private String pharmacistId;
    
    @NotEmpty(message = "At least one dispensation item is required")
    @Valid
    private List<DispensationItemRequest> items;
    
    private String notes;
    
    /**
     * Request object for a dispensation item
     */
    @Data
    public static class DispensationItemRequest {
        
        @NotBlank(message = "Medication ID is required")
        private String medicationId;
        
        @NotBlank(message = "Medication name is required")
        private String medicationName;
        
        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be positive")
        private Integer quantity;
        
        @NotNull(message = "Unit price is required")
        @Positive(message = "Unit price must be positive")
        private BigDecimal unitPrice;
        
        private String batchNumber;
    }
    
    /**
     * Convert the request to a command
     * @return the command
     */
    public CreateDispensationCommand toCommand() {
        List<CreateDispensationCommand.DispensationItemCommand> itemCommands = items.stream()
                .map(item -> CreateDispensationCommand.DispensationItemCommand.builder()
                        .medicationId(item.getMedicationId())
                        .medicationName(item.getMedicationName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .batchNumber(item.getBatchNumber())
                        .build())
                .collect(Collectors.toList());
        
        return CreateDispensationCommand.builder()
                .prescriptionId(prescriptionId)
                .patientId(patientId)
                .pharmacistId(pharmacistId)
                .items(itemCommands)
                .notes(notes)
                .build();
    }
}