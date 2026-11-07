package br.edu.christus.backend.domain.Author;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "autores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 100)
    private String nacionalidade;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false, length = 2000)
    private String biografia;

}
