package com.example.hospitalapi.patient.infrastructure.rest;

import com.example.hospitalapi.patient.application.command.CreatePatientCommand;
import com.example.hospitalapi.patient.application.command.CreatePatientCommandHandler;
import com.example.hospitalapi.patient.application.command.DeletePatientCommand;
import com.example.hospitalapi.patient.application.command.DeletePatientCommandHandler;
import com.example.hospitalapi.patient.application.command.UpdatePatientCommand;
import com.example.hospitalapi.patient.application.command.UpdatePatientCommandHandler;
import com.example.hospitalapi.patient.application.query.GetAllPatientsQuery;
import com.example.hospitalapi.patient.application.query.GetAllPatientsQueryHandler;
import com.example.hospitalapi.patient.application.query.GetPatientByIdQuery;
import com.example.hospitalapi.patient.application.query.GetPatientByIdQueryHandler;
import com.example.hospitalapi.patient.application.query.PatientResponse;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.patient.infrastructure.rest.request.CreatePatientRequest;
import com.example.hospitalapi.patient.infrastructure.rest.request.UpdatePatientRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Patient Management", description = "API for managing patients")
public class PatientController {

    private final CreatePatientCommandHandler createPatientCommandHandler;
    private final UpdatePatientCommandHandler updatePatientCommandHandler;
    private final DeletePatientCommandHandler deletePatientCommandHandler;
    private final GetPatientByIdQueryHandler getPatientByIdQueryHandler;
    private final GetAllPatientsQueryHandler getAllPatientsQueryHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new patient", description = "Creates a new patient with the provided information")
    public ResponseEntity<String> createPatient(@Valid @RequestBody CreatePatientRequest request) {
        log.info("Creating new patient with email: {}", request.getEmail());

        CreatePatientCommand command = CreatePatientCommand.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .build();

        PatientId patientId = createPatientCommandHandler.handle(command);

        log.info("Patient created successfully with ID: {}", patientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientId.toString());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a patient", description = "Updates an existing patient with the provided information")
    public ResponseEntity<Void> updatePatient(@PathVariable String id, @Valid @RequestBody UpdatePatientRequest request) {
        log.info("Updating patient with ID: {}", id);

        UpdatePatientCommand command = UpdatePatientCommand.builder()
                .patientId(id)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .medicalHistory(request.getMedicalHistory())
                .build();

        updatePatientCommandHandler.handle(command);

        log.info("Patient updated successfully with ID: {}", id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a patient", description = "Deletes a patient by ID")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        log.info("Deleting patient with ID: {}", id);

        DeletePatientCommand command = new DeletePatientCommand(id);

        deletePatientCommandHandler.handle(command);

        log.info("Patient deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a patient by ID", description = "Retrieves a patient by ID")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable String id) {
        log.info("Retrieving patient with ID: {}", id);

        GetPatientByIdQuery query = new GetPatientByIdQuery(id);

        PatientResponse response = getPatientByIdQueryHandler.handle(query);

        log.debug("Retrieved patient: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all patients", description = "Retrieves all patients")
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        log.info("Retrieving all patients");

        GetAllPatientsQuery query = new GetAllPatientsQuery();

        List<PatientResponse> response = getAllPatientsQueryHandler.handle(query);

        log.debug("Retrieved {} patients", response.size());
        return ResponseEntity.ok(response);
    }
}
