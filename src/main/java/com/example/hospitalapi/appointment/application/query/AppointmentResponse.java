package com.example.hospitalapi.appointment.application.query;

import com.example.hospitalapi.appointment.domain.valueobject.AppointmentStatus;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * DTO for appointment data
 */
@Builder
public class AppointmentResponse {
    private final String id;
    private final String patientId;
    private final String doctorId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String reason;
    private final String notes;
    private final AppointmentStatus status;

    // Getters
    public String getId() {
        return id;
    }

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

    public String getNotes() {
        return notes;
    }

    public AppointmentStatus getStatus() {
        return status;
    }
}
