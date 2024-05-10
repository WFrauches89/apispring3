package com.meuscursos.apirestspring3.dto.medico;

import com.meuscursos.apirestspring3.dto.enderecos.EnderecoDTO;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizarMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        EnderecoDTO endereco) {


}
