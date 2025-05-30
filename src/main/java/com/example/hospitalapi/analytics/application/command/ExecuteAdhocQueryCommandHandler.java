package com.example.hospitalapi.analytics.application.command;

import com.example.hospitalapi.analytics.application.query.AdhocQueryResponse;
import com.example.hospitalapi.analytics.domain.repository.AdhocQueryRepository;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Handler for ExecuteAdhocQueryCommand
 */
@Service
@RequiredArgsConstructor
public class ExecuteAdhocQueryCommandHandler implements CommandHandler<ExecuteAdhocQueryCommand, AdhocQueryResponse> {

    private final AdhocQueryRepository adhocQueryRepository;

    /**
     * Handle the ExecuteAdhocQueryCommand
     * @param command the command to handle
     * @return the query results
     * @throws IllegalArgumentException if the query is invalid
     */
    @Transactional
    public AdhocQueryResponse handle(ExecuteAdhocQueryCommand command) {
        // Validate query
        if (!adhocQueryRepository.validateQuery(command.getQuery())) {
            throw new IllegalArgumentException("Invalid query: " + command.getQuery());
        }

        // Create a query ID
        String queryId = UUID.randomUUID().toString();

        // Record start time
        long startTime = System.currentTimeMillis();

        // Execute query
        List<Map<String, Object>> results = adhocQueryRepository.executeQuery(
                command.getQuery(),
                command.getParameters()
        );

        // Calculate execution time
        long executionTime = System.currentTimeMillis() - startTime;

        // Create response
        return AdhocQueryResponse.builder()
                .queryId(queryId)
                .query(command.getQuery())
                .results(results)
                .rowCount(results.size())
                .executedAt(LocalDateTime.now())
                .executionTimeMs(executionTime)
                .requestedBy(command.getRequestedBy())
                .build();
    }
}
