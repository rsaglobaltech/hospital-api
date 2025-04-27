package com.example.hospitalapi.security.application.command;

import com.example.hospitalapi.security.domain.event.UserDeletedEvent;
import com.example.hospitalapi.security.domain.exception.UserValidationException;
import com.example.hospitalapi.security.domain.repository.UserRepository;
import com.example.hospitalapi.security.domain.valueobject.UserId;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import com.example.hospitalapi.shared.domain.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for DeleteUserCommand
 */
@Service
@RequiredArgsConstructor
public class DeleteUserCommandHandler implements CommandHandler<DeleteUserCommand, Void> {

    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;

    /**
     * Handle the DeleteUserCommand
     * @param command the command to handle
     * @return void
     */
    @Transactional
    public Void handle(DeleteUserCommand command) {
        // Create value objects
        UserId userId = new UserId(command.getUserId());
        
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new UserValidationException("User not found");
        }
        
        // Delete user
        userRepository.deleteById(userId);
        
        // Publish event
        eventPublisher.publish(new UserDeletedEvent(userId));
        
        return null;
    }
}