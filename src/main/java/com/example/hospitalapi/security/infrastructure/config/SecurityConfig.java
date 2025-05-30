package com.example.hospitalapi.security.infrastructure.config;

import com.example.hospitalapi.security.infrastructure.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security configuration
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/").permitAll()
                .requestMatchers("/api/v1/auth/**", "/auth/**").permitAll()
                .requestMatchers("/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/api/v1/users", "/users").permitAll()
                .requestMatchers("/h2-console/**", "/api/v1/h2-console/**").permitAll()
                .requestMatchers("/management/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/patients/**").hasAnyRole("ADMIN", "DOCTOR", "NURSE", "RECEPTIONIST")
                .requestMatchers("/api/v1/medical-staff/**").hasAnyRole("ADMIN", "RECEPTIONIST")
                .requestMatchers("/api/v1/appointments/**").hasAnyRole("ADMIN", "DOCTOR", "NURSE", "RECEPTIONIST", "PATIENT")
                //.requestMatchers("/api/v1/users/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/clinical-records/**").hasAnyRole("ADMIN", "DOCTOR", "NURSE")
                .requestMatchers("/api/v1/notifications/**").authenticated()
                .requestMatchers("/api/v1/scheduling/slots/**").hasAnyRole("ADMIN", "DOCTOR", "RECEPTIONIST")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
