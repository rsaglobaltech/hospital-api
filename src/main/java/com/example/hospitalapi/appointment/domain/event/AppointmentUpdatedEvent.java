package com.example.hospitalapi.appointment.domain.event;

import com.example.hospitalapi.appointment.domain.entity.Appointment;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when an appointment is updated
 */
@Getter
public class AppointmentUpdatedEvent extends BaseDomainEvent {
    
    private final Appointment appointment;
    
    /**
     * Create a new AppointmentUpdatedEvent
     * @param appointment the appointment that was updated
     */
    public AppointmentUpdatedEvent(Appointment appointment) {
        super(appointment.getId().toString());
        this.appointment = appointment;
    }
}