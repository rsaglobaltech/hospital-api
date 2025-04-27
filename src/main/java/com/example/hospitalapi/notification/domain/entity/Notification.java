package com.example.hospitalapi.notification.domain.entity;

import com.example.hospitalapi.notification.domain.exception.NotificationValidationException;
import com.example.hospitalapi.notification.domain.valueobject.NotificationChannel;
import com.example.hospitalapi.notification.domain.valueobject.NotificationId;
import com.example.hospitalapi.notification.domain.valueobject.NotificationStatus;
import com.example.hospitalapi.notification.domain.valueobject.RecipientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Entity representing a notification in the system
 */
@Getter
@Data
@AllArgsConstructor
public class Notification {
    private final NotificationId id;
    private final String recipientId;
    private final RecipientType recipientType;
    private final NotificationChannel channel;
    private final String subject;
    private final String content;
    private NotificationStatus status;
    private LocalDateTime sentAt;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Notification(NotificationId id, String recipientId, RecipientType recipientType,
                       NotificationChannel channel, String subject, String content) {
        if (id == null) {
            throw new NotificationValidationException("Notification ID cannot be null");
        }
        if (recipientId == null || recipientId.trim().isEmpty()) {
            throw new NotificationValidationException("Recipient ID cannot be empty");
        }
        if (recipientType == null) {
            throw new NotificationValidationException("Recipient type cannot be null");
        }
        if (channel == null) {
            throw new NotificationValidationException("Channel cannot be null");
        }
        if (subject == null || subject.trim().isEmpty()) {
            throw new NotificationValidationException("Subject cannot be empty");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new NotificationValidationException("Content cannot be empty");
        }

        this.id = id;
        this.recipientId = recipientId.trim();
        this.recipientType = recipientType;
        this.channel = channel;
        this.subject = subject.trim();
        this.content = content.trim();
        this.status = NotificationStatus.PENDING;
        this.sentAt = null;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    /**
     * Mark the notification as sent
     */
    public void markAsSent() {
        this.status = NotificationStatus.SENT;
        this.sentAt = LocalDateTime.now();
        this.updatedAt = this.sentAt;
    }

    /**
     * Mark the notification as failed
     */
    public void markAsFailed() {
        this.status = NotificationStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Cancel the notification
     */
    public void cancel() {
        if (this.status == NotificationStatus.SENT) {
            throw new NotificationValidationException("Cannot cancel a notification that has already been sent");
        }
        this.status = NotificationStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Check if the notification is pending
     * @return true if the notification is pending
     */
    public boolean isPending() {
        return this.status == NotificationStatus.PENDING;
    }

    /**
     * Check if the notification is sent
     * @return true if the notification is sent
     */
    public boolean isSent() {
        return this.status == NotificationStatus.SENT;
    }

    /**
     * Check if the notification is failed
     * @return true if the notification is failed
     */
    public boolean isFailed() {
        return this.status == NotificationStatus.FAILED;
    }

    /**
     * Check if the notification is cancelled
     * @return true if the notification is cancelled
     */
    public boolean isCancelled() {
        return this.status == NotificationStatus.CANCELLED;
    }
}