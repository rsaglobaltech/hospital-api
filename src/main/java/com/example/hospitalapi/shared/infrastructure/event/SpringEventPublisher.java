package com.example.hospitalapi.shared.infrastructure.event;

import com.example.hospitalapi.shared.domain.event.DomainEvent;
import com.example.hospitalapi.shared.domain.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Synchronous implementation of EventPublisher using Spring's ApplicationEventPublisher
 * This is kept for backward compatibility but AsyncSpringEventPublisher should be used instead
 */
@Component("syncEventPublisher")
@RequiredArgsConstructor
@Slf4j
public class SpringEventPublisher implements EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent event) {
        log.debug("Synchronously publishing event: {}", event.getClass().getSimpleName());
        applicationEventPublisher.publishEvent(event);
    }
}
