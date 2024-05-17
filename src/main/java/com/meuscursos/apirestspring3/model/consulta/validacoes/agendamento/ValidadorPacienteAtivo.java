package com.meuscursos.apirestspring3.model.consulta.validacoes.agendamento;

import com.meuscursos.apirestspring3.infra.exceptions.ValidacaoException;
import com.meuscursos.apirestspring3.model.consulta.DadosAgendamentoConsulta;
import com.meuscursos.apirestspring3.repository.PacientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private PacientesRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        var pacienteEstaAtivo = repository.findStatusById(dados.idPaciente());
        if (!pacienteEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com paciente excluído");
        }
    }
}
