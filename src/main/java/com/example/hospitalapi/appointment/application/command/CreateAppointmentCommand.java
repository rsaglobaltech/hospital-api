package com.example.hospitalapi.appointment.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Command to create a new appointment
 */
@Builder
public class CreateAppointmentCommand implements Command {
    private final String patientId;
    private final String doctorId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String reason;

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getReason() {
        return reason;
    }
}
