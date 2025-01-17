package dev.omyshko.datahubai.api.mapper;

import dev.omyshko.datahubai.api.model.ConnectionRequest;
import dev.omyshko.datahubai.api.model.ConnectionRequestDetails;
import dev.omyshko.datahubai.api.model.ConnectionResponse;
import dev.omyshko.datahubai.api.model.ConnectionConstants;
import dev.omyshko.datahubai.connections.entity.DatabaseConnectionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Map;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ConnectionMapper {
    
    @Mapping(source = "details", target = "connectionDetails")
    public abstract DatabaseConnectionEntity toEntity(ConnectionRequest request);
    
    @Mapping(source = "connectionDetails", target = "details")
    public abstract ConnectionResponse toResponse(DatabaseConnectionEntity entity);
    
    public Map<String, String> map(ConnectionRequestDetails details) {
        if (details == null) return null;
        return Map.of(
            "host", details.getHost(),
            "port", String.valueOf(details.getPort()),
            "database", details.getDatabase(),
            "username", details.getUsername(),
            "password", details.getPassword()
        );
    }

    public ConnectionRequestDetails map(Map<String, String> details) {
        if (details == null) return null;
        
        ConnectionRequestDetails requestDetails = new ConnectionRequestDetails();
        requestDetails.setHost(details.get("host"));
        try {
            requestDetails.setPort(Integer.parseInt(details.get("port")));
        } catch (NumberFormatException e) {
            requestDetails.setPort(0); // Use default port as fallback
        }
        requestDetails.setDatabase(details.get("database"));
        requestDetails.setUsername(details.get("username"));
        requestDetails.setPassword(ConnectionConstants.PASSWORD_MASK); // Mask password in response
        
        return requestDetails;
    }
} 