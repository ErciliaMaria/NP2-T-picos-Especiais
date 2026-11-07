package br.edu.christus.backend.service.Book;

import br.edu.christus.backend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class DeleteBookService {
    private final BookRepository bookRepository;

    @Transactional
    public String deletarLivro(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Livro não encontrado"
            );
        }

        bookRepository.deleteById(id);
        return "Livro excluído com sucesso!";
    }
}
