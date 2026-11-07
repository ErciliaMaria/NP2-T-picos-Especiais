package br.edu.christus.backend.controller.Loan;

import br.edu.christus.backend.domain.Loan.LoanDTO;
import br.edu.christus.backend.service.Loan.CreateLoanService;
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
public class CreateLoanController {
    private final CreateLoanService service;

    @Operation(summary = "Cadastrar empréstimo")
    @PostMapping
    public ResponseEntity<LoanDTO> criar(@Valid @RequestBody LoanDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }
}
