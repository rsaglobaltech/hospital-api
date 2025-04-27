package com.example.hospitalapi.shared.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configuration for thread pools used by the command and query buses
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {
    
    /**
     * Thread pool for command execution
     */
    @Bean(name = "commandExecutor")
    public Executor commandExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("command-");
        executor.initialize();
        return executor;
    }
    
    /**
     * Thread pool for query execution
     */
    @Bean(name = "queryExecutor")
    public Executor queryExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("query-");
        executor.initialize();
        return executor;
    }
}