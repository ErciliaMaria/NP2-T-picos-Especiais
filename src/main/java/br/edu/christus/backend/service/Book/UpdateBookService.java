package br.edu.christus.backend.service.Book;

import br.edu.christus.backend.domain.Book.Book;
import br.edu.christus.backend.domain.Book.BookDTO;
import br.edu.christus.backend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UpdateBookService {

    private final BookRepository bookRepository;
    private final br.edu.christus.backend.repository.AuthorRepository authorRepository;

    @Transactional
    public BookDTO atualizarLivro(Long id, BookDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Livro não encontrado"
                ));

        book.setTitulo(dto.getTitulo());
        book.setAutor(dto.getAutor());
        book.setEditora(dto.getEditora());
        book.setAnoPublicacao(dto.getAnoPublicacao());
        book.setQuantidadeExemplares(dto.getQuantidadeExemplares());

        if (dto.getAutorId() != null) {
            book.setAutorCadastrado(authorRepository.findById(dto.getAutorId()).orElseThrow(() ->
                    new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Autor não encontrado")));
        } else {
            book.setAutorCadastrado(null);
        }
        Book bookSalvo = bookRepository.save(book);

        return BookDTO.builder()
                .id(bookSalvo.getId())
                .autorId(bookSalvo.getAutorCadastrado() == null ? null : bookSalvo.getAutorCadastrado().getId())
                .titulo(bookSalvo.getTitulo())
                .autor(bookSalvo.getAutor())
                .editora(bookSalvo.getEditora())
                .anoPublicacao(bookSalvo.getAnoPublicacao())
                .quantidadeExemplares(bookSalvo.getQuantidadeExemplares())
                .build();
    }
}