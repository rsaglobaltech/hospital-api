package com.example.hospitalapi.appointment.application.query;

import com.example.hospitalapi.appointment.domain.entity.Appointment;
import com.example.hospitalapi.appointment.domain.repository.AppointmentRepository;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler for GetAllAppointmentsQuery
 */
@Service
@RequiredArgsConstructor
public class GetAllAppointmentsQueryHandler implements QueryHandler<GetAllAppointmentsQuery, List<AppointmentResponse>> {

    private final AppointmentRepository appointmentRepository;

    /**
     * Handle the GetAllAppointmentsQuery
     * @param query the query to handle
     * @return list of all appointments
     */
    @Transactional(readOnly = true)
    public List<AppointmentResponse> handle(GetAllAppointmentsQuery query) {
        // Find all appointments
        List<Appointment> appointments = appointmentRepository.findAll();

        // Map to response
        return appointments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
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
