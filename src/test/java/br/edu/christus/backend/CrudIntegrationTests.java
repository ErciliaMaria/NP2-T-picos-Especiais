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

    long createStudent() throws Exception {
        String matricula = java.util.UUID.randomUUID().toString();
        request("POST", "/api/students", Map.of("nome_completo", "Aluno Teste", "matricula", matricula,
                "turma", "2A", "dataNascimento", "2009-03-12"), 201);
        for (JsonNode student : request("GET", "/api/students", null, 200)) {
            if (matricula.equals(student.get("matricula").asText())) return student.get("id").asLong();
        }
        throw new AssertionError("Aluno cadastrado não retornou na listagem");
    }

    long createBook() throws Exception {
        return request("POST", "/api/books", Map.of("titulo", "Dom Casmurro", "autor", "Machado de Assis",
                "editora", "Editora Teste", "anoPublicacao", 1899, "quantidadeExemplares", 3), 201).get("id").asLong();
    }

    @Test
    void loanLifecycleRelationsAndDates() throws Exception {
        long student = createStudent();
        long book = createBook();
        String path = "/api/loans";
        Map<String, Object> loan = new java.util.HashMap<>(Map.of("alunoId", student, "livroId", book,
                "dataEmprestimo", "2026-01-10", "dataPrevistaDevolucao", "2026-01-20", "observacoes", "Bom estado"));
        long id = request("POST", path, loan, 201).get("id").asLong();
        assertEquals(book, request("GET", path + "/" + id, null, 200).get("livroId").asLong());
        assertTrue(request("GET", path, null, 200).isArray());
        loan.put("dataDevolucao", "2026-01-19");
        assertEquals("2026-01-19", request("PUT", path + "/" + id, loan, 200).get("dataDevolucao").asText());
        request("DELETE", "/api/students/" + student, null, 409);
        request("DELETE", "/api/books/" + book, null, 409);
        loan.put("dataDevolucao", "2026-01-01");
        request("PUT", path + "/" + id, loan, 400);
        loan.remove("dataDevolucao");
        loan.put("dataPrevistaDevolucao", "2026-01-01");
        request("POST", path, loan, 400);
        loan.put("dataPrevistaDevolucao", "2026-01-20");
        loan.put("alunoId", 999999);
        request("POST", path, loan, 404);
        loan.put("alunoId", student);
        loan.put("livroId", 999999);
        request("POST", path, loan, 404);
        loan.put("livroId", book);
        request("DELETE", path + "/" + id, null, 204);
        request("GET", path + "/" + id, null, 404);
        request("PUT", path + "/" + id, loan, 404);
        request("DELETE", path + "/" + id, null, 404);
        request("DELETE", "/api/books/" + book, null, 200);
        request("DELETE", "/api/students/" + student, null, 200);
    }
}
