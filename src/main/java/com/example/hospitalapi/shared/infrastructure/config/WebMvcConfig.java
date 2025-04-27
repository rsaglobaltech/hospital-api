package com.example.hospitalapi.shared.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration for Spring MVC
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Configure resource handlers to serve static resources
     * @param registry the registry to add resource handlers to
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve static resources at the root URL, regardless of the context path
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
