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