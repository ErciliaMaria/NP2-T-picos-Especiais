package br.edu.christus.backend.service.Author;

import br.edu.christus.backend.domain.Author.Author;
import br.edu.christus.backend.domain.Author.AuthorDTO;
import br.edu.christus.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateAuthorService {
    private final AuthorRepository repository;

    @Transactional
    public AuthorDTO criar(AuthorDTO dto) {
        Author entity = new Author();
        entity.setNome(dto.getNome());
        entity.setNacionalidade(dto.getNacionalidade());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setBiografia(dto.getBiografia());
        return AuthorMapper.toDTO(repository.save(entity));
    }
}
