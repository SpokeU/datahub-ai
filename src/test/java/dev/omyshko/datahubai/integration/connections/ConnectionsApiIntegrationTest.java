package dev.omyshko.datahubai.integration.connections;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import dev.omyshko.datahubai.integration.config.TestDataFactory;
import dev.omyshko.datahubai.api.model.ConnectionType;
import dev.omyshko.datahubai.api.model.ConnectionConstants;
import dev.omyshko.datahubai.integration.config.BaseIntegrationTest;
import dev.omyshko.datahubai.connections.repository.DatabaseConnectionRepository;
import dev.omyshko.datahubai.connections.service.ConnectionEncryptionService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Map;

public class ConnectionsApiIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private DatabaseConnectionRepository connectionRepository;

    @Autowired
    private ConnectionEncryptionService encryptionService;

    @Test
    @Sql({"/sql/cleanup.sql"})
    void shouldCreateNewConnection() {
        String connectionName = "test-connection-custom";
        var request = TestDataFactory.createValidConnectionRequest(
            connectionName,
            ConnectionType.POSTGRESQL,
            Arrays.asList("test", "custom")
        );

        Integer connectionId = given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/connections")
        .then()
            .statusCode(201)
            .body("name", equalTo(connectionName))
            .body("type", equalTo(request.getType().toString()))
            .body("tags", hasItems("test", "custom"))
            .body("details.password", equalTo(ConnectionConstants.PASSWORD_MASK))
            .body("createdAt", notNullValue())
            .body("createdBy", notNullValue())
            .extract().path("id");

        // Verify database state
        var savedConnection = connectionRepository.findById(connectionId.longValue())
            .orElseThrow(() -> new AssertionError("Connection not found in database"));

        // Verify tags stored correctly in array format
        assertNotNull(savedConnection.getTags(), "Tags should not be null");
        assertEquals(2, savedConnection.getTags().size(), "Should have exactly 2 tags");
        assertTrue(savedConnection.getTags().containsAll(Arrays.asList("test", "custom")), 
            "All tags should be stored correctly");

        // Verify connection details are encrypted
        Map<String, String> encryptedDetails = savedConnection.getConnectionDetails();
        assertNotNull(encryptedDetails, "Connection details should not be null");
        assertNotEquals(request.getDetails().getPassword(), 
            encryptedDetails.get("password"), "Password should be encrypted");

        // Verify decrypted values match original
        Map<String, String> decryptedDetails = encryptionService.decryptConnectionDetails(encryptedDetails);
        assertEquals(request.getDetails().getHost(), decryptedDetails.get("host"), "Host should match");
        assertEquals(String.valueOf(request.getDetails().getPort()), decryptedDetails.get("port"), "Port should match");
        assertEquals(request.getDetails().getDatabase(), decryptedDetails.get("database"), "Database should match");
        assertEquals(request.getDetails().getUsername(), decryptedDetails.get("username"), "Username should match");
        assertEquals(request.getDetails().getPassword(), decryptedDetails.get("password"), "Password should match after decryption");
    }

    @Test
    @Sql({"/sql/cleanup.sql"})
    void shouldFailWithMissingRequiredFields() {
        var request = TestDataFactory.createValidConnectionRequest(
            null,  // missing name
            ConnectionType.POSTGRESQL,
            Arrays.asList("test")
        );

        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/connections")
        .then()
            .statusCode(400)
            .body("errors", hasSize(1))
            .body("errors[0].code", equalTo("400.validation_error.name"))
            .body("errors[0].message", containsString("must not be null"));
    }

    @Test
    @Sql({"/sql/cleanup.sql"})
    void shouldListConnections() {
        // Setup: Create test connection through API
        var request = TestDataFactory.createValidConnectionRequest(
            "test-connection-1",
            ConnectionType.POSTGRESQL,
            Arrays.asList("test", "integration")
        );

        // Create connection
        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/connections")
        .then()
            .statusCode(201);

        // Test: Get all connections
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/connections")
        .then()
            .log().all()
            .statusCode(200)
            .body("size()", is(1))
            .body("[0].name", equalTo("test-connection-1"))
            .body("[0].tags", hasItems("test", "integration"))
            .body("[0].details.password", equalTo(ConnectionConstants.PASSWORD_MASK));
    }

    @Test
    @Sql({"/sql/cleanup.sql"})
    void shouldFailWithDuplicateConnectionName() {
        var request = TestDataFactory.createValidConnectionRequest(
            "test-connection",
            ConnectionType.POSTGRESQL,
            Arrays.asList("test")
        );

        // Create first connection
        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/connections")
        .then()
            .statusCode(201);

        // Try to create duplicate
        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/connections")
        .then()
            .statusCode(409)
            .body("errors", hasSize(1))
            .body("errors[0].code", equalTo("409.conflict.duplicate_name"))
            .body("errors[0].message", containsString("already exists"));
    }

    @Test
    @Sql({"/sql/cleanup.sql"})
    void shouldRetrieveConnectionById() {
        // Create connection first
        var request = TestDataFactory.createValidConnectionRequest(
            "test-connection",
            ConnectionType.POSTGRESQL,
            Arrays.asList("test")
        );

        Integer connectionId = given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/connections")
        .then()
            .statusCode(201)
            .extract()
            .path("id");

        // Retrieve and verify
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/connections/{id}", connectionId)
        .then()
            .statusCode(200)
            .body("name", equalTo("test-connection"))
            .body("type", equalTo(request.getType().toString()))
            .body("details.password", equalTo(ConnectionConstants.PASSWORD_MASK))
            .body("createdAt", notNullValue());
    }

    @Test
    void shouldReturnNotFoundForNonExistentConnection() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/connections/{id}", "non-existent-id")
        .then()
            .statusCode(404)
            .body("message", containsString("not found"));
    }

    @Test
    @Sql({"/sql/cleanup.sql"})
    void shouldFailWithInvalidConnectionType() {
        var request = TestDataFactory.createValidConnectionRequest(
            "test-connection",
            ConnectionType.POSTGRESQL,
            Arrays.asList("test")
        );

        // Convert to JSON and replace the valid type with invalid
        String requestJson;
        try {
            requestJson = new com.fasterxml.jackson.databind.ObjectMapper()
                .writeValueAsString(request)
                .replace("\"POSTGRESQL\"", "\"INVALID_TYPE\"");
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException("Failed to convert request to JSON", e);
        }

        given()
            .contentType(ContentType.JSON)
            .body(requestJson)
        .when()
            .post("/connections")
        .then()
            .statusCode(400)
            .body("errors", hasSize(1))
            .body("errors[0].code", equalTo("400.validation_error.type"))
            .body("errors[0].message", equalTo("Invalid connection type provided"));
    }
} 