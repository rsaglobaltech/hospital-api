package com.example.hospitalapi.shared.infrastructure.event;

import org.springframework.scheduling.annotation.Async;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for asynchronous event listeners.
 * This combines Spring's @EventListener with @Async to create asynchronous event handlers.
 * Methods annotated with @AsyncEventListener will be executed in a separate thread.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@org.springframework.context.event.EventListener
@Async("eventExecutor")
public @interface AsyncEventListener {
}
