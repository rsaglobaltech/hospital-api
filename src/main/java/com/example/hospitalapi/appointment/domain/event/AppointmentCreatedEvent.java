package com.example.hospitalapi.appointment.domain.event;

import com.example.hospitalapi.appointment.domain.entity.Appointment;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when an appointment is created
 */
@Getter
public class AppointmentCreatedEvent extends BaseDomainEvent {
    
    private final Appointment appointment;
    
    /**
     * Create a new AppointmentCreatedEvent
     * @param appointment the appointment that was created
     */
    public AppointmentCreatedEvent(Appointment appointment) {
        super(appointment.getId().toString());
        this.appointment = appointment;
    }
}