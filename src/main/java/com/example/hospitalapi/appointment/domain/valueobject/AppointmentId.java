package com.example.hospitalapi.appointment.domain.valueobject;

import com.example.hospitalapi.shared.domain.valueobject.Id;

import java.util.UUID;

/**
 * Value object representing an appointment's unique identifier
 */
public class AppointmentId extends Id {
    
    public AppointmentId() {
        super();
    }
    
    public AppointmentId(UUID value) {
        super(value);
    }
    
    public AppointmentId(String value) {
        super(value);
    }
}