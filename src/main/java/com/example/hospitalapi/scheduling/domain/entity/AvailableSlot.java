package com.example.hospitalapi.scheduling.domain.entity;

import com.example.hospitalapi.scheduling.domain.exception.AvailableSlotValidationException;
import com.example.hospitalapi.scheduling.domain.valueobject.AvailableSlotId;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entity representing an available time slot for appointments
 */
@Getter
public class AvailableSlot {
    private final AvailableSlotId id;
    private final String doctorId;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private boolean booked;

    public AvailableSlot(AvailableSlotId id, String doctorId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        if (id == null) {
            throw new AvailableSlotValidationException("Available slot ID cannot be null");
        }
        if (doctorId == null || doctorId.trim().isEmpty()) {
            throw new AvailableSlotValidationException("Doctor ID cannot be empty");
        }
        if (date == null) {
            throw new AvailableSlotValidationException("Date cannot be null");
        }
        if (startTime == null) {
            throw new AvailableSlotValidationException("Start time cannot be null");
        }
        if (endTime == null) {
            throw new AvailableSlotValidationException("End time cannot be null");
        }
        if (startTime.isAfter(endTime)) {
            throw new AvailableSlotValidationException("Start time cannot be after end time");
        }

        this.id = id;
        this.doctorId = doctorId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.booked = false;
    }

    /**
     * Mark the slot as booked
     */
    public void book() {
        if (this.booked) {
            throw new AvailableSlotValidationException("Slot is already booked");
        }
        this.booked = true;
    }

    /**
     * Mark the slot as available
     */
    public void unbook() {
        if (!this.booked) {
            throw new AvailableSlotValidationException("Slot is already available");
        }
        this.booked = false;
    }

    /**
     * Check if the slot is available
     * @return true if the slot is available, false if it's booked
     */
    public boolean isAvailable() {
        return !this.booked;
    }
}
