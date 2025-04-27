package com.example.hospitalapi.appointment.application.query;

import com.example.hospitalapi.appointment.domain.entity.Appointment;
import com.example.hospitalapi.appointment.domain.repository.AppointmentRepository;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentId;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentNotes;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentReason;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentStatus;
import com.example.hospitalapi.medicalstaff.domain.valueobject.DoctorId;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetAppointmentByIdQueryHandlerTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private Appointment appointment;

    private GetAppointmentByIdQueryHandler handler;

    private final UUID appointmentUuid = UUID.randomUUID();
    private final String appointmentId = appointmentUuid.toString();
    private final AppointmentId appointmentIdObj = new AppointmentId(appointmentUuid);
    private final UUID patientUuid = UUID.randomUUID();
    private final PatientId patientId = new PatientId(patientUuid);
    private final String doctorId = "doctor-123";
    private final LocalDateTime startTime = LocalDateTime.now().plusHours(1);
    private final LocalDateTime endTime = startTime.plusHours(1);
    private final String reason = "Annual check-up";
    private final String notes = "Patient requested a follow-up appointment";
    private final AppointmentStatus status = AppointmentStatus.SCHEDULED;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new GetAppointmentByIdQueryHandler(appointmentRepository);

        // Setup appointment mock
        when(appointment.getId()).thenReturn(appointmentIdObj);
        when(appointment.getPatientId()).thenReturn(patientId);
        when(appointment.getDoctorId()).thenReturn(new DoctorId(doctorId));
        when(appointment.getAppointmentTime().getStartTime()).thenReturn(startTime);
        when(appointment.getAppointmentTime().getEndTime()).thenReturn(endTime);
        when(appointment.getReason()).thenReturn(new AppointmentReason(reason));
        when(appointment.getNotes()).thenReturn(new AppointmentNotes(notes));
        when(appointment.getStatus()).thenReturn(status);
    }

    @Test
    void testHandleGetAppointmentByIdQuery() {
        // Arrange
        GetAppointmentByIdQuery query = new GetAppointmentByIdQuery(appointmentId);

        when(appointmentRepository.findById(any(AppointmentId.class))).thenReturn(Optional.of(appointment));

        // Act
        AppointmentResponse response = handler.handle(query);

        // Assert
        verify(appointmentRepository).findById(any(AppointmentId.class));
        
        assertNotNull(response);
        assertEquals(appointmentId, response.getId());
        assertEquals(patientId.toString(), response.getPatientId());
        assertEquals(doctorId, response.getDoctorId());
        assertEquals(startTime, response.getStartTime());
        assertEquals(endTime, response.getEndTime());
        assertEquals(reason, response.getReason());
        assertEquals(notes, response.getNotes());
        assertEquals(status, response.getStatus());
    }

    @Test
    void testHandleGetAppointmentByIdQueryWithNonExistentAppointment() {
        // Arrange
        GetAppointmentByIdQuery query = new GetAppointmentByIdQuery(appointmentId);

        when(appointmentRepository.findById(any(AppointmentId.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> handler.handle(query));
        verify(appointmentRepository).findById(any(AppointmentId.class));
    }
}