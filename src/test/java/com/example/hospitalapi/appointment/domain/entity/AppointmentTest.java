package com.example.hospitalapi.appointment.domain.entity;

import com.example.hospitalapi.appointment.domain.valueobject.AppointmentId;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentStatus;
import com.example.hospitalapi.medicalstaff.domain.valueobject.DoctorId;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {
    
    private AppointmentId appointmentId;
    private PatientId patientId;
    private String doctorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String reason;
    
    @BeforeEach
    void setUp() {
        appointmentId = new AppointmentId();
        patientId = new PatientId();
        doctorId = UUID.randomUUID() + "-doctor";
        startTime = LocalDateTime.now().plusHours(1);
        endTime = startTime.plusHours(1);
        reason = "Annual check-up";
    }
    
    @Test
    void testCreateAppointment() {
        Appointment appointment = new Appointment(
                appointmentId,
                patientId,
                new DoctorId(doctorId),
                startTime,
                endTime,
                reason
        );
        
        assertEquals(appointmentId, appointment.getId());
        assertEquals(patientId, appointment.getPatientId());
        assertEquals(doctorId, appointment.getDoctorId());
        assertEquals(startTime, appointment.getAppointmentTime().getStartTime());
        assertEquals(endTime, appointment.getAppointmentTime().getEndTime());
        assertEquals(reason, appointment.getReason());
        assertEquals("", appointment.getNotes());
        assertEquals(AppointmentStatus.SCHEDULED, appointment.getStatus());
    }
    
    @Test
    void testUpdateAppointment() {
        Appointment appointment = new Appointment(
                appointmentId,
                patientId,
                new DoctorId(doctorId),
                startTime,
                endTime,
                reason
        );
        
        LocalDateTime newStartTime = startTime.plusDays(1);
        LocalDateTime newEndTime = newStartTime.plusHours(1);
        String newReason = "Follow-up appointment";
        String notes = "Patient requested a follow-up appointment";
        
        appointment.updateTimes(newStartTime, newEndTime);
        appointment.updateReason(newReason);
        appointment.updateNotes(notes);
        
        assertEquals(newStartTime, appointment.getAppointmentTime().getStartTime());
        assertEquals(newEndTime, appointment.getAppointmentTime().getEndTime());
        assertEquals(newReason, appointment.getReason());
        assertEquals(notes, appointment.getNotes());
    }
    
    @Test
    void testAppointmentStatusTransitions() {
        Appointment appointment = new Appointment(
                appointmentId,
                patientId,
                new DoctorId(doctorId),
                startTime,
                endTime,
                reason
        );
        
        assertEquals(AppointmentStatus.SCHEDULED, appointment.getStatus());
        
        appointment.startAppointment();
        assertEquals(AppointmentStatus.IN_PROGRESS, appointment.getStatus());
        
        appointment.complete();
        assertEquals(AppointmentStatus.COMPLETED, appointment.getStatus());
        
        // Create a new appointment to test other transitions
        Appointment appointment2 = new Appointment(
                new AppointmentId(),
                patientId,
                new DoctorId(doctorId),
                startTime,
                endTime,
                reason
        );
        
        appointment2.cancel();
        assertEquals(AppointmentStatus.CANCELLED, appointment2.getStatus());
        
        // Create a new appointment to test other transitions
        Appointment appointment3 = new Appointment(
                new AppointmentId(),
                patientId,
                new DoctorId(doctorId),
                startTime,
                endTime,
                reason
        );
        
        appointment3.markNoShow();
        assertEquals(AppointmentStatus.NO_SHOW, appointment3.getStatus());
    }
    
    @Test
    void testCreateAppointmentWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(
                    null,
                    patientId,
                    new DoctorId(doctorId),
                    startTime,
                    endTime,
                    reason
            );
        });
    }
    
    @Test
    void testCreateAppointmentWithNullPatientId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(
                    appointmentId,
                    null,
                    new DoctorId(doctorId),
                    startTime,
                    endTime,
                    reason
            );
        });
    }
    
    @Test
    void testCreateAppointmentWithNullDoctorId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(
                    appointmentId,
                    patientId,
                    null,
                    startTime,
                    endTime,
                    reason
            );
        });
    }
    
    @Test
    void testCreateAppointmentWithEmptyDoctorId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(
                    appointmentId,
                    patientId,
                    new DoctorId(""),
                    startTime,
                    endTime,
                    reason
            );
        });
    }
    
    @Test
    void testCreateAppointmentWithNullStartTime() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(
                    appointmentId,
                    patientId,
                    new DoctorId(doctorId),
                    null,
                    endTime,
                    reason
            );
        });
    }
    
    @Test
    void testCreateAppointmentWithNullEndTime() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(
                    appointmentId,
                    patientId,
                    new DoctorId(doctorId),
                    startTime,
                    null,
                    reason
            );
        });
    }
    
    @Test
    void testCreateAppointmentWithEndTimeBeforeStartTime() {
        LocalDateTime invalidEndTime = startTime.minusHours(1);
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(
                    appointmentId,
                    patientId,
                    new DoctorId(doctorId),
                    startTime,
                    invalidEndTime,
                    reason
            );
        });
    }
    
    @Test
    void testCreateAppointmentWithNullReason() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(
                    appointmentId,
                    patientId,
                    new DoctorId(doctorId),
                    startTime,
                    endTime,
                    null
            );
        });
    }
    
    @Test
    void testCreateAppointmentWithEmptyReason() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(
                    appointmentId,
                    patientId,
                    new DoctorId(doctorId),
                    startTime,
                    endTime,
                    ""
            );
        });
    }
    
    @Test
    void testUpdateTimesWithNullStartTime() {
        Appointment appointment = new Appointment(
                appointmentId,
                patientId,
                new DoctorId(doctorId),
                startTime,
                endTime,
                reason
        );
        
        assertThrows(IllegalArgumentException.class, () -> {
            appointment.updateTimes(null, endTime);
        });
    }
    
    @Test
    void testUpdateTimesWithNullEndTime() {
        Appointment appointment = new Appointment(
                appointmentId,
                patientId,
                new DoctorId(doctorId),
                startTime,
                endTime,
                reason
        );
        
        assertThrows(IllegalArgumentException.class, () -> {
            appointment.updateTimes(startTime, null);
        });
    }
    
    @Test
    void testUpdateTimesWithEndTimeBeforeStartTime() {
        Appointment appointment = new Appointment(
                appointmentId,
                patientId,
                new DoctorId(doctorId),
                startTime,
                endTime,
                reason
        );
        
        LocalDateTime invalidEndTime = startTime.minusHours(1);
        
        assertThrows(IllegalArgumentException.class, () -> {
            appointment.updateTimes(startTime, invalidEndTime);
        });
    }
    
    @Test
    void testUpdateReasonWithNullReason() {
        Appointment appointment = new Appointment(
                appointmentId,
                patientId,
                new DoctorId(doctorId),
                startTime,
                endTime,
                reason
        );
        
        assertThrows(IllegalArgumentException.class, () -> {
            appointment.updateReason(null);
        });
    }
    
    @Test
    void testUpdateReasonWithEmptyReason() {
        Appointment appointment = new Appointment(
                appointmentId,
                patientId,
                new DoctorId(doctorId),
                startTime,
                endTime,
                reason
        );
        
        assertThrows(IllegalArgumentException.class, () -> {
            appointment.updateReason("");
        });
    }
    
    @Test
    void testUpdateNotesWithNullNotes() {
        Appointment appointment = new Appointment(
                appointmentId,
                patientId,
                new DoctorId(doctorId),
                startTime,
                endTime,
                reason
        );
        
        appointment.updateNotes(null);
        assertEquals("", appointment.getNotes());
    }
    
    @Test
    void testUpdateStatusWithNullStatus() {
        Appointment appointment = new Appointment(
                appointmentId,
                patientId,
                new DoctorId(doctorId),
                startTime,
                endTime,
                reason
        );
        
        assertThrows(IllegalArgumentException.class, () -> {
            appointment.updateStatus(null);
        });
    }
}