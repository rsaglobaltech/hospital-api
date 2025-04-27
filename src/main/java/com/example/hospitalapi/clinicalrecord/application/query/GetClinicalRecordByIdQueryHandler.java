package com.example.hospitalapi.clinicalrecord.application.query;

import com.example.hospitalapi.clinicalrecord.domain.entity.ClinicalRecord;
import com.example.hospitalapi.clinicalrecord.domain.exception.ClinicalRecordNotFoundException;
import com.example.hospitalapi.clinicalrecord.domain.repository.ClinicalRecordRepository;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordId;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import org.springframework.stereotype.Service;

/**
 * Handler for GetClinicalRecordByIdQuery
 */
@Service
public class GetClinicalRecordByIdQueryHandler implements QueryHandler<GetClinicalRecordByIdQuery, ClinicalRecordResponse> {

    private final ClinicalRecordRepository clinicalRecordRepository;

    /**
     * Create a new GetClinicalRecordByIdQueryHandler
     * @param clinicalRecordRepository the clinical record repository
     */
    public GetClinicalRecordByIdQueryHandler(ClinicalRecordRepository clinicalRecordRepository) {
        this.clinicalRecordRepository = clinicalRecordRepository;
    }

    /**
     * Handle the GetClinicalRecordByIdQuery
     * @param query the query to handle
     * @return the clinical record response
     * @throws ClinicalRecordNotFoundException if the clinical record is not found
     */
    public ClinicalRecordResponse handle(GetClinicalRecordByIdQuery query) {
        // Create clinical record ID
        ClinicalRecordId clinicalRecordId = new ClinicalRecordId(query.getClinicalRecordId());

        // Find clinical record
        ClinicalRecord clinicalRecord = clinicalRecordRepository.findById(clinicalRecordId)
                .orElseThrow(() -> new ClinicalRecordNotFoundException(clinicalRecordId));

        // Map to response
        return mapToResponse(clinicalRecord);
    }

    /**
     * Map a clinical record entity to a response
     * @param clinicalRecord the clinical record entity
     * @return the clinical record response
     */
    private ClinicalRecordResponse mapToResponse(ClinicalRecord clinicalRecord) {
        return ClinicalRecordResponse.builder()
                .id(clinicalRecord.getId().toString())
                .patientId(clinicalRecord.getPatientId().toString())
                .doctorId(clinicalRecord.getDoctorId().getValue().toString())
                .type(clinicalRecord.getType())
                .title(clinicalRecord.getTitle().getValue().toString())
                .description(clinicalRecord.getDescription().getValue().toString())
                .attachmentUrl(clinicalRecord.getAttachmentUrl().getValue().toString())
                .createdAt(clinicalRecord.getCreatedAt().getValue())
                .updatedAt(clinicalRecord.getUpdatedAt().getValue())
                .build();
    }
}