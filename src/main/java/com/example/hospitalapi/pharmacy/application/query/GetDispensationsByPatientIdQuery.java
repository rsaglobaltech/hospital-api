package com.example.hospitalapi.pharmacy.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Query for retrieving dispensations by patient ID
 */
@Getter
@AllArgsConstructor
public class GetDispensationsByPatientIdQuery implements Query<List<DispensationResponse>> {
    private final String patientId;
}
