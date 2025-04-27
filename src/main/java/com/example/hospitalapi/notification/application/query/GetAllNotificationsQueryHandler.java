package com.example.hospitalapi.notification.application.query;

import com.example.hospitalapi.notification.domain.entity.Notification;
import com.example.hospitalapi.notification.domain.repository.NotificationRepository;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler for GetAllNotificationsQuery
 */
@Service
@RequiredArgsConstructor
public class GetAllNotificationsQueryHandler implements QueryHandler<GetAllNotificationsQuery, List<NotificationResponse>> {

    private final NotificationRepository notificationRepository;

    /**
     * Handle the GetAllNotificationsQuery
     * @param query the query to handle
     * @return list of all notifications
     */
    @Transactional(readOnly = true)
    public List<NotificationResponse> handle(GetAllNotificationsQuery query) {
        // Get all notifications
        List<Notification> notifications = notificationRepository.findAll();

        // Map to response
        return notifications.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Map a Notification entity to a NotificationResponse
     * @param notification the Notification entity
     * @return the NotificationResponse
     */
    private NotificationResponse mapToResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId().toString())
                .recipientId(notification.getRecipientId())
                .recipientType(notification.getRecipientType())
                .channel(notification.getChannel())
                .subject(notification.getSubject())
                .content(notification.getContent())
                .status(notification.getStatus())
                .sentAt(notification.getSentAt())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .build();
    }
}