package com.example.hospitalapi.notification.application.command;

import com.example.hospitalapi.notification.domain.entity.Notification;
import com.example.hospitalapi.notification.domain.event.NotificationCreatedEvent;
import com.example.hospitalapi.notification.domain.event.NotificationFailedEvent;
import com.example.hospitalapi.notification.domain.event.NotificationSentEvent;
import com.example.hospitalapi.notification.domain.repository.NotificationRepository;
import com.example.hospitalapi.notification.domain.service.NotificationService;
import com.example.hospitalapi.notification.domain.valueobject.NotificationId;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import com.example.hospitalapi.shared.domain.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Handler for SendNotificationCommand
 */
@Service
@RequiredArgsConstructor
public class SendNotificationCommandHandler implements CommandHandler<SendNotificationCommand, NotificationId> {

    private final NotificationRepository notificationRepository;
    private final List<NotificationService> notificationServices;
    private final EventPublisher eventPublisher;

    /**
     * Handle the SendNotificationCommand
     * @param command the command to handle
     * @return the ID of the created notification
     */
    @Transactional
    public NotificationId handle(SendNotificationCommand command) {
        // Create notification ID
        NotificationId notificationId = new NotificationId();

        // Create notification entity
        Notification notification = new Notification(
            notificationId,
            command.getRecipientId(),
            command.getRecipientType(),
            command.getChannel(),
            command.getSubject(),
            command.getContent()
        );

        // Save notification
        Notification savedNotification = notificationRepository.save(notification);

        // Publish notification created event
        eventPublisher.publish(new NotificationCreatedEvent(savedNotification));

        // Find a service that can handle the notification
        NotificationService service = notificationServices.stream()
                .filter(s -> s.canHandle(savedNotification))
                .findFirst()
                .orElse(null);

        if (service == null) {
            // Mark notification as failed
            savedNotification.markAsFailed();
            notificationRepository.save(savedNotification);
            
            // Publish notification failed event
            eventPublisher.publish(new NotificationFailedEvent(savedNotification, "No service found to handle the notification"));
            
            return savedNotification.getId();
        }

        // Send notification
        boolean sent = service.send(savedNotification);

        if (sent) {
            // Mark notification as sent
            savedNotification.markAsSent();
            notificationRepository.save(savedNotification);
            
            // Publish notification sent event
            eventPublisher.publish(new NotificationSentEvent(savedNotification));
        } else {
            // Mark notification as failed
            savedNotification.markAsFailed();
            notificationRepository.save(savedNotification);
            
            // Publish notification failed event
            eventPublisher.publish(new NotificationFailedEvent(savedNotification, "Failed to send notification"));
        }

        return savedNotification.getId();
    }
}