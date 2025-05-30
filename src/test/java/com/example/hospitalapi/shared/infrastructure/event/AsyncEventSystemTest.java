package com.example.hospitalapi.shared.infrastructure.event;

import com.example.hospitalapi.shared.domain.event.DomainEvent;
import com.example.hospitalapi.shared.domain.event.EventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AsyncEventSystemTest {

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    @Qualifier("syncEventPublisher")
    private EventPublisher syncEventPublisher;

    @Autowired
    private TestEventHandler testEventHandler;

    @Test
    public void testAsyncEventPublishing() throws Exception {
        // Arrange
        CountDownLatch latch = new CountDownLatch(1);
        testEventHandler.setLatch(latch);
        TestEvent event = new TestEvent(UUID.randomUUID().toString());

        // Act
        eventPublisher.publish(event);

        // Assert
        // If the event is processed asynchronously, the latch should be counted down within a reasonable time
        boolean processed = latch.await(5, TimeUnit.SECONDS);
        assertTrue(processed, "Event should be processed asynchronously");
    }

    @Test
    public void testAsyncEventListener() throws Exception {
        // Arrange
        CountDownLatch latch = new CountDownLatch(1);
        testEventHandler.setAsyncLatch(latch);
        TestEvent event = new TestEvent(UUID.randomUUID().toString());

        // Act
        // Use the application event publisher directly to bypass our EventPublisher
        // This tests that the @AsyncEventListener annotation works correctly
        testEventHandler.getApplicationEventPublisher().publishEvent(event);

        // Assert
        // If the event is processed asynchronously, the latch should be counted down within a reasonable time
        boolean processed = latch.await(5, TimeUnit.SECONDS);
        assertTrue(processed, "Event should be processed asynchronously with @AsyncEventListener");
    }

    @Component
    public static class TestEventHandler {
        private CountDownLatch latch;
        private CountDownLatch asyncLatch;
        private final ApplicationEventPublisher applicationEventPublisher;

        public TestEventHandler(ApplicationEventPublisher applicationEventPublisher) {
            this.applicationEventPublisher = applicationEventPublisher;
        }

        public ApplicationEventPublisher getApplicationEventPublisher() {
            return applicationEventPublisher;
        }

        public void setLatch(CountDownLatch latch) {
            this.latch = latch;
        }

        public void setAsyncLatch(CountDownLatch asyncLatch) {
            this.asyncLatch = asyncLatch;
        }

        @EventListener
        public void handleTestEvent(TestEvent event) {
            System.out.println("Handling TestEvent synchronously: " + event.getAggregateId());
            if (latch != null) {
                latch.countDown();
            }
        }

        @AsyncEventListener
        public void handleTestEventAsync(TestEvent event) {
            System.out.println("Handling TestEvent asynchronously: " + event.getAggregateId());
            if (asyncLatch != null) {
                asyncLatch.countDown();
            }
        }
    }

    public static class TestEvent implements DomainEvent {
        private final String eventId;
        private final LocalDateTime occurredOn;
        private final String aggregateId;

        public TestEvent(String aggregateId) {
            this.eventId = UUID.randomUUID().toString();
            this.occurredOn = LocalDateTime.now();
            this.aggregateId = aggregateId;
        }

        @Override
        public String getEventId() {
            return eventId;
        }

        @Override
        public LocalDateTime getOccurredOn() {
            return occurredOn;
        }

        @Override
        public String getAggregateId() {
            return aggregateId;
        }
    }
}
