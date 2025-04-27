package com.example.hospitalapi.pharmacy.infrastructure.rest;

import com.example.hospitalapi.pharmacy.application.command.CreateMedicationCommand;
import com.example.hospitalapi.pharmacy.application.command.CreateMedicationCommandHandler;
import com.example.hospitalapi.pharmacy.application.command.DeleteMedicationCommand;
import com.example.hospitalapi.pharmacy.application.command.DeleteMedicationCommandHandler;
import com.example.hospitalapi.pharmacy.application.command.UpdateMedicationCommand;
import com.example.hospitalapi.pharmacy.application.command.UpdateMedicationCommandHandler;
import com.example.hospitalapi.pharmacy.application.query.GetAllMedicationsQuery;
import com.example.hospitalapi.pharmacy.application.query.GetAllMedicationsQueryHandler;
import com.example.hospitalapi.pharmacy.application.query.GetMedicationByIdQuery;
import com.example.hospitalapi.pharmacy.application.query.GetMedicationByIdQueryHandler;
import com.example.hospitalapi.pharmacy.application.query.MedicationResponse;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import com.example.hospitalapi.pharmacy.infrastructure.rest.request.CreateMedicationRequest;
import com.example.hospitalapi.pharmacy.infrastructure.rest.request.UpdateMedicationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy/medications")
@RequiredArgsConstructor
@Tag(name = "Medication Management", description = "API for managing medications in the pharmacy")
public class MedicationController {
    
    private final CreateMedicationCommandHandler createMedicationCommandHandler;
    private final UpdateMedicationCommandHandler updateMedicationCommandHandler;
    private final DeleteMedicationCommandHandler deleteMedicationCommandHandler;
    private final GetMedicationByIdQueryHandler getMedicationByIdQueryHandler;
    private final GetAllMedicationsQueryHandler getAllMedicationsQueryHandler;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new medication", description = "Creates a new medication with the provided information")
    public ResponseEntity<String> createMedication(@Valid @RequestBody CreateMedicationRequest request) {
        CreateMedicationCommand command = CreateMedicationCommand.builder()
                .name(request.getName())
                .description(request.getDescription())
                .dosageForm(request.getDosageForm())
                .strength(request.getStrength())
                .manufacturer(request.getManufacturer())
                .price(request.getPrice())
                .requiresPrescription(request.isRequiresPrescription())
                .build();
        
        MedicationId medicationId = createMedicationCommandHandler.handle(command);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(medicationId.toString());
    }
    
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a medication", description = "Updates an existing medication with the provided information")
    public ResponseEntity<Void> updateMedication(@PathVariable String id, @Valid @RequestBody UpdateMedicationRequest request) {
        UpdateMedicationCommand command = UpdateMedicationCommand.builder()
                .medicationId(id)
                .name(request.getName())
                .description(request.getDescription())
                .dosageForm(request.getDosageForm())
                .strength(request.getStrength())
                .manufacturer(request.getManufacturer())
                .price(request.getPrice())
                .requiresPrescription(request.isRequiresPrescription())
                .build();
        
        updateMedicationCommandHandler.handle(command);
        
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a medication", description = "Deletes a medication by ID")
    public ResponseEntity<Void> deleteMedication(@PathVariable String id) {
        DeleteMedicationCommand command = new DeleteMedicationCommand(id);
        
        deleteMedicationCommandHandler.handle(command);
        
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get a medication by ID", description = "Retrieves a medication by ID")
    public ResponseEntity<MedicationResponse> getMedicationById(@PathVariable String id) {
        GetMedicationByIdQuery query = new GetMedicationByIdQuery(id);
        
        MedicationResponse response = getMedicationByIdQueryHandler.handle(query);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Get all medications", description = "Retrieves all medications")
    public ResponseEntity<List<MedicationResponse>> getAllMedications() {
        GetAllMedicationsQuery query = new GetAllMedicationsQuery();
        
        List<MedicationResponse> response = getAllMedicationsQueryHandler.handle(query);
        
        return ResponseEntity.ok(response);
    }
}