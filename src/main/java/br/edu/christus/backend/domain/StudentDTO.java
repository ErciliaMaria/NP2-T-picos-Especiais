package br.edu.christus.backend.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StudentDTO {
    private Long id;
    private String nome_completo;
    private String turma;
    private LocalDate dataNascimento;
    private LocalDateTime createdAt;
}
