package com.example.hospitalapi.clinicalrecord.domain.exception;

import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordId;

/**
 * Exception thrown when a clinical record is not found
 */
public class ClinicalRecordNotFoundException extends ClinicalRecordException {

    /**
     * Create a new ClinicalRecordNotFoundException with the specified clinical record ID
     * @param clinicalRecordId the clinical record ID
     */
    public ClinicalRecordNotFoundException(ClinicalRecordId clinicalRecordId) {
        super("Clinical record not found with ID: " + clinicalRecordId);
    }

    /**
     * Create a new ClinicalRecordNotFoundException with the specified clinical record ID string
     * @param clinicalRecordId the clinical record ID string
     */
    public ClinicalRecordNotFoundException(String clinicalRecordId) {
        super("Clinical record not found with ID: " + clinicalRecordId);
    }
}
