package br.edu.christus.backend.domain;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookIntegration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String authors;

    private String publisher;

    @Column(name = "published_date")
    private String publishedDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer pageCount;

    @Column(unique = true)
    private String isbn;

    @Column(length = 1000)
    private String coverUrl;
}
