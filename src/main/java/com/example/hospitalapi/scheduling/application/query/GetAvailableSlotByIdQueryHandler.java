package com.example.hospitalapi.scheduling.application.query;

import com.example.hospitalapi.scheduling.domain.entity.AvailableSlot;
import com.example.hospitalapi.scheduling.domain.exception.AvailableSlotNotFoundException;
import com.example.hospitalapi.scheduling.domain.repository.AvailableSlotRepository;
import com.example.hospitalapi.scheduling.domain.valueobject.AvailableSlotId;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for GetAvailableSlotByIdQuery
 */
@Service
@RequiredArgsConstructor
public class GetAvailableSlotByIdQueryHandler implements QueryHandler<GetAvailableSlotByIdQuery, AvailableSlotResponse> {

    private final AvailableSlotRepository availableSlotRepository;

    /**
     * Handle the GetAvailableSlotByIdQuery
     * @param query the query to handle
     * @return the available slot response
     * @throws AvailableSlotNotFoundException if the available slot is not found
     */
    @Transactional(readOnly = true)
    public AvailableSlotResponse handle(GetAvailableSlotByIdQuery query) {
        // Create available slot ID
        AvailableSlotId availableSlotId = new AvailableSlotId(query.getAvailableSlotId());

        // Find available slot
        AvailableSlot availableSlot = availableSlotRepository.findById(availableSlotId)
                .orElseThrow(() -> new AvailableSlotNotFoundException(availableSlotId));

        // Map to response
        return mapToResponse(availableSlot);
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