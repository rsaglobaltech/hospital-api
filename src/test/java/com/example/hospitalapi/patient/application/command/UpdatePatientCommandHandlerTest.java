package com.example.hospitalapi.patient.application.command;

import com.example.hospitalapi.patient.domain.entity.Patient;
import com.example.hospitalapi.patient.domain.event.PatientUpdatedEvent;
import com.example.hospitalapi.patient.domain.exception.PatientNotFoundException;
import com.example.hospitalapi.patient.domain.repository.PatientRepository;
import com.example.hospitalapi.patient.domain.valueobject.Email;
import com.example.hospitalapi.patient.domain.valueobject.Name;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.patient.domain.valueobject.PhoneNumber;
import com.example.hospitalapi.shared.domain.event.EventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UpdatePatientCommandHandlerTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private EventPublisher eventPublisher;

    @Mock
    private Patient patient;

    private UpdatePatientCommandHandler handler;

    private final UUID patientUuid = UUID.randomUUID();
    private final String patientId = patientUuid.toString();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new UpdatePatientCommandHandler(patientRepository, eventPublisher);
    }

    @Test
    void testHandleUpdatePatientCommand() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String phoneNumber = "+1234567890";
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        String address = "123 Main St, City, Country";
        String medicalHistory = "No known allergies. Had appendectomy in 2010.";

        UpdatePatientCommand command = UpdatePatientCommand.builder()
                .patientId(patientId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .medicalHistory(medicalHistory)
                .build();

        when(patientRepository.findById(any(PatientId.class))).thenReturn(Optional.of(patient));
        when(patient.getId()).thenReturn(new PatientId(patientUuid));
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        // Act
        handler.handle(command);

        // Assert
        verify(patientRepository).findById(any(PatientId.class));
        verify(patient).updateName(any(Name.class));
        verify(patient).updateEmail(any(Email.class));
        verify(patient).updatePhoneNumber(any(PhoneNumber.class));
        verify(patient).updateAddress(address);
        verify(patient).updateMedicalHistory(medicalHistory);
        verify(patientRepository).save(patient);

        // Verify that the event was published
        verify(eventPublisher).publish(any(PatientUpdatedEvent.class));
    }

    @Test
    void testHandleUpdatePatientCommandWithNonExistentPatient() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String phoneNumber = "+1234567890";
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        String address = "123 Main St, City, Country";
        String medicalHistory = "No known allergies. Had appendectomy in 2010.";

        UpdatePatientCommand command = UpdatePatientCommand.builder()
                .patientId(patientId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .medicalHistory(medicalHistory)
                .build();

        when(patientRepository.findById(any(PatientId.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PatientNotFoundException.class, () -> handler.handle(command));
        verify(patientRepository).findById(any(PatientId.class));
        verify(patientRepository, never()).save(any(Patient.class));
    }
}
