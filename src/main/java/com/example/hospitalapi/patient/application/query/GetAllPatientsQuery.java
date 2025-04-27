package com.example.hospitalapi.patient.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;

import java.util.List;

/**
 * Query to get all patients
 */
public class GetAllPatientsQuery implements Query<List<PatientResponse>> {
    // No parameters needed
}
