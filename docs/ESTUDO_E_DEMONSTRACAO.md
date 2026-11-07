# Estudo e demonstração

## Percurso para entender o código

1. `BackendApplication.main`: inicia o Spring Boot; o perfil seleciona o banco.
2. `AuthorDTO`: corpo da requisição/resposta. `@NotBlank`, `@Size` e `@Past` descrevem restrições; `@Valid` no Controller as aplica às requisições.
3. `CreateAuthorController`: recebe POST e devolve 201; recebe o Service pelo construtor gerado por Lombok.
4. `CreateAuthorService`: transforma o DTO em entidade, chama `save` e converte a resposta com `AuthorMapper`.
5. `Author`: `@Entity` representa a tabela; `@Id` e `@GeneratedValue` definem a chave.
6. `AuthorRepository`: herda os métodos do `JpaRepository<Author, Long>`.
7. `GetAuthorService`: lista e procura por ID; inexistente gera `ResponseStatusException` com 404.
8. `GlobalExceptionHandler`: centraliza as respostas de erro, inclusive entrada inválida e violação de vínculos.
9. `Loan`: `@ManyToOne` aponta para Aluno e Livro. O DTO recebe apenas seus IDs; o Service carrega e verifica as entidades.
10. `Review`: segue o mesmo fluxo, com nota de 1 a 5 e data da avaliação.

As validações simples estão no DTO; as regras envolvendo datas relacionadas e existência de entidades estão nos Services.
`@Transactional` reúne as alterações da operação em uma transação: falha significa rollback.
`@Transactional(readOnly = true)` indica operações de leitura. Mapper faz a conversão manual mostrada na aula; não há ModelMapper.
Lombok reduz código repetitivo; Maven compila e baixa dependências; Spring Boot inicia a aplicação.

Material de referência: PDF do professor, camadas nas páginas 17–22, DTOs 29–37, erros 44–45, repository 47–49 e Swagger 73–79.
A separação em Create/Get/Update/Delete segue a organização do projeto da Ercília. O mapper manual evita repetir os mesmos campos nas respostas.

## Demonstração de até 10 minutos

- 1 minuto: objetivo, cinco recursos e divisão do trabalho.
- 3 minutos, Ercília: cadastrar Aluno/Livro, consultar por ID e mostrar importação real por ISBN.
- 4 minutos, Magnum: cadastrar Autor; criar Empréstimo com IDs existentes; registrar devolução via PUT; cadastrar/editar Avaliação e mostrar nota inválida retornando 400.
- 2 minutos: mostrar listagem, exclusão, 404 e 409; abrir código de um fluxo Controller → Service → Repository; apontar commits de cada integrante.

Deixe a aplicação ligada e o Swagger aberto antes do horário. Use `docs/demonstracao.http` como corpos de exemplo, substituindo IDs pelos retornados.
Para excluir a sequência de demonstração, primeiro exclua avaliações/empréstimos, depois Livro/Aluno e, por último, Autor.
A preparação não substitui a demonstração real: confira a internet antes de importar ISBN.

## Perguntas para nosso estudo posterior

- Por que a API usa DTO em vez de devolver a entidade diretamente?
- Qual a diferença entre 400, 404 e 409? Dê um exemplo real de cada um.
- De onde vem `save`, se não o escrevemos no Repository?
- Como o ID de um aluno vira uma relação no empréstimo?
- Por que não excluímos automaticamente todos os empréstimos ao apagar um aluno?
- O que `@Transactional` evita quando uma operação falha no meio?
- Qual código garante que devolução não antecede a retirada?
- Por que H2 em memória perde dados e o perfil local não?
- Quais métodos foram gerados por Lombok?
- Como os testes detectariam se a consulta por ID desaparecesse?

## Limites assumidos

Sem interface, autenticação, reservas, multas ou controle automático de estoque; não foram solicitados para este escopo mínimo.
O campo textual `autor` de Livro continua existindo junto do vínculo opcional `autorId` para preservar requisições anteriores. A equipe deve preencher os dois de forma coerente.
O serviço externo pode falhar; a API responde 502, em vez de inventar dados. Confirme com o professor o aceite da integração como funcionalidade extra.
