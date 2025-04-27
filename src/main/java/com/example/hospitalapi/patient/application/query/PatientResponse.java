package com.example.hospitalapi.patient.application.query;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO for patient data
 */
@Data
@Builder
public class PatientResponse {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final LocalDate dateOfBirth;
    private final String address;
    private final String medicalHistory;
}