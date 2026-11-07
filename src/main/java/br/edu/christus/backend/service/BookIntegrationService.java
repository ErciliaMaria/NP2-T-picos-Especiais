package br.edu.christus.backend.service;

import br.edu.christus.backend.domain.Book.Book;
import br.edu.christus.backend.domain.BookIntegration;
import br.edu.christus.backend.domain.BookIntegrationDTO;
import br.edu.christus.backend.repository.BookIntegrationRepository;
import br.edu.christus.backend.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.Locale;

@Service
public class BookIntegrationService {
    private final BookIntegrationRepository repository;
    private final BookRepository bookRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BookIntegrationService(BookIntegrationRepository repository, BookRepository bookRepository,
                                  RestTemplate restTemplate) {
        this.repository = repository;
        this.bookRepository = bookRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public BookIntegrationDTO importAndSaveBookByIsbn(String input) {
        // Aceita ISBN com espaços/hífens, guardando apenas o formato normalizado.
        String isbn = input == null ? "" : input.replaceAll("[\\s-]", "").toUpperCase(Locale.ROOT);
        if (!isbn.matches("[0-9]{13}|[0-9]{9}[0-9X]")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ISBN deve ter 13 dígitos ou 10 caracteres, com X permitido apenas no final.");
        }
        if (repository.findByIsbn(isbn).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este ISBN já foi importado.");
        }

        JsonNode dados = consultarOpenLibrary(isbn);
        String titulo = dados.path("title").asText("");
        if (titulo.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Open Library retornou um livro sem título.");
        }
        String autores = obterAutores(dados);

        // Usa a entidade Book da Ercília para disponibilizar o livro no CRUD do acervo.
        Book livro = Book.builder()
                .titulo(limitar(titulo, 150))
                .autor(limitar(autores, 100))
                .quantidadeExemplares(1)
                .build();
        livro = bookRepository.save(livro);

        // Mantém os metadados da integração separados, ligados ao livro do acervo.
        BookIntegration integracao = BookIntegration.builder()
                .title(limitar(titulo, 255))
                .authors(limitar(autores, 500))
                .isbn(isbn)
                .livro(livro)
                .build();
        repository.save(integracao);

        BookIntegrationDTO resposta = new BookIntegrationDTO();
        resposta.setTitle(integracao.getTitle());
        resposta.setAuthors(integracao.getAuthors());
        resposta.setIsbn(isbn);
        resposta.setLivroId(livro.getId());
        return resposta;
    }

    private JsonNode consultarOpenLibrary(String isbn) {
        JsonNode resposta;
        try {
            String json = restTemplate.getForObject(
                    "https://openlibrary.org/api/books?bibkeys=ISBN:{isbn}&format=json&jscmd=data",
                    String.class, isbn);
            resposta = json == null ? null : objectMapper.readTree(json);
        } catch (RestClientException | JsonProcessingException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "Não foi possível consultar a Open Library. Tente novamente.");
        }
        if (resposta == null || !resposta.isObject()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Resposta inválida da Open Library.");
        }
        // A API retorna um objeto cuja chave é, por exemplo, "ISBN:9780140328721".
        JsonNode livro = resposta.get("ISBN:" + isbn);
        if (livro == null || livro.isNull()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado na Open Library.");
        }
        return livro;
    }

    private String obterAutores(JsonNode dados) {
        var nomes = new ArrayList<String>();
        for (JsonNode autor : dados.path("authors")) {
            String nome = autor.path("name").asText("");
            if (!nome.isBlank()) nomes.add(nome);
        }
        return nomes.isEmpty() ? "Autor não informado" : String.join(", ", nomes);
    }

    // Os campos vindos da API externa precisam caber nas colunas já existentes.
    private String limitar(String texto, int tamanho) {
        return texto.substring(0, Math.min(texto.length(), tamanho));
    }
}
