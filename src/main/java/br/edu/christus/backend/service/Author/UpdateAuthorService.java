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
public class UpdateAuthorService {
    private final AuthorRepository repository;

    @Transactional
    public AuthorDTO atualizar(Long id, AuthorDTO dto) {
        Author entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado(a)"));
        entity.setNome(dto.getNome());
        entity.setNacionalidade(dto.getNacionalidade());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setBiografia(dto.getBiografia());
        return AuthorMapper.toDTO(repository.save(entity));
    }
}
