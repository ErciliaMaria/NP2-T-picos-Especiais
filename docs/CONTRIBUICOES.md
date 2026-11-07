# Responsabilidades, desenvolvimento e uso de IA

## Responsabilidades

- Ercília (`ErciliaMaria`): base do projeto, CRUDs de Aluno e Livro, estrutura da integração e configuração inicial de Swagger, conforme commits anteriores.
- Magnum do Vale Freire (`Magnumvf`): responsável por Autor, Empréstimo e Avaliação; complementos para funcionamento integrado, testes e documentação, com assistência de IA.

Não atribuir a Magnum os commits originais da Ercília. Os complementos em arquivos dela constam separadamente no histórico.

## Registro desta implementação

Em 06/11/2026, as mudanças assistidas foram organizadas por branches:

- `feat/magnum-autores`: cinco operações de Autor, tratamento de validação, compatibilidade do Swagger e testes HTTP.
- `feat/magnum-emprestimos`: empréstimos ligados a alunos/livros, datas e testes de integridade.
- `feat/magnum-avaliacoes`: avaliações com nota de 1 a 5, relações e testes.
- `fix/magnum-integracao-final`: consultas por ID e validações que faltavam em Aluno/Livro, vínculo com Autor e consulta real à Open Library.
- `docs/magnum-execucao-estudo`: instruções, perfil persistente local e roteiro de estudo/apresentação.

Cada branch foi integrada à main local depois de validada. As datas reais e autoria configurada estão no Git; não representam trabalho distribuído por semanas anteriores.

## Cronograma

| Data | Trabalho | Responsável |
|---|---|---|
| Antes de 06/11 | Base de Aluno, Livro, integração e Swagger, registrada no Git | Ercília |
| 06/11 | Implementar e integrar os três CRUDs de Magnum; validar conjunto | Magnum com Codex |
| 07/11 (planejado) | Estudar o código, revisar regras com a dupla e ensaiar apresentação | Ambos |
| 08/11 (planejado) | Conferir execução, usuários GitHub, commit entregue e enviar até 23h59 | Ambos |

## Uso de inteligência artificial

Ferramenta: OpenAI Codex.
Finalidades: analisar repositório e material da aula, configurar execução, implementar código, organizar branches/commits, criar e executar testes e preparar documentação.
Verificação técnica: testes HTTP dos CRUDs e erros, teste das rotas do Swagger, testes da integração com serviço externo simulado e compilação do pacote. Também foram verificados o banco em arquivo após reinício e uma consulta real à Open Library; detalhes em `VERIFICACAO.md`.

O código deste ciclo foi produzido com assistência substancial de IA. O estudo e a revisão de compreensão por Magnum serão feitos depois da integração, conforme combinado; não estão sendo declarados como concluídos.
Na apresentação, os integrantes devem explicar o próprio trabalho sem consultar IA. Atualizar este registro após a revisão humana e o ensaio, relatando apenas o que efetivamente foi verificado.
