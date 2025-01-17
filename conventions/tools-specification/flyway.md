### Database Migration Naming Conventions
Flyway migrations should follow this pattern:
```
V{version}__{description}.sql
```
- `V` - Prefix for versioned migrations
- `version` - Version number (e.g., 1, 2, 1.1, 1.2)
- `description` - Brief description using underscores (e.g., create_users_table)

Examples:
```
V1__init_schema.sql
V1.1__add_user_table.sql
V2__add_role_column_to_users.sql
```

### Migration Guidelines
- Each migration script should be immutable (never modify existing migration files)
- Each script should be idempotent when possible
- Add comments to explain complex migrations
- Keep migrations atomic (one logical change per migration)

### Configuration
Configure `flyway.outOfOrder=true` in your Flyway settings to allow merging changes from different branches more easily