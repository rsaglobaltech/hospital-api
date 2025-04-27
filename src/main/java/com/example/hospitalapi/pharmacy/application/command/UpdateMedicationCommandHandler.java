package com.example.hospitalapi.pharmacy.application.command;

import com.example.hospitalapi.pharmacy.domain.entity.Medication;
import com.example.hospitalapi.pharmacy.domain.exception.MedicationNotFoundException;
import com.example.hospitalapi.pharmacy.domain.repository.MedicationRepository;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for updating an existing medication
 */
@Service
@RequiredArgsConstructor
public class UpdateMedicationCommandHandler {
    
    private final MedicationRepository medicationRepository;
    
    /**
     * Handles the command to update an existing medication
     * @param command the command containing updated medication details
     */
    @Transactional
    public void handle(UpdateMedicationCommand command) {
        MedicationId medicationId = new MedicationId(command.getMedicationId());
        
        Medication medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new MedicationNotFoundException(command.getMedicationId()));
        
        medication.updateName(command.getName());
        medication.updateDescription(command.getDescription());
        medication.updateDosageForm(command.getDosageForm());
        medication.updateStrength(command.getStrength());
        medication.updateManufacturer(command.getManufacturer());
        medication.updatePrice(command.getPrice());
        medication.updateRequiresPrescription(command.isRequiresPrescription());
        
        medicationRepository.save(medication);
    }
}