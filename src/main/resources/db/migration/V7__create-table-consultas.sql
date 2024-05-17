CREATE TABLE consultas (
    id SERIAL PRIMARY KEY,
    medicos_id BIGINT NOT NULL,
    pacientes_id BIGINT NOT NULL,
    data TIMESTAMP NOT NULL,

    CONSTRAINT fk_medicos_id FOREIGN KEY (medicos_id) REFERENCES medicos(id),
    CONSTRAINT fk_pacientes_id FOREIGN KEY (pacientes_id) REFERENCES pacientes(id)
);