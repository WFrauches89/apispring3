package com.meuscursos.apirestspring3.dto.pacientes;

import com.meuscursos.apirestspring3.model.Pacientes;

public record DadosPacientesResponse (String nome,
                                      String email,
                                      String Cpf) {

    public DadosPacientesResponse (Pacientes pacientes) {

        this(pacientes.getNome(),pacientes.getCpf(), pacientes.getEmail());

    }

}
