package br.edu.christus.backend.service;

import br.edu.christus.backend.domain.BookIntegration;
import br.edu.christus.backend.domain.BookIntegrationDTO;
import br.edu.christus.backend.repository.BookIntegrationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookIntegrationService {

    private final BookIntegrationRepository repository;
    private final RestTemplate restTemplate;

    public BookIntegrationService(BookIntegrationRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public BookIntegrationDTO importAndSaveBookByIsbn(String isbn) {
        if (isbn.length() != 10 && isbn.length() != 13) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN inválido. Deve conter 10 ou 13 dígitos.");
        }

        BookIntegration bookEntity = new BookIntegration();
        bookEntity.setTitle("Título Exemplo vindo da Open Library");
        bookEntity.setAuthors("Autor Exemplo");

        BookIntegration savedEntity = repository.save(bookEntity);

        BookIntegrationDTO dto = new BookIntegrationDTO();
        dto.setTitle(savedEntity.getTitle());
        dto.setAuthors(savedEntity.getAuthors());

        return dto;
    }
}