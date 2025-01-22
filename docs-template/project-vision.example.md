# DataHub

Organizations face several challenges with database queries:
- Developers need a reliable way to share and manage SQL queries across teams
- Business stakeholders require regular access to specific data insights
- Current solutions (sharing via chat, docs, or verbal communication) are inefficient and error-prone
- Running repetitive business queries manually is time-consuming

DataHub is a centralized web platform for database query management and collaboration that solves these problems by providing a unified platform where teams can manage database connections, create and share queries, and access results through a user-friendly interface. Both technical and non-technical users can easily retrieve the data they need without direct database access or developer intervention.

## Key Features

### [Database Connections Management](features/database-connections-management/database-connections-management.md)
Secure storage and management of database connection details, supporting PostgreSQL for the MVP phase. Users can add, edit, and test database connections through a simple interface.

### [Query Editor & Execution](features/query-editor-execution/query-editor-execution.md)
Interactive SQL editor with syntax highlighting where users can write, execute, and view results of database queries. Includes basic error handling and result set display in a tabular format.

### [Query Management](features/query-management/query-management.md)
A centralized repository where users can save, name, and organize their frequently used queries. Saved queries can be easily found, edited, and re-executed by team members.

### [Access Control](features/access-control/access-control.md)
Basic role-based access control to manage who can:
- View and execute queries
- Create and modify queries
- Manage database connections
- Administer user permissions