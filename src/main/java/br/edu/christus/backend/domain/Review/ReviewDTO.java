package br.edu.christus.backend.domain.Review;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 150, message = "Título deve ter até 150 caracteres")
    private String titulo;

    @NotNull(message = "Nota é obrigatória")
    @Min(value = 1, message = "Nota mínima é 1")
    @Max(value = 5, message = "Nota máxima é 5")
    private Integer nota;

    @NotBlank(message = "Comentário é obrigatório")
    @Size(max = 2000, message = "Comentário deve ter até 2000 caracteres")
    private String comentario;

    @NotNull(message = "Data da avaliação é obrigatória")
    @PastOrPresent(message = "Data da avaliação não pode ser futura")
    private LocalDate dataAvaliacao;

    @NotNull(message = "Aluno é obrigatório")
    @Positive(message = "ID do aluno deve ser positivo")
    private Long alunoId;

    @NotNull(message = "Livro é obrigatório")
    @Positive(message = "ID do livro deve ser positivo")
    private Long livroId;
}
