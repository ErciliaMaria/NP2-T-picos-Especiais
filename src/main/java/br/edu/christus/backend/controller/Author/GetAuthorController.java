package br.edu.christus.backend.controller.Author;

import br.edu.christus.backend.domain.Author.AuthorDTO;
import br.edu.christus.backend.service.Author.GetAuthorService;
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
public class GetAuthorController {
    private final GetAuthorService service;

    @Operation(summary = "Listar autores")
    @GetMapping
    public List<AuthorDTO> listarTodos() {
        return service.listarTodos();
    }

    @Operation(summary = "Consultar autor por ID")
    @GetMapping("/{id}")
    public AuthorDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
}
