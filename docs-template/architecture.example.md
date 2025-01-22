# Architecture

DataHub features a three-tier architecture:
- **Frontend**: Angular (DataHubWeb) - Intuitive interface for managing connections, editing queries, and visualizing results.
- **Backend**: Spring Boot (DataHubAPI) - Secure data operations, query execution, and access control via REST APIs.
- **Database**: PostgreSQL (DataHubDB) - Central storage for encrypted connection details, saved queries, and user permissions, facilitating collaboration and data access management.

## Components

### DataHubAPI
**Type**: REST API  
**Technology**: Spring Boot  
**Description**: A backend service that handles core functionality including database connection management, query execution, and security  
**Key responsibilities**:
  - Database connection management
  - Query execution
  - Security and access control
  - API endpoints for all features  

**Environments**:
  - Local: localhost:8080
  - Dev: api-dev.datahub.com
  - Staging: api-staging.datahub.com
  - Production: api.datahub.com

### DataHubWeb
**Type**: Web Application  
**Technology**: React  
**Description**: A frontend application that provides user interface for managing connections, executing queries, and viewing results  
**Key responsibilities**:
  - User interface for all features
  - Client-side validations
  - Interactive query editor
  - Results visualization  
  
**Environments**:
  - Local: localhost:4200
  - Dev: dev.datahub.com
  - Staging: staging.datahub.com
  - Production: datahub.com

### DataHubDB
**Type**: Database  
**Technology**: PostgreSQL  
**Description**: System database that stores connection configurations, saved queries, and user data  

**Key responsibilities**:
  - Store system configurations
  - Manage user data
  - Store encrypted connection details
  - Save queries and their metadata  
  
**Environments**:
  - Local: localhost:15432
  - Dev: dev.datahub.db:5432
  - Staging: staging.datahub.db:5432
  - Production: datahub.db:5432