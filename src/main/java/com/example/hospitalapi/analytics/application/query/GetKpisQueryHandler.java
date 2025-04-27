package com.example.hospitalapi.analytics.application.query;

import com.example.hospitalapi.analytics.domain.repository.KpiRepository;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Handler for GetKpisQuery
 */
@Service
@RequiredArgsConstructor
public class GetKpisQueryHandler implements QueryHandler<GetKpisQuery, KpiResponse> {

    private final KpiRepository kpiRepository;

    /**
     * Handle the GetKpisQuery
     * @param query the query to handle
     * @return the KPI data
     */
    @Transactional(readOnly = true)
    public KpiResponse handle(GetKpisQuery query) {
        // Get KPI values from repository
        Map<String, Object> kpiValues = kpiRepository.getKpiValues(
                query.getKpiNames(),
                query.getTimePeriod()
        );
        
        // Create response
        return KpiResponse.builder()
                .kpiValues(kpiValues)
                .timePeriod(query.getTimePeriod())
                .calculatedAt(LocalDateTime.now())
                .build();
    }
}