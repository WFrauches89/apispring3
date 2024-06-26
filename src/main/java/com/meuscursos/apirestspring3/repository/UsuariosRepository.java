package com.meuscursos.apirestspring3.repository;

import com.meuscursos.apirestspring3.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {

    Optional<UserDetails> findByEmail(String username);


}
