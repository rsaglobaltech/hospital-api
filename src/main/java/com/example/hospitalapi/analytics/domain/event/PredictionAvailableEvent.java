package com.example.hospitalapi.analytics.domain.event;

import com.example.hospitalapi.analytics.domain.valueobject.PredictionId;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when a prediction is available
 */
@Getter
public class PredictionAvailableEvent extends BaseDomainEvent {
    
    private final String predictionModel;
    private final String patientId;
    private final String result;
    private final String confidence;
    
    /**
     * Create a new PredictionAvailableEvent
     * @param predictionId the ID of the prediction
     * @param predictionModel the name of the prediction model
     * @param patientId the ID of the patient
     * @param result the prediction result
     * @param confidence the confidence level of the prediction
     */
    public PredictionAvailableEvent(PredictionId predictionId, String predictionModel, String patientId, String result, String confidence) {
        super(predictionId.toString());
        this.predictionModel = predictionModel;
        this.patientId = patientId;
        this.result = result;
        this.confidence = confidence;
    }
}