package br.edu.christus.backend.domain;
import jakarta.persistence.*;
import lombok.*;
import br.edu.christus.backend.domain.Book.Book;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id", unique = true)
    // Ao excluir o livro, o banco também remove os metadados desta importação.
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Book livro;
}
