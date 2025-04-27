package com.example.hospitalapi.pharmacy.application.query;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

/**
 * Response object for prescription queries
 */
@Getter
@Builder
public class PrescriptionResponse {
    private final String id;
    private final String patientId;
    private final String doctorId;
    private final LocalDate issueDate;
    private final LocalDate expirationDate;
    private final List<PrescriptionItemResponse> items;
    private final boolean dispensed;
    private final LocalDate dispensedDate;
    private final String notes;
    private final boolean expired;
    private final boolean valid;
    
    /**
     * Response object for prescription item
     */
    @Getter
    @Builder
    public static class PrescriptionItemResponse {
        private final String medicationId;
        private final String medicationName;
        private final int quantity;
        private final String instructions;
    }
}