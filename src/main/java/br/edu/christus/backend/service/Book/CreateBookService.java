package br.edu.christus.backend.service.Book;

import br.edu.christus.backend.domain.Book.Book;
import br.edu.christus.backend.domain.Book.BookDTO;
import br.edu.christus.backend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateBookService {

    private final BookRepository bookRepository;
    private final br.edu.christus.backend.repository.AuthorRepository authorRepository;

    @Transactional
    public BookDTO criarLivro(BookDTO dto) {

        Book book = Book.builder()
                .titulo(dto.getTitulo())
                .autor(dto.getAutor())
                .editora(dto.getEditora())
                .anoPublicacao(dto.getAnoPublicacao())
                .quantidadeExemplares(dto.getQuantidadeExemplares())
                .build();

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
