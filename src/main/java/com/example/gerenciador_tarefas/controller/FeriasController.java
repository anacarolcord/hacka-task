package com.example.gerenciador_tarefas.controller;

import com.example.gerenciador_tarefas.dto.request.FeriasRequestDTO;
import com.example.gerenciador_tarefas.dto.response.FeriasResponse;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.service.FeriasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ferias")
@RequiredArgsConstructor
public class FeriasController {

    private final FeriasService feriasService;

    @PostMapping
    public ResponseEntity<FeriasResponse> criarFerias(@RequestBody FeriasRequestDTO request){
        FeriasResponse response = feriasService.criarFerias(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
