package com.example.hospitalapi.pharmacy.application.command;

import com.example.hospitalapi.pharmacy.domain.entity.Prescription;
import com.example.hospitalapi.pharmacy.domain.exception.PrescriptionAlreadyDispensedException;
import com.example.hospitalapi.pharmacy.domain.exception.PrescriptionNotFoundException;
import com.example.hospitalapi.pharmacy.domain.repository.MedicationRepository;
import com.example.hospitalapi.pharmacy.domain.repository.PrescriptionRepository;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import com.example.hospitalapi.pharmacy.domain.valueobject.PrescriptionId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler for updating an existing prescription
 */
@Service
@RequiredArgsConstructor
public class UpdatePrescriptionCommandHandler {
    
    private final PrescriptionRepository prescriptionRepository;
    private final MedicationRepository medicationRepository;
    
    /**
     * Handles the command to update an existing prescription
     * @param command the command containing updated prescription details
     */
    @Transactional
    public void handle(UpdatePrescriptionCommand command) {
        PrescriptionId prescriptionId = new PrescriptionId(command.getPrescriptionId());
        
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new PrescriptionNotFoundException(command.getPrescriptionId()));
        
        // Cannot update a prescription that has already been dispensed
        if (prescription.isDispensed()) {
            throw new PrescriptionAlreadyDispensedException(command.getPrescriptionId());
        }
        
        // Update expiration date
        prescription.updateExpirationDate(command.getExpirationDate());
        
        // Update notes
        prescription.updateNotes(command.getNotes());
        
        // We don't update items directly in the prescription entity
        // Instead, we would need to create a new prescription with the updated items
        // This is a simplification for this example
        
        prescriptionRepository.save(prescription);
    }
}