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
public class UpdateStudentService {
    private final StudentRepository studentRepository;

    @Transactional
    public StudentDTO atualizarAluno(Long id, StudentDTO dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Estudante não encontrado"
                ));

        student.setNomeCompleto(dto.getNomeCompleto());
        student.setMatricula(dto.getMatricula());
        student.setTurma(dto.getTurma());
        student.setDataNascimento(dto.getDataNascimento());

        Student studentSalvo = studentRepository.save(student);

        return StudentDTO.builder()
                .id(studentSalvo.getId())
                .nomeCompleto(studentSalvo.getNomeCompleto())
                .matricula(studentSalvo.getMatricula())
                .turma(studentSalvo.getTurma())
                .dataNascimento(studentSalvo.getDataNascimento())
                .build();
    }
}