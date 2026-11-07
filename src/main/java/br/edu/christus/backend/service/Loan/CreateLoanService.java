package br.edu.christus.backend.service.Loan;

import br.edu.christus.backend.domain.Loan.Loan;
import br.edu.christus.backend.domain.Loan.LoanDTO;
import br.edu.christus.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateLoanService {
    private final LoanRepository repository;
    private final StudentRepository studentRepository;
    private final BookRepository bookRepository;

    @Transactional
    public LoanDTO criar(LoanDTO dto) {
        Loan entity = new Loan();
        if (dto.getDataPrevistaDevolucao().isBefore(dto.getDataEmprestimo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Previsão de devolução não pode ser anterior ao empréstimo");
        }
        if (dto.getDataDevolucao() != null && dto.getDataDevolucao().isBefore(dto.getDataEmprestimo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Devolução não pode ser anterior ao empréstimo");
        }
        entity.setDataEmprestimo(dto.getDataEmprestimo());
        entity.setDataPrevistaDevolucao(dto.getDataPrevistaDevolucao());
        entity.setDataDevolucao(dto.getDataDevolucao());
        entity.setObservacoes(dto.getObservacoes());
        entity.setAluno(studentRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado")));
        entity.setLivro(bookRepository.findById(dto.getLivroId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado")));
        return LoanMapper.toDTO(repository.save(entity));
    }
}
