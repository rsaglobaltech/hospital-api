package com.example.hospitalapi.scheduling.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;
import lombok.AllArgsConstructor;

/**
 * Command to book an available slot
 */
@AllArgsConstructor
public class BookAvailableSlotCommand implements Command {
    private final String availableSlotId;

    public String getAvailableSlotId() {
        return availableSlotId;
    }
}
