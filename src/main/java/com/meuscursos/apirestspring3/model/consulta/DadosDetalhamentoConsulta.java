package com.meuscursos.apirestspring3.model.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, Long idMedico, Long idPaciente, LocalDateTime data) {
    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getMedicos().getId(), consulta.getPacientes().getId(), consulta.getData());
    }
}
