package com.example.hospitalapi.appointment.domain.exception;

import com.example.hospitalapi.appointment.domain.valueobject.AppointmentId;

/**
 * Exception thrown when an appointment is not found
 */
public class AppointmentNotFoundException extends AppointmentException {

    /**
     * Create a new AppointmentNotFoundException with an appointment ID
     * @param appointmentId the ID of the appointment that was not found
     */
    public AppointmentNotFoundException(AppointmentId appointmentId) {
        super("Appointment not found with ID: " + appointmentId);
    }

    /**
     * Create a new AppointmentNotFoundException with a message
     * @param message the exception message
     */
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}