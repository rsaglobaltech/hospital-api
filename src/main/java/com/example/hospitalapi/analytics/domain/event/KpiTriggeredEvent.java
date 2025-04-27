package com.example.hospitalapi.analytics.domain.event;

import com.example.hospitalapi.analytics.domain.valueobject.KpiId;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when a KPI is triggered
 */
@Getter
public class KpiTriggeredEvent extends BaseDomainEvent {
    
    private final String kpiName;
    private final String kpiValue;
    private final String threshold;
    private final String timePeriod;
    
    /**
     * Create a new KpiTriggeredEvent
     * @param kpiId the ID of the KPI that was triggered
     * @param kpiName the name of the KPI
     * @param kpiValue the value of the KPI
     * @param threshold the threshold that was crossed
     * @param timePeriod the time period for which the KPI was calculated
     */
    public KpiTriggeredEvent(KpiId kpiId, String kpiName, String kpiValue, String threshold, String timePeriod) {
        super(kpiId.toString());
        this.kpiName = kpiName;
        this.kpiValue = kpiValue;
        this.threshold = threshold;
        this.timePeriod = timePeriod;
    }
}