package com.meuscursos.apirestspring3.dto.pacientes;

import com.meuscursos.apirestspring3.dto.enderecos.EnderecoDTO;

public record DadosCadastroPaciente(String nome,
                                    String email,
                                    String telefone,
                                    String cpf,
                                    EnderecoDTO endereco) {
}

