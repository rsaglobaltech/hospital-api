package com.example.hospitalapi.appointment.application.command;

import com.example.hospitalapi.appointment.domain.entity.Appointment;
import com.example.hospitalapi.appointment.domain.repository.AppointmentRepository;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentId;
import com.example.hospitalapi.medicalstaff.domain.valueobject.DoctorId;
import com.example.hospitalapi.patient.domain.exception.PatientNotFoundException;
import com.example.hospitalapi.patient.domain.repository.PatientRepository;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for CreateAppointmentCommand
 */
@Service
@RequiredArgsConstructor
public class CreateAppointmentCommandHandler implements CommandHandler<CreateAppointmentCommand, AppointmentId> {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    /**
     * Handle the CreateAppointmentCommand
     * @param command the command to handle
     * @return the ID of the created appointment
     * @throws PatientNotFoundException if the patient is not found
     */
    @Transactional
    public AppointmentId handle(CreateAppointmentCommand command) {
        // Create value objects
        AppointmentId appointmentId = new AppointmentId();
        PatientId patientId = new PatientId(command.getPatientId());
        DoctorId doctorId = new DoctorId(command.getDoctorId());

        // Validate patient exists
        if (!patientRepository.existsById(patientId)) {
            throw new PatientNotFoundException(patientId);
        }

        // Create appointment entity
        Appointment appointment = new Appointment(
            appointmentId,
            patientId,
            doctorId,
            command.getStartTime(),
            command.getEndTime(),
            command.getReason()
        );

        // Save appointment
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return savedAppointment.getId();
    }
}
