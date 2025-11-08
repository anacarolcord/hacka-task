package com.example.gerenciador_tarefas.controller;

import com.example.gerenciador_tarefas.dto.request.TarefaRequestDto;
import com.example.gerenciador_tarefas.dto.response.TarefaResponseDto;
import com.example.gerenciador_tarefas.dto.response.UsuarioResponseDTO;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.service.TarefaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor

public class TarefaController {

    private final TarefaService service;


    @PostMapping
    public ResponseEntity <TarefaResponseDto> criarTarefa(@RequestBody TarefaRequestDto dto) {
        TarefaResponseDto tarefa= service.salvarTarefa(dto);
        return ResponseEntity.status(201).body(tarefa);
    }

    @GetMapping("/gestores")
    public ResponseEntity listarTarefasGestor(Usuario usuario){
        List<TarefaResponseDto>  tarefas =service.listarTodasGestor();
        return ResponseEntity.status(200).body(tarefas);
    }
    @GetMapping("/usuarios")
    public ResponseEntity listarTarefasUsuarios(Usuario usuario){
        List<TarefaResponseDto>  tarefas =service.listarTodasUsuario();
        return ResponseEntity.status(200).body(tarefas);
    }

    @PatchMapping("/atulizar/gestor/{id}")
    public ResponseEntity atualizarTarefa(@RequestBody TarefaRequestDto dto, @PathVariable("id") String id, Usuario usuario){
        TarefaResponseDto tarefaAtualizada = service.atualizarTarefaGestor(dto, id);
        return ResponseEntity.status(200).body(tarefaAtualizada);
    }

    @PatchMapping("/atulizar/colaborador/{id}")
    public ResponseEntity atualizarTarefaColaborador(@RequestBody TarefaRequestDto dto, @PathVariable("id") String id, Usuario usuario){
        TarefaResponseDto tarefaAtualizada = service.atualizarTarefaColaborador(dto, id);
        return ResponseEntity.status(200).body(tarefaAtualizada);
    }

    @GetMapping("/listar/usuario/{id}")
    public ResponseEntity listarTarefasPorUsuario(Usuario usuario, @PathVariable("id") String id){
        List<TarefaResponseDto>  tarefas =service.listarTodasPeloIdUsuario(id);
        return ResponseEntity.status(200).body(tarefas);
    }



}

