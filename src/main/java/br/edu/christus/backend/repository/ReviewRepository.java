package br.edu.christus.backend.repository;

import br.edu.christus.backend.domain.Review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
