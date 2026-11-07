package br.edu.christus.backend.service.Student;

import br.edu.christus.backend.domain.Student.StudentDTO;
import br.edu.christus.backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetStudentService {
    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public List<StudentDTO> listarTodos() {
        return studentRepository.findAll().stream()
                .map(student -> StudentDTO.builder()
                        .id(student.getId())
                        .nomeCompleto(student.getNomeCompleto())
                        .matricula(student.getMatricula())
                        .turma(student.getTurma())
                        .dataNascimento(student.getDataNascimento())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StudentDTO buscarPorId(Long id) {
        var student = studentRepository.findById(id).orElseThrow(() ->
                new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Aluno não encontrado"));
        return StudentDTO.builder()
                        .id(student.getId())
                        .nomeCompleto(student.getNomeCompleto())
                        .matricula(student.getMatricula())
                        .turma(student.getTurma())
                        .dataNascimento(student.getDataNascimento())
                        .build();
    }
}