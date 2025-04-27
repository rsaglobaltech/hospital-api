package com.example.hospitalapi.security.infrastructure.rest;

import com.example.hospitalapi.security.application.command.CreateUserCommand;
import com.example.hospitalapi.security.application.command.DeleteUserCommand;
import com.example.hospitalapi.security.domain.valueobject.UserId;
import com.example.hospitalapi.security.infrastructure.rest.request.CreateUserRequest;
import com.example.hospitalapi.shared.domain.bus.CommandBus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for user management endpoints
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "API for managing users")
public class UserController {

    private final CommandBus commandBus;

    /**
     * Create a new user
     * @param request the create user request
     * @return the ID of the created user
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Create a new user", 
        description = "Creates a new user with the provided information. This endpoint is publicly accessible."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "User created successfully",
            content = @Content(mediaType = "text/plain", schema = @Schema(type = "string", format = "uuid"))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid request data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "409", 
            description = "Username or email already exists",
            content = @Content
        )
    })
    public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserRequest request) {
        CreateUserCommand command = CreateUserCommand.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .roles(request.getRoles())
                .build();

        UserId userId = (UserId) commandBus.dispatch(command).join();

        return ResponseEntity.status(HttpStatus.CREATED).body(userId.toString());
    }

    /**
     * Delete a user
     * @param id the ID of the user to delete
     * @return no content
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Delete a user", 
        description = "Deletes an existing user. Requires ADMIN role.",
        security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204", 
            description = "User deleted successfully",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "User not found",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403", 
            description = "Forbidden - requires ADMIN role",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "401", 
            description = "Unauthorized - authentication required",
            content = @Content
        )
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to delete", required = true)
            @PathVariable String id
    ) {
        DeleteUserCommand command = new DeleteUserCommand(id);

        commandBus.dispatch(command).join();

        return ResponseEntity.noContent().build();
    }
}
