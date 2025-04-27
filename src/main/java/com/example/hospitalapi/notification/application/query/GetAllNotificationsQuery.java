package com.example.hospitalapi.notification.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;

import java.util.List;

/**
 * Query to get all notifications
 */
public class GetAllNotificationsQuery implements Query<List<NotificationResponse>> {
    // No parameters needed
}