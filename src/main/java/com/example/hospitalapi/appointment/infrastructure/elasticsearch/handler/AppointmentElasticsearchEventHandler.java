package com.example.hospitalapi.appointment.infrastructure.elasticsearch.handler;

import com.example.hospitalapi.appointment.domain.entity.Appointment;
import com.example.hospitalapi.appointment.domain.event.AppointmentCreatedEvent;
import com.example.hospitalapi.appointment.domain.event.AppointmentDeletedEvent;
import com.example.hospitalapi.appointment.domain.event.AppointmentUpdatedEvent;
import com.example.hospitalapi.appointment.infrastructure.elasticsearch.document.AppointmentDocument;
import com.example.hospitalapi.appointment.infrastructure.elasticsearch.repository.AppointmentElasticsearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Event handler for Appointment domain events that updates the Elasticsearch index
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AppointmentElasticsearchEventHandler {
    
    private final AppointmentElasticsearchRepository appointmentElasticsearchRepository;
    
    /**
     * Handle AppointmentCreatedEvent
     * @param event the event to handle
     */
    @EventListener
    public void handle(AppointmentCreatedEvent event) {
        log.info("Handling AppointmentCreatedEvent for appointment with ID: {}", event.getAggregateId());
        Appointment appointment = event.getAppointment();
        AppointmentDocument document = mapToDocument(appointment);
        appointmentElasticsearchRepository.save(document);
    }
    
    /**
     * Handle AppointmentUpdatedEvent
     * @param event the event to handle
     */
    @EventListener
    public void handle(AppointmentUpdatedEvent event) {
        log.info("Handling AppointmentUpdatedEvent for appointment with ID: {}", event.getAggregateId());
        Appointment appointment = event.getAppointment();
        AppointmentDocument document = mapToDocument(appointment);
        appointmentElasticsearchRepository.save(document);
    }
    
    /**
     * Handle AppointmentDeletedEvent
     * @param event the event to handle
     */
    @EventListener
    public void handle(AppointmentDeletedEvent event) {
        log.info("Handling AppointmentDeletedEvent for appointment with ID: {}", event.getAggregateId());
        appointmentElasticsearchRepository.deleteById(event.getAppointmentId().toString());
    }
    
    /**
     * Map an Appointment entity to an AppointmentDocument
     * @param appointment the Appointment entity to map
     * @return the mapped AppointmentDocument
     */
    private AppointmentDocument mapToDocument(Appointment appointment) {
        return AppointmentDocument.builder()
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
}