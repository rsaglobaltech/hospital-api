package com.example.hospitalapi.scheduling.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Query to get an available slot by ID
 */
@Data
@AllArgsConstructor
public class GetAvailableSlotByIdQuery implements Query<AvailableSlotResponse> {
    private final String availableSlotId;
}