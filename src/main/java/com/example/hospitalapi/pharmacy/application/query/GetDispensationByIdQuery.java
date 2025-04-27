package com.example.hospitalapi.pharmacy.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Query for retrieving a dispensation by ID
 */
@Getter
@AllArgsConstructor
public class GetDispensationByIdQuery implements Query<DispensationResponse> {
    private final String dispensationId;
}
