# DataHub

## Overview

Organizations face several challenges with database queries:
- Developers need a reliable way to share and manage SQL queries across teams
- Business stakeholders require regular access to specific data insights
- Current solutions (sharing via chat, docs, or verbal communication) are inefficient and error-prone
- Running repetitive business queries manually is time-consuming

DataHub is a centralized web platform for database query management and collaboration that solves these problems by providing a unified platform where teams can manage database connections, create and share queries, and access results through a user-friendly interface. Both technical and non-technical users can easily retrieve the data they need without direct database access or developer intervention.

## Key Features

### Database Connections Management
Secure storage and management of database connection details, supporting PostgreSQL for the MVP phase. Users can add, edit, and test database connections through a simple interface. [Read more](features/database-connections-management.md)

### Query Editor & Execution
Interactive SQL editor with syntax highlighting where users can write, execute, and view results of database queries. Includes basic error handling and result set display in a tabular format.

### Query Management
A centralized repository where users can save, name, and organize their frequently used queries. Saved queries can be easily found, edited, and re-executed by team members.

### Access Control
Basic role-based access control to manage who can:
- View and execute queries
- Create and modify queries
- Manage database connections
- Administer user permissions

## Roadmap
{Project roadmap information}

## Technical Solution Overview
{A short summary of technologies used in implementation}

### Architecture
{More detailed technical solution overview with system design decisions}

### Components

#### DataHubAPI
- Type: REST API
- Technology: Spring Boot
- Description: A backend service that handles core functionality including database connection management, query execution, and security
- Key responsibilities:
  - Database connection management
  - Query execution
  - Security and access control
  - API endpoints for all features
- Environments:
  - Local: localhost:8080
  - Dev: api-dev.datahub.com
  - Staging: api-staging.datahub.com
  - Production: api.datahub.com

#### DataHubWeb
- Type: Web Application
- Technology: React
- Description: A frontend application that provides user interface for managing connections, executing queries, and viewing results
- Key responsibilities:
  - User interface for all features
  - Client-side validations
  - Interactive query editor
  - Results visualization
- Environments:
  - Local: localhost:4200
  - Dev: dev.datahub.com
  - Staging: staging.datahub.com
  - Production: datahub.com

#### DataHubDB
- Type: Database
- Technology: PostgreSQL
- Description: System database that stores connection configurations, saved queries, and user data
- Key responsibilities:
  - Store system configurations
  - Manage user data
  - Store encrypted connection details
  - Save queries and their metadata
- Environments:
  - Local: localhost:15432
  - Dev: dev.datahub.db:5432
  - Staging: staging.datahub.db:5432
  - Production: datahub.db:5432