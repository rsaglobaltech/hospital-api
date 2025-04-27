package com.example.hospitalapi.scheduling.domain.event;

import com.example.hospitalapi.scheduling.domain.entity.AvailableSlot;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when an available slot is booked
 */
@Getter
public class AvailableSlotBookedEvent extends BaseDomainEvent {
    
    private final AvailableSlot availableSlot;
    
    /**
     * Create a new AvailableSlotBookedEvent
     * @param availableSlot the available slot that was booked
     */
    public AvailableSlotBookedEvent(AvailableSlot availableSlot) {
        super(availableSlot.getId().toString());
        this.availableSlot = availableSlot;
    }
}