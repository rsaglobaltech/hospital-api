# Logging Implementation

This document describes the logging implementation in the Hospital API application.

## Overview

The application uses SLF4J with Logback as the logging framework. Logging is configured differently for each profile (dev, feature, staging, prod) to provide appropriate levels of detail in different environments.

## Configuration

### Profiles

The application has four profiles:

1. **dev** (default): Development environment with detailed logging
2. **feature**: Feature testing environment with moderate logging
3. **staging**: Pre-production environment with minimal logging
4. **prod**: Production environment with only important logs

### Log Levels

The following log levels are used:

- **TRACE**: Very detailed information, typically only used for deep debugging
- **DEBUG**: Detailed information useful for debugging
- **INFO**: Important runtime events (application startup, configuration changes, etc.)
- **WARN**: Potentially harmful situations that might lead to errors
- **ERROR**: Error events that might still allow the application to continue running

### Profile-Specific Settings

#### Dev Profile
- Application code: DEBUG
- Spring Web: DEBUG
- Hibernate SQL: DEBUG
- Hibernate SQL parameters: TRACE

#### Feature Profile
- Application code: DEBUG
- Spring Web: INFO
- Hibernate SQL: DEBUG

#### Staging Profile
- Application code: INFO
- Spring Web: WARN
- Hibernate: WARN

#### Production Profile
- Application code: INFO
- Spring: WARN
- Hibernate: WARN
- Root level: WARN

## Logging in Code

Logging is implemented in the following components:

1. **Controllers**: Log incoming requests and responses
2. **Service/Handler Classes**: Log business operations and exceptions
3. **Repository Adapters**: Log database operations

### Example

```java
@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Slf4j  // Lombok annotation to create SLF4J Logger
public class PatientController {
    
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable String id) {
        log.info("Retrieving patient with ID: {}", id);
        
        // Business logic
        
        log.debug("Retrieved patient: {}", response);
        return ResponseEntity.ok(response);
    }
}
```

## Testing Logging

To test logging with different profiles, run the appropriate test class:

- `LoggingDevProfileTest`: Tests logging with the dev profile
- `LoggingFeatureProfileTest`: Tests logging with the feature profile
- `LoggingStagingProfileTest`: Tests logging with the staging profile
- `LoggingProdProfileTest`: Tests logging with the prod profile

You can also run tests with a specific profile using Maven:

```bash
mvn test -Dspring.profiles.active=dev -Dtest=LoggingTest*
```

## Log Files

Logs are written to:

- Console: All logs according to profile configuration
- File: `./logs/hospital-api.log` with daily rotation
- Archived logs: `./logs/archived/hospital-api-yyyy-MM-dd.log`

The maximum history is 30 days, with a total size cap of 3GB.