package com.example.hospitalapi.pharmacy.infrastructure.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Request object for updating an existing medication
 */
@Data
public class UpdateMedicationRequest {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    private String description;
    
    @NotBlank(message = "Dosage form is required")
    private String dosageForm;
    
    @NotBlank(message = "Strength is required")
    private String strength;
    
    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;
    
    private boolean requiresPrescription;
}