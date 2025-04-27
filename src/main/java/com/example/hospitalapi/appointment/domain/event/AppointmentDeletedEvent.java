package com.example.hospitalapi.appointment.domain.event;

import com.example.hospitalapi.appointment.domain.valueobject.AppointmentId;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when an appointment is deleted
 */
@Getter
public class AppointmentDeletedEvent extends BaseDomainEvent {
    
    private final AppointmentId appointmentId;
    
    /**
     * Create a new AppointmentDeletedEvent
     * @param appointmentId the ID of the appointment that was deleted
     */
    public AppointmentDeletedEvent(AppointmentId appointmentId) {
        super(appointmentId.toString());
        this.appointmentId = appointmentId;
    }
}