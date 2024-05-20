package com.meuscursos.apirestspring3.services;


import com.meuscursos.apirestspring3.dto.usuarios.*;
import com.meuscursos.apirestspring3.enums.Roles;
import com.meuscursos.apirestspring3.infra.exceptions.IncorrectPasswordException;
import com.meuscursos.apirestspring3.infra.exceptions.ValidacaoException;
import com.meuscursos.apirestspring3.model.Usuarios;
import com.meuscursos.apirestspring3.repository.UsuariosRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;


    @Transactional
    public DadosUsuarioResponseDetails cadastrar(DadosCadastroUsuario dadosUser) {
        haveEmail(dadosUser, 0L);

        String senhaEncriptada = passwordEncoder.encode(dadosUser.password());

        dadosUser = new DadosCadastroUsuario(dadosUser.nome(), dadosUser.email(), senhaEncriptada, dadosUser.telefone(), dadosUser.cpf(), dadosUser.endereco());

        var usuariosCriado = new Usuarios(dadosUser);

        usuariosCriado.addAuthority(Roles.ROLE_USER);

        usuariosRepository.save(usuariosCriado);

        return new DadosUsuarioResponseDetails(usuariosCriado);


    }


    @Transactional
    public DadosUsuarioResponseDetails cadastrarUsuarioMaster(DadosCadastroUsuarioMaster dadosUser) {
        haveEmailMaster(dadosUser, 0L);

        if (!"romatecubanos".equals(dadosUser.palavraSecreta())) {
            throw new ValidacaoException("Acesso não autorizado - Você não tem a palavra correta para esta requisição...");
        }

        String senhaEncriptada = passwordEncoder.encode(dadosUser.password());

        var dadosUserComSenha = new DadosCadastroUsuarioMaster(
                dadosUser.nome(),
                dadosUser.email(),
                senhaEncriptada,
                dadosUser.telefone(),
                dadosUser.cpf(),
                dadosUser.endereco(),
                dadosUser.palavraSecreta()
        );

        var usuariosCriadoMaster = new Usuarios(dadosUserComSenha);

        usuariosCriadoMaster.addAuthority(Roles.ROLE_MASTER);

        usuariosRepository.save(usuariosCriadoMaster);

        return new DadosUsuarioResponseDetails(usuariosCriadoMaster);

    }


    public DadosUsuarioResponse getUserById(Long id) {
        var user = usuariosRepository.getReferenceById(id);
        return new DadosUsuarioResponse(user);
    }

    public List<DadosUsuarioResponseDetails> getAllUsers() {
        return usuariosRepository.findAll().stream()
                .map(DadosUsuarioResponseDetails::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public DadosUsuarioResponseDetails updateUser(Long id, DadosAtualizarUsuario dadosAtualizarUsuario) {
        var userToUpdate = usuariosRepository.getReferenceById(id);
        userToUpdate.atualizar(dadosAtualizarUsuario);
        return new DadosUsuarioResponseDetails(userToUpdate);
    }

    @Transactional
    public DadosUsuarioResponseDetails updateUserContext(DadosAtualizarUsuario dadosAtualizarUsuario) {
        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var email = userDetails.getUsername();
        var userToUpdate = getUsuarioByEmail(email);
        userToUpdate.atualizar(dadosAtualizarUsuario);
        return new DadosUsuarioResponseDetails(userToUpdate);
    }

    @Transactional
    public DadosUsuarioResponseDetails updatePassUser(DadosAtualizarSenhaUsuario dados) {
        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var email = userDetails.getUsername();
        var userToUpdate = getUsuarioByEmail(email);

        if (!passwordEncoder.matches(dados.senhaAtual(), userToUpdate.getPassword())) {
            throw new IncorrectPasswordException("Current password is incorrect");
        }

        String senha = passwordEncoder.encode(dados.password());
        userToUpdate.atualizarPass(senha);

        return new DadosUsuarioResponseDetails(userToUpdate);
    }

    @Transactional
    public void deleteUser(Long id) {
        var userToDelete = usuariosRepository.getReferenceById(id);
        userToDelete.deleteUser(id);
    }

    public void haveEmail(DadosCadastroUsuario usuarioRequest, Long id){
        List<DadosUsuarioResponseDetails> listaUsuarioResponse = getAllUsers();

        for (DadosUsuarioResponseDetails usuarioResponse : listaUsuarioResponse){
            if(usuarioResponse.email().equals(usuarioRequest.email()) && usuarioResponse.id() != id){
                throw new ValidacaoException("E-mail já cadastrado!");
            }
        }
    }

    public void haveEmailMaster(DadosCadastroUsuarioMaster dados, Long id){
        List<DadosUsuarioResponseDetails> listaUsuarioResponse = getAllUsers();

        for (DadosUsuarioResponseDetails usuarioResponse : listaUsuarioResponse){
            if(usuarioResponse.email().equals(dados.email()) && usuarioResponse.id() != id){
                throw new ValidacaoException("E-mail já cadastrado!");
            }
        }
    }

    public Usuarios getUsuarioByEmail(String email) {
        Optional<UserDetails> userDetails = usuariosRepository.findByEmail(email);
        if (userDetails.isPresent() && userDetails.get() instanceof Usuarios) {
            return (Usuarios) userDetails.get();
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado com o email: " + email);
        }
    }
//    public List<DadosUsuarioResponseDetails> getAllUsers(){
//        List<Usuarios> usuarios = usuariosRepository.findAll();
//
//        return usuarios
//                .stream()
//                .map(u -> mapper.map(usuarios, DadosUsuarioResponseDetails.class))
//                .collect(Collectors.toList());
//    }

//
//    @Transactional
//    public Usuarios cadastrarUsuario(DadosCadastroUsuario dados) {
//        // Verifica se o usuário já existe
//        if (usuariosRepository.findByEmail(dados.getEmail()).isPresent()) {
//            throw new IllegalArgumentException("Já existe um usuário com este e-mail");
//        }
//
//        // Cria o novo usuário com ROLE_USER por padrão
//        Usuarios novoUsuario = new Usuarios(dados);
//        novoUsuario.addAuthority(Role.ROLE_USER);
//
//        // Codifica a senha antes de salvar
//        String senhaCodificada = passwordEncoder.encode(dados.getPassword());
//        novoUsuario.atualizarPass(senhaCodificada);
//
//        return usuariosRepository.save(novoUsuario);
//    }

//    @Transactional
//    public Usuarios cadastrarUsuarioMaster(DadosCadastroUsuario dados) {
//        // Verifica se o usuário já existe
//        if (usuariosRepository.findByEmail(dados.getEmail()).isPresent()) {
//            throw new IllegalArgumentException("Já existe um usuário com este e-mail");
//        }
//
//        // Cria o novo usuário com ROLE_MASTER
//        Usuarios novoUsuario = new Usuarios(dados);
//        novoUsuario.addAuthority(Role.ROLE_MASTER);
//
//        // Codifica a senha antes de salvar
//        String senhaCodificada = passwordEncoder.encode(dados.getPassword());
//        novoUsuario.atualizarPass(senhaCodificada);
//
//        return usuariosRepository.save(novoUsuario);
//    }


}


