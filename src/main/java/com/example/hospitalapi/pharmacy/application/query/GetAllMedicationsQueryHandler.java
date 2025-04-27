package com.example.hospitalapi.pharmacy.application.query;

import com.example.hospitalapi.pharmacy.domain.entity.Medication;
import com.example.hospitalapi.pharmacy.domain.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler for retrieving all medications
 */
@Service
@RequiredArgsConstructor
public class GetAllMedicationsQueryHandler {
    
    private final MedicationRepository medicationRepository;
    
    /**
     * Handles the query to retrieve all medications
     * @param query the query
     * @return list of medication responses
     */
    @Transactional(readOnly = true)
    public List<MedicationResponse> handle(GetAllMedicationsQuery query) {
        List<Medication> medications = medicationRepository.findAll();
        
        return medications.stream()
                .map(medication -> MedicationResponse.builder()
                        .id(medication.getId().toString())
                        .name(medication.getName())
                        .description(medication.getDescription())
                        .dosageForm(medication.getDosageForm())
                        .strength(medication.getStrength())
                        .manufacturer(medication.getManufacturer())
                        .price(medication.getPrice())
                        .requiresPrescription(medication.isRequiresPrescription())
                        .build())
                .collect(Collectors.toList());
    }
}