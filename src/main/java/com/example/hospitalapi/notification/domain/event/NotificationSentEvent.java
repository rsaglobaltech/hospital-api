package com.example.hospitalapi.notification.domain.event;

import com.example.hospitalapi.notification.domain.entity.Notification;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when a notification is sent
 */
@Getter
public class NotificationSentEvent extends BaseDomainEvent {
    
    private final Notification notification;
    
    /**
     * Create a new NotificationSentEvent
     * @param notification the notification that was sent
     */
    public NotificationSentEvent(Notification notification) {
        super(notification.getId().toString());
        this.notification = notification;
    }
}