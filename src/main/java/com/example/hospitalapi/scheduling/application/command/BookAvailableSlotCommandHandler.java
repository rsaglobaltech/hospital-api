package com.example.hospitalapi.scheduling.application.command;

import com.example.hospitalapi.scheduling.domain.entity.AvailableSlot;
import com.example.hospitalapi.scheduling.domain.event.AvailableSlotBookedEvent;
import com.example.hospitalapi.scheduling.domain.exception.AvailableSlotNotFoundException;
import com.example.hospitalapi.scheduling.domain.repository.AvailableSlotRepository;
import com.example.hospitalapi.scheduling.domain.valueobject.AvailableSlotId;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import com.example.hospitalapi.shared.domain.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for BookAvailableSlotCommand
 */
@Service
@RequiredArgsConstructor
public class BookAvailableSlotCommandHandler implements CommandHandler<BookAvailableSlotCommand, Void> {

    private final AvailableSlotRepository availableSlotRepository;
    private final EventPublisher eventPublisher;

    /**
     * Handle the BookAvailableSlotCommand
     * @param command the command to handle
     * @return null (void)
     * @throws AvailableSlotNotFoundException if the available slot is not found
     */
    @Transactional
    public Void handle(BookAvailableSlotCommand command) {
        // Create available slot ID
        AvailableSlotId availableSlotId = new AvailableSlotId(command.getAvailableSlotId());

        // Find available slot
        AvailableSlot availableSlot = availableSlotRepository.findById(availableSlotId)
                .orElseThrow(() -> new AvailableSlotNotFoundException(availableSlotId));

        // Book available slot
        availableSlot.book();

        // Save available slot
        AvailableSlot savedAvailableSlot = availableSlotRepository.save(availableSlot);

        // Publish event
        eventPublisher.publish(new AvailableSlotBookedEvent(savedAvailableSlot));

        return null;
    }
}