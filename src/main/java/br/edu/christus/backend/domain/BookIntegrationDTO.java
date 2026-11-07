package br.edu.christus.backend.domain;
import lombok.Data;

@Data
public class BookIntegrationDTO {
    private String title;
    private String authors;
    private Long livroId;
    private String isbn;
}
