package com.example.hospitalapi.patient.application.query;

import com.example.hospitalapi.patient.domain.entity.Patient;
import com.example.hospitalapi.patient.domain.repository.PatientRepository;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler for GetAllPatientsQuery
 */
@Service
@RequiredArgsConstructor
public class GetAllPatientsQueryHandler implements QueryHandler<GetAllPatientsQuery, List<PatientResponse>> {

    private final PatientRepository patientRepository;

    /**
     * Handle the GetAllPatientsQuery
     * @param query the query to handle
     * @return list of all patients
     */
    @Transactional(readOnly = true)
    public List<PatientResponse> handle(GetAllPatientsQuery query) {
        // Find all patients
        List<Patient> patients = patientRepository.findAll();

        // Map to response
        return patients.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PatientResponse mapToResponse(Patient patient) {
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
