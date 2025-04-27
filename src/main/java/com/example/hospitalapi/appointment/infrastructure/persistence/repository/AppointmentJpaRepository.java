package com.example.hospitalapi.appointment.infrastructure.persistence.repository;

import com.example.hospitalapi.appointment.domain.valueobject.AppointmentStatus;
import com.example.hospitalapi.appointment.infrastructure.persistence.entity.AppointmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * JPA repository for AppointmentJpaEntity
 */
@Repository
public interface AppointmentJpaRepository extends JpaRepository<AppointmentJpaEntity, String> {
    
    /**
     * Find appointments by patient ID
     */
    List<AppointmentJpaEntity> findByPatientId(String patientId);
    
    /**
     * Find appointments by doctor ID
     */
    List<AppointmentJpaEntity> findByDoctorId(String doctorId);
    
    /**
     * Find appointments by status
     */
    List<AppointmentJpaEntity> findByStatus(AppointmentStatus status);
    
    /**
     * Find appointments by time range
     */
    List<AppointmentJpaEntity> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    
    /**
     * Find appointments by patient ID and time range
     */
    List<AppointmentJpaEntity> findByPatientIdAndStartTimeBetween(String patientId, LocalDateTime start, LocalDateTime end);
    
    /**
     * Find appointments by doctor ID and time range
     */
    List<AppointmentJpaEntity> findByDoctorIdAndStartTimeBetween(String doctorId, LocalDateTime start, LocalDateTime end);
}