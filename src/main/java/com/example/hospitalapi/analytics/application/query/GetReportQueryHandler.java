package com.example.hospitalapi.analytics.application.query;

import com.example.hospitalapi.analytics.domain.repository.ReportRepository;
import com.example.hospitalapi.analytics.domain.valueobject.ReportId;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import com.example.hospitalapi.shared.domain.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * Handler for GetReportQuery
 */
@Service
@RequiredArgsConstructor
public class GetReportQueryHandler implements QueryHandler<GetReportQuery, ReportResponse> {

    private final ReportRepository reportRepository;
    private final EventPublisher eventPublisher;

    /**
     * Handle the GetReportQuery
     * @param query the query to handle
     * @return the report data
     */
    @Transactional(readOnly = true)
    public ReportResponse handle(GetReportQuery query) {
        // Get report data from repository
        Map<String, Object> reportData = reportRepository.getReportByName(
                query.getReportName(), 
                query.getFilterParams()
        );
        
        // Create a report ID
        String reportId = UUID.randomUUID().toString();
        
        // Create response
        ReportResponse response = ReportResponse.builder()
                .reportId(reportId)
                .reportName(query.getReportName())
                .data(reportData)
                .filterParams(query.getFilterParams())
                .generatedAt(LocalDateTime.now())
                .format("JSON")
                .build();
        
        // Publish event (in a real implementation, this would be done in a command handler)
        // This is just for demonstration purposes
        // eventPublisher.publish(new ReportGeneratedEvent(new ReportId(reportId), query.getReportName(), "JSON", "system"));
        
        return response;
    }
}