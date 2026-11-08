package br.edu.christus.backend.service;

import br.edu.christus.backend.domain.BookIntegration;
import br.edu.christus.backend.domain.BookIntegrationDTO;
import br.edu.christus.backend.repository.BookIntegrationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookIntegrationService {

    private final BookIntegrationRepository repository;
    private final RestTemplate restTemplate;

    public BookIntegrationService(BookIntegrationRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public BookIntegrationDTO importAndSaveBookByIsbn(String isbn) {
        if (isbn == null || (isbn.length() != 10 && isbn.length() != 13)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN inválido. Deve conter 10 ou 13 dígitos.");
        }

        String url = "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data";

        try {
            Map response = restTemplate.getForObject(url, Map.class);
            String key = "ISBN:" + isbn;

            if (response == null || !response.containsKey(key)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado na Open Library.");
            }

            Map<String, Object> bookData = (Map<String, Object>) response.get(key);

            String title = (String) bookData.get("title");
            String publishedDate = (String) bookData.get("publish_date");
            Integer pageCount = (Integer) bookData.get("number_of_pages");

            String authors = "Desconhecido";
            if (bookData.get("authors") instanceof List<?> list) {
                authors = list.stream()
                        .filter(a -> a instanceof Map)
                        .map(a -> (String) ((Map<?, ?>) a).get("name"))
                        .filter(n -> n != null)
                        .collect(Collectors.joining(", "));
            }

            String publisher = null;
            if (bookData.get("publishers") instanceof List<?> list && !list.isEmpty() && list.get(0) instanceof Map) {
                publisher = (String) ((Map<?, ?>) list.get(0)).get("name");
            }

            String coverUrl = "https://covers.openlibrary.org/b/isbn/" + isbn + "-M.jpg";
            if (bookData.get("cover") instanceof Map<?, ?> coverMap) {
                if (coverMap.get("large") != null) coverUrl = (String) coverMap.get("large");
                else if (coverMap.get("medium") != null) coverUrl = (String) coverMap.get("medium");
            }

            BookIntegration book = BookIntegration.builder()
                    .title(title != null ? title : "Sem Título")
                    .authors(authors)
                    .publisher(publisher)
                    .publishedDate(publishedDate)
                    .description("Importado via Open Library")
                    .pageCount(pageCount)
                    .isbn(isbn)
                    .coverUrl(coverUrl)
                    .build();

            BookIntegration saved = repository.save(book);

            return new BookIntegrationDTO(
                    saved.getTitle(),
                    saved.getAuthors(),
                    saved.getPublisher(),
                    saved.getPublishedDate(),
                    saved.getDescription(),
                    saved.getPageCount(),
                    saved.getIsbn(),
                    saved.getCoverUrl()
            );

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao importar: " + e.getMessage());
        }
    }
}