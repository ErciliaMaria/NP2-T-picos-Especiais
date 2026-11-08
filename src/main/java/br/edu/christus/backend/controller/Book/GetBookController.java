package br.edu.christus.backend.controller.Book;

import br.edu.christus.backend.domain.Book.BookDTO;
import br.edu.christus.backend.service.Book.GetBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class GetBookController {

    private final GetBookService getBookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> listarTodos() {
        List<BookDTO> livros = getBookService.listarTodos();
        return ResponseEntity.ok(livros);
    }
}