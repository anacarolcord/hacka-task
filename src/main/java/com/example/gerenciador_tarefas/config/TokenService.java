package com.example.gerenciador_tarefas.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.gerenciador_tarefas.entity.Usuario;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service

public class TokenService {
    private String secret= "segredo";

    public String gerarToken(Usuario user){
        try {
            Algorithm algorithm=Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("gerenciador-tarefas")
                    .withSubject(user.getCpf())
                    .withExpiresAt(gerarDataExpiracao())
                    .sign(algorithm);
        } catch (UnsupportedEncodingException ex){
            return "";
        }
    }

    private Date gerarDataExpiracao(){
        Instant instant= LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
                return Date.from(instant);
    }

    public String validarToken(String token){
        try {
            Algorithm algorithm=Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("gerenciador-tarefas")
                    .build()
                    .verify(token)
                    .getSubject();


        }catch (UnsupportedEncodingException ex){
            return "";
        }
    }

}
