package br.edu.christus.backend.controller.Loan;

import br.edu.christus.backend.domain.Loan.LoanDTO;
import br.edu.christus.backend.service.Loan.GetLoanService;
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
public class GetLoanController {
    private final GetLoanService service;

    @Operation(summary = "Listar emprestimos")
    @GetMapping
    public List<LoanDTO> listarTodos() {
        return service.listarTodos();
    }

    @Operation(summary = "Consultar empréstimo por ID")
    @GetMapping("/{id}")
    public LoanDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
}
