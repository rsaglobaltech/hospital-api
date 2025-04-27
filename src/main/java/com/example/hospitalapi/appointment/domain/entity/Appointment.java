package com.example.hospitalapi.appointment.domain.entity;

import com.example.hospitalapi.appointment.domain.valueobject.AppointmentId;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentNotes;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentReason;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentStatus;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentTime;
import com.example.hospitalapi.medicalstaff.domain.valueobject.DoctorId;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.shared.domain.entity.AggregateRoot;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Entity representing an appointment in the hospital system
 */
@Getter
public class Appointment extends AggregateRoot {
    private final AppointmentId id;
    private final PatientId patientId;
    private final DoctorId doctorId;
    private AppointmentTime appointmentTime;
    private AppointmentReason reason;
    private AppointmentNotes notes;
    private AppointmentStatus status;

    public Appointment(AppointmentId id, PatientId patientId, DoctorId doctorId, 
                      LocalDateTime startTime, LocalDateTime endTime, String reasonText) {
        if (id == null) {
            throw new IllegalArgumentException("Appointment ID cannot be null");
        }
        if (patientId == null) {
            throw new IllegalArgumentException("Patient ID cannot be null");
        }
        if (doctorId == null) {
            throw new IllegalArgumentException("Doctor ID cannot be null");
        }

        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentTime = new AppointmentTime(startTime, endTime);
        this.reason = new AppointmentReason(reasonText);
        this.notes = new AppointmentNotes("");
        this.status = AppointmentStatus.SCHEDULED;
    }

    public void updateTimes(LocalDateTime startTime, LocalDateTime endTime) {
        this.appointmentTime = new AppointmentTime(startTime, endTime);
    }

    public void updateReason(String reasonText) {
        this.reason = new AppointmentReason(reasonText);
    }

    public void updateNotes(String notesText) {
        this.notes = new AppointmentNotes(notesText);
    }

    public void updateStatus(AppointmentStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }

    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }

    public void complete() {
        this.status = AppointmentStatus.COMPLETED;
    }

    public void markNoShow() {
        this.status = AppointmentStatus.NO_SHOW;
    }

    public void startAppointment() {
        this.status = AppointmentStatus.IN_PROGRESS;
    }
}
