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

    @Transactional
    public BookDTO criarLivro(BookDTO dto) {

        Book book = Book.builder()
                .titulo(dto.getTitulo())
                .autor(dto.getAutor())
                .editora(dto.getEditora())
                .anoPublicacao(dto.getAnoPublicacao())
                .quantidadeExemplares(dto.getQuantidadeExemplares())
                .build();

        Book bookSalvo = bookRepository.save(book);

        return BookDTO.builder()
                .id(bookSalvo.getId())
                .titulo(bookSalvo.getTitulo())
                .autor(bookSalvo.getAutor())
                .editora(bookSalvo.getEditora())
                .anoPublicacao(bookSalvo.getAnoPublicacao())
                .quantidadeExemplares(bookSalvo.getQuantidadeExemplares())
                .build();
    }
}
