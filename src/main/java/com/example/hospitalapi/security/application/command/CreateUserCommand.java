package com.example.hospitalapi.security.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

/**
 * Command to create a new user
 */
@Getter
@Builder
public class CreateUserCommand implements Command {
    private final String username;
    private final String password;
    private final String email;
    private final Set<String> roles;
}