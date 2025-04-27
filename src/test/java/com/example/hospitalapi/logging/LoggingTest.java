package com.example.hospitalapi.logging;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base test class to verify logging configuration.
 * This class demonstrates how to test logging in different profiles.
 * 
 * To run tests with a specific profile, use:
 * - mvn test -Dspring.profiles.active=dev -Dtest=LoggingTest*
 * - mvn test -Dspring.profiles.active=feature -Dtest=LoggingTest*
 * - mvn test -Dspring.profiles.active=staging -Dtest=LoggingTest*
 * - mvn test -Dspring.profiles.active=prod -Dtest=LoggingTest*
 */
@SpringBootTest
public class LoggingTest {

    protected static final Logger log = LoggerFactory.getLogger(LoggingTest.class);

    @Test
    public void testLogging() {
        // Log messages at different levels
        log.trace("This is a TRACE message");
        log.debug("This is a DEBUG message");
        log.info("This is an INFO message");
        log.warn("This is a WARN message");
        log.error("This is an ERROR message");

        // Log with exception
        try {
            throw new RuntimeException("Test exception");
        } catch (Exception e) {
            log.error("This is an ERROR message with exception", e);
        }

        // Log with parameters
        String param1 = "param1";
        int param2 = 42;
        log.info("This is a message with parameters: {} and {}", param1, param2);
    }
}
