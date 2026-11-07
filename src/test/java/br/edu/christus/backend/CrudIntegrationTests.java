package br.edu.christus.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.datasource.url=jdbc:h2:mem:crudtests", "spring.jpa.hibernate.ddl-auto=create-drop"})
@ActiveProfiles("h2")
class CrudIntegrationTests {
    @Value("${local.server.port}")
    int port;
    final HttpClient client = HttpClient.newHttpClient();
    final ObjectMapper json = new ObjectMapper();

    JsonNode request(String method, String path, Object body, int expected) throws Exception {
        String text = body == null ? "" : json.writeValueAsString(body);
        HttpRequest req = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Content-Type", "application/json")
                .method(method, HttpRequest.BodyPublishers.ofString(text)).build();
        HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(expected, response.statusCode(), method + " " + path + ": " + response.body());
        return response.body().isBlank() ? json.nullNode() : json.readTree(response.body());
    }

    @Test
    void authorLifecycleAndValidation() throws Exception {
        String path = "/api/authors";
        Map<String, Object> author = Map.of("nome", "Machado de Assis", "nacionalidade", "Brasileira",
                "dataNascimento", "1839-06-21", "biografia", "Autor de Dom Casmurro");
        long id = request("POST", path, author, 201).get("id").asLong();
        assertEquals("Machado de Assis", request("GET", path + "/" + id, null, 200).get("nome").asText());
        assertTrue(request("GET", path, null, 200).isArray());
        Map<String, Object> edited = new java.util.HashMap<>(author);
        edited.put("biografia", "Romancista brasileiro");
        assertEquals("Romancista brasileiro", request("PUT", path + "/" + id, edited, 200).get("biografia").asText());
        edited.put("nome", " ");
        assertTrue(request("POST", path, edited, 400).get("message").asText().contains("Nome"));
        request("PUT", path + "/" + id, edited, 400);
        request("DELETE", path + "/" + id, null, 204);
        request("GET", path + "/" + id, null, 404);
        request("PUT", path + "/" + id, author, 404);
        request("DELETE", path + "/" + id, null, 404);
    }

    @Test
    void swaggerDocumentsAuthorRoutes() throws Exception {
        JsonNode paths = request("GET", "/v3/api-docs", null, 200).get("paths");
        assertTrue(paths.get("/api/authors").has("post"));
        assertTrue(paths.get("/api/authors/{id}").has("get"));
    }
}
