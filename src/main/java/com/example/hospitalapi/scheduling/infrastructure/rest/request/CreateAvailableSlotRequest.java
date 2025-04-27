package com.example.hospitalapi.scheduling.infrastructure.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Request for creating an available slot
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAvailableSlotRequest {
    
    @NotBlank(message = "Doctor ID is required")
    private String doctorId;
    
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    @NotNull(message = "Start time is required")
    private LocalTime startTime;
    
    @NotNull(message = "End time is required")
    private LocalTime endTime;
}