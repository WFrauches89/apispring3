package com.meuscursos.apirestspring3.dto.pacientes;

import com.meuscursos.apirestspring3.dto.enderecos.EnderecoDTO;

public record DadosAtualizarPaciente(

        String nome,
        String telefone,
        EnderecoDTO endereco) {


}
