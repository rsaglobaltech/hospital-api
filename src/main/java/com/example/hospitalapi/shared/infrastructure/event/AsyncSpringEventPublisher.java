package com.example.hospitalapi.shared.infrastructure.event;

import com.example.hospitalapi.shared.domain.event.DomainEvent;
import com.example.hospitalapi.shared.domain.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Asynchronous implementation of EventPublisher using Spring's ApplicationEventPublisher.
 * This is the primary implementation that should be used for all event publishing.
 */
@Component
@Primary
@RequiredArgsConstructor
@Slf4j
public class AsyncSpringEventPublisher implements EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Async("eventExecutor")
    public void publish(DomainEvent event) {
        log.debug("Asynchronously publishing event: {}", event.getClass().getSimpleName());
        applicationEventPublisher.publishEvent(event);
    }
}
