package com.example.hospitalapi.medicalstaff.infrastructure.rest;

import com.example.hospitalapi.medicalstaff.application.query.MedicalStaffResponse;
import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
import com.example.hospitalapi.medicalstaff.domain.valueobject.StaffId;
import com.example.hospitalapi.medicalstaff.infrastructure.rest.request.CreateMedicalStaffRequest;
import com.example.hospitalapi.medicalstaff.infrastructure.rest.request.DeactivateMedicalStaffRequest;
import com.example.hospitalapi.medicalstaff.infrastructure.rest.request.UpdateMedicalStaffRequest;
import com.example.hospitalapi.shared.domain.bus.CommandBus;
import com.example.hospitalapi.shared.domain.bus.QueryBus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MedicalStaffControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommandBus commandBus;

    @Mock
    private QueryBus queryBus;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MedicalStaffController controller = new MedicalStaffController(commandBus, queryBus);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // For LocalDate serialization
    }

    @Test
    void testCreateMedicalStaff() throws Exception {
        // Arrange
        CreateMedicalStaffRequest request = new CreateMedicalStaffRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@hospital.com");
        request.setPhoneNumber("+1234567890");
        request.setDateOfBirth(LocalDate.of(1980, 1, 1));
        request.setAddress("123 Main St, City, Country");
        request.setSpecialty(Specialty.CARDIOLOGY);
        request.setDegree("MD");
        request.setInstitution("Harvard Medical School");
        request.setDateObtained(LocalDate.of(2005, 5, 15));
        request.setLicenseNumber("ML123456");
        request.setLicenseExpiryDate(LocalDate.now().plusYears(2));
        request.setHireDate(LocalDate.of(2010, 1, 15));

        UUID staffIdUuid = UUID.randomUUID();
        StaffId staffId = new StaffId(staffIdUuid);
        
        when(commandBus.dispatch(any())).thenReturn(CompletableFuture.completedFuture(staffId));

        // Act & Assert
        mockMvc.perform(post("/api/medical-staff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string(staffIdUuid.toString()));
    }

    @Test
    void testUpdateMedicalStaff() throws Exception {
        // Arrange
        String staffIdStr = UUID.randomUUID().toString();
        
        UpdateMedicalStaffRequest request = new UpdateMedicalStaffRequest();
        request.setFirstName("Jane");
        request.setLastName("Smith");
        request.setEmail("jane.smith@hospital.com");
        request.setPhoneNumber("+9876543210");
        request.setAddress("456 Oak St, Town, Country");
        request.setSpecialty(Specialty.NEUROLOGY);
        
        when(commandBus.dispatch(any())).thenReturn(CompletableFuture.completedFuture(null));

        // Act & Assert
        mockMvc.perform(put("/api/medical-staff/" + staffIdStr)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeactivateMedicalStaff() throws Exception {
        // Arrange
        String staffIdStr = UUID.randomUUID().toString();
        
        DeactivateMedicalStaffRequest request = new DeactivateMedicalStaffRequest();
        request.setTerminationDate(LocalDate.now());
        
        when(commandBus.dispatch(any())).thenReturn(CompletableFuture.completedFuture(null));

        // Act & Assert
        mockMvc.perform(put("/api/medical-staff/" + staffIdStr + "/deactivate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetMedicalStaffById() throws Exception {
        // Arrange
        String staffIdStr = UUID.randomUUID().toString();
        
        MedicalStaffResponse.QualificationResponse qualification = MedicalStaffResponse.QualificationResponse.builder()
                .degree("MD")
                .institution("Harvard Medical School")
                .dateObtained(LocalDate.of(2005, 5, 15))
                .licenseNumber("ML123456")
                .licenseExpiryDate(LocalDate.now().plusYears(2))
                .expired(false)
                .build();
        
        List<MedicalStaffResponse.QualificationResponse> qualifications = new ArrayList<>();
        qualifications.add(qualification);
        
        MedicalStaffResponse response = MedicalStaffResponse.builder()
                .id(staffIdStr)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@hospital.com")
                .phoneNumber("+1234567890")
                .dateOfBirth(LocalDate.of(1980, 1, 1))
                .address("123 Main St, City, Country")
                .specialty(Specialty.CARDIOLOGY)
                .qualifications(qualifications)
                .active(true)
                .hireDate(LocalDate.of(2010, 1, 15))
                .terminationDate(null)
                .build();
        
        when(queryBus.dispatch(any())).thenReturn(CompletableFuture.completedFuture(response));

        // Act & Assert
        mockMvc.perform(get("/api/medical-staff/" + staffIdStr))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(staffIdStr))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@hospital.com"))
                .andExpect(jsonPath("$.specialty").value("CARDIOLOGY"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void testGetAllMedicalStaff() throws Exception {
        // Arrange
        String staffId1Str = UUID.randomUUID().toString();
        String staffId2Str = UUID.randomUUID().toString();
        
        MedicalStaffResponse response1 = MedicalStaffResponse.builder()
                .id(staffId1Str)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@hospital.com")
                .phoneNumber("+1234567890")
                .dateOfBirth(LocalDate.of(1980, 1, 1))
                .address("123 Main St, City, Country")
                .specialty(Specialty.CARDIOLOGY)
                .qualifications(new ArrayList<>())
                .active(true)
                .hireDate(LocalDate.of(2010, 1, 15))
                .terminationDate(null)
                .build();
        
        MedicalStaffResponse response2 = MedicalStaffResponse.builder()
                .id(staffId2Str)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@hospital.com")
                .phoneNumber("+9876543210")
                .dateOfBirth(LocalDate.of(1985, 5, 10))
                .address("456 Oak St, Town, Country")
                .specialty(Specialty.NEUROLOGY)
                .qualifications(new ArrayList<>())
                .active(true)
                .hireDate(LocalDate.of(2015, 3, 20))
                .terminationDate(null)
                .build();
        
        List<MedicalStaffResponse> responseList = Arrays.asList(response1, response2);
        
        when(queryBus.dispatch(any())).thenReturn(CompletableFuture.completedFuture(responseList));

        // Act & Assert
        mockMvc.perform(get("/api/medical-staff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(staffId1Str))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].specialty").value("CARDIOLOGY"))
                .andExpect(jsonPath("$[1].id").value(staffId2Str))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].specialty").value("NEUROLOGY"));
    }

    @Test
    void testGetMedicalStaffBySpecialty() throws Exception {
        // Arrange
        String staffId1Str = UUID.randomUUID().toString();
        String staffId2Str = UUID.randomUUID().toString();
        Specialty specialty = Specialty.CARDIOLOGY;
        
        MedicalStaffResponse response1 = MedicalStaffResponse.builder()
                .id(staffId1Str)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@hospital.com")
                .phoneNumber("+1234567890")
                .dateOfBirth(LocalDate.of(1980, 1, 1))
                .address("123 Main St, City, Country")
                .specialty(specialty)
                .qualifications(new ArrayList<>())
                .active(true)
                .hireDate(LocalDate.of(2010, 1, 15))
                .terminationDate(null)
                .build();
        
        MedicalStaffResponse response2 = MedicalStaffResponse.builder()
                .id(staffId2Str)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@hospital.com")
                .phoneNumber("+9876543210")
                .dateOfBirth(LocalDate.of(1985, 5, 10))
                .address("456 Oak St, Town, Country")
                .specialty(specialty)
                .qualifications(new ArrayList<>())
                .active(true)
                .hireDate(LocalDate.of(2015, 3, 20))
                .terminationDate(null)
                .build();
        
        List<MedicalStaffResponse> responseList = Arrays.asList(response1, response2);
        
        when(queryBus.dispatch(any())).thenReturn(CompletableFuture.completedFuture(responseList));

        // Act & Assert
        mockMvc.perform(get("/api/medical-staff/specialty/" + specialty))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(staffId1Str))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].specialty").value("CARDIOLOGY"))
                .andExpect(jsonPath("$[1].id").value(staffId2Str))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].specialty").value("CARDIOLOGY"));
    }
}