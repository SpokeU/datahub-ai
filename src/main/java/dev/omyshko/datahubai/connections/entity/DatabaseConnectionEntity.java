package dev.omyshko.datahubai.connections.entity;

import org.hibernate.annotations.JdbcTypeCode;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.List;
import java.util.HashMap;

@Entity
@Table(name = "database_connections")
@Data
public class DatabaseConnectionEntity implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL")
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    private String description;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ConnectionType type;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "connection_details", nullable = false, columnDefinition = "jsonb")
    private Map<String, String> connectionDetails;

    @Column(columnDefinition = "text[]")
    private List<String> tags;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "created_by", nullable = false, length = 50)
    private String createdBy;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "updated_by", nullable = false, length = 50)
    private String updatedBy;

    @Override
    public DatabaseConnectionEntity clone() {
        try {
            DatabaseConnectionEntity clone = (DatabaseConnectionEntity) super.clone();
            // Deep copy the connectionDetails map
            if (this.connectionDetails != null) {
                clone.connectionDetails = new HashMap<>(this.connectionDetails);
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Failed to clone DatabaseConnectionEntity", e);
        }
    }
} 