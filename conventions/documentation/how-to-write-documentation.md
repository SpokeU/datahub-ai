## Overview
This document describes structure of documentation for a project.
All pages are located in `project-info` folder.

Page name : `Overview.md`

```markdown
# {Project name}

## Overview

Problem statement

*Idea of the project and why it exists. What problem does it solves*


## Key Features

*Features essential to this project. For facebook its messaging, news feed, friends etc.* 

### {Feature name}
Feature description
*Short description with motication behind it. Up to three sentences*

## Roadmap

## Technical Solution Overview

*A short summary of how above features are implemented from technical perspective.Just to give an idea of what technologies are used*

### Architecture

*More detailed technical solution overview*

### Components

*Separate executables (services, applications, etc.) that are part of the project*

For example:
#### UserService: 
   Type: REST API
   Technology: Spring Boot, JPA
   Build tools: Maven, Docker
   Description: A java service that provides user management functionality. 
   Responsibilities:
   - CRUD operations for database connections
   - Credential encryption/decryption
   - ...
   Environments:
   - Local: localhost:8080
   - Dev: api-dev.datahub.com
   - Staging: api-staging.datahub.com
   - Production: api.datahub.com

#### UserServiceFrontend: 
   Type: Frontend
   Technology: Angular
   Build tools: npm, Docker
   Description: A frontend application that allows users to manage their user accounts.
   Responsibilities:
   - User interface for user management
   - Authentication and authorization
   - ...
   Environments:
   - Local: localhost:4200
   - Dev: dev.datahub.com
   - Staging: staging.datahub.com
   - Production: datahub.com

#### UserServiceDB: 
   Type: Database
   Technology: PostgreSQL
   Description: A database that stores user information.
   Responsibilities:
   - User information storage
   - ...
   Environments:
   - Local: localhost:5432
   - Dev: dev.datahub.db:5432
   - Staging: staging.datahub.db:5432
   - Production: datahub.db:5432


```


Page name : `features/{feature-name}/Overview.md`
*Each feature has its own directory*

```markdown
# {Feature name}

*Overview of the feature. This page serves as an entry point to feature so it should cover all main aspects such as the problem feature solves, high level requirements and links to other subpages (like tech solution) to allow user to discover documentation deeply*

Intention and why. What problem does it solves in scope of a project. Describe how overall feature intention

## High Level Requirements

How business see this problem should be solved in project

## Solution Overview 

How each requirement or group of requirements will be implemented

*A list of components that are part of the feature*
### [Component name](Overview.md#{component-name})

*Description of how this feature is implemented in this component. For example for backend it can be a summary of endpoints, for frontend it can be a summary of UI components. But short and concise just to give a brief overview as below each component will have more detailed documentation*

For example:
Handles all connection management operations including:
- CRUD operations for database connections
- Credential encryption/decryption
...

*When planning a soltuion for the feature check Overview page for any specific component that is already implemented. If there is no specific component for this feature, then it should be discussed of how it would fit into general architecture and then added to Overview page components list*


*A separate page with solution overview for each component.*
[{Component name}](features/{feature-name}/{component-name}.md)

*Below are the templates of how to describe each component type (REST API, Frontend, Database)*
REST API - [API-layer-example.md](component-types/API/REST_API_SPECIFICATION.md)
Frontend - TODO
DATABASE - [DB-layer-example.md](component-types/DB/DB_SPECIFICATION.md)
```