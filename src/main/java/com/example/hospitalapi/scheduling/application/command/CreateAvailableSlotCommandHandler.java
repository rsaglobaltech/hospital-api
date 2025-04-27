package com.example.hospitalapi.scheduling.application.command;

import com.example.hospitalapi.scheduling.domain.entity.AvailableSlot;
import com.example.hospitalapi.scheduling.domain.event.AvailableSlotCreatedEvent;
import com.example.hospitalapi.scheduling.domain.repository.AvailableSlotRepository;
import com.example.hospitalapi.scheduling.domain.valueobject.AvailableSlotId;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import com.example.hospitalapi.shared.domain.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for CreateAvailableSlotCommand
 */
@Service
@RequiredArgsConstructor
public class CreateAvailableSlotCommandHandler implements CommandHandler<CreateAvailableSlotCommand, AvailableSlotId> {

    private final AvailableSlotRepository availableSlotRepository;
    private final EventPublisher eventPublisher;

    /**
     * Handle the CreateAvailableSlotCommand
     * @param command the command to handle
     * @return the ID of the created available slot
     */
    @Transactional
    public AvailableSlotId handle(CreateAvailableSlotCommand command) {
        // Create value objects
        AvailableSlotId availableSlotId = new AvailableSlotId();

        // Create available slot entity
        AvailableSlot availableSlot = new AvailableSlot(
            availableSlotId,
            command.getDoctorId(),
            command.getDate(),
            command.getStartTime(),
            command.getEndTime()
        );

        // Save available slot
        AvailableSlot savedAvailableSlot = availableSlotRepository.save(availableSlot);

        // Publish event
        eventPublisher.publish(new AvailableSlotCreatedEvent(savedAvailableSlot));

        return savedAvailableSlot.getId();
    }
}