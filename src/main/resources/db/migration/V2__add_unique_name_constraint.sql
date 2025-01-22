-- Add unique constraint on name column
ALTER TABLE database_connections ADD CONSTRAINT uk_database_connections_name UNIQUE (name); 