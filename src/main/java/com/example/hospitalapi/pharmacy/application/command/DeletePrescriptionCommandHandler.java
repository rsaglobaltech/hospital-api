package com.example.hospitalapi.pharmacy.application.command;

import com.example.hospitalapi.pharmacy.domain.entity.Prescription;
import com.example.hospitalapi.pharmacy.domain.exception.PrescriptionAlreadyDispensedException;
import com.example.hospitalapi.pharmacy.domain.exception.PrescriptionNotFoundException;
import com.example.hospitalapi.pharmacy.domain.repository.PrescriptionRepository;
import com.example.hospitalapi.pharmacy.domain.valueobject.PrescriptionId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for deleting a prescription
 */
@Service
@RequiredArgsConstructor
public class DeletePrescriptionCommandHandler {
    
    private final PrescriptionRepository prescriptionRepository;
    
    /**
     * Handles the command to delete a prescription
     * @param command the command containing the prescription ID to delete
     */
    @Transactional
    public void handle(DeletePrescriptionCommand command) {
        PrescriptionId prescriptionId = new PrescriptionId(command.getPrescriptionId());
        
        // Check if prescription exists
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new PrescriptionNotFoundException(command.getPrescriptionId()));
        
        // Cannot delete a prescription that has already been dispensed
        if (prescription.isDispensed()) {
            throw new PrescriptionAlreadyDispensedException(command.getPrescriptionId());
        }
        
        prescriptionRepository.deleteById(prescriptionId);
    }
}