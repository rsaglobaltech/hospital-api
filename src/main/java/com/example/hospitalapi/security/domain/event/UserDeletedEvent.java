package com.example.hospitalapi.security.domain.event;

import com.example.hospitalapi.security.domain.valueobject.UserId;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when a user is deleted
 */
@Getter
public class UserDeletedEvent extends BaseDomainEvent {
    
    private final UserId userId;
    
    /**
     * Create a new UserDeletedEvent
     * @param userId the ID of the user that was deleted
     */
    public UserDeletedEvent(UserId userId) {
        super(userId.toString());
        this.userId = userId;
    }
}