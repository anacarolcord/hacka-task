package com.example.gerenciador_tarefas.service;


import com.example.gerenciador_tarefas.dto.request.FeriasRequestDTO;
import com.example.gerenciador_tarefas.dto.response.FeriasResponse;
import com.example.gerenciador_tarefas.entity.Ferias;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class FeriasService {
    private final UsuarioRepository usuarioRepository;
    private final TarefaService tarefaService;
    private final TaskScheduler taskScheduler;

    @Transactional
    public FeriasResponse criarFerias(FeriasRequestDTO request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        Ferias ferias = request.toEntity();
        usuario.setFerias(ferias);
        usuarioRepository.save(usuario);
        agendarTrasferencia(request.idUsuarioReceptor(), usuario.getIdUsuario(), ferias.getDataInicio(), "ferias");
        agendarTrasferencia(usuario.getIdUsuario(), request.idUsuarioReceptor(), ferias.getDataFim(), "fim das ferias");


        return FeriasResponse.fromEntity(ferias, usuario);
    }



    private void agendarTrasferencia(String idUsuariorecebe, String idUsuarioenvia, LocalDateTime dataFerias, String motivo){

        Runnable taskDevolucao = () -> {
            tarefaService.transferirTarefa(idUsuariorecebe, idUsuarioenvia, motivo);
        };
        Instant instanteFerias = dataFerias.atZone(ZoneId.of("America/Sao_Paulo")).toInstant();

        taskScheduler.schedule(taskDevolucao, instanteFerias);

    }


}
