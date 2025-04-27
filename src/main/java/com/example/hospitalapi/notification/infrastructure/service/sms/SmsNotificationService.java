package com.example.hospitalapi.notification.infrastructure.service.sms;

import com.example.hospitalapi.notification.domain.entity.Notification;
import com.example.hospitalapi.notification.domain.service.NotificationService;
import com.example.hospitalapi.notification.domain.valueobject.NotificationChannel;
import com.example.hospitalapi.notification.domain.valueobject.RecipientType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for sending SMS notifications
 * This is a mock implementation that just logs the SMS details
 */
@Service
@Slf4j
public class SmsNotificationService implements NotificationService {

    @Override
    public boolean send(Notification notification) {
        try {
            // In a real application, this would use an SMS gateway service to send the SMS
            String phoneNumber = getPhoneNumber(notification.getRecipientId(), notification.getRecipientType());
            
            log.info("Sending SMS to: {}", phoneNumber);
            log.info("Content: {}", notification.getContent());
            
            // Simulate successful SMS sending
            log.info("SMS notification sent to {}: {}", notification.getRecipientId(), notification.getSubject());
            return true;
        } catch (Exception e) {
            log.error("Failed to send SMS notification to {}: {}", notification.getRecipientId(), e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean canHandle(Notification notification) {
        return notification.getChannel() == NotificationChannel.SMS;
    }
    
    /**
     * Get the phone number for the recipient
     * @param recipientId the recipient ID
     * @param recipientType the recipient type
     * @return the phone number
     */
    private String getPhoneNumber(String recipientId, RecipientType recipientType) {
        // In a real application, this would look up the phone number from the appropriate repository
        // For now, we'll just return a dummy phone number
        return "+1234567890";
    }
}