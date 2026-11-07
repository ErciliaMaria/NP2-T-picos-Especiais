package br.edu.christus.backend.domain.Loan;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {
    private Long id;

    @NotNull(message = "Data do empréstimo é obrigatória")
    @PastOrPresent(message = "Data do empréstimo não pode ser futura")
    private LocalDate dataEmprestimo;

    @NotNull(message = "Data prevista de devolução é obrigatória")
    private LocalDate dataPrevistaDevolucao;

    @PastOrPresent(message = "Data de devolução não pode ser futura")
    private LocalDate dataDevolucao;

    @Size(max = 1000, message = "Observações devem ter até 1000 caracteres")
    private String observacoes;

    @NotNull(message = "Aluno é obrigatório")
    @Positive(message = "ID do aluno deve ser positivo")
    private Long alunoId;

    @NotNull(message = "Livro é obrigatório")
    @Positive(message = "ID do livro deve ser positivo")
    private Long livroId;
}
