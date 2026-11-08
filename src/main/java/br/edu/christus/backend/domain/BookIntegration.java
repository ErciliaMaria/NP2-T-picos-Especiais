package br.edu.christus.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_book_integration")
@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookIntegration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String authors;
    private String publisher;
    private String publishedDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer pageCount;
    private String isbn;
    private String coverUrl;
}