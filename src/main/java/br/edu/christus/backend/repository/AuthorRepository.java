package br.edu.christus.backend.repository;

import br.edu.christus.backend.domain.Author.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
