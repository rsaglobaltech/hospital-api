package com.example.hospitalapi.clinicalrecord.application.query;

import com.example.hospitalapi.clinicalrecord.domain.entity.ClinicalRecord;
import com.example.hospitalapi.clinicalrecord.domain.repository.ClinicalRecordRepository;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordType;
import com.example.hospitalapi.patient.domain.repository.PatientRepository;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler for GetClinicalRecordsByPatientIdQuery
 */
@Service
public class GetClinicalRecordsByPatientIdQueryHandler implements QueryHandler<GetClinicalRecordsByPatientIdQuery, List<ClinicalRecordResponse>> {

    private final ClinicalRecordRepository clinicalRecordRepository;
    private final PatientRepository patientRepository;

    /**
     * Create a new GetClinicalRecordsByPatientIdQueryHandler
     * @param clinicalRecordRepository the clinical record repository
     * @param patientRepository the patient repository
     */
    public GetClinicalRecordsByPatientIdQueryHandler(ClinicalRecordRepository clinicalRecordRepository,
                                                    PatientRepository patientRepository) {
        this.clinicalRecordRepository = clinicalRecordRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * Handle the GetClinicalRecordsByPatientIdQuery
     * @param query the query to handle
     * @return the list of clinical record responses
     */
    public List<ClinicalRecordResponse> handle(GetClinicalRecordsByPatientIdQuery query) {
        // Create patient ID
        PatientId patientId = new PatientId(query.getPatientId());

        // Validate patient exists
        if (!patientRepository.existsById(patientId)) {
            return List.of(); // Return empty list if patient doesn't exist
        }

        // Get clinical records
        List<ClinicalRecord> clinicalRecords;
        if (query.hasTypeFilter()) {
            ClinicalRecordType type = query.getType();
            clinicalRecords = clinicalRecordRepository.findByPatientIdAndType(patientId, type);
        } else {
            clinicalRecords = clinicalRecordRepository.findByPatientId(patientId);
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