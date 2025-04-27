package com.example.hospitalapi.patient.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Query to get a patient by ID
 */
@Data
@AllArgsConstructor
public class GetPatientByIdQuery implements Query<PatientResponse> {
    private final String patientId;
}
