package br.edu.christus.backend.controller.Loan;

import br.edu.christus.backend.domain.Loan.LoanDTO;
import br.edu.christus.backend.service.Loan.UpdateLoanService;
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
public class UpdateLoanController {
    private final UpdateLoanService service;

    @Operation(summary = "Editar empréstimo")
    @PutMapping("/{id}")
    public LoanDTO atualizar(@PathVariable Long id, @Valid @RequestBody LoanDTO dto) {
        return service.atualizar(id, dto);
    }
}
