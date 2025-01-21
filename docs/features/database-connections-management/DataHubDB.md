# Database Connections Management - DataHubDB

## Schema Changes

### New Tables

**database_connections**
```sql
CREATE TABLE database_connections (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    type VARCHAR(20) NOT NULL,  -- Enum: 'POSTGRESQL' for MVP
    connection_details JSONB NOT NULL,  -- Encrypted connection details
    tags TEXT[],
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50) NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50) NOT NULL
);
```

**Example data:**
```json
{
    "id": 1,
    "name": "Production DB",
    "description": "Main production database",
    "type": "POSTGRESQL",
    "connection_details": {
        "encrypted": "base64_encrypted_connection_details..."
    },
    "tags": ["production", "main"],
    "created_at": "2024-03-20T10:00:00Z",
    "created_by": "user_01",
    "updated_at": "2024-03-20T10:00:00Z",
    "updated_by": "user_01"
}
```

### Indexes and Performance Considerations
```sql
-- Index for quick lookups by name
CREATE INDEX idx_database_connections_name ON database_connections(name);

-- Index for searching by tags
CREATE INDEX idx_database_connections_tags ON database_connections USING GIN(tags);
```

### Security Considerations
- Connection details are stored encrypted in the JSONB field
- Only encrypted data is stored in the database
- Encryption/decryption happens in the application layer
- Database-level encryption at rest should be enabled

### Dependencies
- DataHubAPI needs to be updated to handle the encryption/decryption of connection details
- Encryption key management system needs to be in place

### Rollback Plan
1. Drop indexes
```sql
DROP INDEX idx_database_connections_name;
DROP INDEX idx_database_connections_tags;
```