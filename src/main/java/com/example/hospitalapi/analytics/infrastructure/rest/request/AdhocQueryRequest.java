package com.example.hospitalapi.analytics.infrastructure.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Request DTO for executing ad-hoc queries
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdhocQueryRequest {
    
    @NotBlank(message = "Query is required")
    private String query;
    
    @Builder.Default
    private Map<String, Object> parameters = new HashMap<>();
}