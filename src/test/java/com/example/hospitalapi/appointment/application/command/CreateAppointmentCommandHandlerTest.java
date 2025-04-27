package com.example.hospitalapi.appointment.application.command;

import com.example.hospitalapi.appointment.domain.entity.Appointment;
import com.example.hospitalapi.appointment.domain.repository.AppointmentRepository;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentId;
import com.example.hospitalapi.patient.domain.exception.PatientNotFoundException;
import com.example.hospitalapi.patient.domain.repository.PatientRepository;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateAppointmentCommandHandlerTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    private CreateAppointmentCommandHandler handler;

    private final UUID patientUuid = UUID.randomUUID();
    private final String patientId = patientUuid.toString();
    private final String doctorId = "doctor-123";
    private final LocalDateTime startTime = LocalDateTime.now().plusHours(1);
    private final LocalDateTime endTime = startTime.plusHours(1);
    private final String reason = "Annual check-up";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new CreateAppointmentCommandHandler(appointmentRepository, patientRepository);
    }

    @Test
    void testHandleCreateAppointmentCommand() {
        // Arrange
        CreateAppointmentCommand command = CreateAppointmentCommand.builder()
                .patientId(patientId)
                .doctorId(doctorId)
                .startTime(startTime)
                .endTime(endTime)
                .reason(reason)
                .build();

        when(patientRepository.existsById(any(PatientId.class))).thenReturn(true);

        AppointmentId expectedAppointmentId = new AppointmentId(UUID.randomUUID());
        Appointment savedAppointment = mock(Appointment.class);
        when(savedAppointment.getId()).thenReturn(expectedAppointmentId);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(savedAppointment);

        // Act
        AppointmentId result = handler.handle(command);

        // Assert
        assertNotNull(result);
        assertEquals(expectedAppointmentId, result);

        verify(patientRepository).existsById(any(PatientId.class));

        ArgumentCaptor<Appointment> appointmentCaptor = ArgumentCaptor.forClass(Appointment.class);
        verify(appointmentRepository).save(appointmentCaptor.capture());

        Appointment capturedAppointment = appointmentCaptor.getValue();
        assertNotNull(capturedAppointment);
    }

    @Test
    void testHandleCreateAppointmentCommandWithNonExistentPatient() {
        // Arrange
        CreateAppointmentCommand command = CreateAppointmentCommand.builder()
                .patientId(patientId)
                .doctorId(doctorId)
                .startTime(startTime)
                .endTime(endTime)
                .reason(reason)
                .build();

        when(patientRepository.existsById(any(PatientId.class))).thenReturn(false);

        // Act & Assert
        assertThrows(PatientNotFoundException.class, () -> handler.handle(command));
        verify(patientRepository).existsById(any(PatientId.class));
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }
}
