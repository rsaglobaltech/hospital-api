package com.example.hospitalapi.clinicalrecord.application.query;

import com.example.hospitalapi.clinicalrecord.domain.entity.ClinicalRecord;
import com.example.hospitalapi.clinicalrecord.domain.repository.ClinicalRecordRepository;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler for GetAllClinicalRecordsQuery
 */
@Service
public class GetAllClinicalRecordsQueryHandler implements QueryHandler<GetAllClinicalRecordsQuery, List<ClinicalRecordResponse>> {

    private final ClinicalRecordRepository clinicalRecordRepository;

    /**
     * Create a new GetAllClinicalRecordsQueryHandler
     * @param clinicalRecordRepository the clinical record repository
     */
    public GetAllClinicalRecordsQueryHandler(ClinicalRecordRepository clinicalRecordRepository) {
        this.clinicalRecordRepository = clinicalRecordRepository;
    }

    /**
     * Handle the GetAllClinicalRecordsQuery
     * @param query the query to handle
     * @return the list of clinical record responses
     */
    public List<ClinicalRecordResponse> handle(GetAllClinicalRecordsQuery query) {
        // Get clinical records
        List<ClinicalRecord> clinicalRecords;
        if (query.hasTypeFilter()) {
            clinicalRecords = clinicalRecordRepository.findByType(query.getType());
        } else {
            clinicalRecords = clinicalRecordRepository.findAll();
        }

        // Map to responses
        return clinicalRecords.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
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
                .title(clinicalRecord.getTitle().getValue())
                .description(clinicalRecord.getDescription().getValue())
                .attachmentUrl(clinicalRecord.getAttachmentUrl().getValue())
                .createdAt(clinicalRecord.getCreatedAt().getValue())
                .updatedAt(clinicalRecord.getUpdatedAt().getValue())
                .build();
    }
}