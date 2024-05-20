package com.meuscursos.apirestspring3.model;


import com.meuscursos.apirestspring3.dto.enderecos.EnderecoDTO;
import com.meuscursos.apirestspring3.dto.usuarios.DadosAtualizarSenhaUsuario;
import com.meuscursos.apirestspring3.dto.usuarios.DadosAtualizarUsuario;
import com.meuscursos.apirestspring3.dto.usuarios.DadosCadastroUsuario;
import com.meuscursos.apirestspring3.dto.usuarios.DadosCadastroUsuarioMaster;
import com.meuscursos.apirestspring3.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of= "id")
public class Usuarios implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String email;

    private String password;

    private String nome;

    private String telefone;

    private Boolean status;

    private String cpf;
    @Embedded
    private Endereco endereco;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles = new HashSet<>();

    public Usuarios(DadosCadastroUsuario dadosUser) {
        this.status = true;
        this.nome = dadosUser.nome();
        this.email = dadosUser.email();
        this.password = dadosUser.password();
        this.telefone = dadosUser.telefone();
        this.cpf = dadosUser.cpf();
        this.endereco = new Endereco(dadosUser.endereco());
        this.roles = new HashSet<>();
    }

    public Usuarios(DadosCadastroUsuarioMaster dadosUser) {
        this.status = true;
        this.nome = dadosUser.nome();
        this.email = dadosUser.email();
        this.password = dadosUser.password();
        this.telefone = dadosUser.telefone();
        this.cpf = dadosUser.cpf();
        this.endereco = new Endereco(dadosUser.endereco());
        this.roles = new HashSet<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return   roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addAuthority(Roles authority) {
        roles.add(authority);
    }

    public void removeAuthority(Roles authority) {
        roles.remove(authority);
    }

    public void atualizar(DadosAtualizarUsuario dadosAtualizarUsuario) {

        if(dadosAtualizarUsuario != null){
            if(dadosAtualizarUsuario.nome() != null){
                this.nome = dadosAtualizarUsuario.nome();
            }
            if(dadosAtualizarUsuario.telefone() != null){
                this.telefone = dadosAtualizarUsuario.telefone();
            }
            if(dadosAtualizarUsuario.endereco() != null){
                this.endereco.atualizarEndereco(dadosAtualizarUsuario.endereco());
            }

        }
    }

    public void deleteUser (Long id) {
        this.status = false;
    }

    public void atualizarPass(String pass) {

        if(pass != null){
                this.password = pass;

        }
    }
}
