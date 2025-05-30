/**
 * This package provides an asynchronous event system for the Hospital API.
 *
 * <h2>Components</h2>
 *
 * <h3>1. AsyncSpringEventPublisher</h3>
 *
 * <p>The {@code AsyncSpringEventPublisher} is the primary implementation of the
 * {@code EventPublisher} interface. It publishes events asynchronously using
 * Spring's {@code @Async} annotation and a dedicated thread pool.</p>
 *
 * <pre>
 * {@code
 * @Autowired
 * private EventPublisher eventPublisher;
 *
 * // This will publish the event asynchronously
 * eventPublisher.publish(new PatientCreatedEvent(patient));
 * }
 * </pre>
 *
 * <h3>2. AsyncEventListener</h3>
 *
 * <p>The {@code @AsyncEventListener} annotation is a custom annotation that combines
 * Spring's {@code @EventListener} with {@code @Async} to create asynchronous event
 * handlers. Methods annotated with {@code @AsyncEventListener} will be executed in
 * a separate thread.</p>
 *
 * <pre>
 * {@code
 * @Component
 * public class MyEventHandler {
 *
 *     @AsyncEventListener
 *     public void handlePatientCreated(PatientCreatedEvent event) {
 *         // This method will be executed asynchronously
 *         // when a PatientCreatedEvent is published
 *     }
 * }
 * }
 * </pre>
 *
 * <h3>3. Thread Pool Configuration</h3>
 *
 * <p>The event system uses a dedicated thread pool for event processing, configured
 * in {@code ThreadPoolConfig}.</p>
 *
 * <h2>Usage</h2>
 *
 * <h3>Publishing Events</h3>
 *
 * <p>To publish events asynchronously, simply inject the {@code EventPublisher} and
 * call the {@code publish} method.</p>
 *
 * <h3>Handling Events</h3>
 *
 * <p>To handle events asynchronously, create a component with methods annotated with
 * {@code @AsyncEventListener}.</p>
 *
 * <h3>Synchronous Alternative</h3>
 *
 * <p>If you need to publish events synchronously for some reason, you can inject the
 * {@code syncEventPublisher} bean:</p>
 *
 * <pre>
 * {@code
 * @Autowired
 * @Qualifier("syncEventPublisher")
 * private EventPublisher syncEventPublisher;
 *
 * // This will publish the event synchronously
 * syncEventPublisher.publish(new PatientCreatedEvent(patient));
 * }
 * </pre>
 */
package com.example.hospitalapi.shared.infrastructure.event;
