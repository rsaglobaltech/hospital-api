# Hospital API - Monolithic Architecture with Domain-Driven Design

This project is a hospital management system implemented as a monolithic application with a domain-driven design approach. The system is organized into bounded contexts that represent different domains within the hospital management system. In the future, these bounded contexts may be extracted into separate microservices.

## Project Structure

The project is organized into packages that represent different bounded contexts in the domain:

- **shared**: Shared components, domain events, and interfaces used by all bounded contexts
- **patient**: Patient management bounded context
- **medicalstaff**: Medical staff management bounded context
- **appointment**: Appointment management bounded context
- **clinicalrecord**: Clinical records bounded context
- **scheduling**: Scheduling bounded context
- **security**: Security and authentication bounded context
- **notification**: Notification bounded context
- **pharmacy**: Pharmacy management bounded context
- **analytics**: Analytics and reporting bounded context

## Vertical Slicing Approach

This project implements a vertical slicing approach to software development. In vertical slicing, each feature or bounded context is implemented across all layers of the application, from the user interface down to the database. This is in contrast to horizontal layering, where entire layers (e.g., UI, business logic, data access) are built separately.

### Benefits of Vertical Slicing

- **Independent Development**: Teams can work on different bounded contexts without interfering with each other
- **Faster Delivery**: Features can be developed and delivered end-to-end without waiting for other layers to be completed
- **Easier Testing**: Each slice can be tested independently
- **Simplified Maintenance**: Changes to a feature typically affect only one vertical slice
- **Clear Boundaries**: Each bounded context has well-defined responsibilities and interfaces

### Implementation in This Project

Each bounded context in our application follows a vertical slicing pattern with its own:

1. **Domain Layer**: Contains the core business logic, entities, value objects, and domain services
2. **Application Layer**: Contains application services, commands, queries, and their handlers
3. **Infrastructure Layer**: Contains implementations of repositories, adapters for external services, and other infrastructure concerns

The architecture enforces clean architecture principles within each vertical slice:
- Domain layer does not depend on application or infrastructure layers
- Application layer may depend on domain layer but not on infrastructure layer
- Infrastructure layer may depend on both domain and application layers

This approach provides a solid foundation for the future migration to microservices, as each bounded context already forms a cohesive, independent unit.

## Application Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                        Hospital API Application                      │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐ │
│  │   Patient   │  │ MedicalStaff│  │ Appointment │  │ ClinicalRec │ │
│  │   Context   │  │   Context   │  │   Context   │  │   Context   │ │
│  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘ │
│                                                                     │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐ │
│  │ Scheduling  │  │  Security   │  │ Notification│  │  Pharmacy   │ │
│  │   Context   │  │   Context   │  │   Context   │  │   Context   │ │
│  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘ │
│                                                                     │
│  ┌─────────────────────────────────────────────────────────────────┐│
│  │                      Shared Kernel                              ││
│  │  ┌───────────┐ ┌───────────┐ ┌───────────┐ ┌───────────────┐   ││
│  │  │ Command   │ │  Query    │ │  Event    │ │ Domain Models │   ││
│  │  │   Bus     │ │   Bus     │ │ Publisher │ │ & Interfaces  │   ││
│  │  └───────────┘ └───────────┘ └───────────┘ └───────────────┘   ││
│  └─────────────────────────────────────────────────────────────────┘│
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
```

## Running the Application

### Prerequisites

- Java 17
- Maven
- PostgreSQL
- Elasticsearch
- RabbitMQ

### Running the Application

```bash
mvn spring-boot:run
```

The application will start on port 8080 with context path `/api/v1`.

## Communication Between Bounded Contexts

The bounded contexts communicate with each other using:

1. **Event-Driven Architecture**: Domain events are published to RabbitMQ and consumed by interested contexts
2. **Command and Query Buses**: For in-process communication between contexts

## CQRS Architecture

The project implements the Command Query Responsibility Segregation (CQRS) pattern, which separates read and write operations for a data store:

### Command Side
- **Commands**: Represent intentions to change the system state (e.g., CreatePatientCommand, UpdateAppointmentCommand)
- **Command Handlers**: Process commands and apply business logic to modify the domain model
- **Command Bus**: Routes commands to their appropriate handlers

### Query Side
- **Queries**: Represent requests for information without changing state (e.g., GetPatientByIdQuery, GetAllAppointmentsQuery)
- **Query Handlers**: Process queries and return data from the read model
- **Query Bus**: Routes queries to their appropriate handlers

### Benefits of CQRS
- **Separation of Concerns**: Read and write operations can be optimized independently
- **Scalability**: Read and write sides can be scaled separately based on their different loads
- **Performance**: Read models can be denormalized for specific query needs
- **Flexibility**: Different data storage technologies can be used for read and write sides

### Implementation Examples

#### Command Example
Here's an example of a command handler from the Patient bounded context:

```java
@Service
@RequiredArgsConstructor
public class CreatePatientCommandHandler implements CommandHandler<CreatePatientCommand, PatientId> {

    private final PatientRepository patientRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public PatientId handle(CreatePatientCommand command) {
        // Create value objects
        PatientId patientId = new PatientId();
        Name name = new Name(command.getFirstName(), command.getLastName());
        Email email = new Email(command.getEmail());
        PhoneNumber phoneNumber = new PhoneNumber(command.getPhoneNumber());

        // Create patient entity
        Patient patient = new Patient(
            patientId,
            name,
            email,
            phoneNumber,
            command.getDateOfBirth(),
            command.getAddress()
        );

        // Save patient
        Patient savedPatient = patientRepository.save(patient);

        // Publish event
        eventPublisher.publish(new PatientCreatedEvent(savedPatient));

        return savedPatient.getId();
    }
}
```

#### Query Example
Here's an example of a query handler from the Patient bounded context:

```java
@Service
@RequiredArgsConstructor
public class GetPatientByIdQueryHandler implements QueryHandler<GetPatientByIdQuery, PatientResponse> {

    private final PatientRepository patientRepository;

    @Transactional(readOnly = true)
    public PatientResponse handle(GetPatientByIdQuery query) {
        // Create patient ID
        String patientId = query.getPatientId();

        // Find patient in database
        Patient patient = patientRepository.findById(new PatientId(patientId))
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + patientId));

        // Map to response
        return mapPatientToResponse(patient);
    }

    private PatientResponse mapPatientToResponse(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId().toString())
                .firstName(patient.getName().getFirstName())
                .lastName(patient.getName().getLastName())
                .email(patient.getEmail().toString())
                .phoneNumber(patient.getPhoneNumber().toString())
                .dateOfBirth(patient.getDateOfBirth().getValue())
                .address(patient.getAddress().getValue())
                .medicalHistory(patient.getMedicalHistory().getValue())
                .build();
    }
}
```

#### Command Bus Implementation
The application uses an asynchronous command bus to dispatch commands to their handlers:

```java
@Service
public class AsyncCommandBus implements CommandBus {

    private final Executor executor;
    private final Map<Class<? extends Command>, CommandHandler<? extends Command, ?>> handlers;

    public AsyncCommandBus(ApplicationContext context, @Qualifier("commandExecutor") Executor executor) {
        this.executor = executor;
        this.handlers = new HashMap<>();

        // Register all command handlers
        context.getBeansOfType(CommandHandler.class).values().forEach(this::registerHandler);
    }

    @Override
    public <R, C extends Command> CompletableFuture<R> dispatch(C command) {
        return CompletableFuture.supplyAsync(() -> {
            @SuppressWarnings("unchecked")
            CommandHandler<C, R> handler = (CommandHandler<C, R>) handlers.get(command.getClass());

            if (handler == null) {
                throw new CommandHandlerNotFoundException(command);
            }

            return handler.handle(command);
        }, executor);
    }
}
```

### Architecture Enforcement
The CQRS architecture is enforced through ArchUnit tests that validate the pattern:

## Search with Elasticsearch

The application uses Elasticsearch for efficient searching and indexing of domain entities:

### Elasticsearch Documents

Each bounded context has its own Elasticsearch document classes that mirror the domain entities:

- **Patient**: `PatientDocument` for indexing patient information
- **MedicalStaff**: `MedicalStaffDocument` for indexing medical staff information
- **Appointment**: `AppointmentDocument` for indexing appointment information

### Event-Driven Indexing

The application uses an event-driven approach to keep the Elasticsearch indexes in sync with the domain entities:

1. Domain events (e.g., `PatientCreatedEvent`, `MedicalStaffUpdatedEvent`) are published when entities change
2. Event handlers (e.g., `PatientElasticsearchEventHandler`) listen for these events
3. The handlers update the corresponding Elasticsearch documents

Example of an event handler:

```java
@Component
@RequiredArgsConstructor
public class PatientElasticsearchEventHandler {

    private final PatientElasticsearchRepository patientElasticsearchRepository;

    @EventListener
    public void handlePatientCreatedEvent(PatientCreatedEvent event) {
        PatientDocument document = mapToDocument(event.getPatient());
        patientElasticsearchRepository.save(document);
    }

    @EventListener
    public void handlePatientUpdatedEvent(PatientUpdatedEvent event) {
        PatientDocument document = mapToDocument(event.getPatient());
        patientElasticsearchRepository.save(document);
    }

    @EventListener
    public void handlePatientDeletedEvent(PatientDeletedEvent event) {
        patientElasticsearchRepository.deleteById(event.getPatientId().toString());
    }
}
```

### Elasticsearch Configuration

Elasticsearch is configured in the application.properties file:

```properties
# Elasticsearch Configuration
spring.elasticsearch.uris=http://localhost:9200
spring.elasticsearch.connection-timeout=1s
spring.elasticsearch.socket-timeout=30s
spring.elasticsearch.username=
spring.elasticsearch.password=
spring.data.elasticsearch.repositories.enabled=true
```

## Event Publishing with RabbitMQ

The project uses RabbitMQ as the message broker for implementing event-driven communication between bounded contexts:

### Event Publishing
- Domain events are published to a RabbitMQ topic exchange named "domain-events"
- Events are serialized to JSON using Jackson2JsonMessageConverter
- The event class name is used as the routing key (e.g., "PatientCreatedEvent")
- Events are also published to Spring's ApplicationEventPublisher for backward compatibility

### Event Consumption
- Bounded contexts create durable queues bound to the "domain-events" exchange
- Queues are bound with routing keys matching the event types they're interested in
- Event handlers use @RabbitListener annotations to consume events from their queues
- Event handlers process events and update their local state accordingly

### Configuration Example
Here's an example of the RabbitMQ configuration:

```java
@Configuration
public class RabbitMQConfig {

    private static final String EXCHANGE_NAME = "domain-events";

    @Bean
    public Exchange domainEventsExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
```

### Event Handler Example
Here's an example of an event handler that consumes events from RabbitMQ:

```java
@Component
@Slf4j
public class UserRabbitMQEventHandler {

    private static final String EXCHANGE_NAME = "domain-events";

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "user-created-queue", durable = "true"),
            exchange = @Exchange(value = EXCHANGE_NAME, type = "topic"),
            key = "UserCreatedEvent"
    ))
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        log.info("Handling UserCreatedEvent from RabbitMQ for user with ID: {}", event.getAggregateId());
        log.info("User created: {}", event.getUser().getUsername());
    }
}
```

## Database Configuration

The application uses a single database with separate schemas for each bounded context:

- Main schema: `hospital`
- Analytics schema: `hospital_analytics`

## API Documentation

The application provides Swagger UI for API documentation, available at:

http://localhost:8080/api/v1/swagger-ui.html

The API documentation is organized by bounded contexts, with separate controller groups for each context.

## Development

### Building the Project

To build the project:

```bash
mvn clean install
```

### Testing Strategy

The application follows a comprehensive testing strategy with different types of tests to ensure code quality and correctness:

#### Unit Tests

Unit tests focus on testing individual components in isolation, using mocks for dependencies. They are named with the suffix `Test`:

```bash
# Run all unit tests
mvn test -Dtest="*Test"
```

Key unit test categories:
- **Domain Tests**: Test domain entities and value objects (e.g., `PatientTest`, `EmailTest`)
- **Application Tests**: Test command and query handlers (e.g., `CreatePatientCommandHandlerTest`, `GetPatientByIdQueryHandlerTest`)
- **Infrastructure Tests**: Test repository adapters (e.g., `MedicalStaffRepositoryAdapterTest`)

#### Integration Tests

Integration tests verify the interaction between components and external systems. They are named with the suffix `IT`:

```bash
# Run all integration tests
mvn test -Dtest="*IT"
```

#### Application Tests

Application tests focus on testing the API endpoints and controllers. They are named with the suffix `ControllerTest`:

```bash
# Run all application tests
mvn test -Dtest="*ControllerTest"
```

Examples include `PatientControllerTest`, `MedicalStaffControllerTest`, and `AppointmentControllerTest`.

#### Architecture Tests

Architecture tests ensure that the codebase adheres to the defined architectural patterns and constraints. They are named with the suffix `ArchitectureTest`:

```bash
# Run all architecture tests
mvn test -Dtest="*ArchitectureTest"
```

Key architecture tests:
- `ApplicationArchitectureTest`: Verifies overall application architecture
- `BoundedContextArchitectureTest`: Ensures bounded contexts are properly isolated
- `CqrsArchitectureTest`: Verifies CQRS pattern implementation
- `DomainArchitectureTest`: Checks domain layer architecture
- `InfrastructureArchitectureTest`: Validates infrastructure layer architecture

#### Running All Tests

To run all tests:

```bash
mvn test
```

### Code Style

This project uses a standardized code style configuration to ensure consistent code formatting and quality across all modules. The configuration is enforced using:

1. **Checkstyle**: A development tool to help programmers write Java code that adheres to a coding standard
2. **.editorconfig**: A file format and collection of text editor plugins for maintaining consistent coding styles

#### Using Checkstyle

The project includes a `checkstyle.xml` configuration file that enforces Java 17 coding standards. To run the Checkstyle check:

```bash
mvn checkstyle:check
```

Checkstyle validation is also automatically run during the build process in the `validate` phase.

#### Using EditorConfig

The project includes an `.editorconfig` file that defines basic code style settings for various file types. Most modern IDEs (IntelliJ IDEA, VS Code, Eclipse) support EditorConfig natively or through plugins.

For IntelliJ IDEA:
1. Install the EditorConfig plugin (if not already installed)
2. Go to Settings > Editor > Code Style
3. Check "Enable EditorConfig support"

For VS Code:
1. Install the EditorConfig for VS Code extension

For Eclipse:
1. Install the EditorConfig Eclipse plugin

## Deployment

The application can be deployed as a single unit, with plans to migrate to microservices in the future.

### CI/CD Pipeline

The project uses GitHub Actions for continuous integration and deployment. The CI/CD pipeline is split into multiple workflows for better organization and flexibility:

#### Common CI Tasks

The `ci-common.yml` workflow handles common CI tasks for all branches:

- Building the application
- Static code analysis
- Running unit tests, integration tests, application tests, and architecture tests
- Building and pushing a Docker image

#### Environment-Specific CD Workflows

Separate CD workflows are triggered based on the branch:

- `cd-dev.yml`: Deploys to the development environment (triggered by the `dev` branch)
- `cd-feature.yml`: Deploys to the feature environment (triggered by `feature/*` branches)
- `cd-staging.yml`: Deploys to the staging environment (triggered by the `staging` branch)
- `cd-prod.yml`: Deploys to the production environment (triggered by the `main` branch)

#### Infrastructure Deployment

The `cd-infrastructure.yml` workflow handles the deployment of infrastructure components:

- RabbitMQ
- Elasticsearch
- PostgreSQL

This workflow can be triggered manually and allows selecting:
- The environment to deploy to (dev, feature, staging, prod)
- The specific components to deploy (all, rabbitmq, elasticsearch, database)

### Kubernetes Deployment

The application can be deployed to Kubernetes using the following methods:

#### Helm Chart

We provide a Helm chart for deploying the application to Kubernetes:

```bash
# Install the chart
helm install hospital-api ./charts/hospital-api

# For production deployment
helm install hospital-api ./charts/hospital-api -f ./charts/hospital-api/values-prod.yaml
```

For more details on the Helm chart configuration, see the [Helm Chart README](./charts/hospital-api/README.md).

#### ArgoCD and Progressive Delivery

For production environments, we use ArgoCD for GitOps-based deployments and Argo Rollouts for progressive delivery:

1. Install ArgoCD and Argo Rollouts in your cluster
2. Apply the ArgoCD application manifest:

```bash
kubectl apply -f argocd/hospital-api-prod.yaml
```

This will deploy the application using a canary deployment strategy with progressive traffic shifting:

1. Deploy the new version with 20% of traffic
2. Wait for 5 minutes and analyze metrics
3. Increase to 40% of traffic
4. Wait for 5 minutes and analyze metrics
5. Increase to 60% of traffic
6. Wait for 5 minutes and analyze metrics
7. Increase to 80% of traffic
8. Wait for 5 minutes and analyze metrics
9. Complete the rollout if all analyses pass

## Future Migration to Microservices

This project is currently a monolithic application with a domain-driven design approach. In the future, it will be migrated to a microservices architecture. The migration plan involves:

1. Identifying bounded contexts in the monolith (already done)
2. Creating separate modules for each bounded context
3. Implementing event-driven communication between services (foundation already in place with RabbitMQ)
4. Setting up independent databases for each service
5. Configuring each service to run as a standalone application

The current architecture with CQRS and event-driven communication provides a solid foundation for this future migration.
