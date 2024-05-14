package com.meuscursos.apirestspring3.controller;


import com.meuscursos.apirestspring3.dto.login.LoginDTO;
import com.meuscursos.apirestspring3.dto.token.TokenJWTDTO;
import com.meuscursos.apirestspring3.infra.security.TokenService;
import com.meuscursos.apirestspring3.model.Usuarios;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid LoginDTO dados) {
        var tokenAuthentication = new UsernamePasswordAuthenticationToken(dados.email(), dados.password());
        var authentication = manager.authenticate(tokenAuthentication);

        var tokenJWT = tokenService.tokenCreate((Usuarios) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenJWTDTO(tokenJWT));
    }

}
