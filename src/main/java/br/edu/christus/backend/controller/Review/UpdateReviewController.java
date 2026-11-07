package br.edu.christus.backend.controller.Review;

import br.edu.christus.backend.domain.Review.ReviewDTO;
import br.edu.christus.backend.service.Review.UpdateReviewService;
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
public class UpdateReviewController {
    private final UpdateReviewService service;

    @Operation(summary = "Editar avaliação")
    @PutMapping("/{id}")
    public ReviewDTO atualizar(@PathVariable Long id, @Valid @RequestBody ReviewDTO dto) {
        return service.atualizar(id, dto);
    }
}
