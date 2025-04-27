package com.example.hospitalapi.notification.application.query;

import com.example.hospitalapi.notification.domain.entity.Notification;
import com.example.hospitalapi.notification.domain.exception.NotificationNotFoundException;
import com.example.hospitalapi.notification.domain.repository.NotificationRepository;
import com.example.hospitalapi.notification.domain.valueobject.NotificationId;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for GetNotificationByIdQuery
 */
@Service
@RequiredArgsConstructor
public class GetNotificationByIdQueryHandler implements QueryHandler<GetNotificationByIdQuery, NotificationResponse> {

    private final NotificationRepository notificationRepository;

    /**
     * Handle the GetNotificationByIdQuery
     * @param query the query to handle
     * @return the notification response
     * @throws NotificationNotFoundException if the notification is not found
     */
    @Transactional(readOnly = true)
    public NotificationResponse handle(GetNotificationByIdQuery query) {
        // Create notification ID
        NotificationId notificationId = new NotificationId(query.getNotificationId());

        // Find notification
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException(notificationId));

        // Map to response
        return mapToResponse(notification);
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