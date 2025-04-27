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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetAllAppointmentsQueryHandlerTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private Appointment appointment1;

    @Mock
    private Appointment appointment2;

    private GetAllAppointmentsQueryHandler handler;

    private final UUID appointmentUuid1 = UUID.randomUUID();
    private final UUID appointmentUuid2 = UUID.randomUUID();
    private final AppointmentId appointmentId1 = new AppointmentId(appointmentUuid1);
    private final AppointmentId appointmentId2 = new AppointmentId(appointmentUuid2);
    private final UUID patientUuid1 = UUID.randomUUID();
    private final UUID patientUuid2 = UUID.randomUUID();
    private final PatientId patientId1 = new PatientId(patientUuid1);
    private final PatientId patientId2 = new PatientId(patientUuid2);
    private final String doctorId1 = UUID.randomUUID().toString();
    private final String doctorId2 = UUID.randomUUID().toString();
    private final LocalDateTime startTime1 = LocalDateTime.now().plusHours(1);
    private final LocalDateTime endTime1 = startTime1.plusHours(1);
    private final LocalDateTime startTime2 = LocalDateTime.now().plusDays(1);
    private final LocalDateTime endTime2 = startTime2.plusHours(1);
    private final String reason1 = "Annual check-up";
    private final String reason2 = "Follow-up appointment";
    private final String notes1 = "Patient requested a follow-up appointment";
    private final String notes2 = "Patient has a history of high blood pressure";
    private final AppointmentStatus status1 = AppointmentStatus.SCHEDULED;
    private final AppointmentStatus status2 = AppointmentStatus.COMPLETED;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new GetAllAppointmentsQueryHandler(appointmentRepository);

        // Setup appointment1 mock
        when(appointment1.getId()).thenReturn(appointmentId1);
        when(appointment1.getPatientId()).thenReturn(patientId1);
        when(appointment1.getDoctorId()).thenReturn(new DoctorId(doctorId1));
        when(appointment1.getAppointmentTime().getStartTime()).thenReturn(startTime1);
        when(appointment1.getAppointmentTime().getEndTime()).thenReturn(endTime1);
        when(appointment1.getReason()).thenReturn(new AppointmentReason(reason1));
        when(appointment1.getNotes()).thenReturn(new AppointmentNotes(notes1));
        when(appointment1.getStatus()).thenReturn(status1);

        // Setup appointment2 mock
        when(appointment2.getId()).thenReturn(appointmentId2);
        when(appointment2.getPatientId()).thenReturn(patientId2);
        when(appointment2.getDoctorId()).thenReturn(new DoctorId(doctorId2));
        when(appointment2.getAppointmentTime().getStartTime()).thenReturn(startTime2);
        when(appointment2.getAppointmentTime().getEndTime()).thenReturn(endTime2);
        when(appointment2.getReason()).thenReturn(new AppointmentReason(reason2));
        when(appointment2.getNotes()).thenReturn(new AppointmentNotes(notes2));
        when(appointment2.getStatus()).thenReturn(status2);
    }

    @Test
    void testHandleGetAllAppointmentsQuery() {
        // Arrange
        GetAllAppointmentsQuery query = new GetAllAppointmentsQuery();

        when(appointmentRepository.findAll()).thenReturn(Arrays.asList(appointment1, appointment2));

        // Act
        List<AppointmentResponse> response = handler.handle(query);

        // Assert
        verify(appointmentRepository).findAll();
        
        assertNotNull(response);
        assertEquals(2, response.size());
        
        AppointmentResponse response1 = response.get(0);
        assertEquals(appointmentUuid1.toString(), response1.getId());
        assertEquals(patientUuid1.toString(), response1.getPatientId());
        assertEquals(doctorId1, response1.getDoctorId());
        assertEquals(startTime1, response1.getStartTime());
        assertEquals(endTime1, response1.getEndTime());
        assertEquals(reason1, response1.getReason());
        assertEquals(notes1, response1.getNotes());
        assertEquals(status1, response1.getStatus());
        
        AppointmentResponse response2 = response.get(1);
        assertEquals(appointmentUuid2.toString(), response2.getId());
        assertEquals(patientUuid2.toString(), response2.getPatientId());
        assertEquals(doctorId2, response2.getDoctorId());
        assertEquals(startTime2, response2.getStartTime());
        assertEquals(endTime2, response2.getEndTime());
        assertEquals(reason2, response2.getReason());
        assertEquals(notes2, response2.getNotes());
        assertEquals(status2, response2.getStatus());
    }

    @Test
    void testHandleGetAllAppointmentsQueryWithEmptyList() {
        // Arrange
        GetAllAppointmentsQuery query = new GetAllAppointmentsQuery();

        when(appointmentRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<AppointmentResponse> response = handler.handle(query);

        // Assert
        verify(appointmentRepository).findAll();
        
        assertNotNull(response);
        assertTrue(response.isEmpty());
    }
}