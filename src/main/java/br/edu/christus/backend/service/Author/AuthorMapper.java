package br.edu.christus.backend.service.Author;

import br.edu.christus.backend.domain.Author.Author;
import br.edu.christus.backend.domain.Author.AuthorDTO;

// Conversão manual entre entidade persistida e resposta da API.
final class AuthorMapper {
    private AuthorMapper() {}

    static AuthorDTO toDTO(Author entity) {
        return AuthorDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .nacionalidade(entity.getNacionalidade())
                .dataNascimento(entity.getDataNascimento())
                .biografia(entity.getBiografia())
                .build();
    }
}
