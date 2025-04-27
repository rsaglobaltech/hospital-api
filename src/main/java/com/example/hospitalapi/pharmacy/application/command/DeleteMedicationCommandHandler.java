package com.example.hospitalapi.pharmacy.application.command;

import com.example.hospitalapi.pharmacy.domain.exception.MedicationNotFoundException;
import com.example.hospitalapi.pharmacy.domain.repository.MedicationRepository;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for deleting a medication
 */
@Service
@RequiredArgsConstructor
public class DeleteMedicationCommandHandler {
    
    private final MedicationRepository medicationRepository;
    
    /**
     * Handles the command to delete a medication
     * @param command the command containing the medication ID to delete
     */
    @Transactional
    public void handle(DeleteMedicationCommand command) {
        MedicationId medicationId = new MedicationId(command.getMedicationId());
        
        if (!medicationRepository.existsById(medicationId)) {
            throw new MedicationNotFoundException(command.getMedicationId());
        }
        
        medicationRepository.deleteById(medicationId);
    }
}