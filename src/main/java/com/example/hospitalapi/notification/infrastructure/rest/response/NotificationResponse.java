package com.example.hospitalapi.notification.infrastructure.rest.response;

import com.example.hospitalapi.notification.domain.valueobject.NotificationChannel;
import com.example.hospitalapi.notification.domain.valueobject.NotificationStatus;
import com.example.hospitalapi.notification.domain.valueobject.RecipientType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response for a notification
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    
    private String id;
    private String recipientId;
    private RecipientType recipientType;
    private NotificationChannel channel;
    private String subject;
    private String content;
    private NotificationStatus status;
    private LocalDateTime sentAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}