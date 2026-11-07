package br.edu.christus.backend;

import br.edu.christus.backend.repository.BookRepository;
import br.edu.christus.backend.repository.BookIntegrationRepository;
import br.edu.christus.backend.service.BookIntegrationService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@SpringBootTest(properties = {"spring.datasource.url=jdbc:h2:mem:integrationtests", "spring.jpa.hibernate.ddl-auto=create-drop"})
@ActiveProfiles("h2")
class BookIntegrationTests {
    @Autowired RestTemplate restTemplate;
    @Autowired BookIntegrationService service;
    @Autowired BookRepository books;
    @Autowired BookIntegrationRepository integrations;
    MockRestServiceServer remote;

    @BeforeEach
    void setup() { remote = MockRestServiceServer.bindTo(restTemplate).build(); }

    @AfterEach
    void verify() { remote.verify(); remote.reset(); }

    String url(String isbn) {
        return "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data";
    }

    @Test
    void importsIntoCatalogAndPreventsDuplicates() {
        remote.expect(requestTo(url("9780140328721"))).andRespond(withSuccess("""
                {"ISBN:9780140328721":{"title":"Matilda","authors":[{"name":"Roald Dahl"}],
                "publishers":[{"name":"Puffin"}],"publish_date":"1988","number_of_pages":240}}
                """, MediaType.APPLICATION_JSON));
        var dto = service.importAndSaveBookByIsbn("978-0140328721");
        var book = books.findById(dto.getLivroId()).orElseThrow();
        assertEquals("Matilda", book.getTitulo());
        assertEquals("Roald Dahl", book.getAutor());
        assertEquals(1988, book.getAnoPublicacao());
        assertEquals(409, assertThrows(ResponseStatusException.class,
                () -> service.importAndSaveBookByIsbn("9780140328721")).getStatusCode().value());
        books.deleteById(book.getId());
        assertTrue(integrations.findByIsbn("9780140328721").isEmpty());
    }

    @Test
    void invalidChecksumDoesNotCallRemote() {
        assertEquals(400, assertThrows(ResponseStatusException.class,
                () -> service.importAndSaveBookByIsbn("9780140328722")).getStatusCode().value());
    }

    @Test
    void missingBookReturns404() {
        remote.expect(requestTo(url("0451526538"))).andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));
        assertEquals(404, assertThrows(ResponseStatusException.class,
                () -> service.importAndSaveBookByIsbn("0451526538")).getStatusCode().value());
    }

    @Test
    void remoteFailureReturns502() {
        remote.expect(requestTo(url("0451526538"))).andRespond(withStatus(HttpStatus.SERVICE_UNAVAILABLE));
        assertEquals(502, assertThrows(ResponseStatusException.class,
                () -> service.importAndSaveBookByIsbn("0451526538")).getStatusCode().value());
    }
}
