package com.example.hospitalapi.patient.application.command;

import com.example.hospitalapi.patient.domain.entity.Patient;
import com.example.hospitalapi.patient.domain.event.PatientUpdatedEvent;
import com.example.hospitalapi.patient.domain.exception.PatientNotFoundException;
import com.example.hospitalapi.patient.domain.repository.PatientRepository;
import com.example.hospitalapi.patient.domain.valueobject.Email;
import com.example.hospitalapi.patient.domain.valueobject.Name;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.patient.domain.valueobject.PhoneNumber;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import com.example.hospitalapi.shared.domain.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for UpdatePatientCommand
 */
@Service
@RequiredArgsConstructor
public class UpdatePatientCommandHandler implements CommandHandler<UpdatePatientCommand, Void> {

    private final PatientRepository patientRepository;
    private final EventPublisher eventPublisher;

    /**
     * Handle the UpdatePatientCommand
     * @param command the command to handle
     * @return null (void)
     * @throws PatientNotFoundException if the patient is not found
     */
    @Transactional
    public Void handle(UpdatePatientCommand command) {
        // Create patient ID
        PatientId patientId = new PatientId(command.getPatientId());

        // Find patient
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException(patientId));

        // Update patient
        Name name = new Name(command.getFirstName(), command.getLastName());
        Email email = new Email(command.getEmail());
        PhoneNumber phoneNumber = new PhoneNumber(command.getPhoneNumber());

        patient.updateName(name);
        patient.updateEmail(email);
        patient.updatePhoneNumber(phoneNumber);
        patient.updateAddress(command.getAddress());
        patient.updateMedicalHistory(command.getMedicalHistory());

        // Save patient
        Patient savedPatient = patientRepository.save(patient);

        // Publish event
        eventPublisher.publish(new PatientUpdatedEvent(savedPatient));

        return null;
    }
}
