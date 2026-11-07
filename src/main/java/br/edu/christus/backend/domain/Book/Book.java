package br.edu.christus.backend.domain.Book;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "livros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 100)
    private String autor;

    @Column(length = 100)
    private String editora;

    private Integer anoPublicacao;

    @Column(nullable = false)
    private Integer quantidadeExemplares;
}
