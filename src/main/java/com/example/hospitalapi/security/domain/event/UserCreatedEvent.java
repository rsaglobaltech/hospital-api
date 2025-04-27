package com.example.hospitalapi.security.domain.event;

import com.example.hospitalapi.security.domain.entity.User;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when a user is created
 */
@Getter
public class UserCreatedEvent extends BaseDomainEvent {
    
    private final User user;
    
    /**
     * Create a new UserCreatedEvent
     * @param user the user that was created
     */
    public UserCreatedEvent(User user) {
        super(user.getId().toString());
        this.user = user;
    }
}