package com.example.hospitalapi.logging;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test class to verify logging configuration with the 'prod' profile.
 */
@ActiveProfiles("prod")
public class LoggingProdProfileTest extends LoggingTest {

    @Test
    public void testLoggingWithProdProfile() {
        log.info("Testing logging with PROD profile");
        testLogging();
    }
}