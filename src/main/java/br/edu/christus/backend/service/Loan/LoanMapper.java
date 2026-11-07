package br.edu.christus.backend.service.Loan;

import br.edu.christus.backend.domain.Loan.Loan;
import br.edu.christus.backend.domain.Loan.LoanDTO;

// Conversão manual entre entidade persistida e resposta da API.
final class LoanMapper {
    private LoanMapper() {}

    static LoanDTO toDTO(Loan entity) {
        return LoanDTO.builder()
                .id(entity.getId())
                .dataEmprestimo(entity.getDataEmprestimo())
                .dataPrevistaDevolucao(entity.getDataPrevistaDevolucao())
                .dataDevolucao(entity.getDataDevolucao())
                .observacoes(entity.getObservacoes())
                .alunoId(entity.getAluno().getId())
                .livroId(entity.getLivro().getId())
                .build();
    }
}
