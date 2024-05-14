package com.meuscursos.apirestspring3.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.meuscursos.apirestspring3.model.Usuarios;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret.key}")
    private String secret;

    public String tokenCreate(Usuarios usuarios){
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return  JWT.create()
                    .withIssuer("API vollmed")
                    .withSubject(usuarios.getEmail())
                    .withClaim("id", usuarios.getId())
                    .withExpiresAt(instantExpired())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw  new RuntimeException("Erro ao gerar Token",exception);
        }
    }


    public String tokenCheck(String tokenJWT) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API vollmed")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            throw  new RuntimeException("Token inválido ou expirado.",exception);
        }
    }

    private Instant instantExpired() {
        return LocalDateTime.now().plusMinutes(60).toInstant(ZoneOffset.of("-03:00"));
    }


}
