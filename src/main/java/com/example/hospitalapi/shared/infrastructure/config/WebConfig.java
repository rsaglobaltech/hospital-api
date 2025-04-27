package com.example.hospitalapi.shared.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC configuration to handle paths outside the context path
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configure view controllers to handle paths that should work
     * regardless of the context path setting
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // This ensures the root path works even with context path set
        registry.addViewController("/").setViewName("forward:/index.html");
        // Convenient shortcut to H2 console
        registry.addViewController("/h2").setViewName("redirect:/h2-console");
    }
}
