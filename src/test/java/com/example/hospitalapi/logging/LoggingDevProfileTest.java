package com.example.hospitalapi.logging;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test class to verify logging configuration with the 'dev' profile.
 */
@ActiveProfiles("dev")
public class LoggingDevProfileTest extends LoggingTest {

    @Test
    public void testLoggingWithDevProfile() {
        log.info("Testing logging with DEV profile");
        testLogging();
    }
}