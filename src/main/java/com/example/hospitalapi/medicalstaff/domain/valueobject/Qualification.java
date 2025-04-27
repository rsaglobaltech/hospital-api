package com.example.hospitalapi.medicalstaff.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Value object representing a medical qualification
 */
@Getter
@EqualsAndHashCode
public class Qualification {
    private final String degree;
    private final String institution;
    private final LocalDate dateObtained;
    private final String licenseNumber;
    private final LocalDate licenseExpiryDate;

    public Qualification(String degree, String institution, LocalDate dateObtained, 
                        String licenseNumber, LocalDate licenseExpiryDate) {
        if (degree == null || degree.trim().isEmpty()) {
            throw new IllegalArgumentException("Degree cannot be empty");
        }
        if (institution == null || institution.trim().isEmpty()) {
            throw new IllegalArgumentException("Institution cannot be empty");
        }
        if (dateObtained == null) {
            throw new IllegalArgumentException("Date obtained cannot be null");
        }
        if (licenseNumber == null || licenseNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("License number cannot be empty");
        }
        if (licenseExpiryDate == null) {
            throw new IllegalArgumentException("License expiry date cannot be null");
        }
        if (licenseExpiryDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("License has expired");
        }
        
        this.degree = degree.trim();
        this.institution = institution.trim();
        this.dateObtained = dateObtained;
        this.licenseNumber = licenseNumber.trim();
        this.licenseExpiryDate = licenseExpiryDate;
    }
    
    public boolean isExpired() {
        return licenseExpiryDate.isBefore(LocalDate.now());
    }
    
    public boolean isExpiringSoon(int monthsThreshold) {
        LocalDate thresholdDate = LocalDate.now().plusMonths(monthsThreshold);
        return licenseExpiryDate.isBefore(thresholdDate);
    }
}