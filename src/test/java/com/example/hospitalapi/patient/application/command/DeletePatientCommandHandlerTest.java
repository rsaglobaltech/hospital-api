package com.example.hospitalapi.patient.application.command;

import com.example.hospitalapi.patient.domain.event.PatientDeletedEvent;
import com.example.hospitalapi.patient.domain.exception.PatientNotFoundException;
import com.example.hospitalapi.patient.domain.repository.PatientRepository;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.shared.domain.event.EventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeletePatientCommandHandlerTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private EventPublisher eventPublisher;

    private DeletePatientCommandHandler handler;

    private final UUID patientUuid = UUID.randomUUID();
    private final String patientId = patientUuid.toString();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new DeletePatientCommandHandler(patientRepository, eventPublisher);
    }

    @Test
    void testHandleDeletePatientCommand() {
        // Arrange
        DeletePatientCommand command = new DeletePatientCommand(patientId);

        when(patientRepository.existsById(any(PatientId.class))).thenReturn(true);

        // Act
        handler.handle(command);

        // Assert
        verify(patientRepository).existsById(any(PatientId.class));
        verify(eventPublisher).publish(any(PatientDeletedEvent.class));
        verify(patientRepository).deleteById(any(PatientId.class));
    }

    @Test
    void testHandleDeletePatientCommandWithNonExistentPatient() {
        // Arrange
        DeletePatientCommand command = new DeletePatientCommand(patientId);

        when(patientRepository.existsById(any(PatientId.class))).thenReturn(false);

        // Act & Assert
        assertThrows(PatientNotFoundException.class, () -> handler.handle(command));
        verify(patientRepository).existsById(any(PatientId.class));
        verify(eventPublisher, never()).publish(any(PatientDeletedEvent.class));
        verify(patientRepository, never()).deleteById(any(PatientId.class));
    }
}
