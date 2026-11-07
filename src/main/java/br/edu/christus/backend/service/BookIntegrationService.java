package br.edu.christus.backend.service;

import br.edu.christus.backend.domain.Book.Book;
import br.edu.christus.backend.domain.BookIntegration;
import br.edu.christus.backend.domain.BookIntegrationDTO;
import br.edu.christus.backend.repository.BookIntegrationRepository;
import br.edu.christus.backend.repository.BookRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;

@Service
public class BookIntegrationService {
    private final BookIntegrationRepository repository;
    private final BookRepository bookRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public BookIntegrationService(BookIntegrationRepository repository, BookRepository bookRepository, RestTemplate restTemplate) {
        this.repository = repository;
        this.bookRepository = bookRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public BookIntegrationDTO importAndSaveBookByIsbn(String input) {
        String isbn = input == null ? "" : input.replaceAll("[\\s-]", "").toUpperCase(java.util.Locale.ROOT);
        if (!validIsbn(isbn)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN inválido: confira os 10 ou 13 caracteres e o dígito verificador.");
        }
        if (repository.findByIsbn(isbn).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este ISBN já foi importado.");
        }
        JsonNode data;
        try {
            String response = restTemplate.getForObject(
                    "https://openlibrary.org/api/books?bibkeys=ISBN:{isbn}&format=json&jscmd=data", String.class, isbn);
            if (response == null) throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Resposta vazia da Open Library.");
            data = mapper.readTree(response);
        } catch (RestClientException | com.fasterxml.jackson.core.JsonProcessingException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Não foi possível consultar a Open Library. Tente novamente.");
        }
        JsonNode item = data == null ? null : data.get("ISBN:" + isbn);
        if (item == null || item.isNull()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado na Open Library.");
        }
        String title = item.path("title").asText("");
        if (title.isBlank()) throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Open Library retornou livro sem título.");
        var names = new ArrayList<String>();
        for (JsonNode author : item.path("authors")) {
            String name = author.path("name").asText("");
            if (!name.isBlank()) names.add(name);
        }
        String authors = names.isEmpty() ? "Autor não informado" : String.join(", ", names);
        String publisher = item.path("publishers").path(0).path("name").asText(null);
        String publishedDate = item.path("publish_date").asText(null);
        Integer year = null;
        if (publishedDate != null) {
            var match = java.util.regex.Pattern.compile("\\b(\\d{4})\\b").matcher(publishedDate);
            if (match.find()) year = Integer.valueOf(match.group(1));
        }
        // O livro importado entra no mesmo acervo usado pelos empréstimos e avaliações.
        Book book = bookRepository.save(Book.builder().titulo(limit(title, 150)).autor(limit(authors, 100))
                .editora(limit(publisher, 100)).anoPublicacao(year).quantidadeExemplares(1).build());
        BookIntegration integration = BookIntegration.builder()
                .title(limit(title, 255)).authors(limit(authors, 500)).publisher(limit(publisher, 255))
                .publishedDate(limit(publishedDate, 255)).isbn(isbn).livro(book)
                .coverUrl(limit(item.path("cover").path("medium").asText(null), 1000))
                .pageCount(item.path("number_of_pages").isInt() ? item.get("number_of_pages").asInt() : null)
                .build();
        repository.save(integration);
        BookIntegrationDTO dto = new BookIntegrationDTO();
        dto.setTitle(integration.getTitle());
        dto.setAuthors(integration.getAuthors());
        dto.setIsbn(isbn);
        dto.setLivroId(book.getId());
        return dto;
    }

    private static String limit(String value, int size) {
        return value == null ? null : value.substring(0, Math.min(value.length(), size));
    }

    private static boolean validIsbn(String isbn) {
        if (isbn.matches("[0-9]{13}")) {
            int sum = 0;
            for (int i = 0; i < 13; i++) sum += (isbn.charAt(i) - '0') * (i % 2 == 0 ? 1 : 3);
            return sum % 10 == 0;
        }
        if (isbn.matches("[0-9]{9}[0-9X]")) {
            int sum = 0;
            for (int i = 0; i < 10; i++) sum += (isbn.charAt(i) == 'X' ? 10 : isbn.charAt(i) - '0') * (10 - i);
            return sum % 11 == 0;
        }
        return false;
    }
}
