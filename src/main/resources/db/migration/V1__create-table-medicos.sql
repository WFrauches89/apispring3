CREATE TABLE medicos(

    id SERIAL PRIMARY KEY,
    nome varchar(100) NOT NULL,
    email varchar(100) NOT NULL UNIQUE,
    crm varchar(6) NOT NULL UNIQUE,
    especialidade varchar(100) NOT NULL,
    telefone varchar(20) NOT NULL,
    logradouro varchar(100)  NOT NULL,
    bairro varchar(100) NOT NULL,
    cep varchar(9) NOT NULL,
    complemento varchar(100),
    numero varchar(20),
    uf varchar(2)  NOT NULL,
    cidade varchar(50)  NOT NULL

)
