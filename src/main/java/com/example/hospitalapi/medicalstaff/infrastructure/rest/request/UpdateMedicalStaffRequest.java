package com.example.hospitalapi.medicalstaff.infrastructure.rest.request;

import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for updating a medical staff member
 */
public class UpdateMedicalStaffRequest {

    @NotBlank(message = "First name is required")
    @Schema(description = "Staff member's first name", example = "John")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Schema(description = "Staff member's last name", example = "Doe")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Staff member's email address", example = "john.doe@hospital.com")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Schema(description = "Staff member's phone number", example = "+1234567890")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    @Schema(description = "Staff member's address", example = "123 Main St, City, Country")
    private String address;

    @NotNull(message = "Specialty is required")
    @Schema(description = "Staff member's medical specialty", example = "CARDIOLOGY")
    private Specialty specialty;

    // Default constructor
    public UpdateMedicalStaffRequest() {
    }

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }
}
