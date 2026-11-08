package br.edu.christus.backend.repository;


import br.edu.christus.backend.domain.Student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByMatricula(String matricula);
}

