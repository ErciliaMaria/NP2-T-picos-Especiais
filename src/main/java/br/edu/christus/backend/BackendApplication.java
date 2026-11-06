package br.edu.christus.backend;

import br.edu.christus.backend.domain.Student.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(
				title = "API Lib",
				version = "1.0",
				description = "Biblioteca Virtual",
				contact = @Contact(
						name = "Ercilia e Magnum",
						email = "contato@exemplo.com",
						url = "https://www.exemplo.com")


		)
)
@SpringBootApplication
public class BackendApplication {
private SimpleJpaRepository<Student,Long> repository;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}

