package com.example.hospitalapi.notification.domain.event;

import com.example.hospitalapi.notification.domain.entity.Notification;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when a notification fails to send
 */
@Getter
public class NotificationFailedEvent extends BaseDomainEvent {
    
    private final Notification notification;
    private final String errorMessage;
    
    /**
     * Create a new NotificationFailedEvent
     * @param notification the notification that failed to send
     * @param errorMessage the error message
     */
    public NotificationFailedEvent(Notification notification, String errorMessage) {
        super(notification.getId().toString());
        this.notification = notification;
        this.errorMessage = errorMessage;
    }
}