package com.example.hospitalapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Swagger/OpenAPI documentation
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configures the OpenAPI documentation for the Hospital API
     * @return the OpenAPI configuration
     */
    @Bean
    public OpenAPI hospitalOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        String securityDescription = """
            ## JWT Authentication

            To authenticate and use protected endpoints:

            1. First, use the `/auth/login` endpoint to obtain a JWT token
            2. Click the 'Authorize' button at the top right
            3. In the value field, enter your JWT token
            4. Click 'Authorize' and close the dialog
            5. Now you can use protected endpoints

            Note: Do not include the 'Bearer ' prefix, just paste the token value.
            """;

        return new OpenAPI()
                .info(new Info()
                        .title("Hospital API")
                        .description("API for hospital management following DDD, Hexagonal Architecture, and CQRS patterns")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Hospital API Team")
                                .email("contact@hospital-api.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, 
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description(securityDescription)
                        )
                );
    }
}
