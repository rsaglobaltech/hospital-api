package com.example.hospitalapi.notification.infrastructure.event;

import com.example.hospitalapi.notification.application.command.SendNotificationCommand;
import com.example.hospitalapi.notification.domain.valueobject.NotificationChannel;
import com.example.hospitalapi.notification.domain.valueobject.RecipientType;
import com.example.hospitalapi.patient.domain.event.PatientCreatedEvent;
import com.example.hospitalapi.patient.domain.event.PatientUpdatedEvent;
import com.example.hospitalapi.shared.domain.bus.CommandBus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Event handler for patient events that sends notifications
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PatientEventHandler {
    
    private final CommandBus commandBus;
    
    /**
     * Handle PatientCreatedEvent
     * @param event the event to handle
     */
    @EventListener
    public void handle(PatientCreatedEvent event) {
        log.info("Handling PatientCreatedEvent for patient with ID: {}", event.getAggregateId());
        
        // Create a welcome notification for the patient
        SendNotificationCommand command = SendNotificationCommand.builder()
                .recipientId(event.getPatient().getId().toString())
                .recipientType(RecipientType.PATIENT)
                .channel(NotificationChannel.EMAIL)
                .subject("Welcome to Our Hospital")
                .content("Dear " + event.getPatient().getName().getFirstName() + ",\n\n" +
                        "Welcome to our hospital! Your account has been created successfully.\n\n" +
                        "You can now book appointments and access your medical records online.\n\n" +
                        "Best regards,\nHospital Team")
                .build();
        
        // Dispatch the command
        commandBus.dispatch(command);
    }
    
    /**
     * Handle PatientUpdatedEvent
     * @param event the event to handle
     */
    @EventListener
    public void handle(PatientUpdatedEvent event) {
        log.info("Handling PatientUpdatedEvent for patient with ID: {}", event.getAggregateId());
        
        // Create a notification for the patient about the update
        SendNotificationCommand command = SendNotificationCommand.builder()
                .recipientId(event.getPatient().getId().toString())
                .recipientType(RecipientType.PATIENT)
                .channel(NotificationChannel.EMAIL)
                .subject("Your Profile Has Been Updated")
                .content("Dear " + event.getPatient().getName().getFirstName() + ",\n\n" +
                        "Your profile information has been updated.\n\n" +
                        "If you did not make these changes, please contact us immediately.\n\n" +
                        "Best regards,\nHospital Team")
                .build();
        
        // Dispatch the command
        commandBus.dispatch(command);
    }
}