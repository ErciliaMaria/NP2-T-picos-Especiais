package br.edu.christus.backend.controller.Student;

import br.edu.christus.backend.domain.Student.StudentDTO;
import br.edu.christus.backend.service.Student.GetStudentService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Aluno")
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class GetStudentController {
    private final GetStudentService getStudentsService;

    @Operation(summary = "Consultar aluno")
    @GetMapping
    public ResponseEntity<List<StudentDTO>> listarTodos() {
        List<StudentDTO> estudantes = getStudentsService.listarTodos();
        return ResponseEntity.ok(estudantes);
    }
    @Operation(summary = "Consultar aluno")
    @GetMapping("/{id}")
    public StudentDTO buscarPorId(@org.springframework.web.bind.annotation.PathVariable Long id) {
        return getStudentsService.buscarPorId(id);
    }
}
