# Pharmacy Bounded Context

## Overview

The Pharmacy bounded context is responsible for managing medication dispensation, pharmacy inventory, and prescription validation in the hospital system. It provides functionality for tracking medications, managing inventory, processing prescriptions, and recording dispensations.

## Domain Model

### Entities

1. **Medication**
   - Represents a medication in the pharmacy system
   - Properties: id, name, description, dosageForm, strength, manufacturer, price, requiresPrescription

2. **Batch**
   - Represents a batch/lot of medication in the pharmacy system
   - Properties: id, medicationId, batchNumber, manufacturingDate, expirationDate, quantity
   - Methods: isExpired(), hasStock(), addQuantity(), removeQuantity()

3. **Prescription**
   - Represents a prescription issued by a doctor to a patient
   - Properties: id, patientId, doctorId, issueDate, expirationDate, items, dispensed, dispensedDate, notes
   - Methods: isExpired(), isValid(), markAsDispensed()
   - Contains PrescriptionItem: medicationId, quantity, instructions

4. **Dispensation**
   - Represents a medication dispensation to a patient
   - Properties: id, prescriptionId, patientId, pharmacistId, dispensationDateTime, items, totalAmount, notes
   - Contains DispensationItem: medicationId, medicationName, quantity, unitPrice, batchNumber, subtotal

5. **Inventory**
   - Represents the pharmacy inventory for a specific medication
   - Properties: medicationId, batches
   - Methods: getTotalQuantity(), getAvailableQuantity(), hasStock(), removeStock()

### Value Objects

1. **MedicationId**
   - Unique identifier for a medication

2. **BatchId**
   - Unique identifier for a batch

3. **PrescriptionId**
   - Unique identifier for a prescription

4. **DispensationId**
   - Unique identifier for a dispensation

### Repositories

1. **MedicationRepository**
   - Manages persistence of Medication entities

2. **BatchRepository**
   - Manages persistence of Batch entities

3. **PrescriptionRepository**
   - Manages persistence of Prescription entities

4. **DispensationRepository**
   - Manages persistence of Dispensation entities

5. **InventoryRepository**
   - Manages persistence of Inventory entities

## Application Layer

The application layer follows the CQRS (Command Query Responsibility Segregation) pattern, separating commands (write operations) from queries (read operations).

### Commands

1. **Medication Commands**
   - CreateMedicationCommand
   - UpdateMedicationCommand
   - DeleteMedicationCommand

2. **Prescription Commands**
   - CreatePrescriptionCommand
   - UpdatePrescriptionCommand
   - DeletePrescriptionCommand

3. **Dispensation Commands**
   - CreateDispensationCommand
   - UpdateDispensationCommand
   - DeleteDispensationCommand

### Queries

1. **Medication Queries**
   - GetMedicationByIdQuery
   - GetAllMedicationsQuery

2. **Prescription Queries**
   - GetPrescriptionByIdQuery
   - GetAllPrescriptionsQuery
   - GetPrescriptionsByPatientIdQuery

3. **Dispensation Queries**
   - GetDispensationByIdQuery
   - GetAllDispensationsQuery
   - GetDispensationsByPatientIdQuery

## Infrastructure Layer

### REST API

The REST API is implemented using Spring MVC controllers, following the CQRS pattern with separate controllers for commands and queries.

#### Medication Endpoints

- `POST /pharmacy/medications` - Create a new medication
- `PUT /pharmacy/medications/{id}` - Update an existing medication
- `DELETE /pharmacy/medications/{id}` - Delete a medication
- `GET /pharmacy/medications/{id}` - Get a medication by ID
- `GET /pharmacy/medications` - Get all medications

#### Prescription Endpoints

- `POST /pharmacy/prescriptions` - Create a new prescription
- `PUT /pharmacy/prescriptions/{id}` - Update an existing prescription
- `DELETE /pharmacy/prescriptions/{id}` - Delete a prescription
- `GET /pharmacy/prescriptions/{id}` - Get a prescription by ID
- `GET /pharmacy/prescriptions` - Get all prescriptions
- `GET /pharmacy/prescriptions/patient/{patientId}` - Get prescriptions by patient ID

#### Dispensation Endpoints

- `POST /pharmacy/dispensations` - Create a new dispensation
- `PUT /pharmacy/dispensations/{id}` - Update an existing dispensation
- `DELETE /pharmacy/dispensations/{id}` - Delete a dispensation
- `GET /pharmacy/dispensations/{id}` - Get a dispensation by ID
- `GET /pharmacy/dispensations` - Get all dispensations
- `GET /pharmacy/dispensations/patient/{patientId}` - Get dispensations by patient ID

### Persistence

The persistence layer is implemented using Spring Data JPA with the following components:

1. **JPA Entities**
   - MedicationJpaEntity
   - BatchJpaEntity
   - PrescriptionJpaEntity
   - PrescriptionItemJpaEntity
   - DispensationJpaEntity
   - DispensationItemJpaEntity
   - InventoryJpaEntity

2. **JPA Repositories**
   - MedicationJpaRepository
   - BatchJpaRepository
   - PrescriptionJpaRepository
   - PrescriptionItemJpaRepository
   - DispensationJpaRepository
   - DispensationItemJpaRepository
   - InventoryJpaRepository

3. **Repository Adapters**
   - MedicationRepositoryAdapter
   - BatchRepositoryAdapter
   - PrescriptionRepositoryAdapter
   - DispensationRepositoryAdapter
   - InventoryRepositoryAdapter

## Usage Examples

### Creating a Medication

```java
CreateMedicationCommand command = CreateMedicationCommand.builder()
        .name("Paracetamol")
        .description("Pain reliever and fever reducer")
        .dosageForm("Tablet")
        .strength("500mg")
        .manufacturer("Pharma Inc.")
        .price(new BigDecimal("5.99"))
        .requiresPrescription(false)
        .build();

MedicationId medicationId = createMedicationCommandHandler.handle(command);
```

### Creating a Prescription

```java
List<CreatePrescriptionCommand.PrescriptionItemCommand> items = List.of(
        CreatePrescriptionCommand.PrescriptionItemCommand.builder()
                .medicationId(medicationId.toString())
                .quantity(30)
                .instructions("Take 1 tablet every 6 hours as needed for pain")
                .build()
);

CreatePrescriptionCommand command = CreatePrescriptionCommand.builder()
        .patientId(patientId)
        .doctorId(doctorId)
        .issueDate(LocalDate.now())
        .expirationDate(LocalDate.now().plusMonths(6))
        .items(items)
        .notes("Patient has reported allergies to aspirin")
        .build();

PrescriptionId prescriptionId = createPrescriptionCommandHandler.handle(command);
```

### Creating a Dispensation

```java
List<CreateDispensationCommand.DispensationItemCommand> items = List.of(
        CreateDispensationCommand.DispensationItemCommand.builder()
                .medicationId(medicationId.toString())
                .medicationName("Paracetamol 500mg")
                .quantity(30)
                .unitPrice(new BigDecimal("5.99"))
                .batchNumber("B12345")
                .build()
);

CreateDispensationCommand command = CreateDispensationCommand.builder()
        .prescriptionId(prescriptionId.toString())
        .patientId(patientId)
        .pharmacistId(pharmacistId)
        .items(items)
        .notes("Patient was advised about potential side effects")
        .build();

DispensationId dispensationId = createDispensationCommandHandler.handle(command);
```