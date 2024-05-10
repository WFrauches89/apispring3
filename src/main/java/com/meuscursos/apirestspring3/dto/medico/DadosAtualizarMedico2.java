package com.meuscursos.apirestspring3.dto.medico;

import com.meuscursos.apirestspring3.dto.enderecos.EnderecoDTO;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizarMedico2(

        String nome,
        String telefone,
        EnderecoDTO endereco) {


}
