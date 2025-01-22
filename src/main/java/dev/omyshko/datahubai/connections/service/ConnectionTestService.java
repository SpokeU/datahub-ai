package dev.omyshko.datahubai.connections.service;

import dev.omyshko.datahubai.connections.entity.DatabaseConnectionEntity;
import dev.omyshko.datahubai.connections.entity.ConnectionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectionTestService {
    
    private final ConnectionEncryptionService encryptionService;
    
    public TestResult testConnection(DatabaseConnectionEntity connection) {
        Map<String, String> details = connection.getConnectionDetails();
        String url = buildJdbcUrl(connection.getType(), details);
        
        try (Connection jdbcConnection = DriverManager.getConnection(
            url,
            details.get("username"),
            details.get("password")
        )) {
            // Test if connection is valid
            if (jdbcConnection.isValid(5)) { // 5 seconds timeout
                return new TestResult(TestStatus.SUCCESS, "Connection successful");
            } else {
                return new TestResult(TestStatus.FAILED, "Connection is not valid");
            }
        } catch (SQLException e) {
            log.error("Failed to test connection", e);
            return new TestResult(TestStatus.FAILED, "Connection failed: " + e.getMessage());
        }
    }
    
    private String buildJdbcUrl(ConnectionType type, Map<String, String> details) {
        return switch (type) {
            case POSTGRESQL -> String.format(
                "jdbc:postgresql://%s:%s/%s",
                details.get("host"),
                details.get("port"),
                details.get("database")
            );
        };
    }
    
    public record TestResult(TestStatus status, String message) {}
    
    public enum TestStatus {
        SUCCESS,
        FAILED
    }
} 