package com.example.hospitalapi.notification.domain.service;

import com.example.hospitalapi.notification.domain.entity.Notification;

/**
 * Service interface for sending notifications
 */
public interface NotificationService {
    
    /**
     * Send a notification
     * @param notification the notification to send
     * @return true if the notification was sent successfully, false otherwise
     */
    boolean send(Notification notification);
    
    /**
     * Check if the service can handle the notification
     * @param notification the notification to check
     * @return true if the service can handle the notification, false otherwise
     */
    boolean canHandle(Notification notification);
}