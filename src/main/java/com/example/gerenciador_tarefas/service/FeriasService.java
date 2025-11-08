package com.example.gerenciador_tarefas.service;


import com.example.gerenciador_tarefas.dto.request.FeriasRequestDTO;
import com.example.gerenciador_tarefas.dto.response.FeriasResponse;
import com.example.gerenciador_tarefas.entity.Ferias;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeriasService {
    private final UsuarioRepository usuarioRepository;

    public FeriasResponse criarFerias(FeriasRequestDTO request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        Ferias ferias = request.toEntity();
        usuario.setFerias(ferias);
        usuarioRepository.save(usuario);
        return FeriasResponse.fromEntity(ferias, usuario);
    }



}
