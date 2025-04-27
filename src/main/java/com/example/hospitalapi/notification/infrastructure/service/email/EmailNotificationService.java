package com.example.hospitalapi.notification.infrastructure.service.email;

import com.example.hospitalapi.notification.domain.entity.Notification;
import com.example.hospitalapi.notification.domain.service.NotificationService;
import com.example.hospitalapi.notification.domain.valueobject.NotificationChannel;
import com.example.hospitalapi.notification.domain.valueobject.RecipientType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for sending email notifications
 * This is a mock implementation that just logs the email details
 */
@Service
@Slf4j
public class EmailNotificationService implements NotificationService {

    @Override
    public boolean send(Notification notification) {
        try {
            // In a real application, this would use JavaMailSender to send the email
            String emailAddress = getEmailAddress(notification.getRecipientId(), notification.getRecipientType());

            log.info("Sending email to: {}", emailAddress);
            log.info("Subject: {}", notification.getSubject());
            log.info("Content: {}", notification.getContent());

            // Simulate successful email sending
            log.info("Email notification sent to {}: {}", notification.getRecipientId(), notification.getSubject());
            return true;
        } catch (Exception e) {
            log.error("Failed to send email notification to {}: {}", notification.getRecipientId(), e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean canHandle(Notification notification) {
        return notification.getChannel() == NotificationChannel.EMAIL;
    }

    /**
     * Get the email address for the recipient
     * @param recipientId the recipient ID
     * @param recipientType the recipient type
     * @return the email address
     */
    private String getEmailAddress(String recipientId, RecipientType recipientType) {
        // In a real application, this would look up the email address from the appropriate repository
        // For now, we'll just return a dummy email address
        return recipientId + "@example.com";
    }
}
