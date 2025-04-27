package com.example.hospitalapi.pharmacy.application.query;

import com.example.hospitalapi.pharmacy.domain.entity.Prescription;
import com.example.hospitalapi.pharmacy.domain.exception.PrescriptionNotFoundException;
import com.example.hospitalapi.pharmacy.domain.repository.MedicationRepository;
import com.example.hospitalapi.pharmacy.domain.repository.PrescriptionRepository;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import com.example.hospitalapi.pharmacy.domain.valueobject.PrescriptionId;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler for retrieving a prescription by ID
 */
@Service
@RequiredArgsConstructor
public class GetPrescriptionByIdQueryHandler implements QueryHandler<GetPrescriptionByIdQuery, PrescriptionResponse> {

    private final PrescriptionRepository prescriptionRepository;
    private final MedicationRepository medicationRepository;

    /**
     * Handles the query to retrieve a prescription by ID
     * @param query the query containing the prescription ID
     * @return the prescription response
     */
    @Transactional(readOnly = true)
    public PrescriptionResponse handle(GetPrescriptionByIdQuery query) {
        PrescriptionId prescriptionId = new PrescriptionId(query.getPrescriptionId());

        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new PrescriptionNotFoundException(query.getPrescriptionId()));

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
    }
}
