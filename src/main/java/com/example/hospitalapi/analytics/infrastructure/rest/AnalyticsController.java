package com.example.hospitalapi.analytics.infrastructure.rest;

import com.example.hospitalapi.analytics.application.query.AdhocQueryResponse;
import com.example.hospitalapi.analytics.application.command.ExecuteAdhocQueryCommand;
import com.example.hospitalapi.analytics.application.query.*;
import com.example.hospitalapi.analytics.infrastructure.rest.request.AdhocQueryRequest;
import com.example.hospitalapi.shared.domain.bus.CommandBus;
import com.example.hospitalapi.shared.domain.bus.QueryBus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Analytics", description = "API for analytics and reporting")
@RequiredArgsConstructor
public class AnalyticsController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @GetMapping("/reports/{reportName}")
    @Operation(summary = "Get a report by name", description = "Retrieves a report by name with optional filter parameters")
    public ResponseEntity<ReportResponse> getReport(
            @PathVariable String reportName,
            @RequestParam(required = false) Map<String, String> filterParams) {

        GetReportQuery query = GetReportQuery.builder()
                .reportName(reportName)
                .filterParams(filterParams)
                .build();

        ReportResponse response = (ReportResponse) queryBus.dispatch(query).join();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboards/{dashboardId}")
    @Operation(summary = "Get a dashboard by ID", description = "Retrieves a dashboard by ID")
    public ResponseEntity<DashboardResponse> getDashboard(@PathVariable String dashboardId) {
        GetDashboardQuery query = GetDashboardQuery.builder()
                .dashboardId(dashboardId)
                .build();

        DashboardResponse response = (DashboardResponse) queryBus.dispatch(query).join();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/kpis")
    @Operation(summary = "Get KPI values", description = "Retrieves KPI values for specified KPI names and time period")
    public ResponseEntity<KpiResponse> getKpis(
            @RequestParam(required = false) List<String> names,
            @RequestParam(required = false, defaultValue = "lastQuarter") String timePeriod) {

        GetKpisQuery query = GetKpisQuery.builder()
                .kpiNames(names)
                .timePeriod(timePeriod)
                .build();

        KpiResponse response = (KpiResponse) queryBus.dispatch(query).join();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/adhoc-queries")
    @Operation(
            summary = "Execute an ad-hoc query",
            description = "Executes an ad-hoc query with parameters (requires special permissions)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN') or hasRole('DATA_ANALYST')")
    public ResponseEntity<AdhocQueryResponse> executeAdhocQuery(@Valid @RequestBody AdhocQueryRequest request) {
        ExecuteAdhocQueryCommand command = ExecuteAdhocQueryCommand.builder()
                .query(request.getQuery())
                .parameters(request.getParameters())
                .requestedBy("system") // In a real app, this would be the authenticated user
                .build();

        AdhocQueryResponse response = (AdhocQueryResponse) commandBus.dispatch(command).join();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/predictions/{predictionModel}")
    @Operation(summary = "Get prediction results", description = "Retrieves prediction results for a specific model with context parameters")
    public ResponseEntity<PredictionResponse> getPrediction(
            @PathVariable String predictionModel,
            @RequestParam(required = false) Map<String, String> contextParams) {

        GetPredictionQuery query = GetPredictionQuery.builder()
                .predictionModel(predictionModel)
                .contextParams(contextParams)
                .build();

        PredictionResponse response = (PredictionResponse) queryBus.dispatch(query).join();

        return ResponseEntity.ok(response);
    }
}
