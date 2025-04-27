package com.example.hospitalapi.patient.application.query;

import com.example.hospitalapi.patient.domain.entity.Patient;
import com.example.hospitalapi.patient.domain.repository.PatientRepository;
import com.example.hospitalapi.patient.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetPatientByIdQueryHandlerTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private Patient patient;

    private GetPatientByIdQueryHandler handler;

    private final UUID patientUuid = UUID.randomUUID();
    private final String patientId = patientUuid.toString();
    private final PatientId patientIdObj = new PatientId(patientUuid);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new GetPatientByIdQueryHandler(patientRepository);

        // Setup patient mock
        when(patient.getId()).thenReturn(patientIdObj);
        when(patient.getName()).thenReturn(new Name("John", "Doe"));
        when(patient.getEmail()).thenReturn(new Email("john.doe@example.com"));
        when(patient.getPhoneNumber()).thenReturn(new PhoneNumber("+1234567890"));
        when(patient.getDateOfBirth()).thenReturn(new DateOfBirth(LocalDate.of(1990, 1, 1)));
        when(patient.getAddress()).thenReturn(new Address("123 Main St, City, Country"));
        when(patient.getMedicalHistory()).thenReturn(new MedicalHistory("No known allergies"));
    }

    @Test
    void testHandleGetPatientByIdQuery() {
        // Arrange
        GetPatientByIdQuery query = new GetPatientByIdQuery(patientId);

        // Mock repository to return the patient
        when(patientRepository.findById(any(PatientId.class))).thenReturn(Optional.of(patient));

        // Act
        PatientResponse response = handler.handle(query);

        // Assert
        verify(patientRepository).findById(any(PatientId.class));

        assertNotNull(response);
        assertEquals(patientId, response.getId());
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals("john.doe@example.com", response.getEmail());
        assertEquals("+1234567890", response.getPhoneNumber());
        assertEquals(LocalDate.of(1990, 1, 1), response.getDateOfBirth());
        assertEquals("123 Main St, City, Country", response.getAddress());
        assertEquals("No known allergies", response.getMedicalHistory());
    }

    @Test
    void testHandleGetPatientByIdQueryWithNonExistentPatient() {
        // Arrange
        GetPatientByIdQuery query = new GetPatientByIdQuery(patientId);

        // Mock repository to return empty
        when(patientRepository.findById(any(PatientId.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> handler.handle(query));
        verify(patientRepository).findById(any(PatientId.class));
    }
}
