# Custom DataSource Configuration

This document describes the custom DataSource configuration implemented in the Hospital API application.

## Overview

The application uses a custom DataSource configuration instead of Spring Boot's default `DataSourceAutoConfiguration`. This custom configuration provides:

1. **HikariCP Connection Pool**: A high-performance JDBC connection pool
2. **Lazy Connection Proxy**: Avoids unnecessary database connections
3. **Caching**: Improves performance by caching query results

## Implementation Details

### HikariCP Connection Pool

HikariCP is a fast, lightweight, and reliable connection pool that provides:

- Connection pooling with configurable maximum pool size and minimum idle connections
- Connection timeout and maximum lifetime settings
- Connection testing to ensure valid connections

Configuration parameters:
- Maximum pool size: 10 connections
- Minimum idle connections: 5
- Idle timeout: 30 seconds
- Connection timeout: 20 seconds
- Maximum lifetime: 30 minutes
- Connection test query: "SELECT 1"
- Validation timeout: 5 seconds

### Lazy Connection Proxy

The `LazyConnectionDataSourceProxy` from Spring Framework wraps the HikariCP DataSource to:

- Avoid acquiring a database connection until it's actually needed
- Improve performance by reducing unnecessary connection acquisition
- Allow for connection pooling optimizations

### Caching

The application uses Spring's caching abstraction with a `ConcurrentMapCacheManager` to:

- Cache query results to reduce database load
- Improve response times for frequently accessed data
- Provide cache regions for different types of data:
  - "queries": For caching query results
  - "entities": For caching entity objects

## Configuration Class

The configuration is implemented in `com.example.hospitalapi.config.DataSourceConfig`, which:

1. Creates a HikariCP DataSource with optimized settings
2. Wraps it with a lazy connection proxy
3. Configures a cache manager for database query results

## Usage

To use the custom DataSource configuration:

1. The main application class no longer excludes `DataSourceAutoConfiguration`
2. Spring Boot will automatically use our custom DataSource bean
3. Database properties are still configured in the application properties files for each profile

## Profile-Specific Database Configurations

The application has different database configurations for each profile:

- **dev**: H2 in-memory database
- **feature**: PostgreSQL database for feature testing
- **staging**: PostgreSQL database for staging environment
- **prod**: PostgreSQL database for production environment

The custom DataSource configuration works with all these profiles, reading the database properties from the appropriate profile-specific properties file.