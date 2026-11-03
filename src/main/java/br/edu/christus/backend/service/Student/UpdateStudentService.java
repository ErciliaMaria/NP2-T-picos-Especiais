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
        // 1. Busca o aluno existente pelo ID ou retorna 404 Not Found se não existir
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Estudante não encontrado com o ID: " + id
                ));

        // 2. Verifica se a matrícula foi alterada e se já pertence a outro aluno
        if (dto.getMatricula() != null && !dto.getMatricula().equals(student.getMatricula())) {
            if (studentRepository.existsByMatricula(dto.getMatricula())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Já existe outro aluno cadastrado com esta matrícula."
                );
            }
        }

        // 3. Atualiza os dados da entidade com os valores recebidos do DTO
        student.setNomeCompleto(dto.getNomeCompleto());
        student.setMatricula(dto.getMatricula());
        student.setTurma(dto.getTurma());
        student.setDataNascimento(dto.getDataNascimento());

        // 4. Salva as alterações no banco de dados
        Student studentSalvo = studentRepository.save(student);

        // 5. Retorna o DTO atualizado
        return StudentDTO.builder()
                .id(studentSalvo.getId())
                .nomeCompleto(studentSalvo.getNomeCompleto())
                .matricula(studentSalvo.getMatricula())
                .turma(studentSalvo.getTurma())
                .dataNascimento(studentSalvo.getDataNascimento())
                .build();
    }
}