package br.edu.christus.backend.controller.Book;

import br.edu.christus.backend.domain.Book.BookDTO;
import br.edu.christus.backend.service.Book.UpdateBookService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Livro")
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class UpdateBookController {

    private final UpdateBookService updateBookService;

    @Operation(summary = "Editar livro")
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody BookDTO requestDTO
    ) {
        BookDTO livroAtualizado = updateBookService.atualizarLivro(id, requestDTO);
        return ResponseEntity.ok(livroAtualizado);
    }
}