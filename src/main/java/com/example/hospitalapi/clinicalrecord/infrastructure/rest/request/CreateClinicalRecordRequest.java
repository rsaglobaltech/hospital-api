package com.example.hospitalapi.clinicalrecord.infrastructure.rest.request;

import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * Request DTO for creating a clinical record
 */
@Getter
public class CreateClinicalRecordRequest {

    // Getters and setters
    @NotBlank(message = "Patient ID is required")
    @Schema(description = "ID of the patient", example = "123e4567-e89b-12d3-a456-426614174000")
    private String patientId;

    @NotBlank(message = "Doctor ID is required")
    @Schema(description = "ID of the doctor", example = "123e4567-e89b-12d3-a456-426614174001")
    private String doctorId;

    @NotNull(message = "Type is required")
    @Schema(description = "Type of the clinical record", example = "DIAGNOSIS")
    private ClinicalRecordType type;

    @NotBlank(message = "Title is required")
    @Schema(description = "Title of the clinical record", example = "Initial Diagnosis")
    private String title;

    @NotBlank(message = "Description is required")
    @Schema(description = "Description of the clinical record", example = "Patient presented with symptoms of...")
    private String description;

    @Schema(description = "URL of any attachment", example = "https://example.com/attachments/123.pdf")
    private String attachmentUrl;

    // Default constructor
    public CreateClinicalRecordRequest() {
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setType(ClinicalRecordType type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }
}
