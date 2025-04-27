package com.example.hospitalapi.medicalstaff.infrastructure.elasticsearch.document;

import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
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
import java.util.List;

/**
 * Elasticsearch document for MedicalStaff
 */
@Document(indexName = "medical_staff")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalStaffDocument {
    
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
    
    @Field(type = FieldType.Keyword)
    private Specialty specialty;
    
    @Field(type = FieldType.Nested, name = "qualifications")
    private List<QualificationDocument> qualifications;
    
    @Field(type = FieldType.Boolean)
    private boolean active;
    
    @Field(type = FieldType.Date, name = "hire_date")
    private LocalDate hireDate;
    
    @Field(type = FieldType.Date, name = "termination_date")
    private LocalDate terminationDate;
    
    // Professional information
    @Field(type = FieldType.Integer, name = "years_of_experience")
    private Integer yearsOfExperience;
    
    @Field(type = FieldType.Text)
    private String biography;
    
    @Field(type = FieldType.Text, name = "profile_image_url")
    private String profileImageUrl;
    
    @Field(type = FieldType.Text, name = "professional_website")
    private String professionalWebsite;
    
    // Contact information
    @Field(type = FieldType.Keyword, name = "preferred_contact_method")
    private String preferredContactMethod;
    
    @Field(type = FieldType.Text, name = "work_email")
    private String workEmail;
    
    @Field(type = FieldType.Text, name = "work_phone")
    private String workPhone;
    
    @Field(type = FieldType.Text, name = "pager_number")
    private String pagerNumber;
    
    // Availability information
    @Field(type = FieldType.Text, name = "available_days")
    private String availableDays;
    
    @Field(type = FieldType.Text, name = "available_hours")
    private String availableHours;
    
    @Field(type = FieldType.Integer, name = "max_appointments_per_day")
    private Integer maxAppointmentsPerDay;
    
    @Field(type = FieldType.Integer, name = "vacation_days_remaining")
    private Integer vacationDaysRemaining;
    
    // Organizational information
    @Field(type = FieldType.Keyword)
    private String department;
    
    @Field(type = FieldType.Keyword)
    private String position;
    
    @Field(type = FieldType.Keyword, name = "supervisor_id")
    private String supervisorId;
    
    @Field(type = FieldType.Boolean, name = "is_supervisor")
    private Boolean isSupervisor;
    
    // Employment information
    @Field(type = FieldType.Keyword, name = "salary_grade")
    private String salaryGrade;
    
    @Field(type = FieldType.Keyword, name = "contract_type")
    private String contractType;
    
    @Field(type = FieldType.Date, name = "contract_start_date")
    private LocalDate contractStartDate;
    
    @Field(type = FieldType.Date, name = "contract_end_date")
    private LocalDate contractEndDate;
    
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