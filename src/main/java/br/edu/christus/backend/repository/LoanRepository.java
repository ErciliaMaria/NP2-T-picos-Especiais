package br.edu.christus.backend.repository;

import br.edu.christus.backend.domain.Loan.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
