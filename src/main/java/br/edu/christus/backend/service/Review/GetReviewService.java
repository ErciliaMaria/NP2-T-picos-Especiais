package br.edu.christus.backend.service.Review;

import br.edu.christus.backend.domain.Review.Review;
import br.edu.christus.backend.domain.Review.ReviewDTO;
import br.edu.christus.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetReviewService {
    private final ReviewRepository repository;

    @Transactional(readOnly = true)
    public List<ReviewDTO> listarTodos() {
        return repository.findAll().stream().map(ReviewMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public ReviewDTO buscarPorId(Long id) {
        return ReviewMapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avaliação não encontrado(a)")));
    }
}
