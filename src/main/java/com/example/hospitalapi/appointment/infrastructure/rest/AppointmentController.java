package com.example.hospitalapi.appointment.infrastructure.rest;

import com.example.hospitalapi.appointment.application.command.CancelAppointmentCommand;
import com.example.hospitalapi.appointment.application.command.CreateAppointmentCommand;
import com.example.hospitalapi.appointment.application.command.UpdateAppointmentCommand;
import com.example.hospitalapi.appointment.application.query.AppointmentResponse;
import com.example.hospitalapi.appointment.application.query.GetAllAppointmentsQuery;
import com.example.hospitalapi.appointment.application.query.GetAppointmentByIdQuery;
import com.example.hospitalapi.appointment.domain.valueobject.AppointmentId;
import com.example.hospitalapi.shared.domain.bus.CommandBus;
import com.example.hospitalapi.shared.domain.bus.QueryBus;
import com.example.hospitalapi.appointment.infrastructure.rest.request.CreateAppointmentRequest;
import com.example.hospitalapi.appointment.infrastructure.rest.request.UpdateAppointmentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@Tag(name = "Appointment Management", description = "API for managing appointments")
public class AppointmentController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    public AppointmentController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new appointment", description = "Creates a new appointment with the provided information")
    public ResponseEntity<String> createAppointment(@Valid @RequestBody CreateAppointmentRequest request) {
        CreateAppointmentCommand command = CreateAppointmentCommand.builder()
                .patientId(request.getPatientId())
                .doctorId(request.getDoctorId())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .reason(request.getReason())
                .build();

        AppointmentId appointmentId = (AppointmentId) commandBus.dispatch(command).join();

        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentId.toString());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an appointment", description = "Updates an existing appointment with the provided information")
    public ResponseEntity<Void> updateAppointment(@PathVariable String id, @Valid @RequestBody UpdateAppointmentRequest request) {
        UpdateAppointmentCommand command = UpdateAppointmentCommand.builder()
                .appointmentId(id)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .reason(request.getReason())
                .notes(request.getNotes())
                .build();

        commandBus.dispatch(command).join();

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Cancel an appointment", description = "Cancels an existing appointment")
    public ResponseEntity<Void> cancelAppointment(@PathVariable String id) {
        CancelAppointmentCommand command = new CancelAppointmentCommand(id);

        commandBus.dispatch(command).join();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an appointment by ID", description = "Retrieves an appointment by ID")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable String id) {
        GetAppointmentByIdQuery query = new GetAppointmentByIdQuery(id);

        AppointmentResponse response = queryBus.dispatch(query).join();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all appointments", description = "Retrieves all appointments")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        GetAllAppointmentsQuery query = new GetAllAppointmentsQuery();

        List<AppointmentResponse> response = queryBus.dispatch(query).join();

        return ResponseEntity.ok(response);
    }
}
