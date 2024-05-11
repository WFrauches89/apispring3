package com.meuscursos.apirestspring3.dto.medico;

import com.meuscursos.apirestspring3.enums.Especialidade;
import com.meuscursos.apirestspring3.model.Endereco;
import com.meuscursos.apirestspring3.model.Medicos;

import java.net.URI;

public record DadosMedicosResponseDetails(Long id,
                                          String nome,
                                          String email,
                                          String telefone,
                                          String crm,
                                          Especialidade especialidade,
                                          Endereco endereco
                                          ) {


    public DadosMedicosResponseDetails(Medicos medico) {
        this(medico.getId(),medico.getNome(), medico.getEmail(), medico.getTelefone(),medico.getCrm(), medico.getEspecialidade(),  medico.getEndereco());
    }
    public DadosMedicosResponseDetails(Medicos medico, URI uri) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(), medico.getEspecialidade(), medico.getEndereco());

    }


}
