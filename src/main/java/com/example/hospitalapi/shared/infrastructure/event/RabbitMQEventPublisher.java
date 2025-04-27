package com.example.hospitalapi.shared.infrastructure.event;

import com.example.hospitalapi.shared.domain.event.DomainEvent;
import com.example.hospitalapi.shared.domain.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Implementation of EventPublisher using RabbitMQ and Spring's ApplicationEventPublisher
 * This implementation publishes events to both RabbitMQ and Spring's event system
 * for backward compatibility.
 */
//@Component
//@Primary
//@RequiredArgsConstructor
//@Slf4j
public class RabbitMQEventPublisher implements EventPublisher {
    @Override
    public void publish(DomainEvent event) {

    }

//    private final RabbitTemplate rabbitTemplate;
//    private final ApplicationEventPublisher applicationEventPublisher;
//
//    private static final String EXCHANGE_NAME = "domain-events";
//
//    @Override
//    public void publish(DomainEvent event) {
//        try {
//            // Publish to Spring's event system for backward compatibility
//            applicationEventPublisher.publishEvent(event);
//
//            // Publish to RabbitMQ
//            String routingKey = event.getClass().getSimpleName();
//            log.info("Publishing event {} with routing key {}", event.getClass().getSimpleName(), routingKey);
//            rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, event);
//        } catch (Exception e) {
//            log.error("Error publishing event", e);
//            throw new RuntimeException("Error publishing event", e);
//        }
//    }
}
