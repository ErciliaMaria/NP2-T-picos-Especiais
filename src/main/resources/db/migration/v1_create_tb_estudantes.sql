CREATE TABLE estudantes(
                            id NUMERIC PRIMARY KEY,
                            nome_completo VARCHAR(255) NOT NULL,
                            matricula VARCHAR(100) NOT NULL UNIQUE,
                            turma VARCHAR(100) NOT NULL,
                            data_nascimento DATE NOT NULL,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Comentários (notes) nas colunas e na tabela
COMMENT ON TABLE estudantes IS 'Tabela de cadastro dos alunos leitores da biblioteca escolar.';
COMMENT ON COLUMN estudantes.id IS 'Identificador único do estudante';
COMMENT ON COLUMN estudantes.nome_completo IS 'Nome completo do aluno';
COMMENT ON COLUMN estudantes.matricula IS 'Número de matrícula escolar do aluno';
COMMENT ON COLUMN estudantes.turma IS 'Série/Turma do aluno (ex: 1º Ano A, 3º Ano B)';
COMMENT ON COLUMN estudantes.data_nascimento IS 'Data de nascimento do estudante';
COMMENT ON COLUMN estudantes.created_at IS 'Data de criação do registro';