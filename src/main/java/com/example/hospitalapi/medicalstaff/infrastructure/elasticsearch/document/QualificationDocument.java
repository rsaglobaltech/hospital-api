package com.example.hospitalapi.medicalstaff.infrastructure.elasticsearch.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

/**
 * Elasticsearch document for Qualification
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualificationDocument {
    
    @Field(type = FieldType.Keyword)
    private String id;
    
    @Field(type = FieldType.Text)
    private String degree;
    
    @Field(type = FieldType.Text)
    private String institution;
    
    @Field(type = FieldType.Date, name = "date_obtained")
    private LocalDate dateObtained;
    
    @Field(type = FieldType.Text, name = "license_number")
    private String licenseNumber;
    
    @Field(type = FieldType.Date, name = "license_expiry_date")
    private LocalDate licenseExpiryDate;
    
    @Field(type = FieldType.Keyword, name = "staff_id")
    private String staffId;
}