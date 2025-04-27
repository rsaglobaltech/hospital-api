package com.example.hospitalapi.scheduling.infrastructure.rest;

import com.example.hospitalapi.scheduling.application.command.BookAvailableSlotCommand;
import com.example.hospitalapi.scheduling.application.command.CreateAvailableSlotCommand;
import com.example.hospitalapi.scheduling.application.command.UnbookAvailableSlotCommand;
import com.example.hospitalapi.scheduling.application.query.AvailableSlotResponse;
import com.example.hospitalapi.scheduling.application.query.GetAvailableSlotByIdQuery;
import com.example.hospitalapi.scheduling.application.query.GetAvailableSlotsQuery;
import com.example.hospitalapi.scheduling.domain.valueobject.AvailableSlotId;
import com.example.hospitalapi.scheduling.infrastructure.rest.request.CreateAvailableSlotRequest;
import com.example.hospitalapi.shared.domain.bus.CommandBus;
import com.example.hospitalapi.shared.domain.bus.QueryBus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for scheduling operations
 */
@RestController
@RequestMapping("/api/scheduling/slots")
@Tag(name = "Scheduling Management", description = "API for managing available slots")
@RequiredArgsConstructor
public class SchedulingController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new available slot", description = "Creates a new available slot with the provided information")
    public ResponseEntity<String> createAvailableSlot(@Valid @RequestBody CreateAvailableSlotRequest request) {
        CreateAvailableSlotCommand command = CreateAvailableSlotCommand.builder()
                .doctorId(request.getDoctorId())
                .date(request.getDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();

        AvailableSlotId availableSlotId = (AvailableSlotId) commandBus.dispatch(command).join();

        return ResponseEntity.status(HttpStatus.CREATED).body(availableSlotId.toString());
    }

    @PutMapping("/{id}/book")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Book an available slot", description = "Books an available slot")
    public ResponseEntity<Void> bookAvailableSlot(@PathVariable String id) {
        BookAvailableSlotCommand command = new BookAvailableSlotCommand(id);

        commandBus.dispatch(command).join();

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/unbook")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Unbook an available slot", description = "Unbooks an available slot")
    public ResponseEntity<Void> unbookAvailableSlot(@PathVariable String id) {
        UnbookAvailableSlotCommand command = new UnbookAvailableSlotCommand(id);

        commandBus.dispatch(command).join();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an available slot by ID", description = "Retrieves an available slot by ID")
    public ResponseEntity<AvailableSlotResponse> getAvailableSlotById(@PathVariable String id) {
        GetAvailableSlotByIdQuery query = new GetAvailableSlotByIdQuery(id);

        AvailableSlotResponse response = queryBus.dispatch(query).join();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get available slots", description = "Retrieves available slots with optional filtering")
    public ResponseEntity<List<AvailableSlotResponse>> getAvailableSlots(
            @RequestParam(required = false) String doctorId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        GetAvailableSlotsQuery query = new GetAvailableSlotsQuery(doctorId, date);

        List<AvailableSlotResponse> response = queryBus.dispatch(query).join();

        return ResponseEntity.ok(response);
    }
}
