package com.example.gerenciador_tarefas.controller;

import com.example.gerenciador_tarefas.dto.request.CriarTarefaRequest;
import com.example.gerenciador_tarefas.dto.request.TarefaRequestDto;
import com.example.gerenciador_tarefas.dto.response.CriarTarefaResponse;
import com.example.gerenciador_tarefas.dto.response.HistoricoUsuarioDto;
import com.example.gerenciador_tarefas.dto.response.TarefaResponseDto;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.exception.UserNotFoundException;
import com.example.gerenciador_tarefas.service.TarefaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor

public class TarefaController {

    private final TarefaService service;


    @PostMapping
    public ResponseEntity<CriarTarefaResponse> criarTarefa(@RequestBody CriarTarefaRequest dto) {
        CriarTarefaResponse tarefa= service.salvarTarefa(dto);
        return ResponseEntity.status(201).body(tarefa);
    }

    @GetMapping("/gestores")
    public ResponseEntity listarTarefasGestor(){
        List<TarefaResponseDto>  tarefas =service.listarTodasGestor();
        return ResponseEntity.status(200).body(tarefas);
    }
    @GetMapping("/usuarios")
    public ResponseEntity listarTarefasUsuarios(){
        List<TarefaResponseDto>  tarefas =service.listarTodasUsuario();
        return ResponseEntity.status(200).body(tarefas);
    }

    @PatchMapping("/atualizar/gestor/{id}")
    public ResponseEntity atualizarTarefa(@RequestBody TarefaRequestDto dto, @PathVariable("id") String id){
        TarefaResponseDto tarefaAtualizada = service.atualizarTarefaGestor(dto, id);
        return ResponseEntity.status(200).body(tarefaAtualizada);
    }

    @PatchMapping("/atualizar/colaborador/{id}")
    public ResponseEntity atualizarTarefaColaborador(@RequestBody TarefaRequestDto dto, @PathVariable("id") String id ){
        TarefaResponseDto tarefaAtualizada = service.atualizarTarefaColaborador(dto, id);
        return ResponseEntity.status(200).body(tarefaAtualizada);
    }

    @GetMapping("/listar/usuario/{id}")
    public ResponseEntity listarTarefasPorUsuario( @PathVariable("id") String id){
        List<TarefaResponseDto>  tarefas =service.listarTodasPeloIdUsuario( id);
        return ResponseEntity.status(200).body(tarefas);
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<TarefaResponseDto>> listarTarefasPendentesEmAndamento() {
        List<TarefaResponseDto> tarefas = service.listarTarefasPendentesEmAndamento();
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("/historico/{usuarioId}")
    public ResponseEntity<HistoricoUsuarioDto> gerarHistorico(@PathVariable String usuarioId) {
        try {
            HistoricoUsuarioDto historico = service.gerarHistoricoPorUsuario(usuarioId);
            return ResponseEntity.ok(historico);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}



