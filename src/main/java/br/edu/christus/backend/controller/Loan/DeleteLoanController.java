package br.edu.christus.backend.controller.Loan;

import br.edu.christus.backend.domain.Loan.LoanDTO;
import br.edu.christus.backend.service.Loan.DeleteLoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
@Tag(name = "Empréstimo")
@RequiredArgsConstructor
public class DeleteLoanController {
    private final DeleteLoanService service;

    @Operation(summary = "Excluir empréstimo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
