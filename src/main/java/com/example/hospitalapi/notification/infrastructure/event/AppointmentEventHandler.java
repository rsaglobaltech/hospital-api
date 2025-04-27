package com.example.hospitalapi.notification.infrastructure.event;

import com.example.hospitalapi.appointment.domain.event.AppointmentCreatedEvent;
import com.example.hospitalapi.appointment.domain.event.AppointmentUpdatedEvent;
import com.example.hospitalapi.notification.application.command.SendNotificationCommand;
import com.example.hospitalapi.notification.domain.valueobject.NotificationChannel;
import com.example.hospitalapi.notification.domain.valueobject.RecipientType;
import com.example.hospitalapi.shared.domain.bus.CommandBus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Event handler for appointment events that sends notifications
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AppointmentEventHandler {

    private final CommandBus commandBus;

    /**
     * Handle AppointmentCreatedEvent
     * @param event the event to handle
     */
    @EventListener
    public void handle(AppointmentCreatedEvent event) {
        log.info("Handling AppointmentCreatedEvent for appointment with ID: {}", event.getAggregateId());

        // Create a notification for the patient
        SendNotificationCommand command = SendNotificationCommand.builder()
                .recipientId(event.getAppointment().getPatientId().toString())
                .recipientType(RecipientType.PATIENT)
                .channel(NotificationChannel.EMAIL)
                .subject("Appointment Confirmation")
                .content("Your appointment has been scheduled for " + 
                        event.getAppointment().getAppointmentTime().getStartTime() + " with doctor ID " + 
                        event.getAppointment().getDoctorId() + ". Reason: " + 
                        event.getAppointment().getReason())
                .build();

        // Dispatch the command
        commandBus.dispatch(command);

        // Create an SMS notification as well
        SendNotificationCommand smsCommand = SendNotificationCommand.builder()
                .recipientId(event.getAppointment().getPatientId().toString())
                .recipientType(RecipientType.PATIENT)
                .channel(NotificationChannel.SMS)
                .subject("Appointment Reminder")
                .content("Reminder: You have an appointment on " + 
                        event.getAppointment().getAppointmentTime().getStartTime().toLocalDate() + " at " + 
                        event.getAppointment().getAppointmentTime().getStartTime().toLocalTime())
                .build();

        // Dispatch the SMS command
        commandBus.dispatch(smsCommand);
    }

    /**
     * Handle AppointmentUpdatedEvent
     * @param event the event to handle
     */
    @EventListener
    public void handle(AppointmentUpdatedEvent event) {
        log.info("Handling AppointmentUpdatedEvent for appointment with ID: {}", event.getAggregateId());

        // Create a notification for the patient
        SendNotificationCommand command = SendNotificationCommand.builder()
                .recipientId(event.getAppointment().getPatientId().toString())
                .recipientType(RecipientType.PATIENT)
                .channel(NotificationChannel.EMAIL)
                .subject("Appointment Updated")
                .content("Your appointment has been updated. New time: " + 
                        event.getAppointment().getAppointmentTime().getStartTime() + " with doctor ID " + 
                        event.getAppointment().getDoctorId() + ". Reason: " + 
                        event.getAppointment().getReason())
                .build();

        // Dispatch the command
        commandBus.dispatch(command);
    }
}
