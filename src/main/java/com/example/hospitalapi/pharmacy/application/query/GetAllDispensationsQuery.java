package com.example.hospitalapi.pharmacy.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;

import java.util.List;

/**
 * Query for retrieving all dispensations
 */
public class GetAllDispensationsQuery implements Query<List<DispensationResponse>> {
    // No parameters needed
}
