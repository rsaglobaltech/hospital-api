package com.example.hospitalapi.medicalstaff.infrastructure.rest.request;

import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

/**
 * Request DTO for creating a medical staff member
 */
public class CreateMedicalStaffRequest {

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

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @Schema(description = "Staff member's date of birth", example = "1980-01-01")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Address is required")
    @Schema(description = "Staff member's address", example = "123 Main St, City, Country")
    private String address;

    @NotNull(message = "Specialty is required")
    @Schema(description = "Staff member's medical specialty", example = "CARDIOLOGY")
    private Specialty specialty;

    @NotBlank(message = "Degree is required")
    @Schema(description = "Staff member's degree", example = "MD")
    private String degree;

    @NotBlank(message = "Institution is required")
    @Schema(description = "Institution where degree was obtained", example = "Harvard Medical School")
    private String institution;

    @NotNull(message = "Date obtained is required")
    @Past(message = "Date obtained must be in the past")
    @Schema(description = "Date when degree was obtained", example = "2005-05-15")
    private LocalDate dateObtained;

    @NotBlank(message = "License number is required")
    @Schema(description = "Medical license number", example = "ML123456")
    private String licenseNumber;

    @NotNull(message = "License expiry date is required")
    @Schema(description = "License expiry date", example = "2025-05-15")
    private LocalDate licenseExpiryDate;

    @NotNull(message = "Hire date is required")
    @Schema(description = "Date when staff member was hired", example = "2010-01-15")
    private LocalDate hireDate;

    // Default constructor
    public CreateMedicalStaffRequest() {
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public String getDegree() {
        return degree;
    }

    public String getInstitution() {
        return institution;
    }

    public LocalDate getDateObtained() {
        return dateObtained;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public LocalDate getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public LocalDate getHireDate() {
        return hireDate;
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

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setDateObtained(LocalDate dateObtained) {
        this.dateObtained = dateObtained;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setLicenseExpiryDate(LocalDate licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
}
