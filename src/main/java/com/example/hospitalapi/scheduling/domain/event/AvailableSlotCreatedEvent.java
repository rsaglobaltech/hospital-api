package com.example.hospitalapi.scheduling.domain.event;

import com.example.hospitalapi.scheduling.domain.entity.AvailableSlot;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when an available slot is created
 */
@Getter
public class AvailableSlotCreatedEvent extends BaseDomainEvent {
    
    private final AvailableSlot availableSlot;
    
    /**
     * Create a new AvailableSlotCreatedEvent
     * @param availableSlot the available slot that was created
     */
    public AvailableSlotCreatedEvent(AvailableSlot availableSlot) {
        super(availableSlot.getId().toString());
        this.availableSlot = availableSlot;
    }
}