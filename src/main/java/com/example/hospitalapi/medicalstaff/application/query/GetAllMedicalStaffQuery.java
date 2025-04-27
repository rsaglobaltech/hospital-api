package com.example.hospitalapi.medicalstaff.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;

import java.util.List;

/**
 * Query to get all medical staff members
 */
public class GetAllMedicalStaffQuery implements Query<List<MedicalStaffResponse>> {
    // No parameters needed
}