package br.edu.christus.backend.controller.Student;

import br.edu.christus.backend.domain.Student.StudentDTO;
import br.edu.christus.backend.service.Student.UpdateStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class UpdateStudentController {
    private final UpdateStudentService updateStudentService;

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> atualizar(
            @PathVariable Long id,
            @RequestBody StudentDTO requestDTO
    ) {
        StudentDTO alunoAtualizado = updateStudentService.atualizarAluno(id, requestDTO);
        return ResponseEntity.ok(alunoAtualizado);
    }
}
