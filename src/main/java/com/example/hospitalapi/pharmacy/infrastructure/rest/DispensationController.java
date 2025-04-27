package com.example.hospitalapi.pharmacy.infrastructure.rest;

import com.example.hospitalapi.pharmacy.application.command.CreateDispensationCommand;
import com.example.hospitalapi.pharmacy.application.command.CreateDispensationCommandHandler;
import com.example.hospitalapi.pharmacy.application.command.DeleteDispensationCommand;
import com.example.hospitalapi.pharmacy.application.command.DeleteDispensationCommandHandler;
import com.example.hospitalapi.pharmacy.application.command.UpdateDispensationCommand;
import com.example.hospitalapi.pharmacy.application.command.UpdateDispensationCommandHandler;
import com.example.hospitalapi.pharmacy.application.query.DispensationResponse;
import com.example.hospitalapi.pharmacy.application.query.GetAllDispensationsQuery;
import com.example.hospitalapi.pharmacy.application.query.GetAllDispensationsQueryHandler;
import com.example.hospitalapi.pharmacy.application.query.GetDispensationByIdQuery;
import com.example.hospitalapi.pharmacy.application.query.GetDispensationByIdQueryHandler;
import com.example.hospitalapi.pharmacy.application.query.GetDispensationsByPatientIdQuery;
import com.example.hospitalapi.pharmacy.application.query.GetDispensationsByPatientIdQueryHandler;
import com.example.hospitalapi.pharmacy.domain.valueobject.DispensationId;
import com.example.hospitalapi.pharmacy.infrastructure.rest.request.CreateDispensationRequest;
import com.example.hospitalapi.pharmacy.infrastructure.rest.request.UpdateDispensationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy/dispensations")
@RequiredArgsConstructor
@Tag(name = "Dispensation Management", description = "API for managing dispensations in the pharmacy")
public class DispensationController {
    
    private final CreateDispensationCommandHandler createDispensationCommandHandler;
    private final UpdateDispensationCommandHandler updateDispensationCommandHandler;
    private final DeleteDispensationCommandHandler deleteDispensationCommandHandler;
    private final GetDispensationByIdQueryHandler getDispensationByIdQueryHandler;
    private final GetAllDispensationsQueryHandler getAllDispensationsQueryHandler;
    private final GetDispensationsByPatientIdQueryHandler getDispensationsByPatientIdQueryHandler;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new dispensation", description = "Creates a new dispensation with the provided information")
    public ResponseEntity<String> createDispensation(@Valid @RequestBody CreateDispensationRequest request) {
        CreateDispensationCommand command = request.toCommand();
        
        DispensationId dispensationId = createDispensationCommandHandler.handle(command);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(dispensationId.toString());
    }
    
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a dispensation", description = "Updates an existing dispensation with the provided information")
    public ResponseEntity<Void> updateDispensation(@PathVariable String id, @Valid @RequestBody UpdateDispensationRequest request) {
        UpdateDispensationCommand command = UpdateDispensationCommand.builder()
                .dispensationId(id)
                .notes(request.getNotes())
                .build();
        
        updateDispensationCommandHandler.handle(command);
        
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a dispensation", description = "Deletes a dispensation by ID")
    public ResponseEntity<Void> deleteDispensation(@PathVariable String id) {
        DeleteDispensationCommand command = new DeleteDispensationCommand(id);
        
        deleteDispensationCommandHandler.handle(command);
        
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get a dispensation by ID", description = "Retrieves a dispensation by ID")
    public ResponseEntity<DispensationResponse> getDispensationById(@PathVariable String id) {
        GetDispensationByIdQuery query = new GetDispensationByIdQuery(id);
        
        DispensationResponse response = getDispensationByIdQueryHandler.handle(query);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Get all dispensations", description = "Retrieves all dispensations")
    public ResponseEntity<List<DispensationResponse>> getAllDispensations() {
        GetAllDispensationsQuery query = new GetAllDispensationsQuery();
        
        List<DispensationResponse> response = getAllDispensationsQueryHandler.handle(query);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get dispensations by patient ID", description = "Retrieves dispensations for a specific patient")
    public ResponseEntity<List<DispensationResponse>> getDispensationsByPatientId(@PathVariable String patientId) {
        GetDispensationsByPatientIdQuery query = new GetDispensationsByPatientIdQuery(patientId);
        
        List<DispensationResponse> response = getDispensationsByPatientIdQueryHandler.handle(query);
        
        return ResponseEntity.ok(response);
    }
}