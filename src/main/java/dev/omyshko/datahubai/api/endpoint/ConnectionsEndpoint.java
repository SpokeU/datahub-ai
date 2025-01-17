package dev.omyshko.datahubai.api.endpoint;

import dev.omyshko.datahubai.api.ApiApiDelegate;
import dev.omyshko.datahubai.api.mapper.ConnectionMapper;
import dev.omyshko.datahubai.api.model.ConnectionRequest;
import dev.omyshko.datahubai.api.model.ConnectionResponse;
import dev.omyshko.datahubai.connections.entity.DatabaseConnectionEntity;
import dev.omyshko.datahubai.connections.service.DatabaseConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class ConnectionsEndpoint implements ApiApiDelegate {

    private final DatabaseConnectionService connectionService;
    private final ConnectionMapper connectionMapper;
    
    // TODO: Inject proper user context service
    private static final String MOCK_USER_ID = "test-user";

    @Override
    public ResponseEntity<ConnectionResponse> createConnection(ConnectionRequest connectionRequest) {
        DatabaseConnectionEntity entity = connectionMapper.toEntity(connectionRequest);
        DatabaseConnectionEntity savedEntity = connectionService.createConnection(entity, MOCK_USER_ID);
        ConnectionResponse response = connectionMapper.toResponse(savedEntity);
        
        return ResponseEntity.status(201).body(response);
    }

    @Override
    public ResponseEntity<ConnectionResponse> getConnectionById(Long id) {
        DatabaseConnectionEntity entity = connectionService.getConnection(id);
        ConnectionResponse response = connectionMapper.toResponse(entity);
        
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<ConnectionResponse>> getConnections() {
        List<ConnectionResponse> connections = connectionService.getAllConnections()
            .stream()
            .map(connectionMapper::toResponse)
            .collect(Collectors.toList());
            
        return ResponseEntity.ok(connections);
    }
} 