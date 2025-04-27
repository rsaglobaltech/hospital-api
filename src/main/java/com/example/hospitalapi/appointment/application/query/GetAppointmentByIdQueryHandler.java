package com.example.hospitalapi.appointment.application.query;

import com.example.hospitalapi.appointment.domain.entity.Appointment;
import com.example.hospitalapi.appointment.domain.repository.AppointmentRepository;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentId;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for GetAppointmentByIdQuery
 */
@Service
@RequiredArgsConstructor
public class GetAppointmentByIdQueryHandler implements QueryHandler<GetAppointmentByIdQuery, AppointmentResponse> {

    private final AppointmentRepository appointmentRepository;

    /**
     * Handle the GetAppointmentByIdQuery
     * @param query the query to handle
     * @return the appointment data
     * @throws IllegalArgumentException if the appointment is not found
     */
    @Transactional(readOnly = true)
    public AppointmentResponse handle(GetAppointmentByIdQuery query) {
        // Create appointment ID
        AppointmentId appointmentId = new AppointmentId(query.getAppointmentId());

        // Find appointment
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + appointmentId));

        // Map to response
        return mapToResponse(appointment);
    }

    private AppointmentResponse mapToResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId().toString())
                .patientId(appointment.getPatientId().toString())
                .doctorId(appointment.getDoctorId().toString())
                .startTime(appointment.getAppointmentTime().getStartTime())
                .endTime(appointment.getAppointmentTime().getEndTime())
                .reason(appointment.getReason().toString())
                .notes(appointment.getNotes().toString())
                .status(appointment.getStatus())
                .build();
    }
}
