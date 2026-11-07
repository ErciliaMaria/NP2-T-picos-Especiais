package br.edu.christus.backend.controller.Student;

import br.edu.christus.backend.domain.Student.StudentDTO;
import br.edu.christus.backend.service.Student.CreateStudentService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Aluno")
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class CreateStudentController {
    private final CreateStudentService studentService;

    @Operation(summary = "Cadastrar aluno")
    @PostMapping
    public ResponseEntity<StudentDTO> criar(@Valid @RequestBody StudentDTO requestDTO) {

        StudentDTO novoAluno = studentService.criarAluno(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAluno);
    }
}
