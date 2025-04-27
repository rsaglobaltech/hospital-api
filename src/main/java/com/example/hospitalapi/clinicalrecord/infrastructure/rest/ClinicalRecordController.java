package com.example.hospitalapi.clinicalrecord.infrastructure.rest;

import com.example.hospitalapi.clinicalrecord.application.command.CreateClinicalRecordCommand;
import com.example.hospitalapi.clinicalrecord.application.command.DeleteClinicalRecordCommand;
import com.example.hospitalapi.clinicalrecord.application.command.UpdateClinicalRecordCommand;
import com.example.hospitalapi.clinicalrecord.application.query.ClinicalRecordResponse;
import com.example.hospitalapi.clinicalrecord.application.query.GetAllClinicalRecordsQuery;
import com.example.hospitalapi.clinicalrecord.application.query.GetClinicalRecordByIdQuery;
import com.example.hospitalapi.clinicalrecord.application.query.GetClinicalRecordsByPatientIdQuery;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordId;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordType;
import com.example.hospitalapi.clinicalrecord.infrastructure.rest.request.CreateClinicalRecordRequest;
import com.example.hospitalapi.clinicalrecord.infrastructure.rest.request.UpdateClinicalRecordRequest;
import com.example.hospitalapi.shared.domain.bus.CommandBus;
import com.example.hospitalapi.shared.domain.bus.QueryBus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for clinical record operations
 */
@RestController
@RequestMapping("/api/clinical-records")
@Tag(name = "Clinical Records Management", description = "API for managing clinical records")
public class ClinicalRecordController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    public ClinicalRecordController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new clinical record", description = "Creates a new clinical record with the provided information")
    public ResponseEntity<String> createClinicalRecord(@Valid @RequestBody CreateClinicalRecordRequest request) {
        CreateClinicalRecordCommand command = CreateClinicalRecordCommand.builder()
                .patientId(request.getPatientId())
                .doctorId(request.getDoctorId())
                .type(request.getType())
                .title(request.getTitle())
                .description(request.getDescription())
                .attachmentUrl(request.getAttachmentUrl())
                .build();

        ClinicalRecordId clinicalRecordId = (ClinicalRecordId) commandBus.dispatch(command).join();

        return ResponseEntity.status(HttpStatus.CREATED).body(clinicalRecordId.toString());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a clinical record", description = "Updates an existing clinical record with the provided information")
    public ResponseEntity<Void> updateClinicalRecord(@PathVariable String id, @Valid @RequestBody UpdateClinicalRecordRequest request) {
        UpdateClinicalRecordCommand command = UpdateClinicalRecordCommand.builder()
                .clinicalRecordId(id)
                .type(request.getType())
                .title(request.getTitle())
                .description(request.getDescription())
                .attachmentUrl(request.getAttachmentUrl())
                .build();

        commandBus.dispatch(command).join();

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a clinical record", description = "Deletes a clinical record by ID")
    public ResponseEntity<Void> deleteClinicalRecord(@PathVariable String id) {
        DeleteClinicalRecordCommand command = new DeleteClinicalRecordCommand(id);

        commandBus.dispatch(command).join();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a clinical record by ID", description = "Retrieves a clinical record by ID")
    public ResponseEntity<ClinicalRecordResponse> getClinicalRecordById(@PathVariable String id) {
        GetClinicalRecordByIdQuery query = new GetClinicalRecordByIdQuery(id);

        ClinicalRecordResponse response = queryBus.dispatch(query).join();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all clinical records", description = "Retrieves all clinical records with optional filtering by type")
    public ResponseEntity<List<ClinicalRecordResponse>> getAllClinicalRecords(
            @RequestParam(required = false) ClinicalRecordType type) {

        GetAllClinicalRecordsQuery query;
        if (type != null) {
            query = new GetAllClinicalRecordsQuery(type);
        } else {
            query = new GetAllClinicalRecordsQuery();
        }

        List<ClinicalRecordResponse> response = queryBus.dispatch(query).join();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get clinical records by patient ID", description = "Retrieves clinical records for a specific patient with optional filtering by type")
    public ResponseEntity<List<ClinicalRecordResponse>> getClinicalRecordsByPatientId(
            @PathVariable String patientId,
            @RequestParam(required = false) ClinicalRecordType type) {

        GetClinicalRecordsByPatientIdQuery query;
        if (type != null) {
            query = new GetClinicalRecordsByPatientIdQuery(patientId, type);
        } else {
            query = new GetClinicalRecordsByPatientIdQuery(patientId);
        }

        List<ClinicalRecordResponse> response = queryBus.dispatch(query).join();

        return ResponseEntity.ok(response);
    }
}