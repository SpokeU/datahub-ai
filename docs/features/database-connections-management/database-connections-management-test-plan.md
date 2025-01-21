# Overview
This test plan outlines the integration test scenarios for the Database Connections Management feature, focusing on testing the REST API endpoints and their interaction with the database layer.

# Implementation plan
    - Connection Creation (POST /api/v1/connections) ✔️
      - Success Creation ✔️
      - Missing Required Fields Validation ✔️
      - Invalid Connection Type ✔️
      - Invalid Port Number
      - Duplicate Connection Name
      - Field Size Validation
    - Connection Retrieval (GET /api/v1/connections/{id}) 
      - Successful Retrieval ✔️
      - Non-existent Connection
    - Connections Listing (GET /api/v1/connections) 
      - List All Connections ✔️
      - Empty Database List
    - Connection Testing (POST /api/v1/connections/test)
      - Valid Connection Test
      - Invalid Connection Test
    - Edge Cases and Special Scenarios
      - Special Characters Handling
      - Concurrent Operations

# Test Environment Requirements
- TestContainers PostgreSQL container configured for integration tests
- Test encryption key configured in application.yml (32-byte key for testing)
- Flyway migrations enabled for test database schema management
- REST Assured for API testing
- Clean database state using @Sql scripts (/sql/cleanup.sql)
- BaseIntegrationTest class setup with common test configurations

# Test Scenarios

## Connection Creation (POST /api/v1/connections)

### **Success Creation** 

Create connection with valid data and check response body and database record.

**Implemented by**: `ConnectionsApiIntegrationTest.shouldCreateNewConnection`  
**Preconditions**: -  
**Test steps**:
- Create connection by calling POST /api/v1/connections

**Expected Result**:  
Response body checks:
- Response status 201
- Response body matches expected structure
- Audit fields (created_by, created_at) properly set

Database checks:  
- Tags stored correctly in array format
- Database record created with encrypted details

---
### **Missing Required Fields Validation** 

**Implemented by**: `ConnectionsApiIntegrationTest.shouldFailWithMissingRequiredFields`

**Preconditions**: -

**Test steps**:
- Create connection with missing required field (name)

**Expected Result**:
- Response status 400
- Error message indicates missing field
- No database record created

---
### Invalid Connection Type

**Implemented by**: -

**Preconditions**: -

**Test steps**:
- Create connection with invalid connection type

**Expected Result**:
- Response status 400
- Error message indicates invalid type
- No database record created

---
### Invalid Port Number

**Implemented by**: -

**Preconditions**: -

**Test steps**:
- Create connection with negative port number
- Create connection with out of range port number

**Expected Result**:
- Response status 400
- Error message indicates invalid port
- No database record created

---
### Duplicate Connection Name 

**Implemented by**: `ConnectionsApiIntegrationTest.shouldFailWithDuplicateConnectionName`

**Preconditions**: 
- Existing connection with name "test-connection"

**Test steps**:
- Create connection with duplicate name

**Expected Result**:
- Response status 409
- Error message indicates duplicate name
- No new database record created

---
### Field Size Validation

**Implemented by**: -

**Preconditions**: -

**Test steps**:
- Create connection with name > 100 chars
- Create connection with oversized fields

**Expected Result**:
- Response status 400
- Error message indicates size violation
- No database record created

## Connection Retrieval (GET /api/v1/connections/{id})

### Successful Retrieval 

**Implemented by**: `ConnectionsApiIntegrationTest.shouldRetrieveConnectionById`

**Preconditions**:
- Existing connection in database

**Test steps**:
- Retrieve connection by ID

**Expected Result**:
- Response status 200
- All fields correctly returned
- Password masked in response
- Correct metadata returned

#### Non-existent Connection 

**Implemented by**: `ConnectionsApiIntegrationTest.shouldReturnNotFoundForNonExistentConnection`

**Preconditions**: -

**Test steps**:
- Attempt to retrieve non-existent connection

**Expected Result**:
- Response status 404
- Error message indicates not found

## Connections Listing (GET /api/v1/connections)

### List All Connections 

**Implemented by**: `ConnectionsApiIntegrationTest.shouldListConnections`

**Preconditions**:
- Multiple connections in database

**Test steps**:
- Retrieve all connections

**Expected Result**:
- Response status 200
- All connections returned
- Passwords masked
- Correct metadata for each connection

#### Empty Database List

**Implemented by**: -

**Preconditions**:
- Empty database

**Test steps**:
- Retrieve all connections

**Expected Result**:
- Response status 200
- Empty array returned

## Connection Testing (POST /api/v1/connections/test)

### Valid Connection Test

**Implemented by**: -

**Preconditions**: 
- Available PostgreSQL server

**Test steps**:
- Test valid connection details

**Expected Result**:
- Response status 200
- Success message returned
- Connection verified

#### Invalid Connection Test

**Implemented by**: -

**Preconditions**: -

**Test steps**:
- Test with invalid credentials
- Test with unreachable host
- Test with invalid port
- Test with non-existent database

**Expected Result**:
- Response status 400
- Appropriate error message for each case
- Clear indication of failure reason

## Edge Cases and Special Scenarios

### Special Characters Handling 

**Implemented by**: -

**Preconditions**: -

**Test steps**:
- Create connection with Unicode name
- Create connection with special characters in password
- Create connection with whitespace in fields

**Expected Result**:
- Response status 201
- All special characters handled correctly
- Data stored and retrieved correctly

#### Concurrent Operations

**Implemented by**: -

**Preconditions**: -

**Test steps**:
- Perform simultaneous connection creations
- Read connection during update
- Multiple parallel connection tests

**Expected Result**:
- No data corruption
- Proper handling of concurrent requests
- Consistent database state

# Test Data Requirements
- Sample valid PostgreSQL connection details
- Invalid connection configurations
- Test user accounts with different permissions
- Various tag combinations
- Special character test data
- Maximum length test data

# Expected Test Coverage
- All API endpoints
- All error scenarios
- Security requirements
- Data encryption/decryption
- Access control rules
- Input validation rules
- Concurrent access patterns

# Success Criteria
- All test cases pass
- No security vulnerabilities detected
- Performance meets requirements
- Error handling works as expected
- Data encryption verified
- Access control properly enforced