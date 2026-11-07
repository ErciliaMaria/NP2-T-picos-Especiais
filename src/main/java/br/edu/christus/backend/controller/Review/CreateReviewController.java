package br.edu.christus.backend.controller.Review;

import br.edu.christus.backend.domain.Review.ReviewDTO;
import br.edu.christus.backend.service.Review.CreateReviewService;
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
public class CreateReviewController {
    private final CreateReviewService service;

    @Operation(summary = "Cadastrar avaliação")
    @PostMapping
    public ResponseEntity<ReviewDTO> criar(@Valid @RequestBody ReviewDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }
}
