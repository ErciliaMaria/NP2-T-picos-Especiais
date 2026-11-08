# Parte de Magnum — execução e estudo

## Escopo e preservação

Magnum: Autor, Empréstimo e Avaliação do Livro.
Ercília: Aluno, Livro e integração.

Todos os arquivos que existiam no commit `3157ba8` foram restaurados ao conteúdo original, incluindo README, pom.xml, configurações, tratamento global de erros e teste de inicialização. Os complementos ficam somente em arquivos novos da parte de Magnum. O histórico das alterações e da restauração foi preservado.

Consequências dessa restauração: Aluno e Livro voltam a ter apenas a listagem GET, sem consulta por ID. A integração volta a salvar dados fictícios. Esses pontos pertencem à versão original e continuam pendentes em relação ao enunciado; não foram corrigidos nesta branch.

## Executar

Na pasta que contém o pom.xml:

```bash
bash mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

No Windows: use `mvnw.cmd` no lugar de `bash mvnw`.
O perfil `local`, acrescentado por Magnum, usa H2 em arquivo em `data/`. O arquivo `data/.gitignore` protege os dados locais sem alterar o `.gitignore` original. Não inicie duas instâncias usando o mesmo banco.
No IntelliJ, a configuração acrescentada **Biblioteca local** executa esse mesmo comando.

Swagger: http://localhost:8080/swagger-ui/index.html
O perfil padrão PostgreSQL e suas configurações originais foram preservados. Não publique credenciais em prints ou mensagens.

Para executar o teste original sem depender do PostgreSQL:

```bash
bash mvnw clean verify -Dspring.profiles.active=h2
```

O perfil é selecionado pelo comando, sem editar o teste da Ercília. Nesta revisão, a compilação e esse teste passaram. Os três CRUDs também foram verificados por HTTP em banco temporário, incluindo cinco operações, dados inválidos e IDs inexistentes. Swagger UI e documento OpenAPI responderam HTTP 200 com o pom original. Não foram acrescentados testes automatizados ao repositório.

## Fluxo de uma requisição

Referência: material do professor — camadas nas páginas 17–22, DTOs 29–37, erros 44–45, Repository 47–49 e Swagger 73–79.

1. `CreateAuthorController` recebe POST; `@Valid` verifica as restrições de `AuthorDTO`.
2. `CreateAuthorService` transforma o DTO em entidade `Author` e chama o Repository.
3. `AuthorRepository` herda `save` do `JpaRepository`.
4. `AuthorMapper` converte a entidade persistida em DTO, manualmente, como apresentado na aula.
5. O Controller devolve HTTP 201 e o ID gerado pelo banco.

Lombok gera getters, setters e construtores. O Model representa a tabela; o DTO representa os dados da requisição/resposta. `@Transactional` reúne uma operação de banco numa transação.

## Erros sem alterar a parte da Ercília

`MagnumExceptionHandler` é um arquivo novo. O `@RestControllerAdvice(basePackages = ...)` limita sua atuação aos controllers de Autor, Empréstimo e Avaliação. `@Order(-1)` dá prioridade a esse handler para os tipos de erro que ele trata nessas rotas.

Ele devolve mensagens claras para dados inválidos (400) e conflitos de integridade (409). As exceções `ResponseStatusException` continuam usando o handler original, inclusive os erros 404 dos Services.
O `GlobalExceptionHandler` da Ercília não foi modificado. Portanto, uma exclusão de Livro/Aluno impedida pelos vínculos pode retornar 400 e "Erro desconhecido", como no tratamento original. O handler novo não muda essas rotas.

## Demonstrar os três CRUDs

| Recurso | Rota | Campos |
|---|---|---|
| Autor | `/api/authors` | nome, nacionalidade, dataNascimento, biografia |
| Empréstimo | `/api/loans` | alunoId, livroId, dataEmprestimo, dataPrevistaDevolucao, dataDevolucao, observacoes |
| Avaliação | `/api/reviews` | alunoId, livroId, titulo, nota, comentario, dataAvaliacao |

POST cadastra (201); GET lista (200). Com `/{id}`, GET consulta (200), PUT edita (200) e DELETE exclui (204). Envie o corpo completo no PUT. Consultar um ID excluído retorna 404.

Exemplo de Autor:

```json
{"nome":"Machado de Assis","nacionalidade":"Brasileira","dataNascimento":"1839-06-21","biografia":"Autor de Dom Casmurro"}
```

Nome vazio retorna 400. Para Empréstimo/Avaliação, use IDs obtidos nas listagens de Aluno e Livro. Devolução anterior à retirada retorna 400; nota fora de 1 a 5 retorna 400. Datas usam AAAA-MM-DD; devolução pode ficar nula até a entrega do livro.
Empréstimo e Avaliação possuem `@ManyToOne` para as entidades originais, sem alterá-las. O DTO recebe os IDs e o Service busca as entidades. As tabelas novas referenciam as tabelas existentes; exclua primeiro os registros relacionados antes de excluir Aluno/Livro. Empréstimos não alteram automaticamente o estoque.

## Perguntas para o estudo

- Qual a diferença entre Model e DTO?
- Qual o caminho da requisição até o banco?
- De onde vem o método `save`?
- Como os IDs viram relações no Empréstimo?
- Qual regra impede devolver antes de retirar o livro?
- Por que o handler de Magnum não intercepta as rotas da Ercília?

## Contribuições e uso de IA

Magnum do Vale Freire (`Magnumvf`): responsável pelos três CRUDs novos, com assistência de OpenAI Codex para implementação, configuração, verificações e organização de commits. Ercília (`ErciliaMaria`): autora da base preservada.
A restauração foi solicitada para manter integralmente os arquivos originais. Isso não apaga do histórico as intervenções anteriores. O estudo de compreensão e o ensaio da apresentação ainda serão realizados; não estão declarados como concluídos.
Esta branch deve ser revisada antes de merge/publicação. O ZIP disponibilizado anteriormente representa outra versão e não deve ser usado como entrega atual.
