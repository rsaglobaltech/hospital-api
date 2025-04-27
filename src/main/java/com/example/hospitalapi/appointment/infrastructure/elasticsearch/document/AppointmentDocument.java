package com.example.hospitalapi.appointment.infrastructure.elasticsearch.document;

import com.example.hospitalapi.appointment.domain.valueobject.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Elasticsearch document for Appointment
 */
@Document(indexName = "appointments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDocument {
    
    @Id
    private String id;
    
    @Field(type = FieldType.Keyword, name = "patient_id")
    private String patientId;
    
    @Field(type = FieldType.Keyword, name = "doctor_id")
    private String doctorId;
    
    @Field(type = FieldType.Date, name = "start_time")
    private LocalDateTime startTime;
    
    @Field(type = FieldType.Date, name = "end_time")
    private LocalDateTime endTime;
    
    @Field(type = FieldType.Text)
    private String reason;
    
    @Field(type = FieldType.Text)
    private String notes;
    
    @Field(type = FieldType.Keyword)
    private AppointmentStatus status;
    
    // Appointment type information
    @Field(type = FieldType.Keyword, name = "appointment_type")
    private String appointmentType;
    
    @Field(type = FieldType.Keyword)
    private String priority;
    
    // Location information
    @Field(type = FieldType.Keyword, name = "room_number")
    private String roomNumber;
    
    @Field(type = FieldType.Keyword)
    private String floor;
    
    @Field(type = FieldType.Keyword)
    private String building;
    
    @Field(type = FieldType.Text, name = "location_notes")
    private String locationNotes;
    
    // Billing information
    @Field(type = FieldType.Keyword, name = "billing_status")
    private String billingStatus;
    
    @Field(type = FieldType.Keyword, name = "insurance_claim_number")
    private String insuranceClaimNumber;
    
    @Field(type = FieldType.Double)
    private BigDecimal cost;
    
    @Field(type = FieldType.Double, name = "paid_amount")
    private BigDecimal paidAmount;
    
    // Follow-up information
    @Field(type = FieldType.Boolean, name = "follow_up_needed")
    private Boolean followUpNeeded;
    
    @Field(type = FieldType.Keyword, name = "follow_up_appointment_id")
    private String followUpAppointmentId;
    
    @Field(type = FieldType.Text, name = "follow_up_instructions")
    private String followUpInstructions;
    
    // Reminder information
    @Field(type = FieldType.Boolean, name = "reminder_sent")
    private Boolean reminderSent;
    
    @Field(type = FieldType.Date, name = "reminder_time")
    private LocalDateTime reminderTime;
    
    // Audit and cancellation information
    @Field(type = FieldType.Date, name = "created_at")
    private LocalDateTime createdAt;
    
    @Field(type = FieldType.Date, name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Field(type = FieldType.Text, name = "created_by")
    private String createdBy;
    
    @Field(type = FieldType.Text, name = "updated_by")
    private String updatedBy;
    
    @Field(type = FieldType.Text, name = "cancelled_by")
    private String cancelledBy;
    
    @Field(type = FieldType.Date, name = "cancelled_at")
    private LocalDateTime cancelledAt;
    
    @Field(type = FieldType.Text, name = "cancellation_reason")
    private String cancellationReason;
}