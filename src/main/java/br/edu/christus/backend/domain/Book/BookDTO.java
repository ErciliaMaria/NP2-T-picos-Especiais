package br.edu.christus.backend.domain.Book;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 150, message = "Máximo de 150 caracteres")
    private String titulo;

    @NotBlank(message = "Autor é obrigatório")
    @Size(max = 100, message = "Máximo de 100 caracteres")
    private String autor;

    @Size(max = 100, message = "Máximo de 100 caracteres")
    private String editora;

    @Min(value = 1, message = "Ano deve ser positivo")
    private Integer anoPublicacao;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 0, message = "Quantidade não pode ser negativa")
    private Integer quantidadeExemplares;
}
