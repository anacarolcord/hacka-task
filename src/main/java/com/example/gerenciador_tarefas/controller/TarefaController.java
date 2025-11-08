package com.example.gerenciador_tarefas.controller;

import com.example.gerenciador_tarefas.dto.response.TarefaResponseDto;
import com.example.gerenciador_tarefas.service.TarefaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor

public class TarefaController {

    private final TarefaService service;

    @GetMapping("/ativas")
    public ResponseEntity<List<TarefaResponseDto>> listarTarefasPendentesEmAndamento() {
        List<TarefaResponseDto> tarefas = service.listarTarefasPendentesEmAndamento();
        return ResponseEntity.ok(tarefas);
    }
}

