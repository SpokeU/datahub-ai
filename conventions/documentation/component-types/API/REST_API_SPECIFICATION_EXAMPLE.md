This document describes how to write specification for **REST API** type component when designing a system.
Section inside *comment goes here* are a comment about template and its usage and should not be used in actual documentation.

## Create connection

Creates new connection, so later it can be used to run queries

**POST** `/connections`

*As we are working with examples each request/response can have unique name. Template: {request_name} request/response*
#### **Postgres connection creation request**

```json5
{
  "name": "User DB",
  "type": "PG", // Enum: POSTGRESQL
    "host": "localhost",
    "port": 15432,
    "database": "users_db",
    "username": "my_user",
    "password": "my_password"
  }
}
```

#### **Postgres connection creation response**

```json5
{
  "id": 1,
  "name": "User DB",
  "type": "PG", //ConnectionTypeEnum
  "details": {
    "host": "localhost",
    "port": 15432,
    "database": "users_db",
    "username": "my_user",
    "password": "my_password"
  }
}
```

## Get connection
Retrieves connection details by ID

**GET** `/connections/{connectionId}`

*Describe each URL parameter*
`{connectionId}` - Connection ID

#### **Get connection by id response**

*You can reference existing payload to avoid duplication*
Matches a [Postgres connection creation response](#postgres-connection-creation-response)

*Or if not feasible then describe as usual*
#### **Connection by id response**
```json5
{
  "id": 1,
  "name": "User DB",
  "type": "PG", //ConnectionTypeEnum
  "details": {
    "host": "localhost",
    "port": 15432,
    "database": "users_db",
    "username": "my_user",
    "password": "my_password"
  }
}
```

## Multiple items response

*Generic template for multiple items response*

**GET** `/api/v1/connections`

#### **List connections response**
```json5
{
  "items": [
    {
      //Items
    }
  ],
  "totalCount": 10,
  "page": 1,
  "pageSize": 20
}
```

## API Error

Example of error returned by any API

**POST** `/any/endpoint`

**Request**

```json5
{
  //Request body
}
```

**Response**

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
