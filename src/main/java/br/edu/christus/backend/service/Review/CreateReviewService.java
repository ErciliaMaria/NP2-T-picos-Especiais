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
public class CreateReviewService {
    private final ReviewRepository repository;
    private final StudentRepository studentRepository;
    private final BookRepository bookRepository;

    @Transactional
    public ReviewDTO criar(ReviewDTO dto) {
        Review entity = new Review();
        entity.setTitulo(dto.getTitulo());
        entity.setNota(dto.getNota());
        entity.setComentario(dto.getComentario());
        entity.setDataAvaliacao(dto.getDataAvaliacao());
        entity.setAluno(studentRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado")));
        entity.setLivro(bookRepository.findById(dto.getLivroId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado")));
        return ReviewMapper.toDTO(repository.save(entity));
    }
}
