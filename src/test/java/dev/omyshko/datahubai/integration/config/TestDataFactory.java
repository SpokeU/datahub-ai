package dev.omyshko.datahubai.integration.config;

import dev.omyshko.datahubai.api.model.ConnectionRequestDetails;
import dev.omyshko.datahubai.api.model.ConnectionRequest;
import dev.omyshko.datahubai.api.model.ConnectionType;
import java.util.Collections;
import java.util.UUID;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TestDataFactory {


    public static ConnectionRequest createValidConnectionRequest() {
        return createValidConnectionRequest("test-connection-" + UUID.randomUUID(), 
            ConnectionType.POSTGRESQL, Collections.singletonList("test"));
    }

    public static ConnectionRequest createValidConnectionRequest(String name, 
            ConnectionType type, List<String> tags) {
        ConnectionRequest request = new ConnectionRequest();
        request.setName(name);
        request.setDescription("Test connection description");
        request.setType(type);
        request.setTags(tags);

        ConnectionRequestDetails details = new ConnectionRequestDetails();
        details.setHost("localhost");
        details.setPort(5432);
        details.setDatabase("test-db");
        details.setUsername("test-user");
        details.setPassword("test-password");
        
        request.setDetails(details);
        return request;
    }

    public static ConnectionRequest createValidConnectionRequestWithDetails(String name, 
            ConnectionType type, List<String> tags, ConnectionRequestDetails details) {
        ConnectionRequest request = new ConnectionRequest();
        request.setName(name);
        request.setDescription("Test connection description");
        request.setType(type);
        request.setTags(tags);
        request.setDetails(details);
        return request;
    }

    public static ConnectionRequest createInvalidConnectionRequest() {
        ConnectionRequest request = createValidConnectionRequest();
        request.setName(null); // Make it invalid by removing required field
        return request;
    }
}