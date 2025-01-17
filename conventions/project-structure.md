```
├── api
|  ├── endpoint
|  ├── exception
|  ├── model
|  └── utils
├── config
├── integration
|  ├── client
|  ├── model
|  └── exception
├── core
|  ├── model
|  ├── service
|  └── utils
├── {feature-name}
|  ├── entity
|  ├── repository
|  ├── model
|  ├── service
|  └── utils
└── toolsmain
```

- **`api/`**:
    - **`endpoint/`**: Contains classes that handle HTTP requests. 
    Controllers or endpoints responsible for 
        1. Handling request validation if needed
        2. Mapping request models to handler funcation models(For example UserRequest -> UserEntity if service layer receives UserEntity.). This should be done using MapStruct
        3. Handling request exceptions or if no specific requirements about error handling, then expection will be handled by GlobalExceptionHandler
    - **`model/`**: Defines Data Models that are used for representing data in API endpoints.
    - **`mapper/`**: Includes interfaces and classes that map or transform data between api and handler layers, such as from entities to API models (DTOs) and vice versa, often utilizing mapping frameworks like MapStruct.
    - **`validation/`**: Holds custom validation logic, annotations, or classes used for validating API inputs, ensuring that data received from clients meets specific constraints before processing.
    - **`exception_handling/`**: Provides centralized exception handling for the API layer, using **`@ControllerAdvice`** and **`@ExceptionHandler`** annotations to manage exceptions and customize error responses.
- **`integration/`**:
    - **`client/`**: Contains classes that facilitate communication with third-party services, APIs, or systems, encapsulating the logic for external API calls.
    - **`model/`**: Defines models (DTOs) that are specifically used for integrating with third-party APIs, structuring the data exchanged in these interactions.
    - **`exception/`**: Custom exceptions related to third-party integration, handling errors that occur during external API communications
- **`core/`**:
    - **`model/`**: Defines core models that are used for representing data in the application.
    - **`service/`**: Contains classes that provide business logic and functionality for the application.
    - **`utils/`**: Includes utility classes and methods that are used throughout the application.
- **`{feature-name}/`**: A feature is a specific functionality or module of the application. Project structure is divided into features and each feature has its own folder.
    - **`entity/`**: Defines entities that are used for representing data in feature.
    - **`repository/`**: Contains classes that provide data access and manipulation logic for in feature.
    - **`model/`**: Defines models (DTOs) that are used for representing data in feature.
    - **`service/`**: Contains classes that provide business logic and functionality for feature.
    - **`utils/`**: Includes utility classes and methods that are used throughout the feature.
- **`toolsmain/`**: Contains main classes that are used for running different examples for quick testing. 


## Resources

```
resources
├── api
│   └── openapi-specs
└── db
    └── migration
```

- **`src/main/resources/`**:
    - **`api/`**: Contains OpenAPI specification files
        - **`open_api_spec.yaml`**: OpenAPI/Swagger definition file describing the API endpoints
    - **`db/`**: Database-related resources
        - **`migration/`**: Flyway database migration scripts ([Flyway Usage Conventions](how-to-use-tools/flyway.md#database-migration-naming-conventions))

## Test Structure

```
test
├── api
|  ├── endpoint
|  └── utils
├── integration
|  ├── config
|  ├── common
|  └── {feature-name}
├── core
|  ├── service
|  └── utils
└── {feature-name}
   ├── repository
   ├── service
   └── utils
```

- **`test/`**: Contains all test-related code organized by layers and features
    - **`api/`**: Tests for API layer components
        - **`endpoint/`**: Contains tests for REST controllers and endpoints
        - **`utils/`**: Test utilities specific to API testing
    - **`integration/`**: Integration test configurations and implementations
        - **`config/`**: Test configurations (TestContainers, Security, etc.)
        - **`common/`**: Shared test utilities and base test classes
        - **`{feature-name}/`**: Feature-specific integration tests
    - **`core/`**: Tests for core functionality
        - **`service/`**: Tests for core services
        - **`utils/`**: Test utilities for core components
    - **`{feature-name}/`**: Feature-specific tests
        - **`repository/`**: Repository layer tests
        - **`service/`**: Service layer tests
        - **`utils/`**: Feature-specific test utilities

## Test Resources

```
test/resources
├── application-test.yml
├── data
│   └── {feature-name}
└── db
    └── testdata
```

- **`src/test/resources/`**:
    - **`application-test.yml`**: Test-specific application configuration
    - **`data/`**: Test data files organized by feature
        - **`{feature-name}/`**: Feature-specific test data (JSON, CSV, etc.)
    - **`db/`**: Database test data
        - **`testdata/`**: SQL scripts for test data setup
