package com.example.hospitalapi.security.infrastructure.service;

import com.example.hospitalapi.security.domain.entity.User;
import com.example.hospitalapi.security.domain.repository.UserRepository;
import com.example.hospitalapi.security.infrastructure.rest.request.AuthenticationRequest;
import com.example.hospitalapi.security.infrastructure.rest.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service for authentication
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    /**
     * Authenticate a user and generate a JWT token
     * @param request the authentication request
     * @return the authentication response with JWT token
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Get user from database
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Get UserDetails from UserDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        // Generate tokens
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        // Build response
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId().toString())
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }

    /**
     * Refresh a JWT token
     * @param username the username
     * @return the authentication response with new JWT token
     */
    public AuthenticationResponse refreshToken(String username) {
        // Get user from database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Get UserDetails from UserDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Generate tokens
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        // Build response
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId().toString())
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }
}
