package br.edu.christus.backend.domain.Review;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import br.edu.christus.backend.domain.Student.Student;
import br.edu.christus.backend.domain.Book.Book;

@Entity
@Table(name = "avaliacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false)
    private Integer nota;

    @Column(nullable = false, length = 2000)
    private String comentario;

    @Column(nullable = false)
    private LocalDate dataAvaliacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Student aluno;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "livro_id", nullable = false)
    private Book livro;

}
