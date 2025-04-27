package com.example.hospitalapi.appointment.infrastructure.elasticsearch.repository;

import com.example.hospitalapi.appointment.domain.valueobject.AppointmentStatus;
import com.example.hospitalapi.appointment.infrastructure.elasticsearch.document.AppointmentDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Elasticsearch repository for Appointment documents
 */
@Repository
public interface AppointmentElasticsearchRepository extends ElasticsearchRepository<AppointmentDocument, String> {
    
    /**
     * Find appointments by patient ID
     */
    List<AppointmentDocument> findByPatientId(String patientId);
    
    /**
     * Find appointments by doctor ID
     */
    List<AppointmentDocument> findByDoctorId(String doctorId);
    
    /**
     * Find appointments by status
     */
    List<AppointmentDocument> findByStatus(AppointmentStatus status);
    
    /**
     * Find appointments by start time between two dates
     */
    List<AppointmentDocument> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    
    /**
     * Find appointments by appointment type
     */
    List<AppointmentDocument> findByAppointmentType(String appointmentType);
    
    /**
     * Find appointments by priority
     */
    List<AppointmentDocument> findByPriority(String priority);
    
    /**
     * Find appointments by building
     */
    List<AppointmentDocument> findByBuilding(String building);
    
    /**
     * Find appointments by billing status
     */
    List<AppointmentDocument> findByBillingStatus(String billingStatus);
    
    /**
     * Find appointments that need follow-up
     */
    List<AppointmentDocument> findByFollowUpNeededTrue();
    
    /**
     * Find appointments that need reminders
     */
    List<AppointmentDocument> findByReminderSentFalseAndStartTimeBefore(LocalDateTime time);
    
    /**
     * Find appointments by cancellation reason containing text
     */
    List<AppointmentDocument> findByCancellationReasonContaining(String text);
    
    /**
     * Find appointments by patient ID and status
     */
    List<AppointmentDocument> findByPatientIdAndStatus(String patientId, AppointmentStatus status);
    
    /**
     * Find appointments by doctor ID and status
     */
    List<AppointmentDocument> findByDoctorIdAndStatus(String doctorId, AppointmentStatus status);
    
    /**
     * Find appointments by doctor ID and date range
     */
    List<AppointmentDocument> findByDoctorIdAndStartTimeBetween(String doctorId, LocalDateTime start, LocalDateTime end);
    
    /**
     * Find appointments by patient ID and date range
     */
    List<AppointmentDocument> findByPatientIdAndStartTimeBetween(String patientId, LocalDateTime start, LocalDateTime end);
}