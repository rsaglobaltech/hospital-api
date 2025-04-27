package com.example.hospitalapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Hospital API.
 * 
 * Note: We no longer exclude DataSourceAutoConfiguration because we have our own
 * custom DataSourceConfig that provides a DataSource bean.
 */
@SpringBootApplication
public class HospitalApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalApiApplication.class, args);
    }

}
