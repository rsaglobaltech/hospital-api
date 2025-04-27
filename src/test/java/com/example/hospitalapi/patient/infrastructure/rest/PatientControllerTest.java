package com.example.hospitalapi.patient.infrastructure.rest;

import com.example.hospitalapi.patient.application.query.PatientResponse;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.patient.infrastructure.rest.request.CreatePatientRequest;
import com.example.hospitalapi.patient.infrastructure.rest.request.UpdatePatientRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PatientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private com.example.hospitalapi.patient.application.command.CreatePatientCommandHandler createPatientCommandHandler;

    @Mock
    private com.example.hospitalapi.patient.application.command.UpdatePatientCommandHandler updatePatientCommandHandler;

    @Mock
    private com.example.hospitalapi.patient.application.command.DeletePatientCommandHandler deletePatientCommandHandler;

    @Mock
    private com.example.hospitalapi.patient.application.query.GetPatientByIdQueryHandler getPatientByIdQueryHandler;

    @Mock
    private com.example.hospitalapi.patient.application.query.GetAllPatientsQueryHandler getAllPatientsQueryHandler;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        PatientController controller = new PatientController(
                createPatientCommandHandler,
                updatePatientCommandHandler,
                deletePatientCommandHandler,
                getPatientByIdQueryHandler,
                getAllPatientsQueryHandler
        );
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // For LocalDate serialization
    }

    @Test
    void testCreatePatient() throws Exception {
        // Arrange
        CreatePatientRequest request = new CreatePatientRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setPhoneNumber("+1234567890");
        request.setDateOfBirth(LocalDate.of(1990, 1, 1));
        request.setAddress("123 Main St, City, Country");

        UUID patientIdUuid = UUID.randomUUID();
        PatientId patientId = new PatientId(patientIdUuid);
        
        when(createPatientCommandHandler.handle(any())).thenReturn(patientId);

        // Act & Assert
        mockMvc.perform(post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string(patientIdUuid.toString()));
    }

    @Test
    void testUpdatePatient() throws Exception {
        // Arrange
        String patientIdStr = UUID.randomUUID().toString();
        
        UpdatePatientRequest request = new UpdatePatientRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setPhoneNumber("+1234567890");
        request.setDateOfBirth(LocalDate.of(1990, 1, 1));
        request.setAddress("456 Oak St, Town, Country");
        request.setMedicalHistory("No known allergies. Had appendectomy in 2010.");

        // Act & Assert
        mockMvc.perform(put("/api/patients/" + patientIdStr)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePatient() throws Exception {
        // Arrange
        String patientIdStr = UUID.randomUUID().toString();

        // Act & Assert
        mockMvc.perform(delete("/api/patients/" + patientIdStr))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetPatientById() throws Exception {
        // Arrange
        String patientIdStr = UUID.randomUUID().toString();
        
        PatientResponse response = PatientResponse.builder()
                .id(patientIdStr)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("+1234567890")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Main St, City, Country")
                .medicalHistory("No known allergies")
                .build();
        
        when(getPatientByIdQueryHandler.handle(any())).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/patients/" + patientIdStr))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(patientIdStr))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("+1234567890"))
                .andExpect(jsonPath("$.address").value("123 Main St, City, Country"))
                .andExpect(jsonPath("$.medicalHistory").value("No known allergies"));
    }

    @Test
    void testGetAllPatients() throws Exception {
        // Arrange
        String patientId1Str = UUID.randomUUID().toString();
        String patientId2Str = UUID.randomUUID().toString();
        
        PatientResponse response1 = PatientResponse.builder()
                .id(patientId1Str)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("+1234567890")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Main St, City, Country")
                .medicalHistory("No known allergies")
                .build();
        
        PatientResponse response2 = PatientResponse.builder()
                .id(patientId2Str)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .phoneNumber("+9876543210")
                .dateOfBirth(LocalDate.of(1985, 5, 15))
                .address("456 Oak St, Town, Country")
                .medicalHistory("Allergic to penicillin")
                .build();
        
        List<PatientResponse> responseList = Arrays.asList(response1, response2);
        
        when(getAllPatientsQueryHandler.handle(any())).thenReturn(responseList);

        // Act & Assert
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(patientId1Str))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[1].id").value(patientId2Str))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"));
    }
}