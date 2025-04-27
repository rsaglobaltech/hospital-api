package com.example.hospitalapi.appointment.infrastructure.persistence.adapter;

import com.example.hospitalapi.appointment.domain.entity.Appointment;
import com.example.hospitalapi.appointment.domain.repository.AppointmentRepository;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentId;
import com.example.hospitalapi.appointment.infrastructure.persistence.entity.AppointmentJpaEntity;
import com.example.hospitalapi.appointment.infrastructure.persistence.repository.AppointmentJpaRepository;
import com.example.hospitalapi.medicalstaff.domain.valueobject.DoctorId;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adapter implementing AppointmentRepository using JPA
 */
@Component
@RequiredArgsConstructor
public class AppointmentRepositoryAdapter implements AppointmentRepository {
    
    private final AppointmentJpaRepository appointmentJpaRepository;
    
    @Override
    public Appointment save(Appointment appointment) {
        AppointmentJpaEntity appointmentJpaEntity = mapToJpaEntity(appointment);
        AppointmentJpaEntity savedEntity = appointmentJpaRepository.save(appointmentJpaEntity);
        return mapToDomainEntity(savedEntity);
    }
    
    @Override
    public Optional<Appointment> findById(AppointmentId id) {
        return appointmentJpaRepository.findById(id.toString())
                .map(this::mapToDomainEntity);
    }
    
    @Override
    public List<Appointment> findAll() {
        return appointmentJpaRepository.findAll().stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Appointment> findByPatientId(PatientId patientId) {
        return appointmentJpaRepository.findByPatientId(patientId.toString()).stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Appointment> findByDoctorId(String doctorId) {
        return appointmentJpaRepository.findByDoctorId(doctorId).stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Appointment> findByTimeRange(LocalDateTime start, LocalDateTime end) {
        return appointmentJpaRepository.findByStartTimeBetween(start, end).stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(AppointmentId id) {
        appointmentJpaRepository.deleteById(id.toString());
    }
    
    @Override
    public boolean existsById(AppointmentId id) {
        return appointmentJpaRepository.existsById(id.toString());
    }
    
    private AppointmentJpaEntity mapToJpaEntity(Appointment appointment) {
        return AppointmentJpaEntity.builder()
                .id(appointment.getId().toString())
                .patientId(appointment.getPatientId().toString())
                .doctorId(appointment.getDoctorId().getValue().toString())
                .startTime(appointment.getAppointmentTime().getStartTime())
                .endTime(appointment.getAppointmentTime().getEndTime())
                .reason(appointment.getReason().getValue())
                .notes(appointment.getNotes().getValue())
                .status(appointment.getStatus())
                .build();
    }
    
    private Appointment mapToDomainEntity(AppointmentJpaEntity entity) {
        AppointmentId appointmentId = new AppointmentId(UUID.fromString(entity.getId()));
        PatientId patientId = new PatientId(UUID.fromString(entity.getPatientId()));
        
        Appointment appointment = new Appointment(
                appointmentId,
                patientId,
                new DoctorId(entity.getDoctorId()),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getReason()
        );
        
        appointment.updateNotes(entity.getNotes());
        appointment.updateStatus(entity.getStatus());
        
        return appointment;
    }
}