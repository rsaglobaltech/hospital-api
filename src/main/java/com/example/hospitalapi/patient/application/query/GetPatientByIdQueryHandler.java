package com.example.hospitalapi.patient.application.query;

import com.example.hospitalapi.patient.domain.entity.Patient;
import com.example.hospitalapi.patient.domain.repository.PatientRepository;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for GetPatientByIdQuery
 */
@Service
@RequiredArgsConstructor
public class GetPatientByIdQueryHandler implements QueryHandler<GetPatientByIdQuery, PatientResponse> {

    private final PatientRepository patientRepository;

    /**
     * Handle the GetPatientByIdQuery
     * @param query the query to handle
     * @return the patient data
     * @throws IllegalArgumentException if the patient is not found
     */
    @Transactional(readOnly = true)
    public PatientResponse handle(GetPatientByIdQuery query) {
        // Create patient ID
        String patientId = query.getPatientId();

        // Find patient in database
        Patient patient = patientRepository.findById(new PatientId(patientId))
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + patientId));

        // Map to response
        return mapPatientToResponse(patient);
    }

    /**
     * Map a Patient entity to a PatientResponse
     * @param patient the Patient entity to map
     * @return the mapped PatientResponse
     */
    private PatientResponse mapPatientToResponse(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId().toString())
                .firstName(patient.getName().getFirstName())
                .lastName(patient.getName().getLastName())
                .email(patient.getEmail().toString())
                .phoneNumber(patient.getPhoneNumber().toString())
                .dateOfBirth(patient.getDateOfBirth().getValue())
                .address(patient.getAddress().getValue())
                .medicalHistory(patient.getMedicalHistory().getValue())
                .build();
    }
}
