package br.edu.christus.backend.service.Review;

import br.edu.christus.backend.domain.Review.Review;
import br.edu.christus.backend.domain.Review.ReviewDTO;

// Conversão manual entre entidade persistida e resposta da API.
final class ReviewMapper {
    private ReviewMapper() {}

    static ReviewDTO toDTO(Review entity) {
        return ReviewDTO.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .nota(entity.getNota())
                .comentario(entity.getComentario())
                .dataAvaliacao(entity.getDataAvaliacao())
                .alunoId(entity.getAluno().getId())
                .livroId(entity.getLivro().getId())
                .build();
    }
}
