package com.example.hospitalapi.appointment.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentStatusTest {
    
    @Test
    void testAppointmentStatusValues() {
        // Verify all expected enum values exist
        assertEquals(5, AppointmentStatus.values().length);
        assertNotNull(AppointmentStatus.SCHEDULED);
        assertNotNull(AppointmentStatus.IN_PROGRESS);
        assertNotNull(AppointmentStatus.COMPLETED);
        assertNotNull(AppointmentStatus.CANCELLED);
        assertNotNull(AppointmentStatus.NO_SHOW);
    }
    
    @Test
    void testAppointmentStatusOrdinals() {
        // Verify the ordinals are as expected
        assertEquals(0, AppointmentStatus.SCHEDULED.ordinal());
        assertEquals(1, AppointmentStatus.IN_PROGRESS.ordinal());
        assertEquals(2, AppointmentStatus.COMPLETED.ordinal());
        assertEquals(3, AppointmentStatus.CANCELLED.ordinal());
        assertEquals(4, AppointmentStatus.NO_SHOW.ordinal());
    }
    
    @Test
    void testAppointmentStatusNames() {
        // Verify the names are as expected
        assertEquals("SCHEDULED", AppointmentStatus.SCHEDULED.name());
        assertEquals("IN_PROGRESS", AppointmentStatus.IN_PROGRESS.name());
        assertEquals("COMPLETED", AppointmentStatus.COMPLETED.name());
        assertEquals("CANCELLED", AppointmentStatus.CANCELLED.name());
        assertEquals("NO_SHOW", AppointmentStatus.NO_SHOW.name());
    }
    
    @Test
    void testAppointmentStatusValueOf() {
        // Verify valueOf works as expected
        assertSame(AppointmentStatus.SCHEDULED, AppointmentStatus.valueOf("SCHEDULED"));
        assertSame(AppointmentStatus.IN_PROGRESS, AppointmentStatus.valueOf("IN_PROGRESS"));
        assertSame(AppointmentStatus.COMPLETED, AppointmentStatus.valueOf("COMPLETED"));
        assertSame(AppointmentStatus.CANCELLED, AppointmentStatus.valueOf("CANCELLED"));
        assertSame(AppointmentStatus.NO_SHOW, AppointmentStatus.valueOf("NO_SHOW"));
    }
    
    @Test
    void testAppointmentStatusValueOfInvalidName() {
        // Verify valueOf throws IllegalArgumentException for invalid names
        assertThrows(IllegalArgumentException.class, () -> AppointmentStatus.valueOf("INVALID"));
    }
}