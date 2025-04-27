package com.example.hospitalapi.pharmacy.application.command;

import com.example.hospitalapi.medicalstaff.domain.valueobject.DoctorId;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.pharmacy.domain.entity.Prescription;
import com.example.hospitalapi.pharmacy.domain.repository.MedicationRepository;
import com.example.hospitalapi.pharmacy.domain.repository.PrescriptionRepository;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import com.example.hospitalapi.pharmacy.domain.valueobject.PrescriptionDate;
import com.example.hospitalapi.pharmacy.domain.valueobject.PrescriptionId;
import com.example.hospitalapi.pharmacy.domain.valueobject.PrescriptionNotes;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler for creating a new prescription
 */
@Service
@RequiredArgsConstructor
public class CreatePrescriptionCommandHandler implements CommandHandler<CreatePrescriptionCommand, PrescriptionId> {

    private final PrescriptionRepository prescriptionRepository;
    private final MedicationRepository medicationRepository;

    /**
     * Handles the command to create a new prescription
     * @param command the command containing prescription details
     * @return the ID of the created prescription
     */
    @Transactional
    public PrescriptionId handle(CreatePrescriptionCommand command) {
        PrescriptionId prescriptionId = new PrescriptionId();

        List<Prescription.PrescriptionItem> items = command.getItems().stream()
                .map(itemCommand -> {
                    MedicationId medicationId = new MedicationId(itemCommand.getMedicationId());

                    // Verify medication exists (will throw exception if not found)
                    medicationRepository.findById(medicationId)
                            .orElseThrow(() -> new com.example.hospitalapi.pharmacy.domain.exception.MedicationNotFoundException(
                                    itemCommand.getMedicationId()));

                    return new Prescription.PrescriptionItem(
                            medicationId,
                            itemCommand.getQuantity(),
                            itemCommand.getInstructions()
                    );
                })
                .collect(Collectors.toList());

        Prescription prescription = new Prescription(
                prescriptionId,
                new PatientId(command.getPatientId()),
                new DoctorId(command.getDoctorId()),
                command.getIssueDate(),
                command.getExpirationDate(),
                items,
                command.getNotes()
        );

        prescriptionRepository.save(prescription);

        return prescriptionId;
    }
}
