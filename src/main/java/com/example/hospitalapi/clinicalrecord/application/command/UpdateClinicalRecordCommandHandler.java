package com.example.hospitalapi.clinicalrecord.application.command;

import com.example.hospitalapi.clinicalrecord.domain.entity.ClinicalRecord;
import com.example.hospitalapi.clinicalrecord.domain.exception.ClinicalRecordNotFoundException;
import com.example.hospitalapi.clinicalrecord.domain.repository.ClinicalRecordRepository;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordId;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for UpdateClinicalRecordCommand
 */
@Service
public class UpdateClinicalRecordCommandHandler implements CommandHandler<UpdateClinicalRecordCommand, Void> {

    private final ClinicalRecordRepository clinicalRecordRepository;

    /**
     * Create a new UpdateClinicalRecordCommandHandler
     * @param clinicalRecordRepository the clinical record repository
     */
    public UpdateClinicalRecordCommandHandler(ClinicalRecordRepository clinicalRecordRepository) {
        this.clinicalRecordRepository = clinicalRecordRepository;
    }

    /**
     * Handle the UpdateClinicalRecordCommand
     * @param command the command to handle
     * @return null (void)
     * @throws ClinicalRecordNotFoundException if the clinical record is not found
     */
    @Transactional
    public Void handle(UpdateClinicalRecordCommand command) {
        // Create clinical record ID
        ClinicalRecordId clinicalRecordId = new ClinicalRecordId(command.getClinicalRecordId());

        // Find clinical record
        ClinicalRecord clinicalRecord = clinicalRecordRepository.findById(clinicalRecordId)
                .orElseThrow(() -> new ClinicalRecordNotFoundException(clinicalRecordId));

        // Update clinical record
        if (command.getType() != null) {
            clinicalRecord.updateType(command.getType());
        }

        if (command.getTitle() != null && !command.getTitle().isEmpty()) {
            clinicalRecord.updateTitle(command.getTitle());
        }

        if (command.getDescription() != null && !command.getDescription().isEmpty()) {
            clinicalRecord.updateDescription(command.getDescription());
        }

        // Always update attachment URL, even if it's null
        clinicalRecord.updateAttachmentUrl(command.getAttachmentUrl());

        // Save clinical record
        clinicalRecordRepository.save(clinicalRecord);

        return null;
    }
}
