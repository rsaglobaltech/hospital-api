package com.example.hospitalapi.clinicalrecord.application.command;

import com.example.hospitalapi.clinicalrecord.domain.entity.ClinicalRecord;
import com.example.hospitalapi.clinicalrecord.domain.repository.ClinicalRecordRepository;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordId;
import com.example.hospitalapi.patient.domain.exception.PatientNotFoundException;
import com.example.hospitalapi.patient.domain.repository.PatientRepository;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for CreateClinicalRecordCommand
 */
@Service
public class CreateClinicalRecordCommandHandler implements CommandHandler<CreateClinicalRecordCommand, ClinicalRecordId> {

    private final ClinicalRecordRepository clinicalRecordRepository;
    private final PatientRepository patientRepository;

    /**
     * Create a new CreateClinicalRecordCommandHandler
     * @param clinicalRecordRepository the clinical record repository
     * @param patientRepository the patient repository
     */
    public CreateClinicalRecordCommandHandler(ClinicalRecordRepository clinicalRecordRepository, 
                                             PatientRepository patientRepository) {
        this.clinicalRecordRepository = clinicalRecordRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * Handle the CreateClinicalRecordCommand
     * @param command the command to handle
     * @return the ID of the created clinical record
     * @throws PatientNotFoundException if the patient is not found
     */
    @Transactional
    public ClinicalRecordId handle(CreateClinicalRecordCommand command) {
        // Create value objects
        ClinicalRecordId clinicalRecordId = new ClinicalRecordId();
        PatientId patientId = new PatientId(command.getPatientId());

        // Validate patient exists
        if (!patientRepository.existsById(patientId)) {
            throw new PatientNotFoundException(patientId);
        }

        // Create clinical record entity
        ClinicalRecord clinicalRecord = new ClinicalRecord(
            clinicalRecordId,
            patientId,
            command.getDoctorId(),
            command.getType(),
            command.getTitle(),
            command.getDescription()
        );

        // Set attachment URL if provided
        if (command.getAttachmentUrl() != null && !command.getAttachmentUrl().isEmpty()) {
            clinicalRecord.updateAttachmentUrl(command.getAttachmentUrl());
        }

        // Save clinical record
        ClinicalRecord savedClinicalRecord = clinicalRecordRepository.save(clinicalRecord);

        return savedClinicalRecord.getId();
    }
}