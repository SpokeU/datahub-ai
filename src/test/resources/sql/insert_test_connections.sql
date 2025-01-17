INSERT INTO database_connections (
    name, 
    description, 
    type, 
    connection_details, 
    tags, 
    created_by, 
    updated_by
) VALUES (
    'test-connection-1',
    'Test connection description',
    'POSTGRESQL',
    '{"host": "localhost", "port": 5432, "database": "test-db", "username": "test-user", "password": "test-password"}'::jsonb,
    ARRAY['test', 'integration'],
    'test-user',
    'test-user'
); 