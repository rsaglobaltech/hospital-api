package com.example.hospitalapi.clinicalrecord.application.command;

import com.example.hospitalapi.clinicalrecord.domain.exception.ClinicalRecordNotFoundException;
import com.example.hospitalapi.clinicalrecord.domain.repository.ClinicalRecordRepository;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordId;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for DeleteClinicalRecordCommand
 */
@Service
public class DeleteClinicalRecordCommandHandler implements CommandHandler<DeleteClinicalRecordCommand, Void> {

    private final ClinicalRecordRepository clinicalRecordRepository;

    /**
     * Create a new DeleteClinicalRecordCommandHandler
     * @param clinicalRecordRepository the clinical record repository
     */
    public DeleteClinicalRecordCommandHandler(ClinicalRecordRepository clinicalRecordRepository) {
        this.clinicalRecordRepository = clinicalRecordRepository;
    }

    /**
     * Handle the DeleteClinicalRecordCommand
     * @param command the command to handle
     * @return null (void)
     * @throws ClinicalRecordNotFoundException if the clinical record is not found
     */
    @Transactional
    public Void handle(DeleteClinicalRecordCommand command) {
        // Create clinical record ID
        ClinicalRecordId clinicalRecordId = new ClinicalRecordId(command.getClinicalRecordId());

        // Check if clinical record exists
        if (!clinicalRecordRepository.existsById(clinicalRecordId)) {
            throw new ClinicalRecordNotFoundException(clinicalRecordId);
        }

        // Delete clinical record
        clinicalRecordRepository.deleteById(clinicalRecordId);

        return null;
    }
}