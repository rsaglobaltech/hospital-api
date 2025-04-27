package com.example.hospitalapi.appointment.application.command;

import com.example.hospitalapi.appointment.domain.entity.Appointment;
import com.example.hospitalapi.appointment.domain.repository.AppointmentRepository;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateAppointmentCommandHandlerTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private Appointment appointment;

    private UpdateAppointmentCommandHandler handler;

    private final UUID appointmentUuid = UUID.randomUUID();
    private final String appointmentId = appointmentUuid.toString();
    private final LocalDateTime startTime = LocalDateTime.now().plusHours(1);
    private final LocalDateTime endTime = startTime.plusHours(1);
    private final String reason = "Annual check-up";
    private final String notes = "Patient requested a follow-up appointment";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new UpdateAppointmentCommandHandler(appointmentRepository);
    }

    @Test
    void testHandleUpdateAppointmentCommand() {
        // Arrange
        UpdateAppointmentCommand command = UpdateAppointmentCommand.builder()
                .appointmentId(appointmentId)
                .startTime(startTime)
                .endTime(endTime)
                .reason(reason)
                .notes(notes)
                .build();

        when(appointmentRepository.findById(any(AppointmentId.class))).thenReturn(Optional.of(appointment));

        // Act
        handler.handle(command);

        // Assert
        verify(appointmentRepository).findById(any(AppointmentId.class));
        verify(appointment).updateTimes(startTime, endTime);
        verify(appointment).updateReason(reason);
        verify(appointment).updateNotes(notes);
        verify(appointmentRepository).save(appointment);
    }

    @Test
    void testHandleUpdateAppointmentCommandWithNonExistentAppointment() {
        // Arrange
        UpdateAppointmentCommand command = UpdateAppointmentCommand.builder()
                .appointmentId(appointmentId)
                .startTime(startTime)
                .endTime(endTime)
                .reason(reason)
                .notes(notes)
                .build();

        when(appointmentRepository.findById(any(AppointmentId.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> handler.handle(command));
        verify(appointmentRepository).findById(any(AppointmentId.class));
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }
}