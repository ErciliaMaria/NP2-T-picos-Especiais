package br.edu.christus.backend.domain.Author;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150, message = "Nome deve ter até 150 caracteres")
    private String nome;

    @NotBlank(message = "Nacionalidade é obrigatória")
    @Size(max = 100, message = "Nacionalidade deve ter até 100 caracteres")
    private String nacionalidade;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve estar no passado")
    private LocalDate dataNascimento;

    @NotBlank(message = "Biografia é obrigatória")
    @Size(max = 2000, message = "Biografia deve ter até 2000 caracteres")
    private String biografia;

}
