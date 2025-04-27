package com.example.hospitalapi.patient.application.command;

import com.example.hospitalapi.patient.domain.entity.Patient;
import com.example.hospitalapi.patient.domain.event.PatientCreatedEvent;
import com.example.hospitalapi.patient.domain.repository.PatientRepository;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.shared.domain.event.EventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreatePatientCommandHandlerTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private EventPublisher eventPublisher;

    private CreatePatientCommandHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new CreatePatientCommandHandler(patientRepository, eventPublisher);
    }

    @Test
    void testHandleCreatePatientCommand() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String phoneNumber = "+1234567890";
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        String address = "123 Main St, City, Country";

        CreatePatientCommand command = CreatePatientCommand.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .build();

        PatientId expectedPatientId = new PatientId(UUID.randomUUID());
        Patient savedPatient = mock(Patient.class);
        when(savedPatient.getId()).thenReturn(expectedPatientId);
        when(patientRepository.save(any(Patient.class))).thenReturn(savedPatient);

        // Act
        PatientId result = handler.handle(command);

        // Assert
        assertNotNull(result);
        assertEquals(expectedPatientId, result);

        ArgumentCaptor<Patient> patientCaptor = ArgumentCaptor.forClass(Patient.class);
        verify(patientRepository).save(patientCaptor.capture());

        Patient capturedPatient = patientCaptor.getValue();
        assertNotNull(capturedPatient);

        // Verify that the event was published
        verify(eventPublisher).publish(any(PatientCreatedEvent.class));
    }
}
