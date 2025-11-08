package com.example.gerenciador_tarefas.service;


import com.example.gerenciador_tarefas.dto.request.FeriasRequestDTO;
import com.example.gerenciador_tarefas.dto.response.FeriasResponse;
import com.example.gerenciador_tarefas.entity.Ferias;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeriasService {
    private final UsuarioRepository usuarioRepository;
    private final TarefaService tarefaService;
    private final TaskScheduler taskScheduler;

    @Transactional
    public FeriasResponse criarFerias(FeriasRequestDTO request) {
        log.info("Iniciando criacao de ferias, verificando usuario da requisicao...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        log.info("Usuario verificado, mapeando Ferias...");
        Ferias ferias = request.toEntity();

        log.info("Ferias mapeada, atribuindo ao Usuario e persistindo no DB...");
        usuario.setFerias(ferias);
        usuarioRepository.save(usuario);

        log.info("Usuario persistido, agendando transferencia de tarefas...");
        agendarTrasferencia(request.idUsuarioReceptor(), usuario.getIdUsuario(), ferias.getDataInicio(), "ferias");
        agendarTrasferencia(usuario.getIdUsuario(), request.idUsuarioReceptor(), ferias.getDataFim(), "fim das ferias");

        log.info("Tarefas agendadas, finalizando metodo");
        return FeriasResponse.fromEntity(ferias, usuario);
    }

    public List<FeriasResponse> listarTodasFerias() {
        log.info("Iniciando listagem de ferias");
        return usuarioRepository.findAll()
                .stream()
                .filter(usuario -> usuario.getFerias() != null) // pega só quem tem férias cadastradas
                .map(usuario -> FeriasResponse.fromEntity(usuario.getFerias(), usuario))
                .collect(Collectors.toList());
    }

    private void agendarTrasferencia(String idUsuariorecebe, String idUsuarioenvia, LocalDateTime dataFerias, String motivo) {
        log.info("Iniciando execucao agendada da transferencia de tarefa...");
        Runnable taskDevolucao = () -> {
            tarefaService.transferirTarefa(idUsuariorecebe, idUsuarioenvia, motivo);
        };

        log.info("Agendando devolucao das tarefas...");
        Instant instanteFerias = dataFerias.atZone(ZoneId.of("America/Sao_Paulo")).toInstant();
        taskScheduler.schedule(taskDevolucao, instanteFerias);

        log.info("Tarefa de devolucao agendada");
    }


}
