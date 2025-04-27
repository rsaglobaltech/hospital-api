package com.example.hospitalapi.notification.infrastructure.persistence.repository;

import com.example.hospitalapi.notification.domain.valueobject.NotificationChannel;
import com.example.hospitalapi.notification.domain.valueobject.NotificationStatus;
import com.example.hospitalapi.notification.domain.valueobject.RecipientType;
import com.example.hospitalapi.notification.infrastructure.persistence.entity.NotificationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for NotificationJpaEntity
 */
@Repository
public interface NotificationJpaRepository extends JpaRepository<NotificationJpaEntity, String> {
    
    /**
     * Find notifications by recipient ID
     * @param recipientId the recipient ID
     * @return list of notifications for the recipient
     */
    List<NotificationJpaEntity> findByRecipientId(String recipientId);
    
    /**
     * Find notifications by recipient type
     * @param recipientType the recipient type
     * @return list of notifications for the recipient type
     */
    List<NotificationJpaEntity> findByRecipientType(RecipientType recipientType);
    
    /**
     * Find notifications by channel
     * @param channel the notification channel
     * @return list of notifications for the channel
     */
    List<NotificationJpaEntity> findByChannel(NotificationChannel channel);
    
    /**
     * Find notifications by status
     * @param status the notification status
     * @return list of notifications with the status
     */
    List<NotificationJpaEntity> findByStatus(NotificationStatus status);
}