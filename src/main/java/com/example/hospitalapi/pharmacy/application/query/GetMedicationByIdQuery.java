package com.example.hospitalapi.pharmacy.application.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Query for retrieving a medication by ID
 */
@Getter
@AllArgsConstructor
public class GetMedicationByIdQuery {
    private final String medicationId;
}