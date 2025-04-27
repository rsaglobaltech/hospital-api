package com.example.hospitalapi.notification.infrastructure.event;

import com.example.hospitalapi.medicalstaff.domain.event.MedicalStaffCreatedEvent;
import com.example.hospitalapi.medicalstaff.domain.event.MedicalStaffUpdatedEvent;
import com.example.hospitalapi.notification.application.command.SendNotificationCommand;
import com.example.hospitalapi.notification.domain.valueobject.NotificationChannel;
import com.example.hospitalapi.notification.domain.valueobject.RecipientType;
import com.example.hospitalapi.shared.domain.bus.CommandBus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Event handler for medical staff events that sends notifications
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MedicalStaffEventHandler {
    
    private final CommandBus commandBus;
    
    /**
     * Handle MedicalStaffCreatedEvent
     * @param event the event to handle
     */
    @EventListener
    public void handle(MedicalStaffCreatedEvent event) {
        log.info("Handling MedicalStaffCreatedEvent for staff with ID: {}", event.getAggregateId());
        
        // Create a welcome notification for the medical staff member
        SendNotificationCommand command = SendNotificationCommand.builder()
                .recipientId(event.getMedicalStaff().getId().toString())
                .recipientType(RecipientType.MEDICAL_STAFF)
                .channel(NotificationChannel.EMAIL)
                .subject("Welcome to Our Hospital Staff")
                .content("Dear Dr. " + event.getMedicalStaff().getName().getLastName() + ",\n\n" +
                        "Welcome to our hospital staff! Your account has been created successfully.\n\n" +
                        "You can now access the staff portal and view your assigned patients.\n\n" +
                        "Best regards,\nHospital Administration")
                .build();
        
        // Dispatch the command
        commandBus.dispatch(command);
        
        // Also notify the admin about the new staff member
        SendNotificationCommand adminCommand = SendNotificationCommand.builder()
                .recipientId("admin")
                .recipientType(RecipientType.USER)
                .channel(NotificationChannel.EMAIL)
                .subject("New Medical Staff Member Added")
                .content("A new medical staff member has been added to the system:\n\n" +
                        "Name: " + event.getMedicalStaff().getName().getFirstName() + " " + 
                        event.getMedicalStaff().getName().getLastName() + "\n" +
                        "Specialty: " + event.getMedicalStaff().getSpecialty() + "\n" +
                        "Email: " + event.getMedicalStaff().getEmail())
                .build();
        
        // Dispatch the admin command
        commandBus.dispatch(adminCommand);
    }
    
    /**
     * Handle MedicalStaffUpdatedEvent
     * @param event the event to handle
     */
    @EventListener
    public void handle(MedicalStaffUpdatedEvent event) {
        log.info("Handling MedicalStaffUpdatedEvent for staff with ID: {}", event.getAggregateId());
        
        // Create a notification for the medical staff member about the update
        SendNotificationCommand command = SendNotificationCommand.builder()
                .recipientId(event.getMedicalStaff().getId().toString())
                .recipientType(RecipientType.MEDICAL_STAFF)
                .channel(NotificationChannel.EMAIL)
                .subject("Your Profile Has Been Updated")
                .content("Dear Dr. " + event.getMedicalStaff().getName().getLastName() + ",\n\n" +
                        "Your profile information has been updated.\n\n" +
                        "If you did not make these changes, please contact the administration immediately.\n\n" +
                        "Best regards,\nHospital Administration")
                .build();
        
        // Dispatch the command
        commandBus.dispatch(command);
    }
}