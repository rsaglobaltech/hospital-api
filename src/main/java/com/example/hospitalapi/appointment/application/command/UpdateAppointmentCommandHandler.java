package com.example.hospitalapi.appointment.application.command;

import com.example.hospitalapi.appointment.domain.entity.Appointment;
import com.example.hospitalapi.appointment.domain.repository.AppointmentRepository;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentId;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for UpdateAppointmentCommand
 */
@Service
@RequiredArgsConstructor
public class UpdateAppointmentCommandHandler implements CommandHandler<UpdateAppointmentCommand, Void> {

    private final AppointmentRepository appointmentRepository;

    /**
     * Handle the UpdateAppointmentCommand
     * @param command the command to handle
     * @return null (void)
     * @throws IllegalArgumentException if the appointment is not found
     */
    @Transactional
    public Void handle(UpdateAppointmentCommand command) {
        // Create appointment ID
        AppointmentId appointmentId = new AppointmentId(command.getAppointmentId());

        // Find appointment
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + appointmentId));

        // Update appointment
        appointment.updateTimes(command.getStartTime(), command.getEndTime());
        appointment.updateReason(command.getReason());
        appointment.updateNotes(command.getNotes());

        // Save appointment
        appointmentRepository.save(appointment);

        return null;
    }
}
