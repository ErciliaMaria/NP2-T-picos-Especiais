package br.edu.christus.backend.domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "estudantes")
@Data

public class Student {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "nome_completo", nullable = false, length = 255)
    private String nomeCompleto;

    @Column(name = "matricula", nullable = false, unique = true, length = 100)
    private String matricula;

    @Column(name = "turma", nullable = false, length = 100)
    private String turma;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}