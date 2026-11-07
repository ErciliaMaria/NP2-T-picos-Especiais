# Biblioteca escolar — NP2

API REST de biblioteca escolar para Tópicos Especiais, professor Adriano Lima.
Entrega: 08/11/2026, 23h59. Sem interface visual; demonstração pelo Swagger.

## Equipe e responsabilidades

| Integrante | GitHub | Responsabilidade |
|---|---|---|
| Ercília | ErciliaMaria | Base de Aluno, Livro, integração e Swagger |
| Magnum do Vale Freire | Magnumvf | Autor, Empréstimo, Avaliação; complementos de integração, validação, testes e documentação com assistência de Codex |

O histórico Git preserva o trabalho anterior da Ercília. Veja [contribuições e uso de IA](docs/CONTRIBUICOES.md).

## Tecnologias verificadas

- Java 17 definido no `pom.xml`; compilação também verificada usando JDK 21 com alvo Java 17.
- Spring Boot 4.1.1, Maven Wrapper 3.9.16, JPA/Hibernate e Lombok.
- Springdoc 3.1.1 para Swagger compatível com Spring Boot 4.
- H2 para execução local/testes e perfil PostgreSQL disponível.
- Tabelas criadas/atualizadas por Hibernate (`ddl-auto: update`). Migrations não são exigidas pelo professor.

## Executar sem instalar banco

Abra o terminal nesta pasta (a mesma do `pom.xml`):

```bash
bash mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

No Windows, use `mvnw.cmd` no lugar de `bash mvnw`.
É necessário JDK instalado e internet na primeira execução para baixar Maven/dependências.
O perfil `local` usa H2 em arquivo em `data/biblioteca.mv.db`; os dados permanecem após reiniciar.
Não execute duas instâncias usando o mesmo arquivo. Encerre com Ctrl+C.
O diretório `data/` não é enviado ao GitHub.

- Swagger: http://localhost:8080/swagger-ui/index.html
- Documento OpenAPI: http://localhost:8080/v3/api-docs
- A raiz `/` não é uma página inicial: este projeto é uma API.

### IntelliJ

Abra o `pom.xml` como projeto Maven e aguarde a importação das dependências.
Use um JDK 17 ou o JDK 21 já validado. Selecione **Biblioteca local** nas configurações de execução e clique em Run.
A configuração está em `.run/Biblioteca local.run.xml` e executa o comando Maven acima.
Alternativamente, execute `BackendApplication.main` com o argumento `--spring.profiles.active=local`.

Lombok é dependência e processador de anotações no Maven: gera getters, setters, construtores e builders durante a compilação.
Ele não inicia a API. Se a IDE marcar métodos gerados como inexistentes, confira o suporte/plugin Lombok e o processamento de anotações; a compilação Maven serve de referência.

### Perfis e PostgreSQL

| Perfil | Uso | Dados após encerrar |
|---|---|---|
| `local` | Demonstração sem servidor de banco | Mantidos em arquivo H2 |
| `h2` | Testes temporários | Perdidos |
| `psql` | PostgreSQL instalado e banco previamente criado | Mantidos no PostgreSQL |

O perfil padrão do `application.yaml` continua sendo `psql`. Para utilizá-lo, configure no ambiente de execução:

- `SPRING_DATASOURCE_URL`: URL JDBC do seu banco PostgreSQL, incluindo host, porta e nome do banco.
- `SPRING_DATASOURCE_USERNAME`: usuário do banco.
- `SPRING_DATASOURCE_PASSWORD`: senha do banco.

As credenciais não devem ser colocadas em commits, prints ou documentação. Depois execute:

```bash
bash mvnw spring-boot:run -Dspring-boot.run.profiles=psql
```

O perfil PostgreSQL está disponível, mas a validação automatizada desta entrega usa H2.

## Recursos e regras

Cada recurso possui POST e GET na rota base; GET, PUT e DELETE na rota com `/{id}`.
PUT substitui os campos editáveis; envie o corpo completo. IDs de cadastro são gerados pelo banco.

| Recurso | Rota base | Dados principais |
|---|---|---|
| Autor | `/api/authors` | nome, nacionalidade, dataNascimento, biografia |
| Aluno | `/api/students` | nome_completo, matricula, turma, dataNascimento |
| Livro | `/api/books` | titulo, autor, editora, anoPublicacao, quantidadeExemplares, autorId opcional |
| Empréstimo | `/api/loans` | alunoId, livroId, dataEmprestimo, dataPrevistaDevolucao, dataDevolucao opcional, observacoes |
| Avaliação | `/api/reviews` | alunoId, livroId, titulo, nota, comentario, dataAvaliacao |

- Livro pode apontar para Autor via `autorId`. O texto `autor` foi mantido por compatibilidade; envie ambos coerentemente quando houver vínculo.
- Empréstimo e Avaliação obrigatoriamente apontam para Aluno e Livro existentes.
- Não há exclusão em cascata dos empréstimos/avaliações. Excluir registros referenciados retorna 409.
- Notas entre 1 e 5; datas de nascimento no passado; retirada, devolução efetiva e avaliação não podem ser futuras.
- Devolução efetiva e prevista não podem anteceder a retirada. Matrícula de aluno é única.
- Empréstimo registra retirada e devolução; não altera automaticamente a quantidade de exemplares. Reservas e controle automático de disponibilidade estão fora deste escopo.
- Respostas: 201 no cadastro, 200 nas consultas/edições; exclusão usa 204 nos novos CRUDs e mantém 200 em Aluno/Livro. Erros: 400 entrada inválida, 404 inexistente, 409 conflito, 502 falha de serviço externo e 500 falha interna inesperada.

### Funcionalidade extra: importar por ISBN

`POST /api/books/import/{isbn}` consulta a Open Library, valida o dígito do ISBN e grava o livro no acervo usado pelos demais CRUDs. Retorna `livroId`, `isbn`, `title` e `authors` com HTTP 200, preservando a rota original.

Exemplo: `POST /api/books/import/9780140328721`.
O livro recebe inicialmente um exemplar; ajuste pelo PUT de Livro se necessário.
ISBN já importado retorna 409; livro ausente retorna 404; indisponibilidade externa retorna 502.
Títulos/autores/editoras externos são limitados ao tamanho das colunas do catálogo.
Metadados da importação são removidos quando o respectivo livro é excluído, permitindo nova importação.
A demonstração desta funcionalidade precisa de internet. O alinhamento de escopo com o professor cabe à equipe.
Referência: https://openlibrary.org/dev/docs/api/books

## Testar e empacotar

```bash
bash mvnw clean verify
java -jar target/backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=local
```

Os testes usam H2 isolado, iniciam servidor HTTP em porta livre e verificam os cinco CRUDs, vínculos, validações, erros e as rotas no OpenAPI.
A integração externa usa respostas simuladas nos testes para não depender da internet; isso não substitui uma demonstração da consulta real.

Veja o [roteiro de apresentação e estudo](docs/ESTUDO_E_DEMONSTRACAO.md) e as [requisições prontas](docs/demonstracao.http).

## Entrega

Enviar no Moodle ZIP ou pasta do Drive acessível por link, contendo código, nomes/usuários GitHub, link do repositório, instruções, contribuições e registro de IA.
Identifique o commit entregue com `git rev-parse HEAD` e anote esse hash na entrega; não confunda a branch com uma versão imutável.
Repositório: https://github.com/ErciliaMaria/NP2-T-picos-Especiais
Confirme o acesso do professor ao repositório antes da entrega.
