# Database Migrations with Flyway

This directory contains database migration scripts for the hospital-api project. The migrations are managed by Flyway, which ensures that database schema changes are applied in a controlled and repeatable way across different environments.

## Migration Naming Convention

Flyway uses a specific naming convention for migration scripts:

```
V<version>__<description>.sql
```

- `V` indicates a versioned migration
- `<version>` is a version number (e.g., 1, 2, 3, or 1.0, 1.1, 2.0)
- `__` (double underscore) separates the version from the description
- `<description>` is a brief description of the migration, with words separated by underscores
- `.sql` is the file extension for SQL migrations

### Consolidated Files

This directory also contains consolidated files that combine multiple migrations for reference purposes. These files use the `R` prefix instead of `V` to prevent Flyway from executing them as versioned migrations:

```
R<version>__<description>_consolidated.sql
```

- `R` indicates a reference file (not to be executed by Flyway)
- These files are for documentation purposes only and should not be modified to use the `V` prefix

## Migration Order

Migrations are applied in order of their version numbers. Each migration is applied only once, and Flyway keeps track of which migrations have been applied in a table called `flyway_schema_history`.

## Current Migrations

### Schema Migrations
- `V1__create_patients_table.sql`: Creates the patients table
- `V2__create_medical_staff_table.sql`: Creates the medical_staff table
- `V3__create_qualifications_table.sql`: Creates the qualifications table
- `V4__create_appointments_table.sql`: Creates the appointments table
- `V5__create_indexes.sql`: Creates indexes for better performance
- `V10__patient_extensions.sql`: Extends the Patient bounded context with additional fields and indexes
- `V12__create_users_tables.sql`: Creates tables for the security bounded context

### Sample Data Migrations
- `V6__add_sample_data_patients.sql`: Adds sample patient data
- `V7__add_sample_data_medical_staff.sql`: Adds sample medical staff data
- `V8__add_sample_data_qualifications.sql`: Adds sample qualification data
- `V9__add_sample_data_appointments.sql`: Adds sample appointment data
- `V11__add_sample_data_patient_extensions.sql`: Adds sample data for patient extensions
- `V13__add_sample_data_users.sql`: Adds sample user data

### Reference Files (Not Executed by Flyway)
- `R101__add_sample_data_consolidated.sql`: Consolidated version of sample data migrations (for reference only)
- `R103__appointment_extensions_consolidated.sql`: Consolidated version of appointment extensions (for reference only)
- `R104__medicalstaff_extensions_consolidated.sql`: Consolidated version of medical staff extensions (for reference only)

## Adding New Migrations

To add a new migration:

1. Create a new SQL file in this directory with a higher version number than the latest migration
2. Follow the naming convention described above
3. Write the SQL statements for your migration
4. The migration will be automatically applied when the application starts

## Environment-Specific Configuration

Flyway is configured differently for each environment:

- **Development**: Uses H2 in-memory database. Flyway is enabled and can clean the database (`spring.flyway.clean-disabled=false`).
- **Feature**: Uses PostgreSQL database. Flyway is enabled but cannot clean the database (`spring.flyway.clean-disabled=true`).
- **Staging**: Uses PostgreSQL database. Flyway is enabled but cannot clean the database (`spring.flyway.clean-disabled=true`).
- **Production**: Uses PostgreSQL database. Flyway is enabled but cannot clean the database (`spring.flyway.clean-disabled=true`).

## PostgreSQL-Specific Considerations

When running migrations against PostgreSQL databases (feature, staging, and prod environments), keep in mind the following:

1. **Case Sensitivity**: PostgreSQL treats unquoted identifiers as lowercase. Always use double quotes for identifiers if you want to preserve case.
2. **Sequences**: PostgreSQL uses sequences for auto-incrementing columns. The migration scripts use `BIGINT AUTO_INCREMENT` which works with H2, but PostgreSQL uses `BIGSERIAL` or `SERIAL`.
3. **Text Types**: PostgreSQL has specific text types like `TEXT`, `VARCHAR`, etc. The migration scripts use generic SQL types that work with both H2 and PostgreSQL.
4. **Constraints**: PostgreSQL has stricter constraint enforcement than H2. Ensure your data complies with constraints before migrating.
5. **Database Creation**: Ensure the PostgreSQL databases (`hospital_feature`, `hospital_staging`, `hospital_prod`) exist before running the application in those environments.

## More Information

For more information about Flyway, see the [official documentation](https://flywaydb.org/documentation/).
For PostgreSQL documentation, see the [official PostgreSQL documentation](https://www.postgresql.org/docs/).
