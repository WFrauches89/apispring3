package com.meuscursos.apirestspring3.dto.medico;

import com.meuscursos.apirestspring3.enums.Especialidade;
import com.meuscursos.apirestspring3.model.Medicos;

public record DadosMedicosResponse(Long id,
                                   String nome,
                                   String email,
                                   String crm,
                                   Boolean status,
                                   Especialidade especialidade) {

    public DadosMedicosResponse(Medicos medico) {
        this(medico.getId(),medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getStatus(),medico.getEspecialidade());
    }

}
