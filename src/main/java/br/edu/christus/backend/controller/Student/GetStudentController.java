package br.edu.christus.backend.controller.Student;

import br.edu.christus.backend.domain.Student.StudentDTO;
import br.edu.christus.backend.service.Student.GetStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class GetStudentController {
    private final GetStudentService getStudentsService;

    @GetMapping
    public ResponseEntity<List<StudentDTO>> listarTodos() {
        List<StudentDTO> estudantes = getStudentsService.listarTodos();
        return ResponseEntity.ok(estudantes);
    }
}