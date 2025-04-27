package com.example.hospitalapi.appointment.domain.valueobject;

/**
 * Value object representing the status of an appointment
 */
public enum AppointmentStatus {
    SCHEDULED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED,
    NO_SHOW
}