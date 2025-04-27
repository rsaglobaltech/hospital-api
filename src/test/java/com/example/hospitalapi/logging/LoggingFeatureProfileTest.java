package com.example.hospitalapi.logging;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test class to verify logging configuration with the 'feature' profile.
 */
@ActiveProfiles("feature")
public class LoggingFeatureProfileTest extends LoggingTest {

    @Test
    public void testLoggingWithFeatureProfile() {
        log.info("Testing logging with FEATURE profile");
        testLogging();
    }
}