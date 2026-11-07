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

## CRUDs de Magnum

| Recurso | Rota | Campos do corpo |
|---|---|---|
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

## Situação da parte da Ercília

Aluno, Livro e integração foram preservados conforme o commit `3157ba8`, sem os complementos feitos posteriormente por IA. Nessa versão:

- Aluno e Livro possuem cadastro, listagem, edição e exclusão; falta a consulta por ID exigida pelo enunciado.
- `POST /api/books/import/{isbn}` ainda salva dados fictícios; a consulta externa não está implementada.
- Livro mantém o autor como texto, sem vínculo com a entidade Autor.
- Validações e mensagens dessa parte ainda precisam ser revisadas pela responsável.

Portanto, os três CRUDs de Magnum estão implementados; o projeto completo ainda tem pendências. A funcionalidade extra deve ser concluída e alinhada com o professor pela equipe.

## Verificação, estudo e IA

```bash
bash mvnw clean verify
```

Foi preservado o teste de inicialização `BackendApplicationTests`, configurado para H2. Os testes automatizados adicionais criados por IA foram retirados a pedido de redução de escopo. Nesta revisão, a compilação e o teste original passaram, e os três CRUDs foram verificados por requisições HTTP sem adicionar testes ao repositório. Você pode reproduzir a verificação no Swagger: cadastrar, listar, consultar, editar, excluir e conferir 400/404/409.

Ferramenta utilizada: OpenAI Codex, para análise do projeto/material da aula, implementação dos três CRUDs, configuração, verificação e organização de commits. Os testes e complementos anteriores continuam registrados no histórico, mas não fazem parte desta versão. O estudo de compreensão de Magnum ainda será realizado com o [roteiro curto](docs/ESTUDO_E_DEMONSTRACAO.md).

Próximos passos até a entrega: Magnum estudar e demonstrar sua parte; a dupla revisar as pendências, ensaiar e preparar a entrega. Não há alegação de contribuições semanais anteriores além das registradas no Git.

## Entrega

Repositório: https://github.com/ErciliaMaria/NP2-T-picos-Especiais

Enviar código, identificação dos integrantes, instruções, contribuições e registro de IA. Identifique a versão com `git rev-parse HEAD` e confirme o acesso do professor. Os commits novos estão locais; o usuário Magnumvf tinha somente permissão READ na última consulta ao GitHub.
