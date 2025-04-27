package com.example.hospitalapi.pharmacy.application.query;

import com.example.hospitalapi.pharmacy.domain.entity.Prescription;
import com.example.hospitalapi.pharmacy.domain.repository.MedicationRepository;
import com.example.hospitalapi.pharmacy.domain.repository.PrescriptionRepository;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler for retrieving all prescriptions
 */
@Service
@RequiredArgsConstructor
public class GetAllPrescriptionsQueryHandler implements QueryHandler<GetAllPrescriptionsQuery, List<PrescriptionResponse>> {

    private final PrescriptionRepository prescriptionRepository;
    private final MedicationRepository medicationRepository;

    /**
     * Handles the query to retrieve all prescriptions
     * @param query the query
     * @return list of prescription responses
     */
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> handle(GetAllPrescriptionsQuery query) {
        List<Prescription> prescriptions = prescriptionRepository.findAll();

        return prescriptions.stream()
                .map(prescription -> {
                    List<PrescriptionResponse.PrescriptionItemResponse> itemResponses = prescription.getItems().stream()
                            .map(item -> {
                                String medicationName = medicationRepository.findById(item.getMedicationId())
                                        .map(medication -> medication.getName())
                                        .orElse("Unknown Medication");

                                return PrescriptionResponse.PrescriptionItemResponse.builder()
                                        .medicationId(item.getMedicationId().toString())
                                        .medicationName(medicationName)
                                        .quantity(item.getQuantity().getValue())
                                        .instructions(item.getInstructions().getValue())
                                        .build();
                            })
                            .collect(Collectors.toList());

                    return PrescriptionResponse.builder()
                            .id(prescription.getId().toString())
                            .patientId(prescription.getPatientId().toString())
                            .doctorId(prescription.getDoctorId().toString())
                            .issueDate(prescription.getIssueDate().getValue())
                            .expirationDate(prescription.getExpirationDate().getValue())
                            .items(itemResponses)
                            .dispensed(prescription.isDispensed())
                            .dispensedDate(prescription.getDispensedDate() != null ? prescription.getDispensedDate().getValue() : null)
                            .notes(prescription.getNotes().getValue())
                            .expired(prescription.isExpired())
                            .valid(prescription.isValid())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
