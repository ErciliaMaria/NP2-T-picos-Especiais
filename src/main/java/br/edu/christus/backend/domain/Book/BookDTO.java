package br.edu.christus.backend.domain.Book;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
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
    private String titulo;
    private String autor;
    private String editora;
    private Integer anoPublicacao;
    private Integer quantidadeExemplares;
}
