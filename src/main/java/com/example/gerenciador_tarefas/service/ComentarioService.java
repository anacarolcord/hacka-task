package com.example.gerenciador_tarefas.service;

import com.example.gerenciador_tarefas.dto.request.ComentarioRequest;
import com.example.gerenciador_tarefas.dto.response.ComentarioResponse;
import com.example.gerenciador_tarefas.entity.Comentario;
import com.example.gerenciador_tarefas.entity.Tarefa;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComentarioService {
    TarefaRepository tarefaRepository;

    public ComentarioResponse criarComentario(ComentarioRequest request){
        log.info("Iniciando criacao de comentario, verificando usuario da requisicao...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        log.info("Usuario da requisicao verificado, mapeando comentario...");
        Comentario comentario = request.toEntity(usuario.getNome());

        log.info("Comentario mapeado, buscando tarefa no DB...");
        Tarefa tarefa = tarefaRepository.findById(request.tarefaId()).get();

        log.info("Tarefa encontrada, persistindo no DB...");
        tarefaRepository.save(tarefa);

        log.info("Comentario criado, finalizando metodo");
        return ComentarioResponse.fromEntity(comentario);
    }

    public List<Comentario> listarComentariosPorTarefa(String idTarefa){
        log.info("Iniciando listagem de comentarios da tarefa {}, buscando comentarios no DB...", idTarefa);
        List<Comentario> comentarios = tarefaRepository.findById(idTarefa).get().getComentarios();

        log.info("Comentarios encontrados, finalizando metodo");
        return comentarios;
    }

}
