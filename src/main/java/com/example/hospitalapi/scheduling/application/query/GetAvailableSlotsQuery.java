package com.example.hospitalapi.scheduling.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Query to get available slots for a doctor on a specific date
 */
@Data
@AllArgsConstructor
public class GetAvailableSlotsQuery implements Query<List<AvailableSlotResponse>> {
    private final String doctorId;
    private final LocalDate date;
}