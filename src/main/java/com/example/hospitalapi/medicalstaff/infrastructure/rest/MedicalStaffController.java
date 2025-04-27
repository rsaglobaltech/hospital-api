package com.example.hospitalapi.medicalstaff.infrastructure.rest;

import com.example.hospitalapi.medicalstaff.application.command.CreateMedicalStaffCommand;
import com.example.hospitalapi.medicalstaff.application.command.DeactivateMedicalStaffCommand;
import com.example.hospitalapi.medicalstaff.application.command.UpdateMedicalStaffCommand;
import com.example.hospitalapi.medicalstaff.application.query.GetAllMedicalStaffQuery;
import com.example.hospitalapi.medicalstaff.application.query.GetMedicalStaffByIdQuery;
import com.example.hospitalapi.medicalstaff.application.query.GetMedicalStaffBySpecialtyQuery;
import com.example.hospitalapi.medicalstaff.application.query.MedicalStaffResponse;
import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
import com.example.hospitalapi.medicalstaff.domain.valueobject.StaffId;
import com.example.hospitalapi.medicalstaff.infrastructure.rest.request.CreateMedicalStaffRequest;
import com.example.hospitalapi.medicalstaff.infrastructure.rest.request.DeactivateMedicalStaffRequest;
import com.example.hospitalapi.medicalstaff.infrastructure.rest.request.UpdateMedicalStaffRequest;
import com.example.hospitalapi.shared.domain.bus.CommandBus;
import com.example.hospitalapi.shared.domain.bus.QueryBus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medical-staff")
@RequiredArgsConstructor
@Tag(name = "Medical Staff Management", description = "API for managing medical staff")
public class MedicalStaffController {
    
    private final CommandBus commandBus;
    private final QueryBus queryBus;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new medical staff member", description = "Creates a new medical staff member with the provided information")
    public ResponseEntity<String> createMedicalStaff(@Valid @RequestBody CreateMedicalStaffRequest request) {
        CreateMedicalStaffCommand command = CreateMedicalStaffCommand.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .specialty(request.getSpecialty())
                .degree(request.getDegree())
                .institution(request.getInstitution())
                .dateObtained(request.getDateObtained())
                .licenseNumber(request.getLicenseNumber())
                .licenseExpiryDate(request.getLicenseExpiryDate())
                .hireDate(request.getHireDate())
                .build();
        
        StaffId staffId = (StaffId) commandBus.dispatch(command).join();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(staffId.toString());
    }
    
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a medical staff member", description = "Updates an existing medical staff member with the provided information")
    public ResponseEntity<Void> updateMedicalStaff(@PathVariable String id, @Valid @RequestBody UpdateMedicalStaffRequest request) {
        UpdateMedicalStaffCommand command = UpdateMedicalStaffCommand.builder()
                .staffId(id)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .specialty(request.getSpecialty())
                .build();
        
        commandBus.dispatch(command).join();
        
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Deactivate a medical staff member", description = "Deactivates an existing medical staff member")
    public ResponseEntity<Void> deactivateMedicalStaff(@PathVariable String id, @Valid @RequestBody DeactivateMedicalStaffRequest request) {
        DeactivateMedicalStaffCommand command = new DeactivateMedicalStaffCommand(id, request.getTerminationDate());
        
        commandBus.dispatch(command).join();
        
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get a medical staff member by ID", description = "Retrieves a medical staff member by ID")
    public ResponseEntity<MedicalStaffResponse> getMedicalStaffById(@PathVariable String id) {
        GetMedicalStaffByIdQuery query = new GetMedicalStaffByIdQuery(id);
        
        MedicalStaffResponse response = queryBus.dispatch(query).join();
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Get all medical staff members", description = "Retrieves all medical staff members")
    public ResponseEntity<List<MedicalStaffResponse>> getAllMedicalStaff() {
        GetAllMedicalStaffQuery query = new GetAllMedicalStaffQuery();
        
        List<MedicalStaffResponse> response = queryBus.dispatch(query).join();
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/specialty/{specialty}")
    @Operation(summary = "Get medical staff members by specialty", description = "Retrieves medical staff members by specialty")
    public ResponseEntity<List<MedicalStaffResponse>> getMedicalStaffBySpecialty(@PathVariable Specialty specialty) {
        GetMedicalStaffBySpecialtyQuery query = new GetMedicalStaffBySpecialtyQuery(specialty);
        
        List<MedicalStaffResponse> response = queryBus.dispatch(query).join();
        
        return ResponseEntity.ok(response);
    }
}