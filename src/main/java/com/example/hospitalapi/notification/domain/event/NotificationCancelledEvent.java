package com.example.hospitalapi.notification.domain.event;

import com.example.hospitalapi.notification.domain.entity.Notification;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when a notification is cancelled
 */
@Getter
public class NotificationCancelledEvent extends BaseDomainEvent {
    
    private final Notification notification;
    
    /**
     * Create a new NotificationCancelledEvent
     * @param notification the notification that was cancelled
     */
    public NotificationCancelledEvent(Notification notification) {
        super(notification.getId().toString());
        this.notification = notification;
    }
}