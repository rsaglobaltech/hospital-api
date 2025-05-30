# Asynchronous Event System

This package provides an asynchronous event system for the Hospital API. It allows for the publishing and handling of domain events in an asynchronous manner.

## Components

### 1. AsyncSpringEventPublisher

The `AsyncSpringEventPublisher` is the primary implementation of the `EventPublisher` interface. It publishes events asynchronously using Spring's `@Async` annotation and a dedicated thread pool.

```java
@Autowired
private EventPublisher eventPublisher;

// This will publish the event asynchronously
eventPublisher.publish(new PatientCreatedEvent(patient));
```

### 2. AsyncEventListener

The `@AsyncEventListener` annotation is a custom annotation that combines Spring's `@EventListener` with `@Async` to create asynchronous event handlers. Methods annotated with `@AsyncEventListener` will be executed in a separate thread.

```java
@Component
public class MyEventHandler {

    @AsyncEventListener
    public void handlePatientCreated(PatientCreatedEvent event) {
        // This method will be executed asynchronously
        // when a PatientCreatedEvent is published
    }
}
```

### 3. Thread Pool Configuration

The event system uses a dedicated thread pool for event processing, configured in `ThreadPoolConfig`:

```java
@Bean(name = "eventExecutor")
public Executor eventExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(4);
    executor.setMaxPoolSize(10);
    executor.setQueueCapacity(100);
    executor.setThreadNamePrefix("event-");
    executor.initialize();
    return executor;
}
```

## Usage

### Publishing Events

To publish events asynchronously, simply inject the `EventPublisher` and call the `publish` method:

```java
@Service
public class MyService {

    private final EventPublisher eventPublisher;

    public MyService(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void doSomething() {
        // Do some work
        
        // Publish an event asynchronously
        eventPublisher.publish(new SomethingHappenedEvent("123"));
    }
}
```

### Handling Events

To handle events asynchronously, create a component with methods annotated with `@AsyncEventListener`:

```java
@Component
public class MyEventHandler {

    @AsyncEventListener
    public void handleSomethingHappened(SomethingHappenedEvent event) {
        // This method will be executed asynchronously
        // when a SomethingHappenedEvent is published
    }
}
```

## Synchronous Alternative

If you need to publish events synchronously for some reason, you can inject the `syncEventPublisher` bean:

```java
@Autowired
@Qualifier("syncEventPublisher")
private EventPublisher syncEventPublisher;

// This will publish the event synchronously
syncEventPublisher.publish(new PatientCreatedEvent(patient));
```
