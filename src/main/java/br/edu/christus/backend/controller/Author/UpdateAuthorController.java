package br.edu.christus.backend.controller.Author;

import br.edu.christus.backend.domain.Author.AuthorDTO;
import br.edu.christus.backend.service.Author.UpdateAuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
@Tag(name = "Autor")
@RequiredArgsConstructor
public class UpdateAuthorController {
    private final UpdateAuthorService service;

    @Operation(summary = "Editar autor")
    @PutMapping("/{id}")
    public AuthorDTO atualizar(@PathVariable Long id, @Valid @RequestBody AuthorDTO dto) {
        return service.atualizar(id, dto);
    }
}
