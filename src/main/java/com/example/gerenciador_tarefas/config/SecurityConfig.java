package com.example.gerenciador_tarefas.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->authorize
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/tarefas/atribuir").hasAnyRole("GESTOR", "COLABORADORRESPONSAVEL")
                        .requestMatchers("/tarefas/gestores").hasAnyRole("GESTOR", "COLABORADORRESPONSAVEL")
                        .requestMatchers("/tarefas/criar-tarefa").hasAnyRole("GESTOR", "COLABORADORRESPONSAVEL")
                        .requestMatchers("/tarefas/listar-tarefas").hasAnyRole("GESTOR", "COLABORADORRESPONSAVEL")
                        .requestMatchers("/usuarios/admin").hasRole("ADMIN")
                        .requestMatchers("/usuarios").hasAnyRole("GESTOR", "COLABORADORRESPONSAVEL")
                        .requestMatchers("/usuarios/pesquisa").hasAnyRole("GESTOR", "COLABORADORRESPONSAVEL")
                        .requestMatchers("/usuarios/delete/**").hasAnyRole("GESTOR", "COLABORADORRESPONSAVEL")
                        .requestMatchers("/usuarios/gestor").hasAnyRole("GESTOR", "COLABORADORRESPONSAVEL")
                        .requestMatchers("/atualizar/cargo/**").hasRole("ADMIN")
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
