package com.example.hospitalapi.appointment.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Value object representing the time period of an appointment
 */
@Getter
@EqualsAndHashCode
public class AppointmentTime {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public AppointmentTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("Start time cannot be null");
        }
        if (endTime == null) {
            throw new IllegalArgumentException("End time cannot be null");
        }
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }
        
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    /**
     * Returns the duration of the appointment in minutes
     */
    public long getDurationMinutes() {
        return java.time.Duration.between(startTime, endTime).toMinutes();
    }
    
    /**
     * Checks if this appointment time overlaps with another appointment time
     */
    public boolean overlaps(AppointmentTime other) {
        return (this.startTime.isBefore(other.endTime) && this.endTime.isAfter(other.startTime));
    }
}