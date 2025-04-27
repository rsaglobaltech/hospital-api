package com.example.hospitalapi.logging;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test class to verify logging configuration with the 'staging' profile.
 */
@ActiveProfiles("staging")
public class LoggingStagingProfileTest extends LoggingTest {

    @Test
    public void testLoggingWithStagingProfile() {
        log.info("Testing logging with STAGING profile");
        testLogging();
    }
}