package com.example.gerenciador_tarefas.service;

import com.example.gerenciador_tarefas.dto.request.ComentarioRequest;
import com.example.gerenciador_tarefas.dto.response.ComentarioResponse;
import com.example.gerenciador_tarefas.entity.Comentario;
import com.example.gerenciador_tarefas.entity.Tarefa;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioService {
    TarefaRepository tarefaRepository;

    public ComentarioResponse criarComentario(ComentarioRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();
        Comentario comentario = request.toEntity(usuario.getNome());
        Tarefa tarefa = tarefaRepository.findById(request.tarefaId()).get();

        tarefaRepository.save(tarefa);
        return ComentarioResponse.fromEntity(comentario);
    }

    public List<Comentario> listarComentariosPorTarefa(String idTarefa){
        List<Comentario> comentarios = tarefaRepository.findById(idTarefa).get().getComentarios();
        return comentarios;
    }

}
