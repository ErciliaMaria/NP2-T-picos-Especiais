package br.edu.christus.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookIntegrationDTO {

    private String title;
    private String authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private Integer pageCount;
    private String isbn;
    private String coverUrl;
}