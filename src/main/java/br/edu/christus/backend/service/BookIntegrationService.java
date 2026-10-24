package br.edu.christus.backend.service;

import br.edu.christus.backend.domain.BookIntegration;
import br.edu.christus.backend.repository.BookIntegrationRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookIntegrationService {

    private final BookIntegrationRepository bookIntegrationRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public BookIntegrationService(BookIntegrationRepository bookIntegrationRepository, ObjectMapper objectMapper) {
        this.bookIntegrationRepository = bookIntegrationRepository;
        this.restTemplate = new RestTemplate();
        this.objectMapper = objectMapper;
    }

    public BookIntegration importAndSaveBookByIsbn(String isbn) {
        // Verifica se o livro já está cadastrado na base local
        var existingBook = bookIntegrationRepository.findByIsbn(isbn);
        if (existingBook.isPresent()) {
            return existingBook.get();
        }

        // URL da Open Library para busca por ISBN
        String url = "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data";

        try {
            // Faz a requisição HTTP GET
            String jsonResponse = restTemplate.getForObject(url, String.class);

            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            String key = "ISBN:" + isbn;

            JsonNode bookNode = rootNode.get(key);
            if (bookNode == null || bookNode.isMissingNode()) {
                throw new RuntimeException("Livro não encontrado na Open Library para o ISBN: " + isbn);
            }

            // Extração de dados
            String title = bookNode.has("title") ? bookNode.get("title").asText() : "Título desconhecido";

            // Autores
            List<String> authorsList = new ArrayList<>();
            if (bookNode.has("authors") && bookNode.get("authors").isArray()) {
                for (JsonNode authorNode : bookNode.get("authors")) {
                    if (authorNode.has("name")) {
                        authorsList.add(authorNode.get("name").asText());
                    }
                }
            }
            String authorsStr = authorsList.isEmpty() ? "Autor desconhecido" : String.join(", ", authorsList);

            // Editora
            String publisher = null;
            if (bookNode.has("publishers") && bookNode.get("publishers").isArray() && !bookNode.get("publishers").isEmpty()) {
                JsonNode pubNode = bookNode.get("publishers").get(0);
                if (pubNode.has("name")) {
                    publisher = pubNode.get("name").asText();
                }
            }

            // Data de publicação e número de páginas
            String publishedDate = bookNode.has("publish_date") ? bookNode.get("publish_date").asText() : null;
            Integer pageCount = bookNode.has("number_of_pages") ? bookNode.get("number_of_pages").asInt() : null;

            // Capa (url de imagem grande ou média)
            String coverUrl = null;
            if (bookNode.has("cover")) {
                JsonNode coverNode = bookNode.get("cover");
                if (coverNode.has("large")) {
                    coverUrl = coverNode.get("large").asText().replace("http://", "https://");
                } else if (coverNode.has("medium")) {
                    coverUrl = coverNode.get("medium").asText().replace("http://", "https://");
                }
            }

            // Constrói a entidade
            BookIntegration book = BookIntegration.builder()
                    .title(title)
                    .authors(authorsStr)
                    .publisher(publisher)
                    .publishedDate(publishedDate)
                    .description("Importado via Open Library") // Descrição padrão caso venha vazia
                    .pageCount(pageCount)
                    .isbn(isbn)
                    .coverUrl(coverUrl)
                    .build();

            // Salva no PostgreSQL
            return bookIntegrationRepository.save(book);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao importar o livro da Open Library: " + e.getMessage(), e);
        }
    }
}