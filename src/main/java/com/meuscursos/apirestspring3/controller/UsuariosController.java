package com.meuscursos.apirestspring3.controller;


import com.meuscursos.apirestspring3.dto.usuarios.*;
import com.meuscursos.apirestspring3.infra.exceptions.EmailMismatchException;
import com.meuscursos.apirestspring3.infra.exceptions.IncorrectPasswordException;
import com.meuscursos.apirestspring3.infra.exceptions.ValidacaoException;
import com.meuscursos.apirestspring3.infra.security.ConfigSecurity;
import com.meuscursos.apirestspring3.model.Usuarios;
import com.meuscursos.apirestspring3.repository.UsuariosRepository;
import com.meuscursos.apirestspring3.services.UsuariosService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuariosController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuariosService usuariosService;


    //utilizando camada service
    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity<DadosUsuarioResponseDetails> cadastrar(@RequestBody @Valid DadosCadastroUsuario dadosUser, UriComponentsBuilder uriBuilder) {
        var usuariosCriadoService = usuariosService.cadastrar(dadosUser);

        var uri = uriBuilder.path("/usuarios/cadastro/{id}").buildAndExpand(usuariosCriadoService.id()).toUri();

        return ResponseEntity.created(uri).body(usuariosCriadoService);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosUsuarioResponse> getUser(@PathVariable Long id) {
        var userRecuperado = usuariosService.getUserById(id);
        return ResponseEntity.ok(userRecuperado);
    }

    @GetMapping
    public ResponseEntity<List<DadosUsuarioResponseDetails>> getUsersList() {
        var listUsers = usuariosService.getAllUsers();
        return ResponseEntity.ok(listUsers);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosUsuarioResponseDetails> updateUser(@PathVariable Long id, @RequestBody DadosAtualizarUsuario dadosAtualizarUsuario) {
        var updatedUser = usuariosService.updateUser(id, dadosAtualizarUsuario);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<DadosUsuarioResponseDetails> updateUserContext(@RequestBody DadosAtualizarUsuario dadosAtualizarUsuario) {
        var updatedUser = usuariosService.updateUserContext(dadosAtualizarUsuario);
        return ResponseEntity.ok(updatedUser);
    }


    @PutMapping("/password")
    @Transactional
    public ResponseEntity<DadosUsuarioResponseDetails> updatePassUser(@RequestBody DadosAtualizarSenhaUsuario dados) {
        var updatedUser = usuariosService.updatePassUser(dados);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasAuthority('ROLE_MASTER')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usuariosService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cadastro/usermaster")
    public ResponseEntity<DadosUsuarioResponseDetails> cadastrarUsuarioMaster(@RequestBody DadosCadastroUsuarioMaster dados, UriComponentsBuilder uriBuilder) {

        var usuariosCriadoMasterService = usuariosService.cadastrarUsuarioMaster(dados);

        var uri = uriBuilder.path("/usuarios/cadastro/{id}").buildAndExpand(usuariosCriadoMasterService.id()).toUri();

        return ResponseEntity.ok(usuariosCriadoMasterService);
    }


//    @PostMapping("/cadastro")
//    @Transactional
//    public ResponseEntity<DadosUsuarioResponseDetails> cadastrar(@RequestBody @Valid DadosCadastroUsuario dadosUser, UriComponentsBuilder uriBuilder) {
//        String senhaEncriptada = passwordEncoder.encode(dadosUser.password());
//
//        dadosUser = new DadosCadastroUsuario(dadosUser.nome(), dadosUser.email(), senhaEncriptada, dadosUser.telefone(), dadosUser.cpf(), dadosUser.endereco());
//
//        var usuariosCriado = new Usuarios(dadosUser);
//
//        usuariosRepository.save(usuariosCriado);
//
//        var uri = uriBuilder.path("/usuarios/cadastro/{id}").buildAndExpand(usuariosCriado.getId()).toUri();
//
//        return ResponseEntity.created(uri).body(new DadosUsuarioResponseDetails(usuariosCriado));
//
//
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<DadosUsuarioResponse> getUser(@PathVariable Long id) {
//        var userRecuperado = usuariosRepository.getReferenceById(id);
//        return ResponseEntity.ok(new DadosUsuarioResponse(userRecuperado));
//    }


//    @GetMapping
//    public ResponseEntity<List<DadosUsuarioResponse>> getUsersList(){
//        var listUsers = usuariosRepository.findAll().stream().map(DadosUsuarioResponse::new).toList();
//
//        return ResponseEntity.ok(listUsers);
//    }


//    @PutMapping("/{id}")
//    @Transactional
//    public ResponseEntity<DadosUsuarioResponseDetails> updateUser(@PathVariable Long id,@RequestBody DadosAtualizarUsuario dadosAtualizarUsuario) {
//        var userToUpdate = usuariosRepository.getReferenceById(id);
//
//        userToUpdate.atualizar(dadosAtualizarUsuario);
//
//        return ResponseEntity.ok(new DadosUsuarioResponseDetails(userToUpdate));
//
//    }

//    @PutMapping("/update")
//    @Transactional
//    public ResponseEntity<DadosUsuarioResponseDetails> updateUserContext(@RequestBody DadosAtualizarUsuario dadosAtualizarUsuario) {
//        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        var email = userDetails.getUsername();
//        Usuarios userToUpdate = usuariosService.getUsuarioByEmail(email);
//
//        userToUpdate.atualizar(dadosAtualizarUsuario);
//
//        return ResponseEntity.ok(new DadosUsuarioResponseDetails(userToUpdate));
//
//    }

//    @PutMapping("/password")
//    @Transactional
//    public ResponseEntity<DadosUsuarioResponseDetails> updatePassUser(@RequestBody DadosAtualizarSenhaUsuario dados) {
//
//        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        var email = userDetails.getUsername();
//        Usuarios userToUpdate = usuariosService.getUsuarioByEmail(email);
//
//        if (!passwordEncoder.matches(dados.senhaAtual(), userToUpdate.getPassword())) {
//            throw new IncorrectPasswordException("Current password is incorrect");
//        }
//
//        String senha =  passwordEncoder.encode(dados.password());
//
//        userToUpdate.atualizarPass(senha);
//
//        return ResponseEntity.ok(new DadosUsuarioResponseDetails(userToUpdate));
//
//    }

//    @DeleteMapping("/{id}")
//    @Transactional
//    public ResponseEntity deleteUser(@PathVariable Long id){
//        var userToDelete = usuariosRepository.getReferenceById(id);
//
//        userToDelete.deleteUser(id);
//
//        return ResponseEntity.noContent().build();
//
//    }
}
