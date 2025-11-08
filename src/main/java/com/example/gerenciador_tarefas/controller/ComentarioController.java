package com.example.gerenciador_tarefas.controller;

import com.example.gerenciador_tarefas.dto.request.ComentarioRequest;
import com.example.gerenciador_tarefas.dto.response.ComentarioResponse;
import com.example.gerenciador_tarefas.entity.Comentario;
import com.example.gerenciador_tarefas.service.ComentarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioService;

    @PostMapping
    public ResponseEntity<ComentarioResponse> criarComentario(@RequestBody ComentarioRequest request){
        ComentarioResponse response = comentarioService.criarComentario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/tarefa/{idTarefa}")
    public ResponseEntity<List<Comentario>> listarComentariosPorTarefa(@PathVariable ("idTarefa") String idTarefa){
        List<Comentario> comentarios = comentarioService.listarComentariosPorTarefa(idTarefa);
        return ResponseEntity.ok(comentarios);
    }

}
