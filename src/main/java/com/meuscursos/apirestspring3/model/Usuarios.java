package com.meuscursos.apirestspring3.model;


import com.meuscursos.apirestspring3.dto.enderecos.EnderecoDTO;
import com.meuscursos.apirestspring3.dto.usuarios.DadosAtualizarUsuario;
import com.meuscursos.apirestspring3.dto.usuarios.DadosCadastroUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

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

    public Usuarios(DadosCadastroUsuario dadosUser) {
        this.status = true;
        this.nome = dadosUser.nome();
        this.email = dadosUser.email();
        this.password = dadosUser.password();
        this.telefone = dadosUser.telefone();
        this.cpf = dadosUser.cpf();
        this.endereco = new Endereco(dadosUser.endereco());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
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
}
