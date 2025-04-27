package com.example.hospitalapi.appointment.application.command;

import com.example.hospitalapi.appointment.domain.entity.Appointment;
import com.example.hospitalapi.appointment.domain.exception.AppointmentNotFoundException;
import com.example.hospitalapi.appointment.domain.repository.AppointmentRepository;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CancelAppointmentCommandHandlerTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private Appointment appointment;

    private CancelAppointmentCommandHandler handler;

    private final UUID appointmentUuid = UUID.randomUUID();
    private final String appointmentId = appointmentUuid.toString();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new CancelAppointmentCommandHandler(appointmentRepository);
    }

    @Test
    void testHandleCancelAppointmentCommand() {
        // Arrange
        CancelAppointmentCommand command = new CancelAppointmentCommand(appointmentId);

        when(appointmentRepository.findById(any(AppointmentId.class))).thenReturn(Optional.of(appointment));

        // Act
        handler.handle(command);

        // Assert
        verify(appointmentRepository).findById(any(AppointmentId.class));
        verify(appointment).cancel();
        verify(appointmentRepository).save(appointment);
    }

    @Test
    void testHandleCancelAppointmentCommandWithNonExistentAppointment() {
        // Arrange
        CancelAppointmentCommand command = new CancelAppointmentCommand(appointmentId);

        when(appointmentRepository.findById(any(AppointmentId.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppointmentNotFoundException.class, () -> handler.handle(command));
        verify(appointmentRepository).findById(any(AppointmentId.class));
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }
}
