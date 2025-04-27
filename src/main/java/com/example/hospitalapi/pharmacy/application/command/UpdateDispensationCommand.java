package com.example.hospitalapi.pharmacy.application.command;

import lombok.Builder;
import lombok.Getter;

/**
 * Command for updating an existing dispensation
 */
@Getter
@Builder
public class UpdateDispensationCommand {
    private final String dispensationId;
    private final String notes;
}