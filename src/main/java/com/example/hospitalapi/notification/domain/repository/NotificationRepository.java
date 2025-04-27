package com.example.hospitalapi.notification.domain.repository;

import com.example.hospitalapi.notification.domain.entity.Notification;
import com.example.hospitalapi.notification.domain.valueobject.NotificationChannel;
import com.example.hospitalapi.notification.domain.valueobject.NotificationId;
import com.example.hospitalapi.notification.domain.valueobject.NotificationStatus;
import com.example.hospitalapi.notification.domain.valueobject.RecipientType;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Notification entity
 */
public interface NotificationRepository {
    
    /**
     * Save a notification
     * @param notification the notification to save
     * @return the saved notification
     */
    Notification save(Notification notification);
    
    /**
     * Find a notification by ID
     * @param id the notification ID
     * @return the notification if found
     */
    Optional<Notification> findById(NotificationId id);
    
    /**
     * Find all notifications
     * @return list of all notifications
     */
    List<Notification> findAll();
    
    /**
     * Find notifications by recipient ID
     * @param recipientId the recipient ID
     * @return list of notifications for the recipient
     */
    List<Notification> findByRecipientId(String recipientId);
    
    /**
     * Find notifications by recipient type
     * @param recipientType the recipient type
     * @return list of notifications for the recipient type
     */
    List<Notification> findByRecipientType(RecipientType recipientType);
    
    /**
     * Find notifications by channel
     * @param channel the notification channel
     * @return list of notifications for the channel
     */
    List<Notification> findByChannel(NotificationChannel channel);
    
    /**
     * Find notifications by status
     * @param status the notification status
     * @return list of notifications with the status
     */
    List<Notification> findByStatus(NotificationStatus status);
    
    /**
     * Delete a notification
     * @param id the notification ID
     */
    void deleteById(NotificationId id);
    
    /**
     * Check if a notification exists
     * @param id the notification ID
     * @return true if the notification exists
     */
    boolean existsById(NotificationId id);
}