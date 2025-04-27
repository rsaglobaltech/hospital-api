package com.example.hospitalapi.notification.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Query to get a notification by ID
 */
@Data
@AllArgsConstructor
public class GetNotificationByIdQuery implements Query<NotificationResponse> {
    private final String notificationId;
}