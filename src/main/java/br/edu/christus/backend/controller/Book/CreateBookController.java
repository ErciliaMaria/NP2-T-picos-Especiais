package br.edu.christus.backend.controller.Book;

import br.edu.christus.backend.domain.Book.BookDTO;
import br.edu.christus.backend.service.Book.CreateBookService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Livro")
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class CreateBookController {
    private final CreateBookService bookService;

    @Operation(summary = "Cadastrar livro")
    @PostMapping
    public ResponseEntity<BookDTO> criar(@Valid @RequestBody BookDTO requestDTO) {

        BookDTO novoLivro = bookService.criarLivro(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoLivro);
    }
}
