package com.example.hospitalapi.pharmacy.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;

import lombok.Builder;
import lombok.Getter;

/**
 * Command for updating an existing dispensation
 */
@Getter
@Builder
public class UpdateDispensationCommand implements Command {
    private final String dispensationId;
    private final String notes;
}
