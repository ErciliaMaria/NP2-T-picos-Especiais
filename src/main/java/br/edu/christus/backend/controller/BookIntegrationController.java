package br.edu.christus.backend.controller;

import br.edu.christus.backend.domain.BookIntegration;
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

    @PostMapping("/import/{isbn}")
    public ResponseEntity<BookIntegration> importBook(@PathVariable String isbn) {
        BookIntegration savedBook = bookIntegrationService.importAndSaveBookByIsbn(isbn);
        return ResponseEntity.ok(savedBook);
    }
}