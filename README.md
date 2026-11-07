# Biblioteca escolar — NP2

API REST para Tópicos Especiais, professor Adriano Lima. Entrega em 08/11/2026, às 23h59.

## Equipe e escopo

| Integrante | GitHub | Responsabilidade |
|---|---|---|
| Ercília | ErciliaMaria | Aluno, Livro e integração |
| Magnum do Vale Freire | Magnumvf | Autor, Empréstimo e Avaliação do Livro |

A parte de Magnum segue as camadas Model, DTO, Repository, Service e Controller e a separação de operações usada pela Ercília. Cada entidade nova tem pelo menos quatro atributos além do ID. Empréstimos e avaliações se relacionam com Aluno e Livro por `@ManyToOne`.

## Executar

Configuração verificada: Java 17 no `pom.xml`, Spring Boot 4.1.1, Maven Wrapper 3.9.16 e Lombok. A compilação foi executada com JDK 21, gerando código compatível com Java 17. Swagger usa Springdoc 3.1.1, compatível com Spring Boot 4.

Na pasta do `pom.xml`:

```bash
bash mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

No Windows, substitua `bash mvnw` por `mvnw.cmd`. A primeira execução precisa de internet para baixar dependências.
No IntelliJ, abra o projeto Maven e execute **Biblioteca local**, ou execute `BackendApplication.main` com `--spring.profiles.active=local`.
Lombok gera métodos durante a compilação; quem inicia a API é o `main` do Spring Boot.

Swagger: http://localhost:8080/swagger-ui/index.html

- `local`: H2 em arquivo em `data/`, preserva os dados após reiniciar. Não execute duas instâncias simultâneas no mesmo banco.
- `h2`: banco em memória, dados temporários.
- `psql`: perfil padrão, exige PostgreSQL e banco existente. Configure `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` e `SPRING_DATASOURCE_PASSWORD` no ambiente; não publique credenciais. Este perfil não foi validado neste ciclo.

O Hibernate cria/atualiza tabelas com `ddl-auto: update`. Migrations não serão cobradas.

## Cinco CRUDs

| Recurso | Rota | Campos do corpo |
|---|---|---|
| Aluno | `/api/students` | nome_completo, matricula, turma, dataNascimento |
| Livro | `/api/books` | titulo, autor, editora, anoPublicacao, quantidadeExemplares |
| Autor | `/api/authors` | nome, nacionalidade, dataNascimento, biografia |
| Empréstimo | `/api/loans` | alunoId, livroId, dataEmprestimo, dataPrevistaDevolucao, dataDevolucao, observacoes |
| Avaliação | `/api/reviews` | alunoId, livroId, titulo, nota, comentario, dataAvaliacao |

Em cada rota: POST cadastra e GET lista. Com `/{id}`: GET consulta, PUT edita e DELETE exclui.
O ID é gerado pelo banco. PUT recebe o corpo completo. Datas usam `AAAA-MM-DD`; `dataDevolucao` pode ser nula.

Exemplo de cadastro de Autor:

```json
{"nome":"Machado de Assis","nacionalidade":"Brasileira","dataNascimento":"1839-06-21","biografia":"Autor de Dom Casmurro"}
```

Validações: campos obrigatórios/tamanhos, nota entre 1 e 5, datas coerentes e referências existentes. Devolução não pode anteceder a retirada. Excluir Aluno ou Livro vinculado a Empréstimo/Avaliação retorna 409.
Nos três novos CRUDs: cadastro 201, consulta/edição 200, exclusão 204; erros de entrada 400, registro inexistente 404 e conflito de integridade 409.
Empréstimos não alteram automaticamente a quantidade de exemplares.

## Complementos em Aluno, Livro e integração

A base dessas classes é da Ercília. Com autorização de Magnum, foram acrescentados consulta por ID, validações de entrada, retorno do ID no cadastro de Aluno e correções de mensagens. A listagem e as demais rotas foram mantidas. Matrícula repetida retorna 409 tanto no cadastro quanto na edição.

`POST /api/books/import/{isbn}` consulta a Open Library e grava o livro no acervo. Exemplo:

```text
POST /api/books/import/9780140328721
```

A resposta contém `title`, `authors`, `isbn` e `livroId`. Use `livroId` para consultar o livro em `/api/books/{id}` ou criar empréstimos/avaliações. A rota mantém o retorno 200 que já existia.

A importação aceita ISBN de 10 ou 13 caracteres no formato esperado e remove espaços/hífens; não calcula o dígito verificador. Retorna 400 para formato inválido, 404 se não encontrar o livro, 409 para ISBN já importado e 502 se a consulta externa falhar. Precisa de internet.

Importamos título e autores, com um exemplar inicial. Editora e ano podem ser preenchidos depois pelo PUT de Livro. Textos externos são limitados ao tamanho das colunas existentes. `BookIntegration` guarda os dados da importação e aponta para `Book`, o livro do acervo. Ao excluir esse livro, seus metadados de importação também são removidos, permitindo importar novamente o mesmo ISBN.

Livro continua com o autor como texto; não foi criado vínculo novo com Autor. Os relacionamentos obrigatórios de Empréstimo/Avaliação com Aluno/Livro permanecem. A equipe ainda deve confirmar com o professor o alinhamento da integração como funcionalidade extra.

## Verificação, estudo e IA

```bash
bash mvnw clean verify
```

Foi preservado o teste de inicialização `BackendApplicationTests`, configurado para H2. Os testes automatizados adicionais criados por IA foram retirados a pedido de redução de escopo. Nesta revisão, a compilação e o teste original passaram, e os cinco CRUDs foram verificados por requisições HTTP sem adicionar testes ao repositório. Você pode reproduzir a verificação no Swagger: cadastrar, listar, consultar, editar, excluir e conferir 400/404/409.

Ferramenta utilizada: OpenAI Codex, para análise do projeto/material da aula, implementação dos três CRUDs de Magnum, complementos em Aluno/Livro e integração, configuração, verificação e organização de commits. Os testes automatizados adicionais retirados continuam apenas no histórico. O estudo de compreensão de Magnum ainda será realizado com o [roteiro curto](docs/ESTUDO_E_DEMONSTRACAO.md).

Próximos passos até a entrega: Magnum estudar e demonstrar sua parte; a dupla revisar o código, ensaiar e preparar a entrega. Não há alegação de contribuições semanais anteriores além das registradas no Git.

## Entrega

Repositório: https://github.com/ErciliaMaria/NP2-T-picos-Especiais

Enviar código, identificação dos integrantes, instruções, contribuições e registro de IA. Identifique a versão com `git rev-parse HEAD` e confirme o acesso do professor. Os commits novos estão locais; o usuário Magnumvf tinha somente permissão READ na última consulta ao GitHub.
