package com.example.hospitalapi.clinicalrecord.infrastructure.rest.request;

import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for updating a clinical record
 */
public class UpdateClinicalRecordRequest {
    
    @NotNull(message = "Type is required")
    @Schema(description = "Type of the clinical record", example = "DIAGNOSIS")
    private ClinicalRecordType type;
    
    @NotBlank(message = "Title is required")
    @Schema(description = "Title of the clinical record", example = "Updated Diagnosis")
    private String title;
    
    @NotBlank(message = "Description is required")
    @Schema(description = "Description of the clinical record", example = "After further examination, patient...")
    private String description;
    
    @Schema(description = "URL of any attachment", example = "https://example.com/attachments/123.pdf")
    private String attachmentUrl;
    
    // Default constructor
    public UpdateClinicalRecordRequest() {
    }
    
    // Getters and setters
    public ClinicalRecordType getType() {
        return type;
    }
    
    public void setType(ClinicalRecordType type) {
        this.type = type;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getAttachmentUrl() {
        return attachmentUrl;
    }
    
    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }
}