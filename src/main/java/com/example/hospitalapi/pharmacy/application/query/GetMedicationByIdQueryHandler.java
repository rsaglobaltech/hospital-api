package com.example.hospitalapi.pharmacy.application.query;

import com.example.hospitalapi.pharmacy.domain.entity.Medication;
import com.example.hospitalapi.pharmacy.domain.exception.MedicationNotFoundException;
import com.example.hospitalapi.pharmacy.domain.repository.MedicationRepository;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for retrieving a medication by ID
 */
@Service
@RequiredArgsConstructor
public class GetMedicationByIdQueryHandler {
    
    private final MedicationRepository medicationRepository;
    
    /**
     * Handles the query to retrieve a medication by ID
     * @param query the query containing the medication ID
     * @return the medication response
     */
    @Transactional(readOnly = true)
    public MedicationResponse handle(GetMedicationByIdQuery query) {
        MedicationId medicationId = new MedicationId(query.getMedicationId());
        
        Medication medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new MedicationNotFoundException(query.getMedicationId()));
        
        return MedicationResponse.builder()
                .id(medication.getId().toString())
                .name(medication.getName())
                .description(medication.getDescription())
                .dosageForm(medication.getDosageForm())
                .strength(medication.getStrength())
                .manufacturer(medication.getManufacturer())
                .price(medication.getPrice())
                .requiresPrescription(medication.isRequiresPrescription())
                .build();
    }
}