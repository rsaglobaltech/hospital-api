package com.example.hospitalapi.pharmacy.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Query for retrieving a prescription by ID
 */
@Getter
@AllArgsConstructor
public class GetPrescriptionByIdQuery implements Query<PrescriptionResponse> {
    private final String prescriptionId;
}
