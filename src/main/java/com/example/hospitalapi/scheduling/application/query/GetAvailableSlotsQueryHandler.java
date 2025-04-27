package com.example.hospitalapi.scheduling.application.query;

import com.example.hospitalapi.scheduling.domain.entity.AvailableSlot;
import com.example.hospitalapi.scheduling.domain.repository.AvailableSlotRepository;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler for GetAvailableSlotsQuery
 */
@Service
@RequiredArgsConstructor
public class GetAvailableSlotsQueryHandler implements QueryHandler<GetAvailableSlotsQuery, List<AvailableSlotResponse>> {

    private final AvailableSlotRepository availableSlotRepository;

    /**
     * Handle the GetAvailableSlotsQuery
     * @param query the query to handle
     * @return list of available slot responses
     */
    @Transactional(readOnly = true)
    public List<AvailableSlotResponse> handle(GetAvailableSlotsQuery query) {
        // Find available slots
        List<AvailableSlot> availableSlots = availableSlotRepository.findAvailableByDoctorIdAndDate(
                query.getDoctorId(), query.getDate());

        // Map to response
        return availableSlots.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Map an AvailableSlot entity to an AvailableSlotResponse
     * @param availableSlot the AvailableSlot entity
     * @return the AvailableSlotResponse
     */
    private AvailableSlotResponse mapToResponse(AvailableSlot availableSlot) {
        return AvailableSlotResponse.builder()
                .id(availableSlot.getId().toString())
                .doctorId(availableSlot.getDoctorId())
                .date(availableSlot.getDate())
                .startTime(availableSlot.getStartTime())
                .endTime(availableSlot.getEndTime())
                .available(availableSlot.isAvailable())
                .build();
    }
}