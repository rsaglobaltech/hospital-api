package com.example.hospitalapi.appointment.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;

import java.util.List;

/**
 * Query to get all appointments
 */
public class GetAllAppointmentsQuery implements Query<List<AppointmentResponse>> {
    // No parameters needed
}
