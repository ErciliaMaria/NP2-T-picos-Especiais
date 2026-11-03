package br.edu.christus.backend.service.Student;

import br.edu.christus.backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class DeleteStudentService {

    private final StudentRepository studentRepository;

    @Transactional
    public String deletarAluno(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Estudante não encontrado."
            );
        }

        studentRepository.deleteById(id);
        return "Estudante excluído com sucesso!";
    }
}