package com.example.hospitalapi.clinicalrecord.application.query;

import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordType;
import com.example.hospitalapi.shared.domain.bus.Query;

import java.util.List;

/**
 * Query to get clinical records by patient ID
 */
public class GetClinicalRecordsByPatientIdQuery implements Query<List<ClinicalRecordResponse>> {
    private final String patientId;
    private final ClinicalRecordType type;

    /**
     * Create a new GetClinicalRecordsByPatientIdQuery
     * @param patientId the patient ID
     * @param type the clinical record type (optional)
     */
    public GetClinicalRecordsByPatientIdQuery(String patientId, ClinicalRecordType type) {
        this.patientId = patientId;
        this.type = type;
    }

    /**
     * Create a new GetClinicalRecordsByPatientIdQuery
     * @param patientId the patient ID
     */
    public GetClinicalRecordsByPatientIdQuery(String patientId) {
        this.patientId = patientId;
        this.type = null;
    }

    /**
     * Get the patient ID
     * @return the patient ID
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Get the clinical record type
     * @return the clinical record type
     */
    public ClinicalRecordType getType() {
        return type;
    }

    /**
     * Check if the query has a type filter
     * @return true if the query has a type filter
     */
    public boolean hasTypeFilter() {
        return type != null;
    }
}