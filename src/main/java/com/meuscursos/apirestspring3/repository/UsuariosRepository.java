package com.meuscursos.apirestspring3.repository;

import com.meuscursos.apirestspring3.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {

    UserDetails findByEmail(String username);
}
