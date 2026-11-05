package br.edu.christus.backend.controller.Book;

import br.edu.christus.backend.domain.Book.BookDTO;
import br.edu.christus.backend.service.Book.CreateBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class CreateBookController {
    private final CreateBookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO> criar(@RequestBody BookDTO requestDTO) {

        BookDTO novoLivro = bookService.criarLivro(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoLivro);
    }
}
