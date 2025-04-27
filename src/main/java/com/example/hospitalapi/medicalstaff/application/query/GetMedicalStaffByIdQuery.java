package com.example.hospitalapi.medicalstaff.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Query to get a medical staff member by ID
 */
@Data
@AllArgsConstructor
public class GetMedicalStaffByIdQuery implements Query<MedicalStaffResponse> {
    private final String staffId;
}