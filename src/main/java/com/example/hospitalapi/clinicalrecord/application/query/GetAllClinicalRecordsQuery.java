package com.example.hospitalapi.clinicalrecord.application.query;

import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordType;
import com.example.hospitalapi.shared.domain.bus.Query;

import java.util.List;

/**
 * Query to get all clinical records
 */
public class GetAllClinicalRecordsQuery implements Query<List<ClinicalRecordResponse>> {
    private final ClinicalRecordType type;

    /**
     * Create a new GetAllClinicalRecordsQuery
     * @param type the clinical record type (optional)
     */
    public GetAllClinicalRecordsQuery(ClinicalRecordType type) {
        this.type = type;
    }

    /**
     * Create a new GetAllClinicalRecordsQuery
     */
    public GetAllClinicalRecordsQuery() {
        this.type = null;
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