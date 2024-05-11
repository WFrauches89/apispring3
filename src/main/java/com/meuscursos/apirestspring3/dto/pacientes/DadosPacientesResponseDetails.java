package com.meuscursos.apirestspring3.dto.pacientes;

import com.meuscursos.apirestspring3.model.Endereco;
import com.meuscursos.apirestspring3.model.Pacientes;

public record DadosPacientesResponseDetails(Long id,
                                            String nome,
                                            String email,
                                            String telefone,
                                            String cpf,
                                            Endereco endereco) {

    public DadosPacientesResponseDetails(Pacientes pacientes) {

        this(pacientes.getId(),pacientes.getNome(),pacientes.getCpf(), pacientes.getEmail(), pacientes.getTelefone(), pacientes.getEndereco());

    }

}
