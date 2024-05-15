package com.meuscursos.apirestspring3.dto.usuarios;

import com.meuscursos.apirestspring3.model.Pacientes;
import com.meuscursos.apirestspring3.model.Usuarios;

import java.util.List;

public record DadosUsuarioResponse(Long id,
                                   String nome,
                                   String email) {



    public DadosUsuarioResponse(Usuarios userRecuperado) {
        this(userRecuperado.getId(),userRecuperado.getNome(), userRecuperado.getEmail());
    }


}
