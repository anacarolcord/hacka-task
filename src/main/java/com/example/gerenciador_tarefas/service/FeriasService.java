package com.example.gerenciador_tarefas.service;


import com.example.gerenciador_tarefas.dto.request.FeriasRequestDTO;
import com.example.gerenciador_tarefas.dto.response.FeriasResponse;
import com.example.gerenciador_tarefas.entity.Ferias;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.exception.UserNotFoundException;
import com.example.gerenciador_tarefas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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


    public FeriasResponse atualizarFerias(FeriasRequestDTO request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        // Garante que o usuário tem férias cadastradas antes de atualizar
        if (usuario.getFerias() == null) {
            throw new UserNotFoundException("Usuário " + usuario.getNome() + " não possui férias cadastradas para atualizar.");
        }

        // Atualiza os campos permitidos
        usuario.getFerias().setDataInicio(request.dataInicio());
        usuario.getFerias().setDataFim(request.dataFim());

        usuarioRepository.save(usuario);
        return FeriasResponse.fromEntity(usuario.getFerias(), usuario);
    }

    public List<FeriasResponse> listarTodasFerias() {
        return usuarioRepository.findAll()
                .stream()
                .filter(usuario -> usuario.getFerias() != null) // pega só quem tem férias cadastradas
                .map(usuario -> FeriasResponse.fromEntity(usuario.getFerias(), usuario))
                .collect(Collectors.toList());
    }
}

