package com.example.hospitalapi.medicalstaff.application.query;

import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO for medical staff data
 */
@Builder
public class MedicalStaffResponse {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final LocalDate dateOfBirth;
    private final String address;
    private final Specialty specialty;
    private final List<QualificationResponse> qualifications;
    private final boolean active;
    private final LocalDate hireDate;
    private final LocalDate terminationDate;

    // Getters
    public String getId() {
        return id;
    }

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

    public List<QualificationResponse> getQualifications() {
        return qualifications;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    /**
     * DTO for qualification data
     */
    @Builder
    public static class QualificationResponse {
        private final String degree;
        private final String institution;
        private final LocalDate dateObtained;
        private final String licenseNumber;
        private final LocalDate licenseExpiryDate;
        private final boolean expired;

        // Getters
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

        public boolean isExpired() {
            return expired;
        }
    }
}
