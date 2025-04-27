package com.example.hospitalapi.pharmacy.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Command for deleting a dispensation
 */
@Getter
@AllArgsConstructor
public class DeleteDispensationCommand implements Command {
    private final String dispensationId;
}
