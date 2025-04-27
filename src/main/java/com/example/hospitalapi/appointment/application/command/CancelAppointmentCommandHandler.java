package com.example.hospitalapi.appointment.application.command;

import com.example.hospitalapi.appointment.domain.entity.Appointment;
import com.example.hospitalapi.appointment.domain.exception.AppointmentNotFoundException;
import com.example.hospitalapi.appointment.domain.repository.AppointmentRepository;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentId;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for CancelAppointmentCommand
 */
@Service
@RequiredArgsConstructor
public class CancelAppointmentCommandHandler implements CommandHandler<CancelAppointmentCommand, Void> {

    private final AppointmentRepository appointmentRepository;

    /**
     * Handle the CancelAppointmentCommand
     * @param command the command to handle
     * @return null (void)
     * @throws AppointmentNotFoundException if the appointment is not found
     */
    @Transactional
    public Void handle(CancelAppointmentCommand command) {
        // Create appointment ID
        AppointmentId appointmentId = new AppointmentId(command.getAppointmentId());

        // Find appointment
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException(appointmentId));

        // Cancel appointment
        appointment.cancel();

        // Save appointment
        appointmentRepository.save(appointment);

        return null;
    }
}
