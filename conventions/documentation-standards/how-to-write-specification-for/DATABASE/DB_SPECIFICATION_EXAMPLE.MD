# Database Specification

## Schema Changes

### New Tables

**Products**
```sql
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Example data:**

| id | name           | price | created_at          |
|----|----------------|-------|----------------------|
| 1  | Basic Widget   | 19.99 | 2024-03-15T10:00:00Z |

### Modified Tables
**Products (Add inventory tracking)**
```sql
ALTER TABLE products
ADD COLUMN stock_count INTEGER DEFAULT 0,
ADD COLUMN status VARCHAR(20) DEFAULT 'active';
```

**Before/After example:**

*Before*
| id | name           | price | created_at          |
|----|----------------|-------|----------------------|
| 1  | Basic Widget   | 19.99 | 2024-03-15T10:00:00Z |

*After*
| id | name           | price | created_at          | stock_count | status  |
|----|----------------|-------|----------------------|-------------|---------|
| 1  | Basic Widget   | 19.99 | 2024-03-15T10:00:00Z | 100         | active  |

### Indexes and Performance Considerations
- Add index for product search
```sql
CREATE INDEX idx_products_name ON products(name);
```
- Add index for inventory management
```sql
CREATE INDEX idx_products_status_stock ON products(status, stock_count);
```

### Dependencies
- Inventory management service needs to be updated
- Product catalog API needs modification

### Rollback Plan
1. Remove added columns
```sql
ALTER TABLE products
DROP COLUMN stock_count,
DROP COLUMN status;
```
2. Remove indexes
```sql
DROP INDEX idx_products_name;
DROP INDEX idx_products_status_stock;
```
