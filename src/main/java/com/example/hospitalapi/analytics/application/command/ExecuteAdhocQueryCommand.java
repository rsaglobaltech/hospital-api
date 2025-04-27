package com.example.hospitalapi.analytics.application.command;

import com.example.hospitalapi.shared.domain.bus.Command;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Command to execute an ad-hoc query
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteAdhocQueryCommand implements Command {
    
    private String query;
    
    @Builder.Default
    private Map<String, Object> parameters = new HashMap<>();
    
    private String requestedBy;
}