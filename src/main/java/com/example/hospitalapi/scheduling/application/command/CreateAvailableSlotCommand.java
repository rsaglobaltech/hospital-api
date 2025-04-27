package com.example.hospitalapi.scheduling.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Command to create a new available slot
 */
@Builder
public class CreateAvailableSlotCommand implements Command {
    private final String doctorId;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public String getDoctorId() {
        return doctorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
