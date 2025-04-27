package com.example.hospitalapi.appointment.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Command to cancel an appointment
 */
@Getter
@AllArgsConstructor
public class CancelAppointmentCommand implements Command {
    private final String appointmentId;
}
