
CREATE TABLE pacientes(

    id SERIAL PRIMARY KEY,
    nome varchar(100) NOT NULL,
    email varchar(100) NOT NULL UNIQUE,
    telefone varchar(20) NOT NULL,
    cpf varchar(15) NOT NULL UNIQUE,
    logradrouro varchar(100)  NOT NULL,
    bairro varchar(100) NOT NULL,
    cep varchar(9) NOT NULL,
    complemento varchar(100),
    numero varchar(20),
    uf varchar(2)  NOT NULL,
    cidade varchar(50)  NOT NULL

)