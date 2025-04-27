package com.example.hospitalapi.clinicalrecord.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;

/**
 * Query to get a clinical record by ID
 */
public class GetClinicalRecordByIdQuery implements Query<ClinicalRecordResponse> {
    private final String clinicalRecordId;

    /**
     * Create a new GetClinicalRecordByIdQuery
     * @param clinicalRecordId the clinical record ID
     */
    public GetClinicalRecordByIdQuery(String clinicalRecordId) {
        this.clinicalRecordId = clinicalRecordId;
    }

    /**
     * Get the clinical record ID
     * @return the clinical record ID
     */
    public String getClinicalRecordId() {
        return clinicalRecordId;
    }
}