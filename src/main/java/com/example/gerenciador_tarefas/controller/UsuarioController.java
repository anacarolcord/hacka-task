package com.example.gerenciador_tarefas.controller;

import com.example.gerenciador_tarefas.dto.request.CriarColaboradorRequest;
import com.example.gerenciador_tarefas.dto.request.UsuarioGestorRequestDTO;
import com.example.gerenciador_tarefas.dto.request.UsuarioRequestDTO;
import com.example.gerenciador_tarefas.dto.response.UsuarioGestorResponseDTO;
import com.example.gerenciador_tarefas.dto.response.UsuarioResponseDTO;
import com.example.gerenciador_tarefas.entity.enums.Cargo;
import com.example.gerenciador_tarefas.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/gestor")
    public ResponseEntity<UsuarioResponseDTO> cadastrarColaborador(@RequestBody @Valid CriarColaboradorRequest request){
        UsuarioResponseDTO response = usuarioService.criarColaborador(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(){

        List<UsuarioResponseDTO> responseDTO = usuarioService.listarUsuario();
        return ResponseEntity.ok(responseDTO);

    }

    @GetMapping("/pesquisa")
    public ResponseEntity<Object> pesquisaUsuarios(@RequestParam(required = false)String idUsuario,
                                                                     @RequestParam(required = false)String nome,
                                                                     @RequestParam(required = false)Cargo cargo,
                                                                     @RequestParam(required = false)String email,
                                                                     @RequestParam(required = false)Boolean ativo){

        Object responseDTO = usuarioService.pesquisaUsuarios(idUsuario, nome, cargo, email, ativo);
        return ResponseEntity.ok(responseDTO);

    }

    @DeleteMapping("/delete/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> deletarUsuario(@PathVariable String idUsuario){

        UsuarioResponseDTO responseDTO = usuarioService.deletarUsuario(idUsuario);
        return ResponseEntity.ok(responseDTO);

    }

    @PostMapping("/admin")
    public ResponseEntity <UsuarioGestorResponseDTO> criarGestor(@RequestBody UsuarioGestorRequestDTO dto){
        UsuarioGestorResponseDTO usuariogestor=usuarioService.criarGestor(dto);
        return ResponseEntity.status(201).body(usuariogestor);
    }



    @PatchMapping("/atualizar/senha/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> atualizarSenha(@PathVariable("idUsuario") String idUsuario, @RequestBody UsuarioRequestDTO request){

        UsuarioResponseDTO responseDTO = usuarioService.atualizarSenha(idUsuario, request);
        return ResponseEntity.ok(responseDTO);

    }

    @PatchMapping("/atualizar/cargo/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> atualizarCargo(@PathVariable("idUsuario") String idUsuario, @RequestBody UsuarioRequestDTO request){

        UsuarioResponseDTO responseDTO = usuarioService.atualizarCargo(idUsuario, request);
        return ResponseEntity.ok(responseDTO);

    }
}
