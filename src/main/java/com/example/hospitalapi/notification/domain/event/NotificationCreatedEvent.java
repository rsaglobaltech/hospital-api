package com.example.hospitalapi.notification.domain.event;

import com.example.hospitalapi.notification.domain.entity.Notification;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when a notification is created
 */
@Getter
public class NotificationCreatedEvent extends BaseDomainEvent {
    
    private final Notification notification;
    
    /**
     * Create a new NotificationCreatedEvent
     * @param notification the notification that was created
     */
    public NotificationCreatedEvent(Notification notification) {
        super(notification.getId().toString());
        this.notification = notification;
    }
}