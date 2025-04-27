package com.example.hospitalapi.patient.application.command;

import com.example.hospitalapi.patient.domain.entity.Patient;
import com.example.hospitalapi.patient.domain.event.PatientCreatedEvent;
import com.example.hospitalapi.patient.domain.repository.PatientRepository;
import com.example.hospitalapi.patient.domain.valueobject.Email;
import com.example.hospitalapi.patient.domain.valueobject.Name;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.patient.domain.valueobject.PhoneNumber;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import com.example.hospitalapi.shared.domain.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for CreatePatientCommand
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CreatePatientCommandHandler implements CommandHandler<CreatePatientCommand, PatientId> {

    private final PatientRepository patientRepository;
    private final EventPublisher eventPublisher;

    /**
     * Handle the CreatePatientCommand
     * @param command the command to handle
     * @return the ID of the created patient
     */
    @Transactional
    public PatientId handle(CreatePatientCommand command) {
        log.debug("Handling CreatePatientCommand for patient with email: {}", command.getEmail());

        try {
            // Create value objects
            log.trace("Creating value objects for patient");
            PatientId patientId = new PatientId();
            Name name = new Name(command.getFirstName(), command.getLastName());
            Email email = new Email(command.getEmail());
            PhoneNumber phoneNumber = new PhoneNumber(command.getPhoneNumber());

            // Create patient entity
            log.debug("Creating patient entity with ID: {}", patientId);
            Patient patient = new Patient(
                patientId,
                name,
                email,
                phoneNumber,
                command.getDateOfBirth(),
                command.getAddress()
            );

            // Save patient
            log.debug("Saving patient to repository");
            Patient savedPatient = patientRepository.save(patient);

            // Publish event
            log.debug("Publishing PatientCreatedEvent for patient ID: {}", savedPatient.getId());
            eventPublisher.publish(new PatientCreatedEvent(savedPatient));

            log.info("Patient created successfully with ID: {}", savedPatient.getId());
            return savedPatient.getId();
        } catch (Exception e) {
            log.error("Error creating patient with email {}: {}", command.getEmail(), e.getMessage(), e);
            throw e;
        }
    }
}
