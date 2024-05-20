package com.meuscursos.apirestspring3.dto.usuarios;

import com.meuscursos.apirestspring3.model.Endereco;
import com.meuscursos.apirestspring3.model.Usuarios;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record DadosUsuarioResponseDetails (Long id,
                                           String password,
                                           String nome,
                                           String email,
                                           String telefone,
                                           String cpf,
                                           Endereco endereco) {


    public DadosUsuarioResponseDetails(Usuarios usuarios) {

        this(usuarios.getId(), usuarios.getPassword(), usuarios.getNome(), usuarios.getCpf(), usuarios.getEmail(), usuarios.getTelefone(), usuarios.getEndereco());

    }


}