package com.meuscursos.apirestspring3.dto.usuarios;

import com.meuscursos.apirestspring3.dto.enderecos.EnderecoDTO;
import com.meuscursos.apirestspring3.model.Usuarios;

public record DadosAtualizarUsuario(

        String nome,
        String telefone,
        EnderecoDTO endereco) {


}
