package br.edu.christus.backend.controller;

import br.edu.christus.backend.domain.BookIntegrationDTO;
import br.edu.christus.backend.service.BookIntegrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookIntegrationController {

    private final BookIntegrationService bookIntegrationService;

    public BookIntegrationController(BookIntegrationService bookIntegrationService) {
        this.bookIntegrationService = bookIntegrationService;
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Importar livro da Open Library pelo ISBN", tags = "Integração")
    @PostMapping("/import/{isbn}")
    public ResponseEntity<BookIntegrationDTO> importBook(@PathVariable String isbn) {
        BookIntegrationDTO savedBookDto = bookIntegrationService.importAndSaveBookByIsbn(isbn);
        return ResponseEntity.ok(savedBookDto);
    }
}