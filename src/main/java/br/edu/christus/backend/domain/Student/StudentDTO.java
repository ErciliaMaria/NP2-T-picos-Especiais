package br.edu.christus.backend.domain.Student;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDTO {
    private Long id;

    @JsonProperty("nome_completo")
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 255)
    private String nomeCompleto;
    @NotBlank(message = "Matrícula é obrigatória")
    @Size(max = 100)
    private String matricula;
    @NotBlank(message = "Turma é obrigatória")
    @Size(max = 100)
    private String turma;
    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Nascimento deve estar no passado")
    private LocalDate dataNascimento;
}