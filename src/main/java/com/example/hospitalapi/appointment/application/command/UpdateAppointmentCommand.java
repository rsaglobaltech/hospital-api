package com.example.hospitalapi.appointment.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Command to update an existing appointment
 */
@Builder
public class UpdateAppointmentCommand implements Command {
    private final String appointmentId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String reason;
    private final String notes;

    public String getAppointmentId() {
        return appointmentId;
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

    public String getNotes() {
        return notes;
    }
}
