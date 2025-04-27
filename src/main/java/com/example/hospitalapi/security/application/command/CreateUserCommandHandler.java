package com.example.hospitalapi.security.application.command;

import com.example.hospitalapi.security.domain.entity.User;
import com.example.hospitalapi.security.domain.event.UserCreatedEvent;
import com.example.hospitalapi.security.domain.repository.UserRepository;
import com.example.hospitalapi.security.domain.valueobject.Role;
import com.example.hospitalapi.security.domain.valueobject.UserId;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import com.example.hospitalapi.shared.domain.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for CreateUserCommand
 */
@Service
@RequiredArgsConstructor
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand, UserId> {

    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;
    private final PasswordEncoder passwordEncoder;

    /**
     * Handle the CreateUserCommand
     * @param command the command to handle
     * @return the ID of the created user
     */
    @Transactional
    public UserId handle(CreateUserCommand command) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(command.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(command.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Create value objects
        UserId userId = new UserId();

        // Create user entity
        User user = new User(
            userId,
            command.getUsername(),
            passwordEncoder.encode(command.getPassword()),
            command.getEmail()
        );

        // Add roles
        if (command.getRoles() != null) {
            for (String roleName : command.getRoles()) {
                try {
                    Role role = Role.valueOf(roleName);
                    user.addRole(role);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid role: " + roleName);
                }
            }
        }

        // Save user
        User savedUser = userRepository.save(user);

        // Publish event
        eventPublisher.publish(new UserCreatedEvent(savedUser));

        return savedUser.getId();
    }
}
