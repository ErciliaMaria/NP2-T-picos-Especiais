package br.edu.christus.backend.controller.Review;

import br.edu.christus.backend.domain.Review.ReviewDTO;
import br.edu.christus.backend.service.Review.DeleteReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Avaliação")
@RequiredArgsConstructor
public class DeleteReviewController {
    private final DeleteReviewService service;

    @Operation(summary = "Excluir avaliação")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
