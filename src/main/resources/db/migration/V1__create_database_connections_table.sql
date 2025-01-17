-- Create the database_connections table
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

-- Create indexes for performance optimization
CREATE INDEX idx_database_connections_name ON database_connections(name);
CREATE INDEX idx_database_connections_tags ON database_connections USING GIN(tags);

-- Add a comment to explain the table's purpose
COMMENT ON TABLE database_connections IS 'Stores encrypted database connection details and metadata'; 