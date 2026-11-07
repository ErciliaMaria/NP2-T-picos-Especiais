package br.edu.christus.backend.controller.Author;

import br.edu.christus.backend.domain.Author.AuthorDTO;
import br.edu.christus.backend.service.Author.CreateAuthorService;
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
public class CreateAuthorController {
    private final CreateAuthorService service;

    @Operation(summary = "Cadastrar autor")
    @PostMapping
    public ResponseEntity<AuthorDTO> criar(@Valid @RequestBody AuthorDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }
}
