package com.example.hospitalapi.appointment.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Query to get an appointment by ID
 */
@Data
@AllArgsConstructor
public class GetAppointmentByIdQuery implements Query<AppointmentResponse> {
    private final String appointmentId;
}
