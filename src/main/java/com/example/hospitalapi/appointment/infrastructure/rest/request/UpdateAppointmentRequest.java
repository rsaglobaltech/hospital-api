package com.example.hospitalapi.appointment.infrastructure.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Request DTO for updating an appointment
 */
@Data
public class UpdateAppointmentRequest {
    
    @NotNull(message = "Start time is required")
    @FutureOrPresent(message = "Start time must be in the present or future")
    @Schema(description = "Start time of the appointment", example = "2023-06-15T10:00:00")
    private LocalDateTime startTime;
    
    @NotNull(message = "End time is required")
    @FutureOrPresent(message = "End time must be in the present or future")
    @Schema(description = "End time of the appointment", example = "2023-06-15T11:00:00")
    private LocalDateTime endTime;
    
    @NotBlank(message = "Reason is required")
    @Schema(description = "Reason for the appointment", example = "Annual check-up")
    private String reason;
    
    @Schema(description = "Notes about the appointment", example = "Patient has a history of high blood pressure")
    private String notes;
}