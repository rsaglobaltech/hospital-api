package com.example.hospitalapi.scheduling.domain.entity;

import com.example.hospitalapi.scheduling.domain.exception.AvailableSlotValidationException;
import com.example.hospitalapi.scheduling.domain.valueobject.AvailableSlotId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AvailableSlotTest {

    @Test
    void shouldCreateAvailableSlot() {
        // Given
        AvailableSlotId id = new AvailableSlotId();
        String doctorId = "doctor-123";
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);

        // When
        AvailableSlot availableSlot = new AvailableSlot(id, doctorId, date, startTime, endTime);

        // Then
        assertEquals(id, availableSlot.getId());
        assertEquals(doctorId, availableSlot.getDoctorId());
        assertEquals(date, availableSlot.getDate());
        assertEquals(startTime, availableSlot.getStartTime());
        assertEquals(endTime, availableSlot.getEndTime());
        assertFalse(availableSlot.isBooked());
        assertTrue(availableSlot.isAvailable());
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        // Given
        String doctorId = "doctor-123";
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);

        // When & Then
        assertThrows(AvailableSlotValidationException.class, () -> {
            new AvailableSlot(null, doctorId, date, startTime, endTime);
        });
    }

    @Test
    void shouldThrowExceptionWhenDoctorIdIsNull() {
        // Given
        AvailableSlotId id = new AvailableSlotId();
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);

        // When & Then
        assertThrows(AvailableSlotValidationException.class, () -> {
            new AvailableSlot(id, null, date, startTime, endTime);
        });
    }

    @Test
    void shouldThrowExceptionWhenDoctorIdIsEmpty() {
        // Given
        AvailableSlotId id = new AvailableSlotId();
        String doctorId = "";
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);

        // When & Then
        assertThrows(AvailableSlotValidationException.class, () -> {
            new AvailableSlot(id, doctorId, date, startTime, endTime);
        });
    }

    @Test
    void shouldThrowExceptionWhenDateIsNull() {
        // Given
        AvailableSlotId id = new AvailableSlotId();
        String doctorId = "doctor-123";
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);

        // When & Then
        assertThrows(AvailableSlotValidationException.class, () -> {
            new AvailableSlot(id, doctorId, null, startTime, endTime);
        });
    }

    @Test
    void shouldThrowExceptionWhenStartTimeIsNull() {
        // Given
        AvailableSlotId id = new AvailableSlotId();
        String doctorId = "doctor-123";
        LocalDate date = LocalDate.now();
        LocalTime endTime = LocalTime.of(10, 0);

        // When & Then
        assertThrows(AvailableSlotValidationException.class, () -> {
            new AvailableSlot(id, doctorId, date, null, endTime);
        });
    }

    @Test
    void shouldThrowExceptionWhenEndTimeIsNull() {
        // Given
        AvailableSlotId id = new AvailableSlotId();
        String doctorId = "doctor-123";
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(9, 0);

        // When & Then
        assertThrows(AvailableSlotValidationException.class, () -> {
            new AvailableSlot(id, doctorId, date, startTime, null);
        });
    }

    @Test
    void shouldThrowExceptionWhenStartTimeIsAfterEndTime() {
        // Given
        AvailableSlotId id = new AvailableSlotId();
        String doctorId = "doctor-123";
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(9, 0);

        // When & Then
        assertThrows(AvailableSlotValidationException.class, () -> {
            new AvailableSlot(id, doctorId, date, startTime, endTime);
        });
    }

    @Test
    void shouldBookAvailableSlot() {
        // Given
        AvailableSlot availableSlot = createValidAvailableSlot();
        assertTrue(availableSlot.isAvailable());
        assertFalse(availableSlot.isBooked());

        // When
        availableSlot.book();

        // Then
        assertFalse(availableSlot.isAvailable());
        assertTrue(availableSlot.isBooked());
    }

    @Test
    void shouldThrowExceptionWhenBookingAlreadyBookedSlot() {
        // Given
        AvailableSlot availableSlot = createValidAvailableSlot();
        availableSlot.book();
        assertTrue(availableSlot.isBooked());

        // When & Then
        assertThrows(AvailableSlotValidationException.class, () -> {
            availableSlot.book();
        });
    }

    @Test
    void shouldUnbookBookedSlot() {
        // Given
        AvailableSlot availableSlot = createValidAvailableSlot();
        availableSlot.book();
        assertTrue(availableSlot.isBooked());
        assertFalse(availableSlot.isAvailable());

        // When
        availableSlot.unbook();

        // Then
        assertFalse(availableSlot.isBooked());
        assertTrue(availableSlot.isAvailable());
    }

    @Test
    void shouldThrowExceptionWhenUnbookingAvailableSlot() {
        // Given
        AvailableSlot availableSlot = createValidAvailableSlot();
        assertFalse(availableSlot.isBooked());

        // When & Then
        assertThrows(AvailableSlotValidationException.class, () -> {
            availableSlot.unbook();
        });
    }

    private AvailableSlot createValidAvailableSlot() {
        AvailableSlotId id = new AvailableSlotId();
        String doctorId = "doctor-123";
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);
        return new AvailableSlot(id, doctorId, date, startTime, endTime);
    }
}