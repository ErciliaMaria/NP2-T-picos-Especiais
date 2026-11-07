# Estudo do projeto

Referência: material do professor — camadas nas páginas 17–22, DTOs 29–37, erros 44–45, Repository 47–49 e Swagger 73–79.

## Começar pelo cadastro de Autor

1. `AuthorDTO` define os dados recebidos e as validações simples.
2. `CreateAuthorController` recebe POST; `@Valid` verifica o DTO.
3. `CreateAuthorService` transforma os dados em uma entidade `Author` e chama o Repository.
4. `AuthorRepository` herda `save` do `JpaRepository`.
5. `AuthorMapper` converte a entidade salva em DTO, manualmente, como apresentado na aula.
6. O Controller devolve HTTP 201 e o ID gerado pelo banco.

Lombok gera getters, setters e construtores. O Model representa a tabela; o DTO representa os dados da requisição/resposta. `@Transactional` reúne uma operação de banco numa transação.

## Depois: consultas, edição e exclusão

Leia `GetAuthorService`, `UpdateAuthorService` e `DeleteAuthorService`. Registro inexistente gera `ResponseStatusException` com 404. O `GlobalExceptionHandler` transforma erros em respostas HTTP.

Empréstimo e Avaliação seguem a mesma estrutura, acrescentando as relações com Aluno e Livro. O DTO recebe IDs; o Service procura as entidades antes de salvar. As regras de datas relacionadas ficam no Service; as restrições simples estão no DTO.

## Demonstrar no Swagger

- Autor: cadastrar, listar, consultar por ID, editar e excluir. Consultar novamente mostra 404; nome vazio mostra 400.
- Empréstimo: usar IDs de aluno/livro existentes; cadastrar e editar para registrar devolução. Data de devolução anterior à retirada mostra 400.
- Avaliação: cadastrar nota válida, editar e excluir. Nota 6 mostra 400.
- Tentar excluir Aluno/Livro vinculado mostra 409. Remover primeiro avaliações/empréstimos permite excluir os registros relacionados.

A base de Aluno e Livro é da Ercília. As consultas por ID foram acrescentadas no mesmo Controller e Service de consulta, sem remover as listagens. Elas usam `findById` e retornam 404 quando o registro não existe.

## Entender a integração em passos

Leia `BookIntegrationService.importAndSaveBookByIsbn`:

1. Normaliza o ISBN, verifica seu formato e procura uma importação repetida.
2. `consultarOpenLibrary` usa `RestTemplate` para fazer uma requisição HTTP. `ObjectMapper` transforma o texto JSON em `JsonNode`, que permite acessar campos como `title` e `authors`.
3. `obterAutores` reúne os nomes retornados pela API em um texto.
4. Salva `Book` no acervo, com título, autores e um exemplar. Isso permite usar o livro em empréstimos/avaliações.
5. Salva `BookIntegration` com o ISBN e a referência ao livro; devolve o DTO com o ID do acervo.

As duas entidades já existiam no projeto. `@OneToOne` liga os metadados ao livro; `@OnDelete(CASCADE)` faz o banco remover esses metadados quando o livro é excluído. Essa exclusão não apaga empréstimos nem avaliações: esses vínculos continuam impedindo apagar o livro e retornam 409.
`@Transactional` evita salvar apenas metade da operação se ocorrer erro ao gravar os dados.

Para demonstrar: importe `9780140328721`, use `livroId` no GET de Livro e repita a importação para mostrar 409. ISBN com formato incorreto retorna 400. Falha de conexão externa retorna 502, sem salvar dados fictícios.

## Perguntas para verificar compreensão

- Qual a diferença entre Model e DTO?
- Qual o caminho da requisição até o banco?
- De onde vem o método `save`?
- O que diferencia os erros 400, 404 e 409?
- Como alunoId e livroId viram relações no Empréstimo?
- Qual regra impede devolver antes de retirar o livro?
- Qual a diferença entre o GET de listagem e o GET por ID?
- Por que o livro importado é salvo no acervo além dos metadados da integração?

A revisão de compreensão e o ensaio ainda estão pendentes; este roteiro não declara que já foram realizados.
