package com.example.hospitalapi.appointment.application.command;

import com.example.hospitalapi.appointment.domain.entity.Appointment;
import com.example.hospitalapi.appointment.domain.repository.AppointmentRepository;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentId;
import com.example.hospitalapi.medicalstaff.domain.valueobject.DoctorId;
import com.example.hospitalapi.patient.domain.exception.PatientNotFoundException;
import com.example.hospitalapi.patient.domain.repository.PatientRepository;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import com.example.hospitalapi.shared.domain.event.DomainEvent;
import com.example.hospitalapi.shared.domain.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for CreateAppointmentCommand
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CreateAppointmentCommandHandler implements CommandHandler<CreateAppointmentCommand, AppointmentId> {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final EventPublisher eventPublisher;

    /**
     * Handle the CreateAppointmentCommand
     * @param command the command to handle
     * @return the ID of the created appointment
     * @throws PatientNotFoundException if the patient is not found
     */
    @Transactional
    public AppointmentId handle(CreateAppointmentCommand command) {
        log.debug("Handling CreateAppointmentCommand for patient ID: {} and doctor ID: {}",
                 command.getPatientId(), command.getDoctorId());

        try {
            // Create value objects
            log.trace("Creating value objects for appointment");
            AppointmentId appointmentId = new AppointmentId();
            PatientId patientId = new PatientId(command.getPatientId());
            DoctorId doctorId = new DoctorId(command.getDoctorId());

            // Validate patient exists
            if (!patientRepository.existsById(patientId)) {
                throw new PatientNotFoundException(patientId);
            }

            // Create appointment entity
            log.debug("Creating appointment entity with ID: {}", appointmentId);
            Appointment appointment = new Appointment(
                appointmentId,
                patientId,
                doctorId,
                command.getStartTime(),
                command.getEndTime(),
                command.getReason()
            );

            // Save appointment
            log.debug("Saving appointment to repository");
            Appointment savedAppointment = appointmentRepository.save(appointment);

            // Publish domain events
            log.debug("Publishing domain events for appointment ID: {}", savedAppointment.getId());
            savedAppointment.getDomainEvents().forEach(event -> {
                if (event instanceof DomainEvent) {
                    eventPublisher.publish((DomainEvent) event);
                }
            });

            // Clear domain events after publishing
            savedAppointment.clearDomainEvents();

            log.info("Appointment created successfully with ID: {}", savedAppointment.getId());
            return savedAppointment.getId();
        } catch (Exception e) {
            log.error("Error creating appointment: {}", e.getMessage(), e);
            throw e;
        }
    }
}
