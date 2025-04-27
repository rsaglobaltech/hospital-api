package com.example.hospitalapi.notification.application.command;

import com.example.hospitalapi.notification.domain.valueobject.NotificationChannel;
import com.example.hospitalapi.notification.domain.valueobject.RecipientType;
import com.example.hospitalapi.shared.domain.bus.Command;
import lombok.Builder;
import lombok.Data;

/**
 * Command to send a notification
 */
@Data
@Builder
public class SendNotificationCommand implements Command {
    private final String recipientId;
    private final RecipientType recipientType;
    private final NotificationChannel channel;
    private final String subject;
    private final String content;
}