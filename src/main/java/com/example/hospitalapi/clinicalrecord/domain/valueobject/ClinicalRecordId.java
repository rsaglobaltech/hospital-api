package com.example.hospitalapi.clinicalrecord.domain.valueobject;

import com.example.hospitalapi.shared.domain.valueobject.Id;
import java.util.UUID;

/**
 * Value object representing a clinical record ID
 */
public class ClinicalRecordId extends Id {

    /**
     * Create a new clinical record ID with a random UUID
     */
    public ClinicalRecordId() {
        super();
    }

    /**
     * Create a clinical record ID from an existing UUID string
     * @param id the UUID string
     */
    public ClinicalRecordId(String id) {
        super(id);
    }

    /**
     * Create a clinical record ID from an existing UUID
     * @param id the UUID
     */
    public ClinicalRecordId(UUID id) {
        super(id);
    }
}
