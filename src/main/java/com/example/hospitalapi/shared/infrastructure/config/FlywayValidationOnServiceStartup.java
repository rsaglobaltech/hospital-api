package com.example.hospitalapi.shared.infrastructure.config;

import com.example.hospitalapi.shared.infrastructure.exception.DatabaseValidationException;

import lombok.extern.slf4j.Slf4j;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@Configuration
@DependsOn("flyway")
@Profile({"validate-flyway","dev", "feature","staging", "prod"})
@Slf4j
public class FlywayValidationOnServiceStartup implements InitializingBean {

    private final Flyway flyway;

    @Value("${hospital.api.db.validate}")
    private boolean validationActive;

    public FlywayValidationOnServiceStartup(Flyway flyway) {
        this.flyway = flyway;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final ClassicConfiguration configuration = (ClassicConfiguration) flyway.getConfiguration();
        final List<String> schemasToValidate = Arrays.asList(configuration.getSchemas());
        log.info("Validating Flyway schema(s): {}", schemasToValidate);
        for (final String schema : schemasToValidate) {
            List<String> migrationsDirectory = null;
            try {
                if(validationActive){

                }
            }catch (final Exception e){
                throw new DatabaseValidationException("Validation from DB Schemas", e
                );
            }
        }

    }

    private void validate(final String schemaToValidate, final List<String> migrationsDirectory) throws Exception{
        log.info("Validating Flyway schema: {}", schemaToValidate);
        final ClassicConfiguration configuration = new ClassicConfiguration(flyway.getConfiguration());

        configuration.setSchemas(new String[]{ schemaToValidate});
        configuration.setLocations(buildLocations(migrationsDirectory));
        configuration.setIgnoreMigrationPatterns("*:missing");

        new Flyway(configuration).validate();

        log.info("Flyway schema: {} validated successfully", schemaToValidate);
    }

    private Location[] buildLocations(List<String> migrationsDirectory) {
        final var locationList = migrationsDirectory
                                    .stream()//
                                    .map(Location::new)//
                                    .toArray(Location[]::new);

        return locationList;
    }
}
