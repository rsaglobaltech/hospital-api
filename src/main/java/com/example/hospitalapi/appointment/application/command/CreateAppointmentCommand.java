package com.example.hospitalapi.appointment.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Command to create a new appointment
 */
@Getter
@Builder
public class CreateAppointmentCommand implements Command {
    private final String patientId;
    private final String doctorId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String reason;

}
