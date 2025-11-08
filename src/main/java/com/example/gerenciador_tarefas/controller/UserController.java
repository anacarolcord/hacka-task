package com.example.gerenciador_tarefas.controller;

import com.example.gerenciador_tarefas.dto.request.UsuarioGestorRequestDTO;
import com.example.gerenciador_tarefas.dto.request.UsuarioRequestDTO;
import com.example.gerenciador_tarefas.dto.response.UsuarioGestorResponseDTO;
import com.example.gerenciador_tarefas.entity.Usuario;
import com.example.gerenciador_tarefas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UsuarioService usuarioService;


    @PostMapping("/criar-gestor")
    public ResponseEntity <UsuarioGestorResponseDTO> criarGestor(@RequestBody UsuarioGestorRequestDTO dto){
        UsuarioGestorResponseDTO usariogestor=usuarioService.criarAdm(dto);
        return ResponseEntity.status(201).body(usariogestor);

    }




}
