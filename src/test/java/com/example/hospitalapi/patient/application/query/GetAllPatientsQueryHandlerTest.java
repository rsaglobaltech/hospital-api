package com.example.hospitalapi.patient.application.query;

import com.example.hospitalapi.patient.domain.entity.Patient;
import com.example.hospitalapi.patient.domain.repository.PatientRepository;
import com.example.hospitalapi.patient.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetAllPatientsQueryHandlerTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private Patient patient1;

    @Mock
    private Patient patient2;

    private GetAllPatientsQueryHandler handler;

    private final UUID patientUuid1 = UUID.randomUUID();
    private final UUID patientUuid2 = UUID.randomUUID();
    private final PatientId patientId1 = new PatientId(patientUuid1);
    private final PatientId patientId2 = new PatientId(patientUuid2);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new GetAllPatientsQueryHandler(patientRepository);

        // Setup patient1 mock
        when(patient1.getId()).thenReturn(patientId1);
        when(patient1.getName()).thenReturn(new Name("John", "Doe"));
        when(patient1.getEmail()).thenReturn(new Email("john.doe@example.com"));
        when(patient1.getPhoneNumber()).thenReturn(new PhoneNumber("+1234567890"));
        when(patient1.getDateOfBirth()).thenReturn(new DateOfBirth(LocalDate.of(1990, 1, 1)));
        when(patient1.getAddress()).thenReturn(new Address("123 Main St, City, Country"));
        when(patient1.getMedicalHistory()).thenReturn(new MedicalHistory("No known allergies"));

        // Setup patient2 mock
        when(patient2.getId()).thenReturn(patientId2);
        when(patient2.getName()).thenReturn(new Name("Jane", "Smith"));
        when(patient2.getEmail()).thenReturn(new Email("jane.smith@example.com"));
        when(patient2.getPhoneNumber()).thenReturn(new PhoneNumber("+9876543210"));
        when(patient2.getDateOfBirth()).thenReturn(new DateOfBirth(LocalDate.of(1985, 5, 15)));
        when(patient2.getAddress()).thenReturn(new Address("456 Oak St, Town, Country"));
        when(patient2.getMedicalHistory()).thenReturn(new MedicalHistory("Allergic to penicillin"));
    }

    @Test
    void testHandleGetAllPatientsQuery() {
        // Arrange
        GetAllPatientsQuery query = new GetAllPatientsQuery();

        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient1, patient2));

        // Act
        List<PatientResponse> response = handler.handle(query);

        // Assert
        verify(patientRepository).findAll();
        
        assertNotNull(response);
        assertEquals(2, response.size());
        
        PatientResponse response1 = response.get(0);
        assertEquals(patientUuid1.toString(), response1.getId());
        assertEquals("John", response1.getFirstName());
        assertEquals("Doe", response1.getLastName());
        assertEquals("john.doe@example.com", response1.getEmail());
        assertEquals("+1234567890", response1.getPhoneNumber());
        assertEquals(LocalDate.of(1990, 1, 1), response1.getDateOfBirth());
        assertEquals("123 Main St, City, Country", response1.getAddress());
        assertEquals("No known allergies", response1.getMedicalHistory());
        
        PatientResponse response2 = response.get(1);
        assertEquals(patientUuid2.toString(), response2.getId());
        assertEquals("Jane", response2.getFirstName());
        assertEquals("Smith", response2.getLastName());
        assertEquals("jane.smith@example.com", response2.getEmail());
        assertEquals("+9876543210", response2.getPhoneNumber());
        assertEquals(LocalDate.of(1985, 5, 15), response2.getDateOfBirth());
        assertEquals("456 Oak St, Town, Country", response2.getAddress());
        assertEquals("Allergic to penicillin", response2.getMedicalHistory());
    }

    @Test
    void testHandleGetAllPatientsQueryWithEmptyList() {
        // Arrange
        GetAllPatientsQuery query = new GetAllPatientsQuery();

        when(patientRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<PatientResponse> response = handler.handle(query);

        // Assert
        verify(patientRepository).findAll();
        
        assertNotNull(response);
        assertTrue(response.isEmpty());
    }
}