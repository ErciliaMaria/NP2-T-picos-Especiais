package br.edu.christus.backend;

import br.edu.christus.backend.domain.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

@SpringBootApplication
public class BackendApplication {
private SimpleJpaRepository<Student,Long> repository;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}

