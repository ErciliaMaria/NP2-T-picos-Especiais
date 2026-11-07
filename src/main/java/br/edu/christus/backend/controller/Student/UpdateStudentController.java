package br.edu.christus.backend.controller.Student;

import br.edu.christus.backend.domain.Student.StudentDTO;
import br.edu.christus.backend.service.Student.UpdateStudentService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Aluno")
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class UpdateStudentController {
    private final UpdateStudentService updateStudentService;

    @Operation(summary = "Editar aluno")
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody StudentDTO requestDTO
    ) {
        StudentDTO alunoAtualizado = updateStudentService.atualizarAluno(id, requestDTO);
        return ResponseEntity.ok(alunoAtualizado);
    }
}
