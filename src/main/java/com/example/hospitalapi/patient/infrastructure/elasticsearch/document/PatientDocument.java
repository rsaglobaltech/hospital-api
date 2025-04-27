package com.example.hospitalapi.patient.infrastructure.elasticsearch.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Elasticsearch document for Patient
 */
@Document(indexName = "patients")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDocument {
    
    @Id
    private String id;
    
    @Field(type = FieldType.Text, name = "first_name")
    private String firstName;
    
    @Field(type = FieldType.Text, name = "last_name")
    private String lastName;
    
    @Field(type = FieldType.Text)
    private String email;
    
    @Field(type = FieldType.Text, name = "phone_number")
    private String phoneNumber;
    
    @Field(type = FieldType.Date, name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Field(type = FieldType.Text)
    private String address;
    
    @Field(type = FieldType.Text, name = "medical_history")
    private String medicalHistory;
    
    // Insurance information
    @Field(type = FieldType.Text, name = "insurance_provider")
    private String insuranceProvider;
    
    @Field(type = FieldType.Text, name = "insurance_policy_number")
    private String insurancePolicyNumber;
    
    @Field(type = FieldType.Date, name = "insurance_expiry_date")
    private LocalDate insuranceExpiryDate;
    
    // Emergency contact information
    @Field(type = FieldType.Text, name = "emergency_contact_name")
    private String emergencyContactName;
    
    @Field(type = FieldType.Text, name = "emergency_contact_phone")
    private String emergencyContactPhone;
    
    @Field(type = FieldType.Text, name = "emergency_contact_relationship")
    private String emergencyContactRelationship;
    
    // Patient preferences
    @Field(type = FieldType.Text, name = "preferred_language")
    private String preferredLanguage;
    
    @Field(type = FieldType.Text, name = "preferred_communication_method")
    private String preferredCommunicationMethod;
    
    @Field(type = FieldType.Text, name = "special_needs")
    private String specialNeeds;
    
    // Patient status
    @Field(type = FieldType.Keyword)
    private String status;
    
    // Audit fields
    @Field(type = FieldType.Date, name = "created_at")
    private LocalDateTime createdAt;
    
    @Field(type = FieldType.Date, name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Field(type = FieldType.Text, name = "created_by")
    private String createdBy;
    
    @Field(type = FieldType.Text, name = "updated_by")
    private String updatedBy;
}