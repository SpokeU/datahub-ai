# Database Connections Management API

This document describes the REST API endpoints for database connections management feature.

## Create Connection

Creates a new database connection configuration.

**POST** `/api/v1/connections`

#### **Database connection creation request**
```json5
{
  "name": "Production DB",           // Human-readable connection name
  "description": "Main production database", // Optional description
  "type": "POSTGRESQL",             // Enum: POSTGRESQL (MVP)
  "details": {
    "host": "db.example.com",       // Database host
    "port": 5432,                   // Database port
    "database": "main_db",          // Database name
    "username": "app_user",         // Database username
    "password": "secret"            // Database password
  },
  "tags": ["production", "main"]    // Optional tags for grouping/filtering
}
```

#### **Database connection creation response**
```json5
{
  "id": 1,                          // Unique connection ID
  "name": "Production DB",
  "description": "Main production database",
  "type": "POSTGRESQL",
  "details": {
    "host": "db.example.com",
    "port": 5432,
    "database": "main_db",
    "username": "app_user"
    // Password is not returned in response
  },
  "tags": ["production", "main"],
  "createdAt": "2024-03-20T10:00:00Z",
  "createdBy": "user_01...",
  "updatedAt": "2024-03-20T10:00:00Z",
  "updatedBy": "user_01..."
}
```

## Get Connection

Retrieves a database connection configuration by its ID.

**GET** `/api/v1/connections/{connectionId}`

`{connectionId}` - Unique identifier of the connection

#### **Get connection by id response**
*Response payload matches the structure of:*
[Database connection creation response](#database-connection-creation-response)

*With the following differences:*
- Password field in details is replaced with "******"

## List Connections

Retrieves all database connections accessible to the user.

**GET** `/api/v1/connections`

#### **List connections response**
```json5
{
  "items": [
    {
      // Same structure as Database connection creation response
    }
  ],
  "totalCount": 10,
  "page": 1,
  "pageSize": 20
}
```

## Update Connection

Updates an existing database connection configuration.

**PUT** `/api/v1/connections/{connectionId}`

`{connectionId}` - Unique identifier of the connection

#### **Update connection request**
*Matches the structure of [Database connection creation request](#database-connection-creation-request)*

#### **Update connection response**
*Matches the structure of [Database connection creation response](#database-connection-creation-response)*

## Delete Connection

Deletes a database connection configuration.

**DELETE** `/api/v1/connections/{connectionId}`

`{connectionId}` - Unique identifier of the connection

Response: 204 No Content

## Test Connection Configuration

Tests a database connection configuration before saving.

**POST** `/api/v1/connections/test`

#### **Test connection request**
*Matches the structure of [Database connection creation request](#database-connection-creation-request)*

#### **Test connection response**
```json5
{
  "status": "SUCCESS", // Enum: SUCCESS, FAILED
  "message": "Connection successful" // Optional message, especially for failures
}
```

## Test Existing Connection

Tests an existing database connection.

**POST** `/api/v1/connections/{connectionId}/test`

`{connectionId}` - Unique identifier of the connection

Response matches the structure of Test connection response

## Get Connection Health

Retrieves health status for a specific connection.

**GET** `/api/v1/connections/{connectionId}/health`

`{connectionId}` - Unique identifier of the connection

#### **Connection health response**
```json5
{
  "status": "HEALTHY", // Enum: HEALTHY, DEGRADED, UNHEALTHY
  "lastChecked": "2024-03-20T10:00:00Z",
  "message": "Connection is working properly", // Optional status message
  "metrics": {
    "responseTime": 50, // ms
    "uptime": 99.9 // percentage
  }
}
```

## Get All Connections Health

Retrieves health status for all accessible connections.

**GET** `/api/v1/connections/health`

#### **All connections health response**
```json5
{
  "items": [
    {
      "connectionId": 1,
      // Rest matches Connection health response structure
    }
  ]
}
```

## API Error

Example of error returned by any API endpoint

#### **Generic Error Response**

code: 400 or 500

```json5
{
  "code": "{error_code}.{error_type}",
  "message": "{error_message}"
}
```

error_code - *Error code*
error_type - *Error type*
error_message - *Error message* 
