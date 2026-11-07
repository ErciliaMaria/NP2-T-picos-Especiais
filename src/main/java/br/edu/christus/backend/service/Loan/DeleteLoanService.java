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
public class DeleteLoanService {
    private final LoanRepository repository;

    @Transactional
    public void excluir(Long id) {
        Loan entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empréstimo não encontrado(a)"));
        repository.delete(entity);
        repository.flush();
    }
}
