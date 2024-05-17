package com.meuscursos.apirestspring3.controller;


import com.meuscursos.apirestspring3.dto.usuarios.DadosAtualizarUsuario;
import com.meuscursos.apirestspring3.dto.usuarios.DadosCadastroUsuario;
import com.meuscursos.apirestspring3.dto.usuarios.DadosUsuarioResponse;
import com.meuscursos.apirestspring3.dto.usuarios.DadosUsuarioResponseDetails;
import com.meuscursos.apirestspring3.infra.security.ConfigSecurity;
import com.meuscursos.apirestspring3.model.Usuarios;
import com.meuscursos.apirestspring3.repository.UsuariosRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuariosController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/cadastro")
    public ResponseEntity<DadosUsuarioResponseDetails> cadastrar(@RequestBody @Valid DadosCadastroUsuario dadosUser, UriComponentsBuilder uriBuilder) {
        String senhaEncriptada = passwordEncoder.encode(dadosUser.password());

        dadosUser = new DadosCadastroUsuario(dadosUser.nome(), dadosUser.email(), senhaEncriptada, dadosUser.telefone(), dadosUser.cpf(), dadosUser.endereco());

        var usuariosCriado = new Usuarios(dadosUser);

        usuariosRepository.save(usuariosCriado);

        var uri = uriBuilder.path("/usuarios/cadastro/{id}").buildAndExpand(usuariosCriado.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosUsuarioResponseDetails(usuariosCriado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosUsuarioResponse> getUser(@PathVariable Long id) {
        var userRecuperado = usuariosRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosUsuarioResponse(userRecuperado));
    }

    @GetMapping
    public ResponseEntity<List<DadosUsuarioResponse>> getUsersList(){
        var listUsers = usuariosRepository.findAll().stream().map(DadosUsuarioResponse::new).toList();

        return ResponseEntity.ok(listUsers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosUsuarioResponseDetails> updateUser(@PathVariable Long id,@RequestBody DadosAtualizarUsuario dadosAtualizarUsuario) {
        var userToUpdate = usuariosRepository.getReferenceById(id);

        userToUpdate.atualizar(dadosAtualizarUsuario);

        return ResponseEntity.ok(new DadosUsuarioResponseDetails(userToUpdate));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        var userToDelete = usuariosRepository.getReferenceById(id);

        userToDelete.deleteUser(id);

        return ResponseEntity.noContent().build();

    }

}
