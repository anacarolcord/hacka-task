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


    @PostMapping("/criar-tarefa")
    public ResponseEntity <TarefaResponseDto> criarTarefa(@RequestBody TarefaRequestDto dto) {
        TarefaResponseDto tarefa= service.salvarTarefa(dto);
        return ResponseEntity.status(201).body(tarefa);
    }

    @GetMapping("/listar-tarefas")
    public ResponseEntity listartarefas(Usuario usuario){
        List<TarefaResponseDto>  tarefas =service.listarTodasGestor(usuario);
        return ResponseEntity.status(200).body(tarefas);
    }
}

