package com.example.hospitalapi.pharmacy.application.query;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Response object for medication queries
 */
@Getter
@Builder
public class MedicationResponse {
    private final String id;
    private final String name;
    private final String description;
    private final String dosageForm;
    private final String strength;
    private final String manufacturer;
    private final BigDecimal price;
    private final boolean requiresPrescription;
}