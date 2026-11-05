package br.edu.christus.backend.controller.Book;

import br.edu.christus.backend.domain.Book.BookDTO;
import br.edu.christus.backend.service.Book.UpdateBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class UpdateBookController {

    private final UpdateBookService updateBookService;

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> atualizar(
            @PathVariable Long id,
            @RequestBody BookDTO requestDTO
    ) {
        BookDTO livroAtualizado = updateBookService.atualizarLivro(id, requestDTO);
        return ResponseEntity.ok(livroAtualizado);
    }
}