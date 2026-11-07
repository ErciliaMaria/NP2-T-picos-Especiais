package br.edu.christus.backend.controller.Book;

import br.edu.christus.backend.domain.Book.BookDTO;
import br.edu.christus.backend.service.Book.GetBookService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Tag(name = "Livro")
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class GetBookController {

    private final GetBookService getBookService;

    @Operation(summary = "Consultar livro")
    @GetMapping
    public ResponseEntity<List<BookDTO>> listarTodos() {
        List<BookDTO> livros = getBookService.listarTodos();
        return ResponseEntity.ok(livros);
    }
    @Operation(summary = "Consultar livro")
    @GetMapping("/{id}")
    public BookDTO buscarPorId(@PathVariable Long id) {
        return getBookService.buscarPorId(id);
    }
}
