package com.example.hospitalapi.pharmacy.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Query for retrieving prescriptions by patient ID
 */
@Getter
@AllArgsConstructor
public class GetPrescriptionsByPatientIdQuery implements Query<List<PrescriptionResponse>> {
    private final String patientId;
    private final boolean onlyValid; // If true, only return valid (not expired and not dispensed) prescriptions
}
