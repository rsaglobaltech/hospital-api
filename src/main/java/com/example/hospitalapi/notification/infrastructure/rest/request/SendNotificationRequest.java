package com.example.hospitalapi.notification.infrastructure.rest.request;

import com.example.hospitalapi.notification.domain.valueobject.NotificationChannel;
import com.example.hospitalapi.notification.domain.valueobject.RecipientType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request for sending a notification
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendNotificationRequest {
    
    @NotBlank(message = "Recipient ID is required")
    private String recipientId;
    
    @NotNull(message = "Recipient type is required")
    private RecipientType recipientType;
    
    @NotNull(message = "Channel is required")
    private NotificationChannel channel;
    
    @NotBlank(message = "Subject is required")
    private String subject;
    
    @NotBlank(message = "Content is required")
    private String content;
}