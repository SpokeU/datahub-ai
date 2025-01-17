package dev.omyshko.datahubai.connections.service;

import dev.omyshko.datahubai.connections.entity.DatabaseConnectionEntity;
import dev.omyshko.datahubai.connections.exception.ConnectionNotFoundException;
import dev.omyshko.datahubai.connections.repository.DatabaseConnectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DatabaseConnectionService {
    
    private final DatabaseConnectionRepository connectionRepository;
    private final ConnectionEncryptionService encryptionService;

    @Transactional
    public DatabaseConnectionEntity createConnection(DatabaseConnectionEntity connection, String userId) {
        // Create a copy for persistence to avoid modifying the original
        DatabaseConnectionEntity entityToSave = connection.clone();
        
        // Encrypt sensitive data
        Map<String, String> encryptedDetails = encryptionService.encryptConnectionDetails(
            entityToSave.getConnectionDetails()
        );
        entityToSave.setConnectionDetails(encryptedDetails);

        // Set audit fields
        OffsetDateTime now = OffsetDateTime.now();
        entityToSave.setCreatedAt(now);
        entityToSave.setCreatedBy(userId);
        entityToSave.setUpdatedAt(now);
        entityToSave.setUpdatedBy(userId);

        // Save encrypted entity
        DatabaseConnectionEntity savedEntity = connectionRepository.save(entityToSave);
        
        // Return original connection with generated ID and audit fields
        connection.setId(savedEntity.getId());
        connection.setCreatedAt(savedEntity.getCreatedAt());
        connection.setCreatedBy(savedEntity.getCreatedBy());
        connection.setUpdatedAt(savedEntity.getUpdatedAt());
        connection.setUpdatedBy(savedEntity.getUpdatedBy());
        
        return connection;
    }

    @Transactional(readOnly = true)
    public DatabaseConnectionEntity getConnection(Long id) {
        DatabaseConnectionEntity connection = connectionRepository.findById(id)
            .orElseThrow(() -> new ConnectionNotFoundException("Connection not found with id: " + id));

        // Decrypt sensitive data
        Map<String, String> decryptedDetails = encryptionService.decryptConnectionDetails(
            connection.getConnectionDetails()
        );
        connection.setConnectionDetails(decryptedDetails);

        return connection;
    }

    @Transactional(readOnly = true)
    public List<DatabaseConnectionEntity> getAllConnections() {
        List<DatabaseConnectionEntity> connections = connectionRepository.findAll();
        
        // Decrypt all connections' details
        connections.forEach(connection -> {
            Map<String, String> decryptedDetails = encryptionService.decryptConnectionDetails(
                connection.getConnectionDetails()
            );
            connection.setConnectionDetails(decryptedDetails);
        });

        return connections;
    }
} 