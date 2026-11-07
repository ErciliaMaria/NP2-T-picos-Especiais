package br.edu.christus.backend.service.Student;

import br.edu.christus.backend.domain.Student.Student;
import br.edu.christus.backend.domain.Student.StudentDTO;
import br.edu.christus.backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CreateStudentService {
    private final StudentRepository studentRepository;

    @Transactional
    public StudentDTO criarAluno(StudentDTO dto) {
        if (studentRepository.existsByMatricula(dto.getMatricula())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe um aluno cadastrado com esta matrícula."
            );
        }

        Student student = Student.builder()
                .nomeCompleto(dto.getNomeCompleto())
                .matricula(dto.getMatricula())
                .turma(dto.getTurma())
                .dataNascimento(dto.getDataNascimento())
                .build();

        Student studentSalvo = studentRepository.save(student);

        return StudentDTO.builder()
                .id(studentSalvo.getId())
                .turma(studentSalvo.getTurma())
                .dataNascimento(studentSalvo.getDataNascimento())
                .nomeCompleto(studentSalvo.getNomeCompleto())
                .matricula(studentSalvo.getMatricula())
                .build();
    }
}