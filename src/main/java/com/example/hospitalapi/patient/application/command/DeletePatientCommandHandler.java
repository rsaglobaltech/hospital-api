package com.example.hospitalapi.patient.application.command;

import com.example.hospitalapi.patient.domain.event.PatientDeletedEvent;
import com.example.hospitalapi.patient.domain.exception.PatientNotFoundException;
import com.example.hospitalapi.patient.domain.repository.PatientRepository;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import com.example.hospitalapi.shared.domain.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for DeletePatientCommand
 */
@Service
@RequiredArgsConstructor
public class DeletePatientCommandHandler implements CommandHandler<DeletePatientCommand, Void> {

    private final PatientRepository patientRepository;
    private final EventPublisher eventPublisher;

    /**
     * Handle the DeletePatientCommand
     * @param command the command to handle
     * @return null (void)
     * @throws PatientNotFoundException if the patient is not found
     */
    @Transactional
    public Void handle(DeletePatientCommand command) {
        // Create patient ID
        PatientId patientId = new PatientId(command.getPatientId());

        // Check if patient exists
        if (!patientRepository.existsById(patientId)) {
            throw new PatientNotFoundException(patientId);
        }

        // Publish event before deleting the patient
        eventPublisher.publish(new PatientDeletedEvent(patientId));

        // Delete patient
        patientRepository.deleteById(patientId);

        return null;
    }
}
