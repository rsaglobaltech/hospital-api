package com.example.hospitalapi.security.infrastructure.rest;

import com.example.hospitalapi.security.infrastructure.rest.request.AuthenticationRequest;
import com.example.hospitalapi.security.infrastructure.rest.response.AuthenticationResponse;
import com.example.hospitalapi.security.infrastructure.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for authentication endpoints
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for authentication")
public class AuthController {

    private final AuthenticationService authenticationService;

    /**
     * Authenticate a user and generate a JWT token
     * @param request the authentication request
     * @return the authentication response with JWT token
     */
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    @Operation(
        summary = "Authenticate user", 
        description = "Authenticate a user with username and password and generate a JWT token"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Authentication successful",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))
        ),
        @ApiResponse(
            responseCode = "401", 
            description = "Invalid credentials",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid request",
            content = @Content
        )
    })
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    /**
     * Refresh a JWT token
     * @param request the refresh token request
     * @return the authentication response with new JWT token
     */
    @PostMapping("/refresh-token")
    @Operation(
        summary = "Refresh token", 
        description = "Refresh a JWT token using the username"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Token refreshed successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "User not found",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid request",
            content = @Content
        )
    })
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.refreshToken(request.getUsername()));
    }
}
