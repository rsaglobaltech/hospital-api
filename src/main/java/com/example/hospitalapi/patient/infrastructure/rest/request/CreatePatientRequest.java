package com.example.hospitalapi.patient.infrastructure.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

/**
 * Request DTO for creating a patient
 */
@Data
public class CreatePatientRequest {
    
    @NotBlank(message = "First name is required")
    @Schema(description = "Patient's first name", example = "John")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Schema(description = "Patient's last name", example = "Doe")
    private String lastName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Patient's email address", example = "john.doe@example.com")
    private String email;
    
    @NotBlank(message = "Phone number is required")
    @Schema(description = "Patient's phone number", example = "+1234567890")
    private String phoneNumber;
    
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @Schema(description = "Patient's date of birth", example = "1990-01-01")
    private LocalDate dateOfBirth;
    
    @NotBlank(message = "Address is required")
    @Schema(description = "Patient's address", example = "123 Main St, City, Country")
    private String address;
}