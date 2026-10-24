package br.edu.christus.backend.repository;

import br.edu.christus.backend.domain.BookIntegration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookIntegrationRepository extends JpaRepository<BookIntegration, Long> {
    Optional<BookIntegration> findByIsbn(String isbn);
}
