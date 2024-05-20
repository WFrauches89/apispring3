package com.meuscursos.apirestspring3.dto.usuarios;

import com.meuscursos.apirestspring3.dto.enderecos.EnderecoDTO;

public record DadosAtualizarSenhaUsuario(

        String senhaAtual,
        String password) {

}
