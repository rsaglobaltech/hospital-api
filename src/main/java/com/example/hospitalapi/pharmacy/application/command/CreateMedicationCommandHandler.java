package com.example.hospitalapi.pharmacy.application.command;

import com.example.hospitalapi.pharmacy.domain.entity.Medication;
import com.example.hospitalapi.pharmacy.domain.repository.MedicationRepository;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for creating a new medication
 */
@Service
@RequiredArgsConstructor
public class CreateMedicationCommandHandler {
    
    private final MedicationRepository medicationRepository;
    
    /**
     * Handles the command to create a new medication
     * @param command the command containing medication details
     * @return the ID of the created medication
     */
    @Transactional
    public MedicationId handle(CreateMedicationCommand command) {
        MedicationId medicationId = new MedicationId();
        
        Medication medication = new Medication(
                medicationId,
                command.getName(),
                command.getDescription(),
                command.getDosageForm(),
                command.getStrength(),
                command.getManufacturer(),
                command.getPrice(),
                command.isRequiresPrescription()
        );
        
        medicationRepository.save(medication);
        
        return medicationId;
    }
}