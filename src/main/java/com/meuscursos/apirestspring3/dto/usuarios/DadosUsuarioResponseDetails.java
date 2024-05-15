package com.meuscursos.apirestspring3.dto.usuarios;

import com.meuscursos.apirestspring3.model.Endereco;
import com.meuscursos.apirestspring3.model.Usuarios;
import com.meuscursos.apirestspring3.repository.UsuariosRepository;

public record DadosUsuarioResponseDetails (Long id,
                                           String nome,
                                           String email,
                                           String telefone,
                                           String cpf,
                                           Endereco endereco) {

    public DadosUsuarioResponseDetails(Usuarios usuarios) {

        this(usuarios.getId(), usuarios.getNome(), usuarios.getCpf(), usuarios.getEmail(), usuarios.getTelefone(), usuarios.getEndereco());

    }
}