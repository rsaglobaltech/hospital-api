package com.example.hospitalapi.security.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Command to delete a user
 */
@Getter
@AllArgsConstructor
public class DeleteUserCommand implements Command {
    private final String userId;
}