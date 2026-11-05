package br.edu.christus.backend.controller.Book;

import br.edu.christus.backend.domain.MessageResponse;
import br.edu.christus.backend.service.Book.DeleteBookService;
import br.edu.christus.backend.service.Student.DeleteStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class DeleteBookController {
    private final DeleteBookService deleteBookService;

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deletar(@PathVariable Long id) {
        deleteBookService.deletarLivro(id);
        return ResponseEntity.ok(new MessageResponse("Livro excluído com sucesso!"));
    }
}
