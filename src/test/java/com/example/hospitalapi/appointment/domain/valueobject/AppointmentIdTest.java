package com.example.hospitalapi.appointment.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentIdTest {
    
    @Test
    void testCreateAppointmentIdWithNoArguments() {
        AppointmentId appointmentId = new AppointmentId();
        
        assertNotNull(appointmentId.getValue());
        assertTrue(appointmentId.getValue() instanceof UUID);
    }
    
    @Test
    void testCreateAppointmentIdWithUUID() {
        UUID uuid = UUID.randomUUID();
        AppointmentId appointmentId = new AppointmentId(uuid);
        
        assertEquals(uuid, appointmentId.getValue());
        assertEquals(uuid.toString(), appointmentId.toString());
    }
    
    @Test
    void testCreateAppointmentIdWithString() {
        UUID uuid = UUID.randomUUID();
        AppointmentId appointmentId = new AppointmentId(uuid.toString());
        
        assertEquals(uuid, appointmentId.getValue());
        assertEquals(uuid.toString(), appointmentId.toString());
    }
    
    @Test
    void testCreateAppointmentIdWithInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AppointmentId("invalid-uuid");
        });
    }
    
    @Test
    void testAppointmentIdEquality() {
        UUID uuid = UUID.randomUUID();
        AppointmentId appointmentId1 = new AppointmentId(uuid);
        AppointmentId appointmentId2 = new AppointmentId(uuid);
        AppointmentId appointmentId3 = new AppointmentId(UUID.randomUUID());
        
        assertEquals(appointmentId1, appointmentId2);
        assertNotEquals(appointmentId1, appointmentId3);
        assertNotEquals(appointmentId2, appointmentId3);
    }
    
    @Test
    void testAppointmentIdHashCode() {
        UUID uuid = UUID.randomUUID();
        AppointmentId appointmentId1 = new AppointmentId(uuid);
        AppointmentId appointmentId2 = new AppointmentId(uuid);
        
        assertEquals(appointmentId1.hashCode(), appointmentId2.hashCode());
    }
}