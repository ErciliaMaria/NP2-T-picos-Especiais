package br.edu.christus.backend.controller.Review;

import br.edu.christus.backend.domain.Review.ReviewDTO;
import br.edu.christus.backend.service.Review.GetReviewService;
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
public class GetReviewController {
    private final GetReviewService service;

    @Operation(summary = "Listar avaliacoes")
    @GetMapping
    public List<ReviewDTO> listarTodos() {
        return service.listarTodos();
    }

    @Operation(summary = "Consultar avaliação por ID")
    @GetMapping("/{id}")
    public ReviewDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
}
