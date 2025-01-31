## [Create Connection](DataHubAPI.md#create-connection)

### Create Postgres Connection

#### Input
[ConnectionRequest](DataHubAPI.md#connectionrequest)
```json5
{
  "name": "Production DB",
  "description": "Main production database",
  "type": "POSTGRESQL",
  "details": {
    "host": "db.example.com",
    "port": 5432,
    "database": "main_db",
    "username": "app_user",
    "password": "secret"
  },
  "tags": ["production", "main"]
}
```

#### Output

##### Database State
After successful processing, the following record will be created in the `database_connections` table:

```jsonc
{
    "id": <auto-generated>,
    "name": "Production DB",
    "description": "Main production database",
    "type": "POSTGRESQL",
    "connection_details": {
        "host": "<encrypted_base64>",
        "port": "<encrypted_base64>",
        "database": "<encrypted_base64>",
        "username": "<encrypted_base64>",
        "password": "<encrypted_base64>"
    },
    "tags": ["production", "main"],
    "created_at": "<timestamp>",
    "created_by": "<requesting_user_id>",
    "updated_at": "<timestamp>",
    "updated_by": "<requesting_user_id>"
}
```

**Important Notes:**
- Each field in connection_details is individually encrypted and stored as base64-encoded strings
- The JSONB structure preserves the field names but contains encrypted values
- The original values are never stored in plain text
- Timestamps are automatically set to the current time in UTC
- The requesting user's ID is recorded in both created_by and updated_by fields


