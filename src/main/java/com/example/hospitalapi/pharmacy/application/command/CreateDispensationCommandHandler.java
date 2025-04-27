package com.example.hospitalapi.pharmacy.application.command;

import com.example.hospitalapi.pharmacy.domain.entity.Dispensation;
import com.example.hospitalapi.pharmacy.domain.entity.Prescription;
import com.example.hospitalapi.pharmacy.domain.exception.MedicationNotFoundException;
import com.example.hospitalapi.pharmacy.domain.exception.PrescriptionAlreadyDispensedException;
import com.example.hospitalapi.pharmacy.domain.exception.PrescriptionNotFoundException;
import com.example.hospitalapi.pharmacy.domain.repository.DispensationRepository;
import com.example.hospitalapi.pharmacy.domain.repository.InventoryRepository;
import com.example.hospitalapi.pharmacy.domain.repository.MedicationRepository;
import com.example.hospitalapi.pharmacy.domain.repository.PrescriptionRepository;
import com.example.hospitalapi.pharmacy.domain.valueobject.DispensationId;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import com.example.hospitalapi.pharmacy.domain.valueobject.PrescriptionId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler for creating a new dispensation
 */
@Service
@RequiredArgsConstructor
public class CreateDispensationCommandHandler {
    
    private final DispensationRepository dispensationRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final MedicationRepository medicationRepository;
    private final InventoryRepository inventoryRepository;
    
    /**
     * Handles the command to create a new dispensation
     * @param command the command containing dispensation details
     * @return the ID of the created dispensation
     */
    @Transactional
    public DispensationId handle(CreateDispensationCommand command) {
        DispensationId dispensationId = new DispensationId();
        PrescriptionId prescriptionId = new PrescriptionId(command.getPrescriptionId());
        
        // Verify prescription exists and is valid
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new PrescriptionNotFoundException(command.getPrescriptionId()));
        
        if (prescription.isDispensed()) {
            throw new PrescriptionAlreadyDispensedException(command.getPrescriptionId());
        }
        
        if (prescription.isExpired()) {
            throw new com.example.hospitalapi.pharmacy.domain.exception.PharmacyValidationException(
                    "Prescription with ID " + command.getPrescriptionId() + " is expired");
        }
        
        // Create dispensation items
        List<Dispensation.DispensationItem> items = command.getItems().stream()
                .map(itemCommand -> {
                    MedicationId medicationId = new MedicationId(itemCommand.getMedicationId());
                    
                    // Verify medication exists
                    medicationRepository.findById(medicationId)
                            .orElseThrow(() -> new MedicationNotFoundException(itemCommand.getMedicationId()));
                    
                    // Update inventory (remove stock)
                    inventoryRepository.findByMedicationId(medicationId)
                            .ifPresent(inventory -> inventory.removeStock(itemCommand.getQuantity()));
                    
                    return new Dispensation.DispensationItem(
                            medicationId,
                            itemCommand.getMedicationName(),
                            itemCommand.getQuantity(),
                            itemCommand.getUnitPrice(),
                            itemCommand.getBatchNumber()
                    );
                })
                .collect(Collectors.toList());
        
        // Create dispensation
        Dispensation dispensation = new Dispensation(
                dispensationId,
                prescriptionId,
                command.getPatientId(),
                command.getPharmacistId(),
                items,
                command.getNotes()
        );
        
        // Mark prescription as dispensed
        prescription.markAsDispensed();
        
        // Save changes
        prescriptionRepository.save(prescription);
        dispensationRepository.save(dispensation);
        
        return dispensationId;
    }
}