package com.example.hospitalapi.clinicalrecord.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;

/**
 * Command to delete a clinical record
 */
public class DeleteClinicalRecordCommand implements Command {
    private final String clinicalRecordId;

    /**
     * Create a new DeleteClinicalRecordCommand
     * @param clinicalRecordId the clinical record ID
     */
    public DeleteClinicalRecordCommand(String clinicalRecordId) {
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