package com.meuscursos.apirestspring3.model.consulta.validacoes.agendamento;

import com.meuscursos.apirestspring3.infra.exceptions.ValidacaoException;
import com.meuscursos.apirestspring3.model.consulta.DadosAgendamentoConsulta;
import com.meuscursos.apirestspring3.repository.MedicosRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private MedicosRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        //escolha do medico opcional
        if (dados.idMedico() == null) {
            return;
        }

        var medicoEstaAtivo = repository.findStatusById(dados.idMedico());
        if (!medicoEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com médico excluído");
        }
    }

}
