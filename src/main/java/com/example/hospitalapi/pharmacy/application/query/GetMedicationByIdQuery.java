package com.example.hospitalapi.pharmacy.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Query for retrieving a medication by ID
 */
@Getter
@AllArgsConstructor
public class GetMedicationByIdQuery implements Query<Void> {
    private final String medicationId;
}
