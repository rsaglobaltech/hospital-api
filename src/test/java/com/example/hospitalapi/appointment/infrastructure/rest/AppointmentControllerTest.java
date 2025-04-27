package com.example.hospitalapi.appointment.infrastructure.rest;

import com.example.hospitalapi.appointment.application.query.AppointmentResponse;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentId;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentStatus;
import com.example.hospitalapi.appointment.infrastructure.rest.request.CreateAppointmentRequest;
import com.example.hospitalapi.appointment.infrastructure.rest.request.UpdateAppointmentRequest;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AppointmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommandBus commandBus;

    @Mock
    private QueryBus queryBus;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        AppointmentController controller = new AppointmentController(commandBus, queryBus);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // For LocalDateTime serialization
    }

    @Test
    void testCreateAppointment() throws Exception {
        // Arrange
        CreateAppointmentRequest request = new CreateAppointmentRequest();
        request.setPatientId(UUID.randomUUID().toString());
        request.setDoctorId(UUID.randomUUID().toString());
        request.setStartTime(LocalDateTime.now().plusDays(1));
        request.setEndTime(LocalDateTime.now().plusDays(1).plusHours(1));
        request.setReason("Annual check-up");

        UUID appointmentIdUuid = UUID.randomUUID();
        AppointmentId appointmentId = new AppointmentId(appointmentIdUuid);
        
        when(commandBus.dispatch(any())).thenReturn(CompletableFuture.completedFuture(appointmentId));

        // Act & Assert
        mockMvc.perform(post("/api/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string(appointmentIdUuid.toString()));
    }

    @Test
    void testUpdateAppointment() throws Exception {
        // Arrange
        String appointmentIdStr = UUID.randomUUID().toString();
        
        UpdateAppointmentRequest request = new UpdateAppointmentRequest();
        request.setStartTime(LocalDateTime.now().plusDays(2));
        request.setEndTime(LocalDateTime.now().plusDays(2).plusHours(1));
        request.setReason("Follow-up appointment");
        request.setNotes("Patient has a history of high blood pressure");
        
        when(commandBus.dispatch(any())).thenReturn(CompletableFuture.completedFuture(null));

        // Act & Assert
        mockMvc.perform(put("/api/appointments/" + appointmentIdStr)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testCancelAppointment() throws Exception {
        // Arrange
        String appointmentIdStr = UUID.randomUUID().toString();
        
        when(commandBus.dispatch(any())).thenReturn(CompletableFuture.completedFuture(null));

        // Act & Assert
        mockMvc.perform(put("/api/appointments/" + appointmentIdStr + "/cancel"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAppointmentById() throws Exception {
        // Arrange
        String appointmentIdStr = UUID.randomUUID().toString();
        String patientIdStr = UUID.randomUUID().toString();
        String doctorIdStr = UUID.randomUUID().toString();
        
        AppointmentResponse response = AppointmentResponse.builder()
                .id(appointmentIdStr)
                .patientId(patientIdStr)
                .doctorId(doctorIdStr)
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(1))
                .reason("Annual check-up")
                .notes("Patient has a history of high blood pressure")
                .status(AppointmentStatus.SCHEDULED)
                .build();
        
        when(queryBus.dispatch(any())).thenReturn(CompletableFuture.completedFuture(response));

        // Act & Assert
        mockMvc.perform(get("/api/appointments/" + appointmentIdStr))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(appointmentIdStr))
                .andExpect(jsonPath("$.patientId").value(patientIdStr))
                .andExpect(jsonPath("$.doctorId").value(doctorIdStr))
                .andExpect(jsonPath("$.reason").value("Annual check-up"))
                .andExpect(jsonPath("$.notes").value("Patient has a history of high blood pressure"))
                .andExpect(jsonPath("$.status").value("SCHEDULED"));
    }

    @Test
    void testGetAllAppointments() throws Exception {
        // Arrange
        String appointmentId1Str = UUID.randomUUID().toString();
        String patientId1Str = UUID.randomUUID().toString();
        String doctorId1Str = UUID.randomUUID().toString();
        
        AppointmentResponse response1 = AppointmentResponse.builder()
                .id(appointmentId1Str)
                .patientId(patientId1Str)
                .doctorId(doctorId1Str)
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(1))
                .reason("Annual check-up")
                .notes("Patient has a history of high blood pressure")
                .status(AppointmentStatus.SCHEDULED)
                .build();
        
        String appointmentId2Str = UUID.randomUUID().toString();
        String patientId2Str = UUID.randomUUID().toString();
        String doctorId2Str = UUID.randomUUID().toString();
        
        AppointmentResponse response2 = AppointmentResponse.builder()
                .id(appointmentId2Str)
                .patientId(patientId2Str)
                .doctorId(doctorId2Str)
                .startTime(LocalDateTime.now().plusDays(2))
                .endTime(LocalDateTime.now().plusDays(2).plusHours(1))
                .reason("Follow-up appointment")
                .notes("")
                .status(AppointmentStatus.SCHEDULED)
                .build();
        
        List<AppointmentResponse> responseList = Arrays.asList(response1, response2);
        
        when(queryBus.dispatch(any())).thenReturn(CompletableFuture.completedFuture(responseList));

        // Act & Assert
        mockMvc.perform(get("/api/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(appointmentId1Str))
                .andExpect(jsonPath("$[0].patientId").value(patientId1Str))
                .andExpect(jsonPath("$[0].reason").value("Annual check-up"))
                .andExpect(jsonPath("$[1].id").value(appointmentId2Str))
                .andExpect(jsonPath("$[1].patientId").value(patientId2Str))
                .andExpect(jsonPath("$[1].reason").value("Follow-up appointment"));
    }
}