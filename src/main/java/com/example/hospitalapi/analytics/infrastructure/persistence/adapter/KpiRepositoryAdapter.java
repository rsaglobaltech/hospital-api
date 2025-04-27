package com.example.hospitalapi.analytics.infrastructure.persistence.adapter;

import com.example.hospitalapi.analytics.domain.repository.KpiRepository;
import com.example.hospitalapi.analytics.domain.valueobject.KpiId;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Adapter implementation for KpiRepository
 */
@Component
public class KpiRepositoryAdapter implements KpiRepository {

    // In a real implementation, this would use a database or data warehouse
    // This is just a mock implementation for demonstration purposes
    private final Map<String, Map<String, Object>> kpis = new HashMap<>();
    private final Map<String, Map<String, Object>> kpiDefinitions = new HashMap<>();

    public KpiRepositoryAdapter() {
        // Initialize some mock KPI definitions
        Map<String, Object> losKpi = new HashMap<>();
        losKpi.put("id", UUID.randomUUID().toString());
        losKpi.put("name", "AvgLengthOfStay");
        losKpi.put("description", "Average Length of Stay in Days");
        losKpi.put("unit", "days");
        losKpi.put("query", "SELECT AVG(length_of_stay) FROM admissions WHERE discharge_date BETWEEN :start_date AND :end_date");
        kpiDefinitions.put(losKpi.get("name").toString(), losKpi);

        Map<String, Object> readmissionKpi = new HashMap<>();
        readmissionKpi.put("id", UUID.randomUUID().toString());
        readmissionKpi.put("name", "ReadmissionRate");
        readmissionKpi.put("description", "30-day Readmission Rate");
        readmissionKpi.put("unit", "percentage");
        readmissionKpi.put("query", "SELECT COUNT(DISTINCT patient_id) / (SELECT COUNT(DISTINCT patient_id) FROM admissions) * 100 FROM admissions WHERE admission_date BETWEEN :start_date AND :end_date AND is_readmission = true");
        kpiDefinitions.put(readmissionKpi.get("name").toString(), readmissionKpi);

        Map<String, Object> mortalityKpi = new HashMap<>();
        mortalityKpi.put("id", UUID.randomUUID().toString());
        mortalityKpi.put("name", "MortalityRate");
        mortalityKpi.put("description", "In-hospital Mortality Rate");
        mortalityKpi.put("unit", "percentage");
        mortalityKpi.put("query", "SELECT COUNT(CASE WHEN discharge_disposition = 'Expired' THEN 1 END) / COUNT(*) * 100 FROM admissions WHERE discharge_date BETWEEN :start_date AND :end_date");
        kpiDefinitions.put(mortalityKpi.get("name").toString(), mortalityKpi);
    }

    @Override
    public Map<String, Object> getKpiValues(List<String> kpiNames, String timePeriod) {
        // In a real implementation, this would query a database or data warehouse
        // and calculate the KPI values based on the time period
        
        Map<String, Object> result = new HashMap<>();
        
        // If no KPI names provided, use all available KPIs
        if (kpiNames == null || kpiNames.isEmpty()) {
            kpiNames = new ArrayList<>(kpiDefinitions.keySet());
        }
        
        // Generate mock values for each requested KPI
        for (String kpiName : kpiNames) {
            if (kpiDefinitions.containsKey(kpiName)) {
                Map<String, Object> kpiDef = kpiDefinitions.get(kpiName);
                
                // Generate a mock value based on the KPI name and time period
                double value = generateMockKpiValue(kpiName, timePeriod);
                
                // Add to result
                Map<String, Object> kpiValue = new HashMap<>();
                kpiValue.put("value", value);
                kpiValue.put("unit", kpiDef.get("unit"));
                kpiValue.put("description", kpiDef.get("description"));
                kpiValue.put("timePeriod", timePeriod);
                
                result.put(kpiName, kpiValue);
            }
        }
        
        return result;
    }

    private double generateMockKpiValue(String kpiName, String timePeriod) {
        // Generate a realistic mock value based on the KPI name and time period
        Random random = new Random();
        
        switch (kpiName) {
            case "AvgLengthOfStay":
                // Average length of stay in days (typically 4-6 days)
                return 4.0 + random.nextDouble() * 2.0;
            case "ReadmissionRate":
                // Readmission rate (typically 10-20%)
                return 10.0 + random.nextDouble() * 10.0;
            case "MortalityRate":
                // Mortality rate (typically 2-5%)
                return 2.0 + random.nextDouble() * 3.0;
            default:
                // Generic value between 0 and 100
                return random.nextDouble() * 100.0;
        }
    }

    @Override
    public Optional<Map<String, Object>> findById(KpiId kpiId) {
        // In a real implementation, this would query a database by ID
        return Optional.ofNullable(kpis.get(kpiId.toString()))
                .map(HashMap::new);
    }

    @Override
    public Optional<Map<String, Object>> findByName(String kpiName) {
        // In a real implementation, this would query a database by name
        return Optional.ofNullable(kpiDefinitions.get(kpiName))
                .map(HashMap::new);
    }

    @Override
    public List<Map<String, Object>> findAll() {
        // Return all KPI definitions
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> kpi : kpiDefinitions.values()) {
            result.add(new HashMap<>(kpi));
        }
        return result;
    }

    @Override
    public KpiId saveKpi(String kpiName, Map<String, Object> kpiDefinition) {
        // In a real implementation, this would save to a database
        KpiId kpiId = new KpiId();
        
        // Create a new KPI definition with the provided data
        Map<String, Object> kpi = new HashMap<>(kpiDefinition);
        kpi.put("id", kpiId.toString());
        kpi.put("name", kpiName);
        
        // Save to the in-memory map
        kpiDefinitions.put(kpiName, kpi);
        
        return kpiId;
    }

    @Override
    public void updateKpi(KpiId kpiId, Map<String, Object> kpiDefinition) {
        // In a real implementation, this would update a database record
        // Find the KPI by ID
        Optional<Map.Entry<String, Map<String, Object>>> entry = kpiDefinitions.entrySet().stream()
                .filter(e -> kpiId.toString().equals(e.getValue().get("id")))
                .findFirst();
        
        if (entry.isPresent()) {
            Map<String, Object> existingKpi = entry.get().getValue();
            
            // Update the KPI with the provided data
            // but preserve the id and name
            String id = (String) existingKpi.get("id");
            String name = (String) existingKpi.get("name");
            
            existingKpi.clear();
            existingKpi.putAll(kpiDefinition);
            existingKpi.put("id", id);
            existingKpi.put("name", name);
        }
    }

    @Override
    public void deleteKpi(KpiId kpiId) {
        // In a real implementation, this would delete from a database
        // Find the KPI by ID
        Optional<Map.Entry<String, Map<String, Object>>> entry = kpiDefinitions.entrySet().stream()
                .filter(e -> kpiId.toString().equals(e.getValue().get("id")))
                .findFirst();
        
        entry.ifPresent(e -> kpiDefinitions.remove(e.getKey()));
    }
}