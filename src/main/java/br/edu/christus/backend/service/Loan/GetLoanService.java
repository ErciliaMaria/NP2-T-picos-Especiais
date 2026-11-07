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
public class GetLoanService {
    private final LoanRepository repository;

    @Transactional(readOnly = true)
    public List<LoanDTO> listarTodos() {
        return repository.findAll().stream().map(LoanMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public LoanDTO buscarPorId(Long id) {
        return LoanMapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empréstimo não encontrado(a)")));
    }
}
