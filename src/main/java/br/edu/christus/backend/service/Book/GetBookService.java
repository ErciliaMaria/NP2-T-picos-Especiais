package br.edu.christus.backend.service.Book;

import br.edu.christus.backend.domain.Book.BookDTO;
import br.edu.christus.backend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetBookService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<BookDTO> listarTodos() {
        return bookRepository.findAll().stream()
                .map(book -> BookDTO.builder()
                        .id(book.getId())
                        .titulo(book.getTitulo())
                        .autor(book.getAutor())
                        .editora(book.getEditora())
                        .anoPublicacao(book.getAnoPublicacao())
                        .quantidadeExemplares(book.getQuantidadeExemplares())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookDTO buscarPorId(Long id) {
        var book = bookRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
        return BookDTO.builder()
                .id(book.getId())
                .titulo(book.getTitulo())
                .autor(book.getAutor())
                .editora(book.getEditora())
                .anoPublicacao(book.getAnoPublicacao())
                .quantidadeExemplares(book.getQuantidadeExemplares())
                .build();
    }
}