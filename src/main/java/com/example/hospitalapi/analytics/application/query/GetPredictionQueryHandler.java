package com.example.hospitalapi.analytics.application.query;

import com.example.hospitalapi.analytics.domain.repository.PredictionRepository;
import com.example.hospitalapi.analytics.domain.valueobject.PredictionId;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import com.example.hospitalapi.shared.domain.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * Handler for GetPredictionQuery
 */
@Service
@RequiredArgsConstructor
public class GetPredictionQueryHandler implements QueryHandler<GetPredictionQuery, PredictionResponse> {

    private final PredictionRepository predictionRepository;
    private final EventPublisher eventPublisher;

    /**
     * Handle the GetPredictionQuery
     * @param query the query to handle
     * @return the prediction data
     */
    @Transactional(readOnly = true)
    public PredictionResponse handle(GetPredictionQuery query) {
        // Get prediction data from repository
        Map<String, Object> predictionData = predictionRepository.getPrediction(
                query.getPredictionModel(),
                query.getContextParams()
        );
        
        // Create a prediction ID
        String predictionId = UUID.randomUUID().toString();
        
        // Extract confidence from prediction data or use default
        String confidence = predictionData.containsKey("confidence") 
                ? predictionData.get("confidence").toString() 
                : "N/A";
        
        // Create response
        PredictionResponse response = PredictionResponse.builder()
                .predictionId(predictionId)
                .predictionModel(query.getPredictionModel())
                .result(predictionData)
                .contextParams(query.getContextParams())
                .generatedAt(LocalDateTime.now())
                .confidence(confidence)
                .build();
        
        // Publish event (in a real implementation, this would be done in a command handler)
        // This is just for demonstration purposes
        // String patientId = query.getContextParams().getOrDefault("patientId", "unknown");
        // eventPublisher.publish(new PredictionAvailableEvent(new PredictionId(predictionId), query.getPredictionModel(), patientId, "result", confidence));
        
        return response;
    }
}