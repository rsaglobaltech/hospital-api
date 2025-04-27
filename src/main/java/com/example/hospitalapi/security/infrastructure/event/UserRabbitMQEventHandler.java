package com.example.hospitalapi.security.infrastructure.event;

import com.example.hospitalapi.security.domain.event.UserCreatedEvent;
import com.example.hospitalapi.security.domain.event.UserDeletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Event handler for User domain events from RabbitMQ
 */
@Component
@Slf4j
public class UserRabbitMQEventHandler {
    
    private static final String EXCHANGE_NAME = "domain-events";
    
    /**
     * Handle UserCreatedEvent from RabbitMQ
     * @param event the event to handle
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "user-created-queue", durable = "true"),
            exchange = @Exchange(value = EXCHANGE_NAME, type = "topic"),
            key = "UserCreatedEvent"
    ))
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        log.info("Handling UserCreatedEvent from RabbitMQ for user with ID: {}", event.getAggregateId());
        log.info("User created: {}", event.getUser().getUsername());
    }
    
    /**
     * Handle UserDeletedEvent from RabbitMQ
     * @param event the event to handle
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "user-deleted-queue", durable = "true"),
            exchange = @Exchange(value = EXCHANGE_NAME, type = "topic"),
            key = "UserDeletedEvent"
    ))
    public void handleUserDeletedEvent(UserDeletedEvent event) {
        log.info("Handling UserDeletedEvent from RabbitMQ for user with ID: {}", event.getAggregateId());
        log.info("User deleted with ID: {}", event.getUserId().toString());
    }
}