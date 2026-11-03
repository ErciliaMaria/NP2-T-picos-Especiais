package br.edu.christus.backend.controller.Student;

import br.edu.christus.backend.domain.MessageResponse;
import br.edu.christus.backend.service.Student.DeleteStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class DeleteStudentController {

    private final DeleteStudentService deleteStudentService;

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deletar(@PathVariable Long id) {
        deleteStudentService.deletarAluno(id);
        return ResponseEntity.ok(new MessageResponse("Estudante excluído com sucesso!"));    }
}