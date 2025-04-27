package com.example.hospitalapi.analytics.domain.event;

import com.example.hospitalapi.analytics.domain.valueobject.ReportId;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when a report is generated
 */
@Getter
public class ReportGeneratedEvent extends BaseDomainEvent {
    
    private final String reportName;
    private final String reportFormat;
    private final String generatedBy;
    
    /**
     * Create a new ReportGeneratedEvent
     * @param reportId the ID of the report that was generated
     * @param reportName the name of the report
     * @param reportFormat the format of the report (e.g., PDF, CSV)
     * @param generatedBy the user or system that generated the report
     */
    public ReportGeneratedEvent(ReportId reportId, String reportName, String reportFormat, String generatedBy) {
        super(reportId.toString());
        this.reportName = reportName;
        this.reportFormat = reportFormat;
        this.generatedBy = generatedBy;
    }
}