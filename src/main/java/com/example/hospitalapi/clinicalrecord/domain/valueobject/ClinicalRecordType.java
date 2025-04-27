package com.example.hospitalapi.clinicalrecord.domain.valueobject;

/**
 * Enum representing the type of clinical record
 */
public enum ClinicalRecordType {
    DIAGNOSIS("Diagnosis"),
    PRESCRIPTION("Prescription"),
    LAB_RESULT("Laboratory Result"),
    VITAL_SIGNS("Vital Signs"),
    MEDICAL_HISTORY("Medical History"),
    PROGRESS_NOTE("Progress Note"),
    TREATMENT_PLAN("Treatment Plan"),
    IMAGING_RESULT("Imaging Result"),
    CONSULTATION("Consultation"),
    DISCHARGE_SUMMARY("Discharge Summary"),
    REFERRAL("Referral"),
    ALLERGY("Allergy"),
    IMMUNIZATION("Immunization"),
    PROCEDURE("Procedure");

    private final String displayName;

    ClinicalRecordType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}