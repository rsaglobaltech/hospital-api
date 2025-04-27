package com.example.hospitalapi.pharmacy.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;

import java.util.List;

/**
 * Query for retrieving all prescriptions
 */
public class GetAllPrescriptionsQuery implements Query<List<PrescriptionResponse>> {
    // No parameters needed
}
