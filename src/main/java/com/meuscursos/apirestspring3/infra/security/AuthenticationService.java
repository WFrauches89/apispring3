package com.meuscursos.apirestspring3.infra.security;


import com.meuscursos.apirestspring3.repository.AuthenticationUsuariosRepository;
import com.meuscursos.apirestspring3.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private AuthenticationUsuariosRepository usuariosRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuariosRepository.findByEmail(username);
    }

}
