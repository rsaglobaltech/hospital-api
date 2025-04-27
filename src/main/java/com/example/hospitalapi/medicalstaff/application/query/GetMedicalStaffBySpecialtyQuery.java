package com.example.hospitalapi.medicalstaff.application.query;

import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
import com.example.hospitalapi.shared.domain.bus.Query;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Query to get medical staff members by specialty
 */
@Data
@AllArgsConstructor
public class GetMedicalStaffBySpecialtyQuery implements Query<List<MedicalStaffResponse>> {
    private final Specialty specialty;
}