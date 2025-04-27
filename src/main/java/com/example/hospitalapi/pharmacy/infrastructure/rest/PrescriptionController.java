package com.example.hospitalapi.pharmacy.infrastructure.rest;

import com.example.hospitalapi.pharmacy.application.command.CreatePrescriptionCommand;
import com.example.hospitalapi.pharmacy.application.command.CreatePrescriptionCommandHandler;
import com.example.hospitalapi.pharmacy.application.command.DeletePrescriptionCommand;
import com.example.hospitalapi.pharmacy.application.command.DeletePrescriptionCommandHandler;
import com.example.hospitalapi.pharmacy.application.command.UpdatePrescriptionCommand;
import com.example.hospitalapi.pharmacy.application.command.UpdatePrescriptionCommandHandler;
import com.example.hospitalapi.pharmacy.application.query.GetAllPrescriptionsQuery;
import com.example.hospitalapi.pharmacy.application.query.GetAllPrescriptionsQueryHandler;
import com.example.hospitalapi.pharmacy.application.query.GetPrescriptionByIdQuery;
import com.example.hospitalapi.pharmacy.application.query.GetPrescriptionByIdQueryHandler;
import com.example.hospitalapi.pharmacy.application.query.GetPrescriptionsByPatientIdQuery;
import com.example.hospitalapi.pharmacy.application.query.GetPrescriptionsByPatientIdQueryHandler;
import com.example.hospitalapi.pharmacy.application.query.PrescriptionResponse;
import com.example.hospitalapi.pharmacy.domain.valueobject.PrescriptionId;
import com.example.hospitalapi.pharmacy.infrastructure.rest.request.CreatePrescriptionRequest;
import com.example.hospitalapi.pharmacy.infrastructure.rest.request.UpdatePrescriptionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy/prescriptions")
@RequiredArgsConstructor
@Tag(name = "Prescription Management", description = "API for managing prescriptions in the pharmacy")
public class PrescriptionController {
    
    private final CreatePrescriptionCommandHandler createPrescriptionCommandHandler;
    private final UpdatePrescriptionCommandHandler updatePrescriptionCommandHandler;
    private final DeletePrescriptionCommandHandler deletePrescriptionCommandHandler;
    private final GetPrescriptionByIdQueryHandler getPrescriptionByIdQueryHandler;
    private final GetAllPrescriptionsQueryHandler getAllPrescriptionsQueryHandler;
    private final GetPrescriptionsByPatientIdQueryHandler getPrescriptionsByPatientIdQueryHandler;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new prescription", description = "Creates a new prescription with the provided information")
    public ResponseEntity<String> createPrescription(@Valid @RequestBody CreatePrescriptionRequest request) {
        CreatePrescriptionCommand command = request.toCommand();
        
        PrescriptionId prescriptionId = createPrescriptionCommandHandler.handle(command);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(prescriptionId.toString());
    }
    
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a prescription", description = "Updates an existing prescription with the provided information")
    public ResponseEntity<Void> updatePrescription(@PathVariable String id, @Valid @RequestBody UpdatePrescriptionRequest request) {
        UpdatePrescriptionCommand command = request.toCommand(id);
        
        updatePrescriptionCommandHandler.handle(command);
        
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a prescription", description = "Deletes a prescription by ID")
    public ResponseEntity<Void> deletePrescription(@PathVariable String id) {
        DeletePrescriptionCommand command = new DeletePrescriptionCommand(id);
        
        deletePrescriptionCommandHandler.handle(command);
        
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get a prescription by ID", description = "Retrieves a prescription by ID")
    public ResponseEntity<PrescriptionResponse> getPrescriptionById(@PathVariable String id) {
        GetPrescriptionByIdQuery query = new GetPrescriptionByIdQuery(id);
        
        PrescriptionResponse response = getPrescriptionByIdQueryHandler.handle(query);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Get all prescriptions", description = "Retrieves all prescriptions")
    public ResponseEntity<List<PrescriptionResponse>> getAllPrescriptions() {
        GetAllPrescriptionsQuery query = new GetAllPrescriptionsQuery();
        
        List<PrescriptionResponse> response = getAllPrescriptionsQueryHandler.handle(query);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get prescriptions by patient ID", description = "Retrieves prescriptions for a specific patient")
    public ResponseEntity<List<PrescriptionResponse>> getPrescriptionsByPatientId(
            @PathVariable String patientId,
            @RequestParam(required = false, defaultValue = "false") boolean onlyValid) {
        
        GetPrescriptionsByPatientIdQuery query = new GetPrescriptionsByPatientIdQuery(patientId, onlyValid);
        
        List<PrescriptionResponse> response = getPrescriptionsByPatientIdQueryHandler.handle(query);
        
        return ResponseEntity.ok(response);
    }
}